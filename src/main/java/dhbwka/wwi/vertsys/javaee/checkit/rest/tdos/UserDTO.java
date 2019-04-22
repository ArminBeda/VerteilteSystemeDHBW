/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhbwka.wwi.vertsys.javaee.checkit.rest.tdos;

import dhbwka.wwi.vertsys.javaee.checkit.common.jpa.User;

/**
 *
 * @author BEDAAR
 */
public class UserDTO {
    
    private String username, firstname, lastname;
    
        public UserDTO(String username, String firstname, String lastname) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
    }
        
  public UserDTO(User user){
      User thisuser = user; 
      
      thisuser = user;
      
        this.username = user.getUsername();
        this.firstname = user.getVorname();
        this.lastname = user.getNachname();
    }
        
            public UserDTO() {
    }


    
    
    //<editor-fold>

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    //</editor-fold>
    
        
        
    
}
