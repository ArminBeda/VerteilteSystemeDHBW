/*
 * Copyright © 2018 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.checkit.projects.jpa;

import dhbwka.wwi.vertsys.javaee.checkit.common.jpa.User;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Eine zu erledigende Aufgabe.
 */
@Entity
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "project_ids")
    @TableGenerator(name = "project_ids", initialValue = 0, allocationSize = 50)
    private long id;

    @ManyToOne
    @NotNull(message = "Das Projekt muss einem Projektleiter zugeordnet werden.")
    private User owner;

    @ManyToOne
    private Abteilung abteilung;

    @Column(length = 50)
    @NotNull(message = "Die Bezeichnung darf nicht leer sein.")
    @Size(min = 1, max = 50, message = "Die Bezeichnung muss zwischen ein und 50 Zeichen lang sein.")
    private String shortText;

    @Lob
    @NotNull
    private String longText;

    @NotNull(message = "Das Datum darf nicht leer sein.")
    private Date dueDate;
    
    @NotNull(message = "Das Datum darf nicht leer sein.")
    private Date beginDate;

    @NotNull(message = "Die Uhrzeit darf nicht leer sein.")
    private Time dueTime;

    @NotNull(message = "Die Uhrzeit darf nicht leer sein.")
    private Time beginTime;

    @Enumerated(EnumType.STRING)
    @NotNull
    private ProjectStatus status = ProjectStatus.OPEN;
    
    @Enumerated(EnumType.STRING)
    @NotNull
    private Priority priority = Priority.LOW_PRIORITY;
    
    @NotNull
    boolean extern;
    
    

    //<editor-fold defaultstate="collapsed" desc="Konstruktoren">
    public Project() {
    }

    public Project(User owner, Abteilung abteilung, String shortText, String longText, Date dueDate, Time dueTime, Date beginDate, Time beginTime, boolean extern) {
        this.owner = owner;
        this.abteilung =abteilung;
        this.shortText = shortText;
        this.longText = longText;
        this.dueDate = dueDate;
        this.dueTime = dueTime;
        this.beginDate = beginDate;
        this.beginTime = beginTime;
        this.extern = extern;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Setter und Getter">
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Abteilung getAbteilung() {
        return abteilung;
    }

    public void setAbteilung(Abteilung abteilung) {
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

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Time getDueTime() {
        return dueTime;
    }

    public void setDueTime(Time dueTime) {
        this.dueTime = dueTime;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Time getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Time beginTime) {
        this.beginTime = beginTime;
    }
    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }
    
   public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
    
    public void setExtern(boolean b) {
        this.extern = b;
    }
    
    public boolean getExtern(){
        return this.extern;
    }
    
    //</editor-fold>

}
