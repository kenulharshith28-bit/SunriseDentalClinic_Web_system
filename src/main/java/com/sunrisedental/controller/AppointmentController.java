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
import com.sunrisedental.service.EmailService;
import com.sunrisedental.service.SmtpEmailService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
    private final EmailService emailService;

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

            this.emailService =
                    new SmtpEmailService();

        } catch (SQLException exception) {

            throw new IllegalStateException(
                    "Failed to initialize appointment controller",
                    exception);
        }
    }

    /*
     * Compatibility constructor for older tests.
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
        this.emailService = null;
    }

    /*
     * Compatibility constructor for older controller tests.
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
        this.emailService = null;
    }

    /*
     * Compatibility constructor for appointment/treatment tests.
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

        this.emailService =
                null;
    }

    /*
     * Full constructor for tests involving email notification.
     */
    public AppointmentController(
            final AppointmentService appointmentService,
            final AppointmentDAO appointmentDAO,
            final PatientDAO patientDAO,
            final DentistDAO dentistDAO,
            final TreatmentDAO treatmentDAO,
            final TreatmentTypeDAO treatmentTypeDAO,
            final EmailService emailService) {

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

        this.emailService =
                emailService;
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

        /*
         * POST /appointments/all =
         * manual appointment cancellation.
         */
        if ("/appointments/all".equals(
                request.getServletPath())) {

            cancelAppointment(
                    request,
                    response);

            return;
        }

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

            final String[] selectedTreatmentTypeIds =
                    request.getParameterValues(
                            "treatmentTypeIds");

            /*
             * Generate daily appointment number.
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
             * Save appointment and obtain generated ID.
             */
            final int appointmentId =
                    appointmentService
                            .saveAppointment(
                                    appointment);

            /*
             * Save selected treatment records.
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

                    treatmentDAO
                            .saveTreatment(
                                    treatment);
                }
            }

            /*
             * Send appointment confirmation email.
             */
            try {

                sendAppointmentConfirmationEmail(
                        patientId,
                        dentistId,
                        appointmentNumber,
                        appointmentDate,
                        appointmentTime,
                        selectedTreatmentTypeIds);

            } catch (RuntimeException emailException) {

                System.err.println(
                        "Appointment created, but confirmation email failed: "
                                + emailException.getMessage());
            }

            /*
             * Redirect directly to Billing.
             */
            final String encodedAppointmentNumber =
                    URLEncoder.encode(
                            appointmentNumber,
                            StandardCharsets.UTF_8);

            final String redirectUrl =
                    request.getContextPath()
                            + "/bills"
                            + "?appointmentId="
                            + appointmentId
                            + "&appointmentDate="
                            + appointmentDate
                            + "&appointmentNumber="
                            + encodedAppointmentNumber
                            + "&created=true";

            response.sendRedirect(
                    redirectUrl);

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
                    "Unable to create appointment because "
                            + "the appointment number already exists "
                            + "for this date");

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

    /*
     * Sends a confirmation email after an appointment
     * and its treatment records have been saved.
     */
    private void sendAppointmentConfirmationEmail(
            final int patientId,
            final int dentistId,
            final String appointmentNumber,
            final LocalDate appointmentDate,
            final LocalTime appointmentTime,
            final String[] selectedTreatmentTypeIds) {

        if (emailService == null
                || patientDAO == null
                || dentistDAO == null) {

            return;
        }

        try {

            final Patient patient =
                    findPatientById(
                            patientId);

            if (patient == null
                    || patient.getEmail() == null
                    || patient.getEmail().isBlank()) {

                return;
            }

            final Dentist dentist =
                    findDentistById(
                            dentistId);

            final String treatmentNames =
                    buildTreatmentNames(
                            selectedTreatmentTypeIds);

            final String subject =
                    "Sunrise Dental Clinic - Appointment Confirmation";

            final String body =
                    buildAppointmentEmailBody(
                            patient,
                            dentist,
                            appointmentNumber,
                            appointmentDate,
                            appointmentTime,
                            treatmentNames);

            emailService.sendEmail(
                    patient.getEmail(),
                    subject,
                    body);

        } catch (SQLException exception) {

            throw new IllegalStateException(
                    "Unable to load appointment email information",
                    exception);
        }
    }

    /*
     * Sends a cancellation email after an appointment
     * has successfully been cancelled.
     */
    private void sendAppointmentCancellationEmail(
            final Appointment appointment) {

        if (appointment == null
                || emailService == null
                || patientDAO == null
                || dentistDAO == null) {

            return;
        }

        try {

            final Patient patient =
                    findPatientById(
                            appointment.getPatientId());

            /*
             * Patients without an email address can still
             * have their appointments cancelled.
             */
            if (patient == null
                    || patient.getEmail() == null
                    || patient.getEmail().isBlank()) {

                return;
            }

            final Dentist dentist =
                    findDentistById(
                            appointment.getDentistId());

            final String dentistName;

            if (dentist != null) {

                dentistName =
                        "Dr. "
                                + dentist.getFullName();

            } else {

                dentistName =
                        "Not available";
            }

            final String subject =
                    "Sunrise Dental Clinic - Appointment Cancelled";

            final String body =
                    "Sunrise Dental Clinic\n"
                            + "----------------------------------------\n\n"
                            + "Appointment Cancellation\n\n"
                            + "Dear "
                            + patient.getFullName()
                            + ",\n\n"
                            + "Your dental appointment has been cancelled.\n\n"
                            + "Appointment Number: "
                            + appointment.getAppointmentNumber()
                            + "\n"
                            + "Dentist: "
                            + dentistName
                            + "\n"
                            + "Date: "
                            + appointment.getAppointmentDate()
                            + "\n"
                            + "Time: "
                            + appointment.getAppointmentTime()
                            + "\n\n"
                            + "If you would like to arrange another appointment, "
                            + "please contact Sunrise Dental Clinic.\n\n"
                            + "Thank you.\n"
                            + "Sunrise Dental Clinic\n";

            emailService.sendEmail(
                    patient.getEmail(),
                    subject,
                    body);

        } catch (SQLException exception) {

            throw new IllegalStateException(
                    "Unable to load cancellation email information",
                    exception);
        }
    }

    private Patient findPatientById(
            final int patientId)
            throws SQLException {

        final List<Patient> patients =
                patientDAO
                        .findAllPatients();

        for (Patient patient : patients) {

            if (patient.getPatientId()
                    == patientId) {

                return patient;
            }
        }

        return null;
    }

    private Dentist findDentistById(
            final int dentistId)
            throws SQLException {

        final List<Dentist> dentists =
                dentistDAO
                        .findAllDentists();

        for (Dentist dentist : dentists) {

            if (dentist.getDentistId()
                    == dentistId) {

                return dentist;
            }
        }

        return null;
    }

    /*
     * Finds an appointment before cancellation so that
     * its patient, dentist, date and time can be used
     * in the cancellation email.
     */
    private Appointment findAppointmentById(
            final int appointmentId)
            throws SQLException {

        final List<Appointment> appointments =
                appointmentDAO
                        .findAllAppointments();

        for (Appointment appointment : appointments) {

            if (appointment.getAppointmentId()
                    == appointmentId) {

                return appointment;
            }
        }

        return null;
    }

    private String buildTreatmentNames(
            final String[] selectedTreatmentTypeIds)
            throws SQLException {

        if (selectedTreatmentTypeIds == null
                || selectedTreatmentTypeIds.length == 0
                || treatmentTypeDAO == null) {

            return "No treatment selected";
        }

        final List<TreatmentType> treatmentTypes =
                treatmentTypeDAO
                        .findAllTreatmentTypes();

        final List<String> selectedNames =
                new ArrayList<>();

        for (String treatmentTypeIdValue
                : selectedTreatmentTypeIds) {

            final int treatmentTypeId =
                    Integer.parseInt(
                            treatmentTypeIdValue);

            for (TreatmentType treatmentType
                    : treatmentTypes) {

                if (treatmentType
                        .getTreatmentTypeId()
                        == treatmentTypeId) {

                    selectedNames.add(
                            treatmentType
                                    .getTreatmentName());

                    break;
                }
            }
        }

        if (selectedNames.isEmpty()) {

            return "No treatment selected";
        }

        return String.join(
                ", ",
                selectedNames);
    }

    private String buildAppointmentEmailBody(
            final Patient patient,
            final Dentist dentist,
            final String appointmentNumber,
            final LocalDate appointmentDate,
            final LocalTime appointmentTime,
            final String treatmentNames) {

        final String dentistName;

        if (dentist != null) {

            dentistName =
                    "Dr. "
                            + dentist.getFullName();

        } else {

            dentistName =
                    "To be confirmed";
        }

        return "Sunrise Dental Clinic\n"
                + "----------------------------------------\n\n"
                + "Appointment Confirmation\n\n"
                + "Dear "
                + patient.getFullName()
                + ",\n\n"
                + "Your dental appointment has been successfully scheduled.\n\n"
                + "Appointment Number: "
                + appointmentNumber
                + "\n"
                + "Patient: "
                + patient.getFullName()
                + "\n"
                + "Dentist: "
                + dentistName
                + "\n"
                + "Date: "
                + appointmentDate
                + "\n"
                + "Time: "
                + appointmentTime
                + "\n"
                + "Treatment(s): "
                + treatmentNames
                + "\n\n"
                + "Please arrive a few minutes before your scheduled time.\n\n"
                + "Thank you for choosing Sunrise Dental Clinic.\n";
    }

    private void cancelAppointment(
            final HttpServletRequest request,
            final HttpServletResponse response)
            throws ServletException, IOException {

        try {

            if (appointmentDAO == null) {

                throw new IllegalStateException(
                        "Appointment management is unavailable");
            }

            final String appointmentIdValue =
                    request.getParameter(
                            "appointmentId");

            if (appointmentIdValue == null
                    || appointmentIdValue.isBlank()) {

                request.getSession()
                        .setAttribute(
                                "appointmentError",
                                "Please select an appointment to cancel");

                response.sendRedirect(
                        request.getContextPath()
                                + "/appointments/all");

                return;
            }

            final int appointmentId =
                    Integer.parseInt(
                            appointmentIdValue);

            /*
             * Load appointment details before updating
             * its status to CANCELLED.
             */
            final Appointment appointment =
                    findAppointmentById(
                            appointmentId);

            final boolean cancelled =
                    appointmentDAO
                            .cancelAppointment(
                                    appointmentId);

            if (cancelled) {

                /*
                 * Cancellation itself must remain successful
                 * even if SMTP/email fails.
                 */
                try {

                    sendAppointmentCancellationEmail(
                            appointment);

                } catch (RuntimeException emailException) {

                    System.err.println(
                            "Appointment cancelled, but cancellation email failed: "
                                    + emailException.getMessage());
                }

                request.getSession()
                        .setAttribute(
                                "appointmentMessage",
                                "Appointment cancelled successfully");

            } else {

                request.getSession()
                        .setAttribute(
                                "appointmentError",
                                "Appointment could not be cancelled. "
                                        + "It may already be completed or cancelled.");
            }

            response.sendRedirect(
                    request.getContextPath()
                            + "/appointments/all");

        } catch (NumberFormatException exception) {

            request.getSession()
                    .setAttribute(
                            "appointmentError",
                            "Invalid appointment");

            response.sendRedirect(
                    request.getContextPath()
                            + "/appointments/all");

        } catch (SQLException exception) {

            throw new ServletException(
                    "Unable to cancel appointment",
                    exception);
        }
    }

    private void loadDropdownData(
            final HttpServletRequest request)
            throws SQLException {

        if (patientDAO != null) {

            final List<Patient> patients =
                    patientDAO
                            .findAllPatients();

            request.setAttribute(
                    "patients",
                    patients);
        }

        if (dentistDAO != null) {

            final List<Dentist> dentists =
                    dentistDAO
                            .findAllDentists();

            request.setAttribute(
                    "dentists",
                    dentists);
        }

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

            if (appointmentDAO == null) {

                throw new IllegalStateException(
                        "Appointment management is unavailable");
            }

            /*
             * Automatically cancel past scheduled
             * appointments.
             */
            appointmentDAO
                    .cancelExpiredAppointments();

            final Object successMessage =
                    request.getSession()
                            .getAttribute(
                                    "appointmentMessage");

            if (successMessage != null) {

                request.setAttribute(
                        "successMessage",
                        successMessage);

                request.getSession()
                        .removeAttribute(
                                "appointmentMessage");
            }

            final Object errorMessage =
                    request.getSession()
                            .getAttribute(
                                    "appointmentError");

            if (errorMessage != null) {

                request.setAttribute(
                        "errorMessage",
                        errorMessage);

                request.getSession()
                        .removeAttribute(
                                "appointmentError");
            }

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

            /*
             * Filter by appointment date.
             */
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

            /*
             * Filter by status.
             */
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

            /*
             * Newest appointments first.
             */
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

            if (patientDAO != null) {

                request.setAttribute(
                        "patients",
                        patientDAO
                                .findAllPatients());
            }

            if (dentistDAO != null) {

                request.setAttribute(
                        "dentists",
                        dentistDAO
                                .findAllDentists());
            }

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