/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhbwka.wwi.vertsys.javaee.checkit.projects.ejb;

import dhbwka.wwi.vertsys.javaee.checkit.common.ejb.EntityBean;
import dhbwka.wwi.vertsys.javaee.checkit.projects.jpa.Mitarbeiter;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Einfache EJB mit den üblichen CRUD-Methoden für Abteilungen.
 */

@Stateless
public class MitarbeiterBean{

    @PersistenceContext
    protected EntityManager em;

    public MitarbeiterBean() {
    }

    public List<Mitarbeiter> findAllEntries() {
        return em.createQuery("SELECT e FROM CHECKIT.CHECKIT_MITARBEITER e "
                            + "  ORDER BY nachname DESC, ")
                .getResultList();
    }

}