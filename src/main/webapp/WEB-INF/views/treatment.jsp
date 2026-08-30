<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <title>Assign Treatment</title>
</head>

<body>

<h2>Assign Treatment to Appointment</h2>

<form method="post"
      action="${pageContext.request.contextPath}/treatments">

  <p>
    <label>Appointment ID:</label><br>

    <input type="number"
           name="appointmentId"
           required>
  </p>

  <p>
    <label>Treatment Type:</label><br>

    <select name="treatmentTypeId"
            required>

      <option value="">
        Select treatment
      </option>

      <%
        java.util.List<com.sunrisedental.model.TreatmentType> treatmentTypes =
                (java.util.List<com.sunrisedental.model.TreatmentType>)
                        request.getAttribute("treatmentTypes");

        if (treatmentTypes != null) {
          for (com.sunrisedental.model.TreatmentType treatmentType
                  : treatmentTypes) {
      %>

      <option value="<%= treatmentType.getTreatmentTypeId() %>">
        <%= treatmentType.getTreatmentName() %>
        - <%= treatmentType.getTreatmentFee() %>
      </option>

      <%
          }
        }
      %>

    </select>
  </p>

  <p>
    <label>Description:</label><br>

    <textarea name="description"
              rows="4"
              cols="40"></textarea>
  </p>

  <button type="submit">
    Assign Treatment
  </button>

</form>

<% if (request.getAttribute("successMessage") != null) { %>

<p>
  ${successMessage}
</p>

<% } %>

<% if (request.getAttribute("errorMessage") != null) { %>

<p>
  ${errorMessage}
</p>

<% } %>

<hr>

<p>
  <a href="${pageContext.request.contextPath}/bills">
    Go to Billing
  </a>
</p>

<p>
  <a href="${pageContext.request.contextPath}/dashboard">
    Back to Dashboard
  </a>
</p>

</body>
</html>