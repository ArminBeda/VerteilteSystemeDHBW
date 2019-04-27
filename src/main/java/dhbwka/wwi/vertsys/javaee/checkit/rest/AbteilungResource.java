/*
 * Copyright © 2019 Armin Beda
 * 
 * E-Mail: bedaarmin@gmail.com
 * 
 * Copyright © 2019 Gamze-Nur Topkaç
 * 
 * E-Mail: gamzetopkac@hotmail.de
 * 
 * Copyright © 2019 Aisha Choudhery
 * 
 * E-Mail: aisha.ch@hotmail.de
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.checkit.rest;

import dhbwka.wwi.vertsys.javaee.checkit.projects.ejb.AbteilungBean;
import dhbwka.wwi.vertsys.javaee.checkit.projects.ejb.ProjectBean;
import dhbwka.wwi.vertsys.javaee.checkit.projects.jpa.Abteilung;
import dhbwka.wwi.vertsys.javaee.checkit.rest.tdos.ProjectDTO;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author BEDAAR
 */
@Path("Abteilungen")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AbteilungResource {
    
    @EJB
    AbteilungBean abteilungBean;
    
    @GET
    public List<Abteilung> findProjects(@QueryParam("query") @DefaultValue("") String query) {        
        return this.abteilungBean.findByQuery(query);
    }
    
    
}
