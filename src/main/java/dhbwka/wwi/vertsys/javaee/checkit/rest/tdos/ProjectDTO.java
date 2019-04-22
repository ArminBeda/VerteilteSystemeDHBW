/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhbwka.wwi.vertsys.javaee.checkit.rest.tdos;

import dhbwka.wwi.vertsys.javaee.checkit.projects.jpa.Project;
import java.text.Format;
import java.text.SimpleDateFormat;

/**
 *
 * @author BEDAAR
 */
public class ProjectDTO {
    
      private UserDTO owner;
      private String abteilung;
      private String shortText;
      private String longText;
      private String dueDate;
      private String dueTime;
      private String beginDate;
      private String beginTime;
      private String extern;
      private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
      public ProjectDTO(Project project) {
       Project p = project;
       
      Format formatter = new SimpleDateFormat("dd.MM.yyyy");
       
       Format formatter2 = new SimpleDateFormat("HH:mm:ss");
       
       this.owner = new UserDTO(project.getOwner());
       
       if (project.getAbteilung() == null) {
            this.abteilung = "keine";
       }
       else {
          this.abteilung = project.getAbteilung().getName(); 
       }
        
        this.shortText = project.getShortText();
        this.longText = project.getLongText();
        this.dueDate = formatter.format(project.getDueDate());
        this.dueTime = formatter2.format(project.getDueDate());
        this.beginDate = formatter.format(project.getBeginDate());
        this.beginTime = formatter2.format(project.getBeginDate());
        this.extern = Boolean.toString(project.getExtern());
        this.id = String.valueOf(project.getId());
        
    }
      


    public UserDTO getOwner() {
        return owner;
    }

    public void setOwner(UserDTO owner) {
        this.owner = owner;
    }

    public String getAbteilung() {
        return abteilung;
    }

    public void setAbteilung(String abteilung) {
        this.abteilung = abteilung;
    }

    public String getShortText() {
        return shortText;
    }

    public void setShortText(String shortText) {
        this.shortText = shortText;
    }

    public String getLongText() {
        return longText;
    }

    public void setLongText(String longText) {
        this.longText = longText;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getDueTime() {
        return dueTime;
    }

    public void setDueTime(String dueTime) {
        this.dueTime = dueTime;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getExtern() {
        return extern;
    }

    public void setExtern(String extern) {
        this.extern = extern;
    }




       
       
    
}
