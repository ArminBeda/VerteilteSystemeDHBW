/*
 * Copyright © 2019 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.checkit.dashboard.web;

import dhbwka.wwi.vertsys.javaee.checkit.dashboard.ejb.DashboardContentProvider;
import dhbwka.wwi.vertsys.javaee.checkit.dashboard.ejb.DashboardSection;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet für die Startseite mit dem Übersichts-Dashboard.
 */
@WebServlet(urlPatterns = {"/app/dashboard/"})
public class DashboardServlet extends HttpServlet {

    // Kacheln für Aufgaben
    @EJB(beanName = "projects")
    DashboardContentProvider projectContent;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Dashboard-Rubriken und Kacheln erzeugen und im Request Context ablegen
        List<DashboardSection> sections = new ArrayList<>();
        request.setAttribute("sections", sections);
        
        projectContent.createDashboardContent(sections);

        // Anfrage an die JSP weiterleiten
        request.getRequestDispatcher("/WEB-INF/dashboard/dashboard.jsp").forward(request, response);
    }

}
