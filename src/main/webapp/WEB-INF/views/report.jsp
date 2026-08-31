<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Reports</title>
</head>

<body>

<h2>Reports</h2>

<form method="get"
      action="${pageContext.request.contextPath}/reports">

    <p>
        <label>Select Report Type:</label><br>

        <select name="reportType"
                required>

            <option value="">
                Select report
            </option>

            <option value="appointment">
                Appointment Report
            </option>

            <option value="bill">
                Bill Report
            </option>

        </select>
    </p>

    <button type="submit">
        Generate Report
    </button>

</form>

<hr>

<% if (request.getAttribute("report") != null) { %>

<h3>Generated Report</h3>

<pre>${report}</pre>

<% } %>

<% if (request.getAttribute("errorMessage") != null) { %>

<p>
    ${errorMessage}
</p>

<% } %>

<hr>

<p>
    <a href="${pageContext.request.contextPath}/dashboard">
        Back to Dashboard
    </a>
</p>

</body>
</html>