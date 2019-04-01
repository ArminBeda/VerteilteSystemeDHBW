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
import dhbwka.wwi.vertsys.javaee.checkit.common.jpa.User.Password;
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
    
    
    boolean errorVar = false;
    
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      
     if (!errorVar) {
         request.setAttribute("signup_form", this.createUserForm(userbean.getCurrentUser(), request)); 
      }

      
      RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/projects/user_edit.jsp");
      
       
        dispatcher.forward(request, response);
      
        // Alte Formulardaten aus der Session entfernen
        HttpSession session = request.getSession();
       
       session.removeAttribute("signup_form");

       
       
    } 
    
  @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Formulareingaben auslesen        
        User user = this.userbean.getCurrentUser();
        
        
        String vorname = request.getParameter("new_vorname");
        String nachname = request.getParameter("new_nachname");
        //String altesPassword = request.getParameter("altesPassword"); 
        String password1 = request.getParameter("password1");
        String password2 = request.getParameter("password2");
        
    //    List<String> errors = this.validationBean.validate(user);
    //    Password p = user.getPassword();
     //   this.validationBean.validate(user.getPassword(), errors);
        User testuser = new User("ttttest", vorname, nachname, "testpassword");
        List<String> errors = this.validationBean.validate(testuser);
        Password p = testuser.getPassword();
        
   
     
     
        if (password1.equals("")){
            password1 = null;
        }
        if (password2.equals("")){
            password2 = null;
        }
        
        
        
        if (password1 != null && 
            password2 != null &&
            !password1.equals("") &&
            !password2.equals("") &&
            !password1.equals(password2)) {
            errors.add("Die beiden Passwörter stimmen nicht überein.");
        }
        
        //When the user changes the account we have to validate the new dates (e.g) max 64 character
        
        if ((password1 == null && password2 == null) || (password1.equals("") && password2.equals("") ) ) {
         //If the user do not want to change the password, we only want to check a validity of the vorname and surname
         //If we try to validate currentuser, it cannot be validated, propably because of the Hashcode of the password
         //We will test it with testpassword
         this.validationBean.validate(testuser.getPassword(), errors);
        } 
        else {
         //If the user write a password in, we change the password of the testuser
         testuser.setPassword(password1);
         //and then we do the validation
         this.validationBean.validate(testuser.getPassword(), errors);
        }
        
       user.setVorname(vorname);
       user.setNachname(nachname);
        
       
        
        
        if (password1 != null && !password1.equals("") && errors.isEmpty()) {
        try {
            this.userbean.changePassword(user, password1);
         } catch (UserBean.InvalidCredentialsException ex) {
            Logger.getLogger(UserEditServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        

        
     if (errors.isEmpty()) {
         // Keine Fehler: User update, Startseite aufrufen 
            this.userbean.update(user);
            response.sendRedirect(WebUtils.appUrl(request, "/app/dashboard/"));
            this.errorVar = false;
           
       } else {
            // Fehler: Formuler erneut anzeigenx
            this.errorVar = true;
            
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            formValues.setErrors(errors);
            
            HttpSession session = request.getSession();
            session.setAttribute("signup_form", formValues);
            
            response.sendRedirect(request.getRequestURI());
        }
   
    }
    
    
       private FormValues createUserForm(User owner, HttpServletRequest request) {
        Map<String, String[]> values = new HashMap<>();
        
      
        
        String vornameToSet;
        String nachnameToSet;
        
        if (request.getParameter("new_vorname") == null || request.getParameter("new_vorname").equals("")) {
            vornameToSet = owner.getVorname();
        }
        else {
            vornameToSet = request.getParameter("new_vorname");
        }
        
        if (request.getParameter("new_nachname") == null || request.getParameter("new_nachname").equals("")) {
            nachnameToSet = owner.getNachname();
        }
        else {
            nachnameToSet = request.getParameter("new_vorname");
        }

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

    
    
    
    
    

    

