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

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

@WebServlet("/patients/new")
public class PatientController extends HttpServlet {

    private final PatientDAO patientDAO;

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile(
                    "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
            );

    public PatientController() {

        try {

            this.patientDAO =
                    new PatientDAOImpl();

        } catch (SQLException exception) {

            throw new IllegalStateException(
                    "Failed to initialize patient controller",
                    exception);
        }
    }

    /*
     * Constructor used by unit tests.
     */
    public PatientController(
            final PatientDAO patientDAO) {

        this.patientDAO =
                patientDAO;
    }

    @Override
    protected void doGet(
            final HttpServletRequest request,
            final HttpServletResponse response)
            throws ServletException, IOException {

        forwardToPatientForm(
                request,
                response);
    }

    @Override
    protected void doPost(
            final HttpServletRequest request,
            final HttpServletResponse response)
            throws ServletException, IOException {

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

        /*
         * Preserve entered values if validation fails.
         */
        request.setAttribute(
                "firstNameValue",
                firstName);

        request.setAttribute(
                "lastNameValue",
                lastName);

        request.setAttribute(
                "phoneValue",
                phone);

        request.setAttribute(
                "emailValue",
                email);

        request.setAttribute(
                "dateOfBirthValue",
                dateOfBirthValue);

        request.setAttribute(
                "addressValue",
                address);

        try {

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
                            0,
                            firstName,
                            lastName,
                            phone,
                            emptyToNull(
                                    email),
                            dateOfBirth,
                            address
                    );

            final boolean saved =
                    patientDAO
                            .savePatient(
                                    patient);

            if (!saved) {

                request.setAttribute(
                        "errorMessage",
                        "Patient could not be saved");

                forwardToPatientForm(
                        request,
                        response);

                return;
            }

            /*
             * Return to appointment creation after
             * successfully registering the patient.
             */
            response.sendRedirect(
                    request.getContextPath()
                            + "/appointments");

        } catch (IllegalArgumentException exception) {

            request.setAttribute(
                    "errorMessage",
                    exception.getMessage());

            forwardToPatientForm(
                    request,
                    response);

        } catch (SQLException exception) {

            throw new ServletException(
                    "Unable to save patient",
                    exception);
        }
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

    private void forwardToPatientForm(
            final HttpServletRequest request,
            final HttpServletResponse response)
            throws ServletException, IOException {

        final RequestDispatcher dispatcher =
                request.getRequestDispatcher(
                        "/WEB-INF/views/patient-form.jsp");

        dispatcher.forward(
                request,
                response);
    }
}