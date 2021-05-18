package com.decagon.obamax.kanban.service;

import com.decagon.obamax.kanban.exception.RecordNotFoundException;
import com.decagon.obamax.kanban.model.Task;
import com.decagon.obamax.kanban.model.User;
import com.decagon.obamax.kanban.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    TaskRepository repository;

    public List<Task> getAllTasks(User user)
    {
        List<Task> result = repository.findAllByUser(user);

        if(result.size() > 0) {
            return result;
        } else {
            return new ArrayList<>();
        }
    }

    public Task getTaskById (Long id) throws RecordNotFoundException
    {
        Optional<Task> task = repository.findById(id);

        if(task.isPresent()) {
            return task.get();
        } else {
            throw new RecordNotFoundException("No Task record exist for given id");
        }
    }

    public Task createOrUpdateTask(Task entity)
    {
        entity = repository.save(entity);

        return entity;
    }

    public void deleteTaskById(Long id) throws RecordNotFoundException
    {
        Optional<Task> task = repository.findById(id);

        if(task.isPresent())
        {
            repository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No Task record exist for given id");
        }
    }

}
