package com.sunrisedental.controller;

import com.sunrisedental.dao.AppointmentDAO;
import com.sunrisedental.dao.AppointmentDAOImpl;
import com.sunrisedental.dao.DentistDAO;
import com.sunrisedental.dao.DentistDAOImpl;
import com.sunrisedental.dao.PatientDAO;
import com.sunrisedental.dao.PatientDAOImpl;
import com.sunrisedental.dao.TreatmentDAO;
import com.sunrisedental.dao.TreatmentDAOImpl;
import com.sunrisedental.dao.TreatmentTypeDAO;
import com.sunrisedental.dao.TreatmentTypeDAOImpl;

import com.sunrisedental.model.Appointment;
import com.sunrisedental.model.Dentist;
import com.sunrisedental.model.Patient;
import com.sunrisedental.model.Treatment;
import com.sunrisedental.model.TreatmentType;

import com.sunrisedental.service.AppointmentService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@WebServlet(urlPatterns = {
        "/appointments",
        "/appointments/all"
})
public class AppointmentController extends HttpServlet {

    private final AppointmentService appointmentService;
    private final AppointmentDAO appointmentDAO;
    private final PatientDAO patientDAO;
    private final DentistDAO dentistDAO;
    private final TreatmentDAO treatmentDAO;
    private final TreatmentTypeDAO treatmentTypeDAO;

    public AppointmentController() {

        try {

            final AppointmentDAO appointmentDAO =
                    new AppointmentDAOImpl();

            this.appointmentDAO =
                    appointmentDAO;

            this.appointmentService =
                    new AppointmentService(
                            appointmentDAO);

            this.patientDAO =
                    new PatientDAOImpl();

            this.dentistDAO =
                    new DentistDAOImpl();

            this.treatmentDAO =
                    new TreatmentDAOImpl();

            this.treatmentTypeDAO =
                    new TreatmentTypeDAOImpl();

        } catch (SQLException exception) {

            throw new IllegalStateException(
                    "Failed to initialize appointment controller",
                    exception);
        }
    }

    /*
     * Kept for older controller unit tests.
     */
    public AppointmentController(
            final AppointmentService appointmentService) {

        this.appointmentService =
                appointmentService;

        this.appointmentDAO = null;
        this.patientDAO = null;
        this.dentistDAO = null;
        this.treatmentDAO = null;
        this.treatmentTypeDAO = null;
    }

    /*
     * Kept for existing tests/code that use the old
     * four-argument constructor.
     */
    public AppointmentController(
            final AppointmentService appointmentService,
            final AppointmentDAO appointmentDAO,
            final PatientDAO patientDAO,
            final DentistDAO dentistDAO) {

        this.appointmentService =
                appointmentService;

        this.appointmentDAO =
                appointmentDAO;

        this.patientDAO =
                patientDAO;

        this.dentistDAO =
                dentistDAO;

        this.treatmentDAO = null;
        this.treatmentTypeDAO = null;
    }

    /*
     * Full constructor for testing the new treatment workflow.
     */
    public AppointmentController(
            final AppointmentService appointmentService,
            final AppointmentDAO appointmentDAO,
            final PatientDAO patientDAO,
            final DentistDAO dentistDAO,
            final TreatmentDAO treatmentDAO,
            final TreatmentTypeDAO treatmentTypeDAO) {

        this.appointmentService =
                appointmentService;

        this.appointmentDAO =
                appointmentDAO;

        this.patientDAO =
                patientDAO;

        this.dentistDAO =
                dentistDAO;

        this.treatmentDAO =
                treatmentDAO;

        this.treatmentTypeDAO =
                treatmentTypeDAO;
    }

    @Override
    protected void doGet(
            final HttpServletRequest request,
            final HttpServletResponse response)
            throws ServletException, IOException {

        if ("/appointments/all".equals(
                request.getServletPath())) {

            showAllAppointments(
                    request,
                    response);

            return;
        }

        final String appointmentNumber =
                request.getParameter(
                        "appointmentNumber");

        final String appointmentDateValue =
                request.getParameter(
                        "appointmentDate");

        try {

            loadDropdownData(
                    request);

            if (appointmentNumber != null
                    && !appointmentNumber.isBlank()
                    && appointmentDateValue != null
                    && !appointmentDateValue.isBlank()) {

                try {

                    final LocalDate appointmentDate =
                            LocalDate.parse(
                                    appointmentDateValue);

                    final Optional<Appointment> appointment =
                            appointmentService
                                    .searchAppointment(
                                            appointmentDate,
                                            appointmentNumber);

                    request.setAttribute(
                            "appointment",
                            appointment.orElse(null));

                    if (appointment.isEmpty()) {

                        request.setAttribute(
                                "errorMessage",
                                "Appointment not found");
                    }

                } catch (IllegalArgumentException exception) {

                    request.setAttribute(
                            "errorMessage",
                            exception.getMessage());
                }
            }

            forwardToAppointmentPage(
                    request,
                    response);

        } catch (SQLException exception) {

            throw new ServletException(
                    "Unable to load appointment page",
                    exception);
        }
    }

    @Override
    protected void doPost(
            final HttpServletRequest request,
            final HttpServletResponse response)
            throws ServletException, IOException {

        try {

            final int patientId =
                    Integer.parseInt(
                            request.getParameter(
                                    "patientId"));

            final int dentistId =
                    Integer.parseInt(
                            request.getParameter(
                                    "dentistId"));

            final LocalDate appointmentDate =
                    LocalDate.parse(
                            request.getParameter(
                                    "appointmentDate"));

            final LocalTime appointmentTime =
                    LocalTime.parse(
                            request.getParameter(
                                    "appointmentTime"));

            final String status =
                    request.getParameter(
                            "status");

            final String notes =
                    request.getParameter(
                            "notes");

            /*
             * Multiple treatment checkboxes will use
             * name="treatmentTypeIds".
             */
            final String[] selectedTreatmentTypeIds =
                    request.getParameterValues(
                            "treatmentTypeIds");

            /*
             * Appointment number is generated automatically
             * for the selected appointment date.
             */
            final String appointmentNumber =
                    appointmentService
                            .generateNextAppointmentNumber(
                                    appointmentDate);

            final Appointment appointment =
                    new Appointment(
                            0,
                            appointmentNumber,
                            patientId,
                            dentistId,
                            appointmentDate,
                            appointmentTime,
                            status,
                            notes
                    );

            /*
             * Save appointment and receive the generated
             * database appointment_id.
             */
            final int appointmentId =
                    appointmentService
                            .saveAppointment(
                                    appointment);

            /*
             * Treatments are optional.
             *
             * If the receptionist selected multiple treatments,
             * each one is saved against the newly created
             * appointment.
             */
            if (selectedTreatmentTypeIds != null
                    && treatmentDAO != null) {

                for (String treatmentTypeIdValue
                        : selectedTreatmentTypeIds) {

                    final int treatmentTypeId =
                            Integer.parseInt(
                                    treatmentTypeIdValue);

                    final Treatment treatment =
                            new Treatment(
                                    0,
                                    appointmentId,
                                    treatmentTypeId,
                                    null
                            );

                    treatmentDAO.saveTreatment(
                            treatment);
                }
            }

            /*
             * Create another Appointment object containing
             * the real generated database ID so the JSP can
             * continue the workflow correctly.
             */
            final Appointment createdAppointment =
                    new Appointment(
                            appointmentId,
                            appointmentNumber,
                            patientId,
                            dentistId,
                            appointmentDate,
                            appointmentTime,
                            status,
                            notes
                    );

            request.setAttribute(
                    "successMessage",
                    "Appointment created successfully. "
                            + "Appointment Number: "
                            + appointmentNumber);

            request.setAttribute(
                    "createdAppointment",
                    createdAppointment);

            /*
             * Useful if the receptionist wants to continue
             * straight to billing.
             */
            request.setAttribute(
                    "createdAppointmentId",
                    appointmentId);

            loadDropdownData(
                    request);

            forwardToAppointmentPage(
                    request,
                    response);

        } catch (NumberFormatException exception) {

            request.setAttribute(
                    "errorMessage",
                    "Patient, dentist and treatment values must be valid");

            loadDropdownDataSafely(
                    request);

            forwardToAppointmentPage(
                    request,
                    response);

        } catch (SQLIntegrityConstraintViolationException exception) {

            request.setAttribute(
                    "errorMessage",
                    "Unable to create appointment because the appointment number already exists for this date");

            loadDropdownDataSafely(
                    request);

            forwardToAppointmentPage(
                    request,
                    response);

        } catch (IllegalArgumentException exception) {

            request.setAttribute(
                    "errorMessage",
                    exception.getMessage());

            loadDropdownDataSafely(
                    request);

            forwardToAppointmentPage(
                    request,
                    response);

        } catch (SQLException exception) {

            throw new ServletException(
                    "Unable to save appointment or treatments",
                    exception);
        }
    }

    private void loadDropdownData(
            final HttpServletRequest request)
            throws SQLException {

        if (patientDAO != null) {

            final List<Patient> patients =
                    patientDAO.findAllPatients();

            request.setAttribute(
                    "patients",
                    patients);
        }

        if (dentistDAO != null) {

            final List<Dentist> dentists =
                    dentistDAO.findAllDentists();

            request.setAttribute(
                    "dentists",
                    dentists);
        }

        /*
         * Treatment types are now also loaded
         * onto the appointment page.
         */
        if (treatmentTypeDAO != null) {

            final List<TreatmentType> treatmentTypes =
                    treatmentTypeDAO
                            .findAllTreatmentTypes();

            request.setAttribute(
                    "treatmentTypes",
                    treatmentTypes);
        }
    }

    private void loadDropdownDataSafely(
            final HttpServletRequest request) {

        try {

            loadDropdownData(
                    request);

        } catch (SQLException exception) {

            request.setAttribute(
                    "errorMessage",
                    "Unable to load patient, dentist or treatment data");
        }
    }

    private void forwardToAppointmentPage(
            final HttpServletRequest request,
            final HttpServletResponse response)
            throws ServletException, IOException {

        final RequestDispatcher dispatcher =
                request.getRequestDispatcher(
                        "/WEB-INF/views/appointment.jsp");

        dispatcher.forward(
                request,
                response);
    }

    private void showAllAppointments(
            final HttpServletRequest request,
            final HttpServletResponse response)
            throws ServletException, IOException {

        try {

            final String appointmentDateValue =
                    request.getParameter(
                            "appointmentDate");

            final String status =
                    request.getParameter(
                            "status");

            final List<Appointment> appointments =
                    new ArrayList<>(
                            appointmentDAO
                                    .findAllAppointments());

            if (appointmentDateValue != null
                    && !appointmentDateValue.isBlank()) {

                final LocalDate appointmentDate =
                        LocalDate.parse(
                                appointmentDateValue);

                appointments.removeIf(
                        appointment ->
                                !appointmentDate.equals(
                                        appointment
                                                .getAppointmentDate()));

                request.setAttribute(
                        "filterAppointmentDate",
                        appointmentDateValue);
            }

            if (status != null
                    && !status.isBlank()
                    && !"ALL".equals(status)) {

                appointments.removeIf(
                        appointment ->
                                !status.equals(
                                        appointment
                                                .getStatus()));

                request.setAttribute(
                        "filterStatus",
                        status);
            }

            appointments.sort(
                    Comparator
                            .comparing(
                                    Appointment::getAppointmentDate)
                            .thenComparing(
                                    Appointment::getAppointmentTime)
                            .reversed());

            request.setAttribute(
                    "appointments",
                    appointments);

            request.getRequestDispatcher(
                            "/WEB-INF/views/appointments-all.jsp")
                    .forward(
                            request,
                            response);

        } catch (DateTimeParseException exception) {

            request.setAttribute(
                    "errorMessage",
                    "Appointment date must be valid");

            request.setAttribute(
                    "appointments",
                    List.of());

            request.getRequestDispatcher(
                            "/WEB-INF/views/appointments-all.jsp")
                    .forward(
                            request,
                            response);

        } catch (SQLException exception) {

            throw new ServletException(
                    "Unable to load appointments",
                    exception);
        }
    }
}