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

import dhbwka.wwi.vertsys.javaee.checkit.common.ejb.UserBean;
import dhbwka.wwi.vertsys.javaee.checkit.rest.tdos.UserDTO;
import dhbwka.wwi.vertsys.javaee.checkit.rest.tdos.UserFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author BEDAAR
 */
@Path("Users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
    
    @EJB
    UserFacade userFacade;
    
    @EJB
    UserBean userBean;
    

    
    @GET
    @Path("{id}")
    public UserDTO find(@PathParam("id") String id) {
        return userFacade.findUser(id);
    }
    
    
    @GET
    public List<UserDTO> findAllDTO() {
        return userFacade.findAll();
    }

    

    
}
