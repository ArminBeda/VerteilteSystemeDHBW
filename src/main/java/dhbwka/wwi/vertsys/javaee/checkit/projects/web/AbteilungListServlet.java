/*
 * Copyright © 2018 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.checkit.projects.web;

import dhbwka.wwi.vertsys.javaee.checkit.common.web.FormValues;
import dhbwka.wwi.vertsys.javaee.checkit.projects.ejb.AbteilungBean;
import dhbwka.wwi.vertsys.javaee.checkit.projects.ejb.ProjectBean;
import dhbwka.wwi.vertsys.javaee.checkit.common.ejb.ValidationBean;
import dhbwka.wwi.vertsys.javaee.checkit.projects.jpa.Abteilung;
import dhbwka.wwi.vertsys.javaee.checkit.projects.jpa.Project;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Seite zum Anzeigen und Bearbeiten der Abteilungen. Die Seite besitzt ein
 * Formular, mit dem eine neue Abteilung angelegt werden kann, sowie eine Liste,
 * die zum Löschen der Abteilungen verwendet werden kann.
 */
@WebServlet(urlPatterns = {"/app/projects/abteilungen/"})
public class AbteilungListServlet extends HttpServlet {

    @EJB
    AbteilungBean abteilungBean;

    @EJB
    ProjectBean projectBean;

    @EJB
    ValidationBean validationBean;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Alle vorhandenen Abteilungen ermitteln
        request.setAttribute("abteilungen", this.abteilungBean.findAllSorted());

        // Anfrage an dazugerhörige JSP weiterleiten
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/projects/abteilung_list.jsp");
        dispatcher.forward(request, response);

        // Alte Formulardaten aus der Session entfernen
        HttpSession session = request.getSession();
        session.removeAttribute("abteilungen_form");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Angeforderte Aktion ausführen        
        String action = request.getParameter("action");

        if (action == null) {
            action = "";
        }

        switch (action) {
            case "create":
                this.createAbteilung(request, response);
                break;
            case "delete":
                this.deleteAbteilungen(request, response);
                break;
        }
    }

    /**
     * Aufgerufen in doPost(): Neue Abteilung anlegen
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void createAbteilung(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Formulareingaben prüfen
        String name = request.getParameter("name");

        Abteilung abteilung = new Abteilung(name);
        List<String> errors = this.validationBean.validate(abteilung);

        // Neue Abteilung anlegen
        if (errors.isEmpty()) {
            this.abteilungBean.saveNew(abteilung);
        }

        // Browser auffordern, die Seite neuzuladen
        if (!errors.isEmpty()) {
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            formValues.setErrors(errors);

            HttpSession session = request.getSession();
            session.setAttribute("abteilungen_form", formValues);
        }

        response.sendRedirect(request.getRequestURI());
    }

    /**
     * Aufgerufen in doPost(): Markierte Abteilungen löschen
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void deleteAbteilungen(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Markierte Abteilung IDs auslesen
        String[] abteilungIds = request.getParameterValues("abteilung");

        if (abteilungIds == null) {
            abteilungIds = new String[0];
        }

        // Abteilungen löschen
        for (String abteilungId : abteilungIds) {
            // Zu löschende Abteilung ermitteln
            Abteilung abteilung;

            try {
                abteilung = this.abteilungBean.findById(Long.parseLong(abteilungId));
            } catch (NumberFormatException ex) {
                continue;
            }

            if (abteilung == null) {
                continue;
            }

            // Bei allen betroffenen Aufgaben, den Bezug zur Abteilung aufheben
            List<Project> projects = abteilung.getProjects();

            if (projects != null) {
                projects.forEach((Project project) -> {
                    project.setAbteilung(null);
                    this.projectBean.update(project);
                });
            }

            // Und weg damit
            this.abteilungBean.delete(abteilung);
        }

        // Browser auffordern, die Seite neuzuladen
        response.sendRedirect(request.getRequestURI());
    }

}
