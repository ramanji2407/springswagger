package com.spring.pms.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.spring.pms.Entity.Subtask;
import com.spring.pms.Entity.Task;
import com.spring.pms.Entity.User;
import com.spring.pms.Exceptions.BadRequestException;
import com.spring.pms.Exceptions.DetailsNotFoundException;
import com.spring.pms.Repository.SubtaskRepository;
import com.spring.pms.Repository.TaskRepository;
import com.spring.pms.Repository.UserRepo;

@Service
public class SubTaskService {
	@Autowired
	private SubtaskRepository subtaskRepository;
	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private UserRepo userRepo;
	public List<Subtask>getAllSubtasks()
	{
		return subtaskRepository.findAll();
	}
	public Subtask getAllSubtask( long id)
	{
		System.out.println(id);
		
		return subtaskRepository.findById(id);
	}
	
	public void postSubtask( Subtask subtask,  long id)
	{
		Task task=taskRepository.findById(id);
		if(task==null)
		{
			throw new DetailsNotFoundException("Task_was_not_found");

		}
		if(task.getStatus().equals("Completed"))
		{
			throw new BadRequestException("Cannot_create_sub_task_beacuse_task was_completed");
		}
		subtask.setTask(task);
		task.getSubtask().add(subtask);
		//taskRepository.save(task);
		int userid=task.getUser().getId();
		System.out.println(userid);
		User user=userRepo.findById(userid);
		subtask.setUsers(user);
		user.getSubtasks().add(subtask);
	//	userRepo.save(user);
		subtaskRepository.save(subtask);
		
	}
	public Subtask updateSubtask( Subtask subtask ,   long id)
	{
		Subtask subtask2=subtaskRepository.findById(id);
		subtask2.setDescription(subtask.getDescription());
		subtask2.setId(subtask.getId());
		subtask2.setName(subtask.getName());
		subtask2.setStatus(subtask.getStatus());
		subtask2.setDue_date(subtask.getDue_date());
		subtaskRepository.save(subtask2);
		return subtask2;
	}
	
	public void deleteSubtask(  long id)
	{
		subtaskRepository.deleteById(id);
	}

}
