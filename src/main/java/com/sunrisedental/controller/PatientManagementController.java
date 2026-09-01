package com.sunrisedental.controller;

import com.sunrisedental.dao.PatientDAO;
import com.sunrisedental.dao.PatientDAOImpl;
import com.sunrisedental.model.Patient;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@WebServlet(urlPatterns = {
        "/patients/manage",
        "/patients/edit",
        "/patients/delete"
})
public class PatientManagementController
        extends HttpServlet {

    private final PatientDAO patientDAO;

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile(
                    "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
            );

    public PatientManagementController() {

        try {

            this.patientDAO =
                    new PatientDAOImpl();

        } catch (SQLException exception) {

            throw new IllegalStateException(
                    "Failed to initialize patient management controller",
                    exception);
        }
    }

    /*
     * Constructor for unit tests.
     */
    public PatientManagementController(
            final PatientDAO patientDAO) {

        this.patientDAO =
                patientDAO;
    }

    @Override
    protected void doGet(
            final HttpServletRequest request,
            final HttpServletResponse response)
            throws ServletException, IOException {

        if (!isAdmin(
                request)) {

            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN);

            return;
        }

        final String servletPath =
                request.getServletPath();

        if ("/patients/edit".equals(
                servletPath)) {

            showEditPatient(
                    request,
                    response);

            return;
        }

        showManagePatients(
                request,
                response);
    }

    @Override
    protected void doPost(
            final HttpServletRequest request,
            final HttpServletResponse response)
            throws ServletException, IOException {

        if (!isAdmin(
                request)) {

            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN);

            return;
        }

        final String servletPath =
                request.getServletPath();

        if ("/patients/edit".equals(
                servletPath)) {

            updatePatient(
                    request,
                    response);

            return;
        }

        if ("/patients/delete".equals(
                servletPath)) {

            deletePatient(
                    request,
                    response);

            return;
        }

        response.sendError(
                HttpServletResponse.SC_NOT_FOUND);
    }

    private void showManagePatients(
            final HttpServletRequest request,
            final HttpServletResponse response)
            throws ServletException, IOException {

        try {

            loadFlashMessages(
                    request);

            final List<Patient> patients =
                    patientDAO
                            .findAllPatients();

            request.setAttribute(
                    "patients",
                    patients);

            final RequestDispatcher dispatcher =
                    request.getRequestDispatcher(
                            "/WEB-INF/views/manage-patients.jsp");

            dispatcher.forward(
                    request,
                    response);

        } catch (SQLException exception) {

            throw new ServletException(
                    "Unable to load patients",
                    exception);
        }
    }

    private void showEditPatient(
            final HttpServletRequest request,
            final HttpServletResponse response)
            throws ServletException, IOException {

        try {

            final int patientId =
                    parsePatientId(
                            request);

            final Optional<Patient> patient =
                    patientDAO
                            .findById(
                                    patientId);

            if (patient.isEmpty()) {

                setFlashError(
                        request,
                        "Patient not found");

                redirectToManage(
                        request,
                        response);

                return;
            }

            request.setAttribute(
                    "patient",
                    patient.get());

            final RequestDispatcher dispatcher =
                    request.getRequestDispatcher(
                            "/WEB-INF/views/edit-patient.jsp");

            dispatcher.forward(
                    request,
                    response);

        } catch (NumberFormatException exception) {

            setFlashError(
                    request,
                    "Invalid patient");

            redirectToManage(
                    request,
                    response);

        } catch (SQLException exception) {

            throw new ServletException(
                    "Unable to load patient",
                    exception);
        }
    }

    private void updatePatient(
            final HttpServletRequest request,
            final HttpServletResponse response)
            throws ServletException, IOException {

        try {

            final int patientId =
                    parsePatientId(
                            request);

            final String firstName =
                    getTrimmedParameter(
                            request,
                            "firstName");

            final String lastName =
                    getTrimmedParameter(
                            request,
                            "lastName");

            final String phone =
                    getTrimmedParameter(
                            request,
                            "phone");

            final String email =
                    getTrimmedParameter(
                            request,
                            "email");

            final String dateOfBirthValue =
                    getTrimmedParameter(
                            request,
                            "dateOfBirth");

            final String address =
                    getTrimmedParameter(
                            request,
                            "address");

            validateRequiredFields(
                    firstName,
                    lastName,
                    phone,
                    address);

            validateEmail(
                    email);

            final LocalDate dateOfBirth =
                    parseAndValidateDateOfBirth(
                            dateOfBirthValue);

            final Patient patient =
                    new Patient(
                            patientId,
                            firstName,
                            lastName,
                            phone,
                            emptyToNull(
                                    email),
                            dateOfBirth,
                            address
                    );

            final boolean updated =
                    patientDAO
                            .updatePatient(
                                    patient);

            if (updated) {

                setFlashMessage(
                        request,
                        "Patient updated successfully");

            } else {

                setFlashError(
                        request,
                        "Patient could not be updated");
            }

            redirectToManage(
                    request,
                    response);

        } catch (NumberFormatException exception) {

            setFlashError(
                    request,
                    "Invalid patient");

            redirectToManage(
                    request,
                    response);

        } catch (IllegalArgumentException exception) {

            request.setAttribute(
                    "errorMessage",
                    exception.getMessage());

            preserveEditValues(
                    request);

            forwardBackToEdit(
                    request,
                    response);

        } catch (SQLException exception) {

            throw new ServletException(
                    "Unable to update patient",
                    exception);
        }
    }

    private void deletePatient(
            final HttpServletRequest request,
            final HttpServletResponse response)
            throws ServletException, IOException {

        try {

            final int patientId =
                    parsePatientId(
                            request);

            final boolean deleted =
                    patientDAO
                            .deletePatient(
                                    patientId);

            if (deleted) {

                setFlashMessage(
                        request,
                        "Patient deleted successfully");

            } else {

                setFlashError(
                        request,
                        "Patient could not be deleted");
            }

            redirectToManage(
                    request,
                    response);

        } catch (NumberFormatException exception) {

            setFlashError(
                    request,
                    "Invalid patient");

            redirectToManage(
                    request,
                    response);

        } catch (SQLException exception) {

            /*
             * Most likely the patient is still referenced
             * by one or more appointment records.
             */
            setFlashError(
                    request,
                    "This patient cannot be deleted because "
                            + "they are linked to existing appointments.");

            redirectToManage(
                    request,
                    response);
        }
    }

    private int parsePatientId(
            final HttpServletRequest request) {

        final String patientIdValue =
                request.getParameter(
                        "patientId");

        if (patientIdValue == null
                || patientIdValue.isBlank()) {

            throw new NumberFormatException(
                    "Patient ID is required");
        }

        return Integer.parseInt(
                patientIdValue);
    }

    private void validateRequiredFields(
            final String firstName,
            final String lastName,
            final String phone,
            final String address) {

        if (firstName == null
                || firstName.isBlank()) {

            throw new IllegalArgumentException(
                    "First name is required");
        }

        if (lastName == null
                || lastName.isBlank()) {

            throw new IllegalArgumentException(
                    "Last name is required");
        }

        if (phone == null
                || phone.isBlank()) {

            throw new IllegalArgumentException(
                    "Contact number is required");
        }

        if (address == null
                || address.isBlank()) {

            throw new IllegalArgumentException(
                    "Address is required");
        }

        if (firstName.length() > 50) {

            throw new IllegalArgumentException(
                    "First name must not exceed 50 characters");
        }

        if (lastName.length() > 50) {

            throw new IllegalArgumentException(
                    "Last name must not exceed 50 characters");
        }

        if (phone.length() > 20) {

            throw new IllegalArgumentException(
                    "Contact number must not exceed 20 characters");
        }

        if (address.length() > 255) {

            throw new IllegalArgumentException(
                    "Address must not exceed 255 characters");
        }
    }

    private void validateEmail(
            final String email) {

        if (email == null
                || email.isBlank()) {

            return;
        }

        if (email.length() > 100) {

            throw new IllegalArgumentException(
                    "Email must not exceed 100 characters");
        }

        if (!EMAIL_PATTERN
                .matcher(email)
                .matches()) {

            throw new IllegalArgumentException(
                    "Please enter a valid email address");
        }
    }

    private LocalDate parseAndValidateDateOfBirth(
            final String dateOfBirthValue) {

        if (dateOfBirthValue == null
                || dateOfBirthValue.isBlank()) {

            return null;
        }

        try {

            final LocalDate dateOfBirth =
                    LocalDate.parse(
                            dateOfBirthValue);

            if (dateOfBirth.isAfter(
                    LocalDate.now())) {

                throw new IllegalArgumentException(
                        "Date of birth cannot be in the future");
            }

            return dateOfBirth;

        } catch (DateTimeParseException exception) {

            throw new IllegalArgumentException(
                    "Date of birth must be valid");
        }
    }

    private boolean isAdmin(
            final HttpServletRequest request) {

        final HttpSession session =
                request.getSession(
                        false);

        return session != null
                && "ADMIN".equals(
                session.getAttribute(
                        "role"));
    }

    private String getTrimmedParameter(
            final HttpServletRequest request,
            final String parameterName) {

        final String value =
                request.getParameter(
                        parameterName);

        if (value == null) {

            return null;
        }

        return value.trim();
    }

    private String emptyToNull(
            final String value) {

        if (value == null
                || value.isBlank()) {

            return null;
        }

        return value;
    }

    private void setFlashMessage(
            final HttpServletRequest request,
            final String message) {

        request.getSession()
                .setAttribute(
                        "patientMessage",
                        message);
    }

    private void setFlashError(
            final HttpServletRequest request,
            final String message) {

        request.getSession()
                .setAttribute(
                        "patientError",
                        message);
    }

    private void loadFlashMessages(
            final HttpServletRequest request) {

        final HttpSession session =
                request.getSession();

        final Object message =
                session.getAttribute(
                        "patientMessage");

        final Object error =
                session.getAttribute(
                        "patientError");

        if (message != null) {

            request.setAttribute(
                    "successMessage",
                    message);

            session.removeAttribute(
                    "patientMessage");
        }

        if (error != null) {

            request.setAttribute(
                    "errorMessage",
                    error);

            session.removeAttribute(
                    "patientError");
        }
    }

    private void preserveEditValues(
            final HttpServletRequest request) {

        final Patient patient =
                new Patient(
                        Integer.parseInt(
                                request.getParameter(
                                        "patientId")),
                        getTrimmedParameter(
                                request,
                                "firstName"),
                        getTrimmedParameter(
                                request,
                                "lastName"),
                        getTrimmedParameter(
                                request,
                                "phone"),
                        emptyToNull(
                                getTrimmedParameter(
                                        request,
                                        "email")),
                        parseDateSafely(
                                getTrimmedParameter(
                                        request,
                                        "dateOfBirth")),
                        getTrimmedParameter(
                                request,
                                "address")
                );

        request.setAttribute(
                "patient",
                patient);
    }

    private LocalDate parseDateSafely(
            final String value) {

        if (value == null
                || value.isBlank()) {

            return null;
        }

        try {

            return LocalDate.parse(
                    value);

        } catch (DateTimeParseException exception) {

            return null;
        }
    }

    private void forwardBackToEdit(
            final HttpServletRequest request,
            final HttpServletResponse response)
            throws ServletException, IOException {

        final RequestDispatcher dispatcher =
                request.getRequestDispatcher(
                        "/WEB-INF/views/edit-patient.jsp");

        dispatcher.forward(
                request,
                response);
    }

    private void redirectToManage(
            final HttpServletRequest request,
            final HttpServletResponse response)
            throws IOException {

        response.sendRedirect(
                request.getContextPath()
                        + "/patients/manage");
    }
}