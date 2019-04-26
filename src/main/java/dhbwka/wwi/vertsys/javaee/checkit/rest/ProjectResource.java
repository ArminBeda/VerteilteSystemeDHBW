package dhbwka.wwi.vertsys.javaee.checkit.rest;



import dhbwka.wwi.vertsys.javaee.checkit.projects.ejb.ProjectBean;
import dhbwka.wwi.vertsys.javaee.checkit.projects.jpa.Project;
import dhbwka.wwi.vertsys.javaee.checkit.rest.tdos.ProjectDTO;
import dhbwka.wwi.vertsys.javaee.checkit.rest.tdos.ProjectFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * REST-Webservice zur Verwaltung von Projects.
 */
@Path("Projects")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProjectResource {

    @EJB
    ProjectBean projectBean;
    
    @EJB
    ProjectFacade projectFacade;

    /**
     * GET /api/Projects/
     *
     * GET /api/Projects/?query=…
     *
     * Auslesen einer Liste von Musikstücken. Optional kann an die URL der
     * Parameter ?query=… angehängt werden, um die Liste auf bestimmte Stücke
     * passend zum übergebenen Suchbegriff zu begrenzen.
     *
     * @param query Suchbegriff
     * @return Eine Liste mit allen gefundenen Stücken
     */
    
    @GET
    public List<ProjectDTO> findProjects(@QueryParam("query") @DefaultValue("") String query) {        
        return this.projectFacade.findByQuery(query);
    }
    
    
    @GET
    public List<ProjectDTO> findProjects() {        
        return this.projectFacade.findAll();
    }

    /**
     * POST /api/Projects/
     *
     * Speichern eines neuen Projects.Der Project muss im JSON-Format an den Server
     * geschickt werden.
     *
     * @param project Zu speichernder Project
     * @return Gespeicherte Projectdaten
     */
    @POST
    public Project saveNewProject(@Valid Project project) {
        return this.projectBean.saveNew(project);
    }

    /**
     * GET /api/Projects/{id}/
     *
     * Auslesen eines einzelnen Projects anhand seiner ID.
     *
     * @param id ID des gesuchten Projects
     * @return Gefundener Project
     */
    @Path("{id}")
    @GET
    public ProjectDTO getProject(@PathParam("id") long id) {
        
        return new ProjectDTO( this.projectBean.findById(id));
    }

    /**
     * PUT /api/Projects/{id}/
     *
     * Aktualisieren eines vorhandenen Projects.Der Project muss im JSON-Format an den
     * Server geschickt werden.
     *
     * @param project Zu speichernder Project
     * @return Gespeicherte Projectdaten
     */
    @Path("{id}")
    @PUT
    public Project Response(@Valid Project project) {
        return this.projectBean.update(project);
    }

    /**
     * DELETE /api/Projects/{id}/
     *
     * Löschen eines vorhandenen Projects.
     *
     * @param id ID des zu löschenden Projects
     * @return Daten des gelöschten Projects
     */
    @Path("{id}")
    @DELETE
    public Project deleteProject(@PathParam("id") long id) {
        Project project = this.projectBean.findById(id);

        if (project != null) {
            this.projectBean.delete(project);
        }

        return project;
    }



}
