package com.spring.pms.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.spring.pms.Dto.Projectdto;
import com.spring.pms.Entity.Project;
import com.spring.pms.Entity.User;
import com.spring.pms.Exceptions.DetailsNotFoundException;
import com.spring.pms.Repository.ProjectRepo;
import com.spring.pms.Repository.UserRepo;

@Service
public class ProjectService {
	@Autowired 
	private ProjectRepo projectRepo;
	@Autowired
	private UserRepo userRepo;
	
	
//	@Autowired
//	private PasswordEncoder encoder;
	
	public List<Project>getAllProjects()
	{
		return projectRepo.findAll();
	}
	public Project getAllProject( int id)
	{
		return projectRepo.findById(id);
	}
	
	public void postProject( Project project)
	{
		//Set<User> user = project.getUser();
		projectRepo.save(project);

//		User user = userRepo.findById(id);
//		user.getProjects().add(project);
	    //user.forEach(users -> users.setPassword(encoder.encode(users.getPassword())));
	//	projectRepo.save(project);
		
	}
	
	public Project updateProject( Project project ,   int id)
	{
		Project projectupdate=projectRepo.findById(id);
		projectupdate.setDeadlineDate(project.getDeadlineDate());
		projectupdate.setDescription(project.getDescription());
		projectupdate.setName(project.getName());
		projectupdate.setId(project.getId());
		projectupdate.setStatus(project.getStatus());
		projectupdate.setStartDate(project.getStartDate());
		projectRepo.save(projectupdate);
		
		return projectupdate;
	}
	
	public void deleteProject(  int id)
	{
		projectRepo.deleteById(id);
	}
	
	 public void createProjectWithUsers(Projectdto projectDTO, int id) {
//		 Project project1=projectRepo.findById(id);
//			if(project1==null)
//			{
//				throw new DetailsNotFoundException("Projects_were_not_found_with_id");
//			}
//
//		 
//		 Project project = projectRepo.findById(id);
//		 List<Integer> userIds = projectDTO.getUserIds();
//		 for(int i:userIds)
//		 {
//			 User users1 = userRepo.findById(i);
//			 if(users1==null)
//			 {
//					throw new DetailsNotFoundException("Users_were_not_found_with_id");
//
//			 }
//		 }
//		 
//	        List<User> users = userRepo.findAllById(projectDTO.getUserIds());
//	        
//	        users.forEach(e->e.getProjects().add(project));
//	        
//	        projectRepo.save(project);
//	        userRepo.saveAll(users);
		 
		 
		 User users1 = userRepo.findById(id);
		 if(users1==null)
				{
					throw new DetailsNotFoundException("Projects_were_not_found_with_id");
				}
		 List<Integer> userIds = projectDTO.getUserIds();
		 for(int i:userIds)
		 {
             Project project = projectRepo.findById(i);
             if(project==null)
    			 {
    					throw new DetailsNotFoundException("Users_were_not_found_with_id");
    
    			 }
    		 users1.getProjects().add(project);

			
		 }
		 userRepo.save(users1);
	        
	    }

}
