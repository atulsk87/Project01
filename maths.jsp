<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>   
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jstl/xml" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jstl/sql" %>
 
<html>
<form:form method="post" action="maths">
    <head>
        <title>Arithmetic Expression</title>
    </head>
    
    <body>
    
    <table >    
        <tr>   
        <td> </td>       
        <td> Expression : </td>
    	<td> <form:input path="expression"  /> </td>
    </tr>
    
    <tr>   
        <td> </td>    
    	<td> <input type="submit" value="Submit" /> </td>
    </tr>
    
    </table>
    
        <h1>Output : <c:out value="${message}"></c:out></h1>
        
        <c:out value="History:"></c:out>
        
        <%
        String str = String.valueOf(request.getSession().getAttribute("oldExpressions"));
        String[] strArray = str.split("|");
          
        for(int i = 0; i< strArray.length; i++)
        {
        %>
        	<c:out value="<%=strArray[i]%>"></c:out>
        <%
        } 
        %>
        
    </body>
     </form:form>    
</html>