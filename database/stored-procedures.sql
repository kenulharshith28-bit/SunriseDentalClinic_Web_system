USE sunrise_dental_clinic;

DROP PROCEDURE IF EXISTS get_daily_appointments;

DELIMITER //

CREATE PROCEDURE get_daily_appointments(
    IN selected_date DATE
)
BEGIN

SELECT
    a.appointment_id,
    a.appointment_number,
    CONCAT(p.first_name, ' ', p.last_name) AS patient_name,
    p.phone AS patient_contact,
    p.address AS patient_address,
    CONCAT(d.first_name, ' ', d.last_name) AS dentist_name,
    d.specialization,
    a.appointment_date,
    a.appointment_time,
    a.status,
    a.notes

FROM appointments a

         INNER JOIN patients p
                    ON a.patient_id = p.patient_id

         INNER JOIN dentists d
                    ON a.dentist_id = d.dentist_id

WHERE a.appointment_date = selected_date

ORDER BY a.appointment_time;

END //

DELIMITER ;