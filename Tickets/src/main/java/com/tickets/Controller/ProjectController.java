package com.tickets.Controller;

import com.tickets.Entity.CustomizedFiled;
import com.tickets.Entity.Project;

import com.tickets.Entity.Ticket;
import com.tickets.Exception.ResourceNotFoundException;
import com.tickets.Exception.TicketNotFoundException;
import com.tickets.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/projects")
public class ProjectController {
    private static final Logger log = LoggerFactory.getLogger(ProjectController.class);
    @Autowired
    private ProjectService projectService;

    @PostMapping()
    public Project createProject(@RequestBody Project project) {
        return projectService.createProject(project);
    }

    @GetMapping
    public List<Project> getprojects() {
        return projectService.getAllProjects();
    }

    @GetMapping("/{id}")
    public Optional<Project> getProjectById(@PathVariable String id) {
        return projectService.getProjectById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteProjectById(@PathVariable String id) {
        projectService.deleteProjectById(id);
    }
    @PutMapping("/{id}/customfield")
    public ResponseEntity<Map<String, Object>> updateProjectWithCustomField(@PathVariable String id, @RequestBody CustomizedFiled customField) {
        Map<String, Object> response = new HashMap<>();
        HttpStatus status = null;


        try {
            Project project= projectService.updateProjectWithCustomField(id, customField);
            System.out.println("this is project for frontend"+project);
            response.put("message", "Success");
            response.put("data", project);
            status = HttpStatus.OK;
        } catch (IllegalArgumentException e) {
            System.out.println("inide the exception");
            response.put("message", e.getMessage());
            response.put("data", null);
            status = HttpStatus.BAD_REQUEST;
        } catch (Exception e) {
            response.put("message", "An error occurred");
            response.put("data", null);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(response, status);


    }
    @DeleteMapping("/{projectId}/customfields/{fieldId}")
    public ResponseEntity<?> deleteCustomField(@PathVariable String projectId, @PathVariable String fieldId) {
        log.info("inside delete Custom Fileds for a project");
        try {
            Project updatedProject = projectService.deleteCustomField(projectId, fieldId);
            return ResponseEntity.ok(updatedProject);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
