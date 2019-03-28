/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhbwka.wwi.vertsys.javaee.checkit.projects.web;

import dhbwka.wwi.vertsys.javaee.checkit.common.ejb.UserBean;
import dhbwka.wwi.vertsys.javaee.checkit.common.web.FormValues;
import dhbwka.wwi.vertsys.javaee.checkit.projects.ejb.AbteilungBean;
import dhbwka.wwi.vertsys.javaee.checkit.projects.ejb.ProjectBean;
import dhbwka.wwi.vertsys.javaee.checkit.common.ejb.ValidationBean;
import dhbwka.wwi.vertsys.javaee.checkit.common.jpa.User;
import dhbwka.wwi.vertsys.javaee.checkit.common.web.WebUtils;
import dhbwka.wwi.vertsys.javaee.checkit.projects.jpa.Abteilung;
import dhbwka.wwi.vertsys.javaee.checkit.projects.jpa.MitarbeiterName;
import dhbwka.wwi.vertsys.javaee.checkit.projects.jpa.Priority;
import dhbwka.wwi.vertsys.javaee.checkit.projects.jpa.Project;
import dhbwka.wwi.vertsys.javaee.checkit.projects.jpa.ProjectStatus;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import static jdk.internal.net.http.common.Log.errors;

/**
 * Seite zum Anzeigen und Bearbeiten der Abteilungen. Die Seite besitzt ein
 * Formular, mit dem eine neue Abteilung angelegt werden kann, sowie eine Liste,
 * die zum Löschen der Abteilungen verwendet werden kann.
 */

/**
 *
 * @author aishach
 */
@WebServlet(urlPatterns = {"/app/projects/useredit/"})
public class UserEditServlet extends HttpServlet{
    
    
    @EJB
    UserBean userbean;

    @EJB
    ValidationBean validationBean;
    
    
    
    
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
      
        
/*// Verfügbare Abteilungen und Stati für die Suchfelder ermitteln
        request.setAttribute("username", this.UserBean.findAllSorted());
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
        
        */

       
        
        
    request.setAttribute("signup_form", this.createUserForm(userbean.getCurrentUser()));
        
        
        
        
    
            request.getRequestDispatcher("/WEB-INF/projects/user_edit.jsp").forward(request, response);
            
    
    } 
    
  @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Formulareingaben auslesen        
        User user = this.userbean.getCurrentUser();
        
        
        String vorname = request.getParameter("vorname");
        String nachname = request.getParameter("nachname");
        //String altesPassword = request.getParameter("altesPassword"); 
        String password1 = request.getParameter("password1");
        String password2 = request.getParameter("password2");

        List<String> errors = this.validationBean.validate(user);
        this.validationBean.validate(user.getPassword(), errors);
        
        if (password1 != null && password2 != null && !password1.equals(password2)) {
            errors.add("Die beiden Passwörter stimmen nicht überein.");
        }
        
       user.setVorname(vorname);
       user.setNachname(nachname);
        
       
        
        
        if (password1 != null && password1 != "" && errors.isEmpty()) {
        try {
            this.userbean.changePassword(user, password1);
         } catch (UserBean.InvalidCredentialsException ex) {
            Logger.getLogger(UserEditServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        
     if (errors.isEmpty()) {
            this.userbean.update(user);
        }
   
        
      
            response.sendRedirect(WebUtils.appUrl(request, "/app/dashboard/"));
      
    }
    
    
       private FormValues createUserForm(User owner) {
        Map<String, String[]> values = new HashMap<>();

        values.put("new_vorname", new String[]{
            owner.getVorname()
        });
        
       values.put("new_nachname", new String[]{
            owner.getNachname()
        });

        
        
       

        FormValues formValues = new FormValues();
        formValues.setValues(values);
        return formValues;
    } 
}

    
    
    
    
    

    

