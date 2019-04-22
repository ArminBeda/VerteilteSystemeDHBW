package dhbwka.wwi.vertsys.javaee.checkit.rest;

import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Einstiegspunkt für unseren REST-Webservice. Hier wird der URL-Prefix aller
 * Aufrufe definiert (über @ApplicationPath), sowie alle Collections und
 * Resourcen dem Webservice hinzugefügt. Diese Klasse muss daher immer angepasst
 * werden, wenn weitere Collections oder Resourcen hinzukommen.
 */
@ApplicationPath("api")
public class MusicalRestAPI extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        
        // Hier für jede Webservice-Klasse eine Zeile hinzufügen
        resources.add(CreateDemoData.class);
        resources.add(ProjectResource.class);

        return resources;
    }

}
