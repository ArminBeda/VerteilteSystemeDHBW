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

import dhbwka.wwi.vertsys.javaee.checkit.common.web.WebUtils;
import dhbwka.wwi.vertsys.javaee.checkit.common.web.FormValues;
import dhbwka.wwi.vertsys.javaee.checkit.projects.ejb.AbteilungBean;
import dhbwka.wwi.vertsys.javaee.checkit.projects.ejb.ProjectBean;
import dhbwka.wwi.vertsys.javaee.checkit.common.ejb.UserBean;
import dhbwka.wwi.vertsys.javaee.checkit.common.ejb.ValidationBean;
import dhbwka.wwi.vertsys.javaee.checkit.projects.jpa.MitarbeiterName;
import dhbwka.wwi.vertsys.javaee.checkit.projects.jpa.Project;
import dhbwka.wwi.vertsys.javaee.checkit.projects.jpa.ProjectStatus;
import dhbwka.wwi.vertsys.javaee.checkit.projects.jpa.Priority;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Seite zum Anlegen oder Bearbeiten einer Aufgabe.
 */
@WebServlet(urlPatterns = "/app/projects/project/*")
public class ProjectEditServlet extends HttpServlet {

    @EJB
    ProjectBean projectBean;

    @EJB
    AbteilungBean abteilungBean;

    @EJB
    UserBean userBean;

    @EJB
    ValidationBean validationBean;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verfügbare Abteilungen und Stati für die Suchfelder ermitteln
        request.setAttribute("abteilungen", this.abteilungBean.findAllSorted());
        request.setAttribute("statuses", ProjectStatus.values());
        request.setAttribute("priorities", Priority.values());
     request.setAttribute("mitarbeiterName", MitarbeiterName.values());
        // Zu bearbeitende Aufgabe einlesen
        HttpSession session = request.getSession();

        Project project = this.getRequestedProject(request);
        request.setAttribute("edit", project.getId() != 0);
                                
        if (session.getAttribute("project_form") == null) {
            // Keine Formulardaten mit fehlerhaften Daten in der Session,
            // daher Formulardaten aus dem Datenbankobjekt übernehmen
            request.setAttribute("project_form", this.createProjectForm(project));
        }

        // Anfrage an die JSP weiterleiten
        request.getRequestDispatcher("/WEB-INF/projects/project_edit.jsp").forward(request, response);
        
        session.removeAttribute("project_form");
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
            case "save":
                this.saveProject(request, response);
                break;
            case "delete":
                this.deleteProject(request, response);
                break;
        }
    }

    /**
     * Aufgerufen in doPost(): Neue oder vorhandene Aufgabe speichern
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void saveProject(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Formulareingaben prüfen
        List<String> errors = new ArrayList<>();

        String projectAbteilung = request.getParameter("project_abteilung");
        String projectDueDate = request.getParameter("project_due_date");
        String projectDueTime = request.getParameter("project_due_time");
        String projectBeginDate = request.getParameter("project_begin_date");
        String projectBeginTime = request.getParameter("project_begin_time");
        String projectStatus = request.getParameter("project_status");
        String projectPriority = request.getParameter("project_priority");
        String projectShortText = request.getParameter("project_short_text");
        String projectLongText = request.getParameter("project_long_text");
        String extErne =request.getParameter("project_is_extern");
        boolean extern = request.getParameter("project_is_extern") != null;
        String mitarbeiterName = request.getParameter("mitarbeiterName");
        
        Project project = this.getRequestedProject(request);

        if (projectAbteilung != null && !projectAbteilung.trim().isEmpty()) {
            try {
                project.setAbteilung(this.abteilungBean.findById(Long.parseLong(projectAbteilung)));
            } catch (NumberFormatException ex) {
                // Ungültige oder keine ID mitgegeben
            }
        }

        Date dueDate = WebUtils.parseDate(projectDueDate);
        Time dueTime = WebUtils.parseTime(projectDueTime);
        Date beginDate = WebUtils.parseDate(projectBeginDate);
        Time beginTime = WebUtils.parseTime(projectBeginTime);

        if (dueDate != null) {
            project.setDueDate(dueDate);
        } else {
            errors.add("Das Datum muss dem Format dd.mm.yyyy entsprechen.");
        }
        if (beginDate != null) {
            project.setBeginDate(beginDate);
        } else {
            errors.add("Das Datum muss dem Format dd.mm.yyyy entsprechen.");
        }

        if (dueTime != null) {
            project.setDueTime(dueTime);
        } else {
            errors.add("Die Uhrzeit muss dem Format hh:mm:ss entsprechen.");
        }
         if (beginTime != null) {
            project.setBeginTime(beginTime);
        } else {
            errors.add("Die Uhrzeit muss dem Format hh:mm:ss entsprechen.");
        }

        try {
            project.setStatus(ProjectStatus.valueOf(projectStatus));
        } catch (IllegalArgumentException ex) {
            errors.add("Der ausgewählte Status ist nicht vorhanden.");
        }
        
       try {
            project.setPriority(Priority.valueOf(projectPriority));
        } catch (IllegalArgumentException ex) {
            errors.add("Der ausgewählte Priority ist nicht vorhanden.");
        }
       
       
        project.setExtern(extern);

        project.setShortText(projectShortText);
        project.setLongText(projectLongText);

        this.validationBean.validate(project, errors);

        // Datensatz speichern
        if (errors.isEmpty()) {
            this.projectBean.update(project);
        }

        // Weiter zur nächsten Seite
        if (errors.isEmpty()) {
            // Keine Fehler: Startseite aufrufen
            response.sendRedirect(WebUtils.appUrl(request, "/app/projects/list/"));
        } else {
            // Fehler: Formuler erneut anzeigen
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            formValues.setErrors(errors);

            HttpSession session = request.getSession();
            session.setAttribute("project_form", formValues);

            response.sendRedirect(request.getRequestURI());
        }
    }

    /**
     * Aufgerufen in doPost: Vorhandene Aufgabe löschen
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void deleteProject(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Datensatz löschen
        Project project = this.getRequestedProject(request);
        this.projectBean.delete(project);

        // Zurück zur Übersicht
        response.sendRedirect(WebUtils.appUrl(request, "/app/projects/list/"));
    }

    /**
     * Zu bearbeitende Aufgabe aus der URL ermitteln und zurückgeben. Gibt
     * entweder einen vorhandenen Datensatz oder ein neues, leeres Objekt
     * zurück.
     *
     * @param request HTTP-Anfrage
     * @return Zu bearbeitende Aufgabe
     */
    private Project getRequestedProject(HttpServletRequest request) {
        // Zunächst davon ausgehen, dass ein neuer Satz angelegt werden soll
        Project project = new Project();
        project.setOwner(this.userBean.getCurrentUser());
       
        project.setDueDate(new Date(System.currentTimeMillis() + 86400000 ) );
        project.setDueTime(new Time(System.currentTimeMillis()));
        project.setBeginDate(new Date(System.currentTimeMillis()));
        project.setBeginTime(new Time(System.currentTimeMillis()));

        // ID aus der URL herausschneiden
        String projectId = request.getPathInfo();

        if (projectId == null) {
            projectId = "";
        }

        projectId = projectId.substring(1);

        if (projectId.endsWith("/")) {
            projectId = projectId.substring(0, projectId.length() - 1);
        }

        // Versuchen, den Datensatz mit der übergebenen ID zu finden
        try {
            project = this.projectBean.findById(Long.parseLong(projectId));
        } catch (NumberFormatException ex) {
            // Ungültige oder keine ID in der URL enthalten
        }

        return project;
    }

    /**
     * Neues FormValues-Objekt erzeugen und mit den Daten eines aus der
     * Datenbank eingelesenen Datensatzes füllen. Dadurch müssen in der JSP
     * keine hässlichen Fallunterscheidungen gemacht werden, ob die Werte im
     * Formular aus der Entity oder aus einer vorherigen Formulareingabe
     * stammen.
     *
     * @param project Die zu bearbeitende Aufgabe
     * @return Neues, gefülltes FormValues-Objekt
     */
    private FormValues createProjectForm(Project project) {
        Map<String, String[]> values = new HashMap<>();

        values.put("project_owner", new String[]{
            project.getOwner().getUsername()
        });

        if (project.getAbteilung() != null) {
            values.put("project_abteilung", new String[]{
                "" + project.getAbteilung().getId()
            });
        }

        values.put("project_due_date", new String[]{
            WebUtils.formatDate(project.getDueDate())
        });
        
        values.put("project_begin_date", new String[]{
            WebUtils.formatDate(project.getBeginDate())
        });
        values.put("project_due_time", new String[]{
            WebUtils.formatTime(project.getDueTime())
        });
 
        values.put("project_begin_time", new String[]{
            WebUtils.formatTime(project.getBeginTime())
        });

        values.put("project_status", new String[]{
            project.getStatus().toString()
        });
        
          values.put("project_priority", new String[]{
            project.getPriority().toString()
        });
                     values.put("mitarbeiterName", new String[]{
            project.getMitarbeitername().toString()
        });


        values.put("project_short_text", new String[]{
            project.getShortText()
        });

        values.put("project_long_text", new String[]{
            project.getLongText()
        });

        boolean ex = project.getExtern();
        if (!project.getExtern()) {
             values.put("is_extern", new String[]{
            ""
            });
        }
        else {
              values.put("is_extern", null);
        }
        
        System.out.println("Moin Leud");
        
       

        FormValues formValues = new FormValues();
        formValues.setValues(values);
        return formValues;
    }
    
    
   public void setExtern() {
        int moin = 5;
        moin = 7;
    }

}
