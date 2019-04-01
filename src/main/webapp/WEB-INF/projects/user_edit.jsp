<%-- 
    Document   : user_edit
    Created on : 26.03.2019, 09:57:28
    Author     : aishach
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<template:base>
    <jsp:attribute name="title">
        Benutzerdaten bearbeiten
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/project_list.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/app/dashboard/"/>">Dashboard</a>
        </div>

        <div class="menuitem">
            <a href="<c:url value="/app/projects/project/new/"/>">Projekt anlegen</a>
        </div>

        <div class="menuitem">
            <a href="<c:url value="/app/projects/abteilungen/"/>">Abteilungen bearbeiten</a>
        </div>
         <div class="menuitem">
            <a href="<c:url value="/app/projects/useredit/"/>">Benutzerdaten bearbeiten</a>
        </div>
    </jsp:attribute>
        
        
      

    <jsp:attribute name="content">
        <div class="container">
            <form method="post" class="stacked">
                <div class="column">
                    <%-- CSRF-Token --%>
                    <input type="hidden" name="csrf_token" value="${csrf_token}">

                    <%-- Eingabefelder --%>
                    
                    <label for="new_vorname">
                        Vorname ändern:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="new_vorname" value="${signup_form.values["new_vorname"][0]}">
                    </div>
                    
                    <label for="new_nachname">
                        Nachname ändern:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="new_nachname" value="${signup_form.values["new_nachname"][0]}">
                    </div>

                   
                    <!--<label for="signup_password1">
                       Altes Passwort:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="password" name="altesPassword" value="${signup_form.values["altesPassword"][0]}">
                    </div>
                    -->
                    <label for="signup_password1">
                       Neues Passwort:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="password" name="password1" value="${signup_form.values["password1"][0]}">
                    </div>

                    <label for="new_password2">
                        Neues Passwort (wdh.):
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="password" name="password2" value="${signup_form.values["password2"][0]}">
                    </div>

                    <%-- Button zum Abschicken --%>
                    <div class="side-by-side">
                        <button class="icon-pencil" type="submit">
                            Speichern
                        </button>
                    </div>
                </div>
                    
                <%-- Fehlermeldungen --%>
                <c:if test="${!empty signup_form.errors}">
                    <ul class="errors">
                        <c:forEach items="${signup_form.errors}" var="error">
                            <li>${error}</li>
                            </c:forEach>
                    </ul>
                </c:if>
            </form>
        </div>
    </jsp:attribute>
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
</template:base>








