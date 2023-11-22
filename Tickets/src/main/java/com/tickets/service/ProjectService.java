package com.tickets.service;

import com.tickets.Entity.CustomizedFiled;
import com.tickets.Entity.Project;
import com.tickets.Entity.Ticket;
import com.tickets.Exception.ResourceNotFoundException;
import com.tickets.Repository.CustomizedFiledRepo;
import com.tickets.Repository.ProjectRepo;
import com.tickets.Repository.TicketRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepo projectRepo;

    @Autowired
    private CustomizedFiledRepo customizedFiledRepo;
    @Autowired
    private TicketRepo ticketRepo;
    public Project createProject(Project project) {
        return projectRepo.save(project);
    }

    public List<Project> getAllProjects() {
        return projectRepo.findAll();
    }

    public Optional<Project> getProjectById(String id) {
        return projectRepo.findById(id);
    }

    public void deleteProjectById(String id) {
        projectRepo.deleteById(id);
    }
    public Project updateProjectWithCustomField(String id, CustomizedFiled customizedFiled){

      Optional<Project> project =projectRepo.findById(id);
      List<CustomizedFiled> customizedFileds=project.get().getCustomizedFileds();
      if(customizedFileds==null){
          customizedFiled= customizedFiledRepo.save(customizedFiled);
          customizedFileds= new ArrayList<>();
          customizedFileds.add(customizedFiled);
      }else {

           List<CustomizedFiled> finalCustomizedFileds = customizedFileds;
          CustomizedFiled finalCustomizedFiled = customizedFiled;
          boolean nameExists = finalCustomizedFileds.stream()
                  .anyMatch(existingField -> existingField.getName().trim().equals(finalCustomizedFiled.getName().trim()));

          if (!nameExists) {
              customizedFiled= customizedFiledRepo.save(customizedFiled);

              customizedFileds.add(customizedFiled);
          } else {
              throw new IllegalArgumentException("CustomizedField with the same name already exists.");
          }
      }


        project.get().setCustomizedFileds(customizedFileds);
        System.out.println(project);
        return  projectRepo.save(project.get());
    }
    public Project deleteCustomField(String projectId, String fieldId) {
        Optional<Project> optionalProject = projectRepo.findById(projectId);
       Optional<CustomizedFiled> customizedFiled= customizedFiledRepo.findById(fieldId);
        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();


            project.getCustomizedFileds().removeIf(customField -> customField.getId().equals(fieldId));


            customizedFiledRepo.deleteById(fieldId);

            List<Ticket> tickets = ticketRepo.findByProjectIn_Id(projectId);

            // Iterate through the tickets and remove the custom field with the matching name

            tickets.forEach(ticket -> {
                Map<String, Object> customFields = ticket.getCustomFields();
                customFields.entrySet().removeIf(entry -> entry.getKey().equals(customizedFiled.get().getName()));
            });
            ticketRepo.saveAll(tickets);
            System.out.println(tickets);
            return projectRepo.save(project);
        } else {
            throw new ResourceNotFoundException("Project not found with id: " + projectId);
        }
    }
}








