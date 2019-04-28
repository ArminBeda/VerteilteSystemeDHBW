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
package dhbwka.wwi.vertsys.javaee.checkit.rest.tdos;

import dhbwka.wwi.vertsys.javaee.checkit.common.ejb.UserBean;
import dhbwka.wwi.vertsys.javaee.checkit.common.jpa.User;
import dhbwka.wwi.vertsys.javaee.checkit.projects.jpa.Project;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author BEDAAR
 */
@Stateless
public class UserFacade {
    
     @EJB
    UserBean userBean;

    public List<UserDTO> findAll() {
        List<User> users = userBean.findAll();
        return users.stream().map((user) -> {
            UserDTO userDTO = new UserDTO(user);
            return userDTO;
        }).collect(Collectors.toList());
    }

    public UserDTO findUser(String id) {
        return new UserDTO(userBean.findUser(id));
    }
    
           public List<UserDTO> findByQuery(String query){
            List<User> users = userBean.findByQuery(query);
            return users.stream().map((user) -> {
            UserDTO userDTO = new UserDTO(user);
            return userDTO;
        }).collect(Collectors.toList());
    }
       
    
}
