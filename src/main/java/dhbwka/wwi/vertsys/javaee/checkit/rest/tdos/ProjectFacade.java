/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhbwka.wwi.vertsys.javaee.checkit.rest.tdos;

import dhbwka.wwi.vertsys.javaee.checkit.projects.ejb.ProjectBean;
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
public class ProjectFacade  {
    
    @EJB
    ProjectBean projectBean;
    
        public List<ProjectDTO> findAll(){
        List<Project> projects = projectBean.findAll();
        return projects.stream().map((project) -> {
            ProjectDTO projectDTO = new ProjectDTO(project);
            int b = 5;
            return projectDTO;
        }).collect(Collectors.toList());
    }
        
        
       public List<ProjectDTO> findByQuery(String query){
        List<Project> projects = projectBean.findByQuery(query);
        return projects.stream().map((project) -> {
            ProjectDTO projectDTO = new ProjectDTO(project);
            int b = 5;
            return projectDTO;
        }).collect(Collectors.toList());
    }
       
    
}
