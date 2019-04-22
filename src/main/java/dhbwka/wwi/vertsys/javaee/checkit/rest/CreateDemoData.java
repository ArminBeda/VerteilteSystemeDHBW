/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhbwka.wwi.vertsys.javaee.checkit.rest;

import dhbwka.wwi.vertsys.javaee.checkit.common.ejb.UserBean;
import dhbwka.wwi.vertsys.javaee.checkit.projects.ejb.AbteilungBean;
import dhbwka.wwi.vertsys.javaee.checkit.projects.ejb.ProjectBean;
import dhbwka.wwi.vertsys.javaee.checkit.projects.jpa.Project;
import java.sql.Date;
import java.sql.Time;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author BEDAAR
 */
/**
 * Minimaler REST-Webservice, der eine Reihe von Demodaten für uns anlegt.
 */
@Path("CreateDemoData")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CreateDemoData {

    @EJB
    ProjectBean musicPieceBean;
    
    @EJB
    UserBean userBean;
    
    @EJB
    AbteilungBean abteilungBean;

    
    

    @GET
    public StatusResponse createDemoData() {
        for (Project project : this.musicPieceBean.findByQuery("DemoTestProject")) {
            this.musicPieceBean.delete(project);
        }
        
        

        Project project = new Project(userBean.getCurrentUser(), 
        abteilungBean.findByName("Financial Services").get(0), 
        "DemoTestProjectA", 
        "Moin Leut", 
        new Date(System.currentTimeMillis()),
        new Time(System.currentTimeMillis()),
        new Date(System.currentTimeMillis() + 86400000 ),
        new Time(System.currentTimeMillis()),
        false);
        this.musicPieceBean.saveNew(project);
        
        project = new Project(userBean.getCurrentUser(), 
        abteilungBean.findByName("Financial Services").get(0), 
        "DemoTestProjectB", 
        "Moin Leut", 
        new Date(System.currentTimeMillis()),
        new Time(System.currentTimeMillis()),
        new Date(System.currentTimeMillis() + 86400000 ),
        new Time(System.currentTimeMillis()),
        false);
       this.musicPieceBean.saveNew(project);

        // Statusmeldung zurückgeben
        StatusResponse response;
        response = new StatusResponse();
        response.status = "OK";
        response.message = "Demodaten wurden angelegt.";
        return response;
    }

}