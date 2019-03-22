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
        Liste der Aufgaben
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
    </jsp:attribute>

    <jsp:attribute name="content">
        <%-- Suchfilter --%>
        <form method="GET" class="horizontal" id="search">
            <input type="text" name="search_text" value="${param.search_text}" placeholder="Projektbezeichnung"/>

            <select name="search_abteilung">
                <option value="">Alle Abteilungen</option>

                <c:forEach items="${abteilungen}" var="abteilung">
                    <option value="${abteilung.id}" ${param.search_abteilung == abteilung.id ? 'selected' : ''}>
                        <c:out value="${abteilung.name}" />
                    </option>
                </c:forEach>
            </select>

            <select name="search_status">
                <option value="">Alle Stati</option>

                <c:forEach items="${statuses}" var="status">
                    <option value="${status}" ${param.search_status == status ? 'selected' : ''}>
                        <c:out value="${status.label}"/>
                    </option>
                </c:forEach>
            </select>
            
          

            <button class="icon-search" type="submit">
                Suchen
            </button>
        </form>

        <%-- Gefundene Aufgaben --%>
        <c:choose>
            <c:when test="${empty projects}">
                <p>
                    Es wurden keine Aufgaben gefunden. 🐈
                </p>
            </c:when>
            <c:otherwise>
                <jsp:useBean id="utils" class="dhbwka.wwi.vertsys.javaee.checkit.common.web.WebUtils"/>
                
                <table>
                    <thead>
                        <tr>
                            <th>Projektbezeichnung</th>
                            <th>Abteilung</th>
                            <th>Projektleiter</th>
                            <th>Status</th>
                            <th>Fällig am</th>
                            <th>Prio</th>
                        </tr>
                    </thead>
                    <c:forEach items="${projects}" var="project">
                        <tr>
                            <td>
                                <a href="<c:url value="/app/projects/project/${project.id}/"/>">
                                    <c:out value="${project.shortText}"/>
                                </a>
                            </td>
                            <td>
                                <c:out value="${project.abteilung.name}"/>
                            </td>
                            <td>
                                <c:out value="${project.owner.username}"/>
                            </td>
                            <td>
                                <c:out value="${project.status.label}"/>
                            </td>
                            <td>
                                <c:out value="${utils.formatDate(project.dueDate)}"/>
                                <c:out value="${utils.formatTime(project.dueTime)}"/>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:otherwise>
        </c:choose>
    </jsp:attribute>
</template:base>