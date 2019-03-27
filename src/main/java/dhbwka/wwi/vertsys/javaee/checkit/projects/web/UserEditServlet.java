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

/**
 *
 * @author aishach
 */
@WebServlet(urlPatterns = {"/app/projects/useredit/"})
public class UserEditServlet extends HttpServlet{
    
    
    @EJB
    UserBean userBean;

    @EJB
    ValidationBean validationBean;
    
    
    
    
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
       
    
            request.getRequestDispatcher("/WEB-INF/projects/user_edit.jsp").forward(request, response);
            
    
    } 
    
  @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Formulareingaben auslesen        
        String vornameNeu = request.getParameter("new_vorname");
        String nachnameNeu = request.getParameter("new_nachname");
        String password1Neu = request.getParameter("new_password1");
        String password2Neu = request.getParameter("new_password2");

       /* 
        // Eingaben prüfen
        User user = new User(vornameNeu, nachnameNeu, password1Neu);
        List<String> errors = this.validationBean.validate(user);
        this.validationBean.validate(user.getPassword(), errors);
        
        if (password1Neu != null && password2Neu != null && !password1Neu.equals(password2Neu)) {
            errors.add("Die beiden Passwörter stimmen nicht überein.");
        }
        */
        
        
        /*
        // Neuen Benutzer anlegen
        if (errors.isEmpty()) {
            try {
                this.userBean.signup(username, vorname, nachname, password1);
            } catch (UserBean.UserAlreadyExistsException ex) {
                errors.add(ex.getMessage());
            }
        }
        */
        
        /*
        
        // Weiter zur nächsten Seite
        if (errors.isEmpty()) {
            // Keine Fehler: Startseite aufrufen
            request.login(username, password1);
            response.sendRedirect(WebUtils.appUrl(request, "/app/dashboard/"));
        } else {
            // Fehler: Formuler erneut anzeigen
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            formValues.setErrors(errors);
            
            HttpSession session = request.getSession();
            session.setAttribute("signup_form", formValues);
            
            response.sendRedirect(request.getRequestURI());
        }
    */
    }}
    
    
    
    
    
    

    

