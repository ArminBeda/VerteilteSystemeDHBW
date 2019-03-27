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

import dhbwka.wwi.vertsys.javaee.checkit.projects.ejb.AbteilungBean;
import dhbwka.wwi.vertsys.javaee.checkit.projects.ejb.ProjectBean;
import dhbwka.wwi.vertsys.javaee.checkit.projects.jpa.Abteilung;
import dhbwka.wwi.vertsys.javaee.checkit.projects.jpa.Project;
import dhbwka.wwi.vertsys.javaee.checkit.projects.jpa.ProjectStatus;
import dhbwka.wwi.vertsys.javaee.checkit.projects.jpa.Priority;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet für die tabellarische Auflisten der Aufgaben.
 */
@WebServlet(urlPatterns = {"/app/projects/list/"})
public class ProjectListServlet extends HttpServlet {

    @EJB
    private AbteilungBean abteilungBean;
    
    @EJB
    private ProjectBean projectBean;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verfügbare Abteilungen und Stati für die Suchfelder ermitteln
        request.setAttribute("abteilungen", this.abteilungBean.findAllSorted());
        request.setAttribute("statuses", ProjectStatus.values());

        // Suchparameter aus der URL auslesen
        String searchText = request.getParameter("search_text");
        String searchAbteilung = request.getParameter("search_abteilung");
        String searchStatus = request.getParameter("search_status");

        // Anzuzeigende Aufgaben suchen
        Abteilung abteilung = null;
        ProjectStatus status = null;

        if (searchAbteilung != null) {
            try {
                abteilung = this.abteilungBean.findById(Long.parseLong(searchAbteilung));
            } catch (NumberFormatException ex) {
                abteilung = null;
            }
        }

        if (searchStatus != null) {
            try {
                status = ProjectStatus.valueOf(searchStatus);
            } catch (IllegalArgumentException ex) {
                status = null;
            }

        }

        List<Project> projects = this.projectBean.search(searchText, abteilung, status);
        request.setAttribute("projects", projects);
        
        

        // Anfrage an die JSP weiterleiten
        request.getRequestDispatcher("/WEB-INF/projects/project_list.jsp").forward(request, response);
    }
}
