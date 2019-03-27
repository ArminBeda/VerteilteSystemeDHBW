<%-- 
    Copyright © 2018 Dennis Schulmeister-Zimolong

    E-Mail: dhbw@windows3.de
    Webseite: https://www.wpvs.de/

    Dieser Quellcode ist lizenziert unter einer
    Creative Commons Namensnennung 4.0 International Lizenz.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

 

<template:base>
    <jsp:attribute name="title">
        <c:choose>
            <c:when test="${edit}">
                Projekt bearbeiten
            </c:when>
            <c:otherwise>
                Projekt anlegen
            </c:otherwise>
        </c:choose>
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/project_edit.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/app/dashboard/"/>">Dashboard</a>
        </div>
        
        <div class="menuitem">
            <a href="<c:url value="/app/projects/list/"/>">Liste</a>
        </div>
         <div class="menuitem">
            <a href="<c:url value="/app/projects/abteilungen/"/>">Abteilungen bearbeiten</a>
        </div>
         <div class="menuitem">
            <a href="<c:url value="/app/projects/useredit/"/>">Benutzerdaten bearbeiten</a>
        </div>
    </jsp:attribute>

    <jsp:attribute name="content">
        <form method="post" class="stacked">
            <div class="column">
                <%-- CSRF-Token --%>
                <input type="hidden" name="csrf_token" value="${csrf_token}">

                <%-- Eingabefelder --%>
                <label for="project_owner">Projektleiter:</label>
                <div class="side-by-side">
                    <input type="text" name="project_owner" value="${project_form.values["project_owner"][0]}" readonly="readonly">
                </div>

                <label for="project_abteilung">Abteilung:</label>
                <div class="side-by-side">
                    <select name="project_abteilung">
                        <option value="">Keine Abteilung</option>

                        <c:forEach items="${abteilungen}" var="abteilung">
                            <option value="${abteilung.id}" ${project_form.values["project_abteilung"][0] == abteilung.id.toString() ? 'selected' : ''}>
                                <c:out value="${abteilung.name}" />
                            </option>
                        </c:forEach>
                    </select>
                </div>

                  <label for="project_begin_date">
                    Projekt beginnt am:
                    <span class="required">*</span>
                </label>
                <div class="side-by-side">
                    <input type="text" name="project_begin_date" value="${project_form.values["project_begin_date"][0]}">
                    <input type="text" name="project_begin_time" value="${project_form.values["project_begin_time"][0]}">
                </div>

                
                <label for="project_due_date">
                    Projekt fällig am:
                    <span class="required">*</span>
                </label>
                <div class="side-by-side">
                    <input type="text" name="project_due_date" value="${project_form.values["project_due_date"][0]}">
                    <input type="text" name="project_due_time" value="${project_form.values["project_due_time"][0]}">
                </div>

                <label for="project_status">
                    Projektstatus:
                    <span class="required">*</span>
                </label>
                <div class="side-by-side margin">
                    <select name="project_status">
                        <c:forEach items="${statuses}" var="status">
                            <option value="${status}" ${project_form.values["project_status"][0] == status ? 'selected' : ''}>
                                <c:out value="${status.label}"/>
                            </option>
                        </c:forEach>
                    </select>
                </div>
                
                <label for="project_priority">
                    Priorität:
                    <span class="required">*</span>
                </label>
                <div class="side-by-side margin">
                    <select name="project_priority">
                        <c:forEach items="${priorities}" var="priority">
                            <option value="${priority}" ${project_form.values["project_priority"][0] == status ? 'selected' : ''}>
                                <c:out value="${priority.label}"/>
                            </option>
                        </c:forEach>
                    </select>
                </div>
                
                
               <label for="project_is_extern">
                    Extern:
                           <input type="checkbox" name="project_is_extern"  onchange="setExtern()" value="${is_extern}" ${project_form.values["is_extern"][0] == checked ? 'checked' : ''}>

                </label>
                
            
       
           

                <label for="project_short_text">
                    Projektbezeichnung:
                    <span class="required">*</span>
                </label>
                <div class="side-by-side">
                    <input type="text" name="project_short_text" value="${project_form.values["project_short_text"][0]}">
                </div>

                <label for="project_long_text">
                    Beschreibung:
                </label>
                <div class="side-by-side">
                    <textarea name="project_long_text"><c:out value="${project_form.values['project_long_text'][0]}"/></textarea>
                </div>              
               
                  
                   

                <%-- Button zum Abschicken --%>
                <div class="side-by-side">
                    <button class="icon-pencil" type="submit" name="action" value="save">
                        Sichern
                    </button>

                    <c:if test="${edit}">
                        <button class="icon-trash" type="submit" name="action" value="delete">
                            Löschen
                        </button>
                    </c:if>
                </div>
            </div>

            <%-- Fehlermeldungen --%>
            <c:if test="${!empty project_form.errors}">
                <ul class="errors">
                    <c:forEach items="${project_form.errors}" var="error">
                        <li>${error}</li>
                    </c:forEach>
                </ul>
            </c:if>
        </form>
         
            
            
            
    </jsp:attribute>
</template:base>
