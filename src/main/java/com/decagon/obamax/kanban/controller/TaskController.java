package com.decagon.obamax.kanban.controller;

import com.decagon.obamax.kanban.exception.RecordNotFoundException;
import com.decagon.obamax.kanban.model.Task;
import com.decagon.obamax.kanban.model.User;
import com.decagon.obamax.kanban.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
public class TaskController {
    @Autowired
    TaskService taskService;

    @RequestMapping("/home")
    public String getAllTasks(Model model, HttpSession session)
    {
        User user = (User) session.getAttribute("currentUser");
        if(user == null){
            return "redirect:/auth";
        }
        List<Task> list = taskService.getAllTasks(user);
        model.addAttribute("tasks", list);
        model.addAttribute("task", new Task());
        return "index";
    }

    @GetMapping(path = {"/edit/{id}"})
    public String editTaskById(Model model, @PathVariable("id") Optional<Long> id)
            throws RecordNotFoundException
    {
        if (id.isPresent()) {
            Task entity = taskService.getTaskById(id.get());
            model.addAttribute("task", entity);
        } else {
            model.addAttribute("task", new Task());
        }
        return "edit-task";
    }

    @PostMapping(path = "/edit_task/{id}")
    public String updateTask(@ModelAttribute("task") Task task, @PathVariable("id") Optional<Long> id, HttpSession session){
        User user = (User) session.getAttribute("currentUser");
        task.setUser(user);
        task.setId(id.get());
        taskService.createOrUpdateTask(task);
        return "redirect:/home";
    }

    @RequestMapping(path = "/delete/{id}")
    public String deleteTaskById(@PathVariable("id") Long id)
            throws RecordNotFoundException
    {
        taskService.deleteTaskById(id);
        return "redirect:/home";
    }

    @GetMapping("/createTaskGet")
    public String createTask(Model model)
    {
        model.addAttribute("task", new Task());
        return "index";
    }

    @PostMapping(path = "/createTask/{position}")
    public String createTask(@ModelAttribute("task") Task task, @PathVariable("position") String position, HttpSession session)
    {
        User user = (User) session.getAttribute("currentUser");
        task.setUser(user);
        task.setPosition(position);
        taskService.createOrUpdateTask(task);
        return "redirect:/home";
    }

    @RequestMapping(path = {"/doing/{id}"})
    public String todoToDoing(@PathVariable("id") Optional<Long> id)
            throws RecordNotFoundException
    {
        Task entity = taskService.getTaskById(id.get());
        entity.setPosition("doing");
        taskService.createOrUpdateTask(entity);
        return "redirect:/home";
    }

    @RequestMapping(path = {"/todo/{id}"})
    public String moveToToDo(@PathVariable("id") Optional<Long> id)
            throws RecordNotFoundException
    {
        Task entity = taskService.getTaskById(id.get());
        entity.setPosition("todo");
        taskService.createOrUpdateTask(entity);
        return "redirect:/home";
    }

    @RequestMapping(path = {"/done/{id}"})
    public String moveToDone(@PathVariable("id") Optional<Long> id)
            throws RecordNotFoundException
    {
        Task entity = taskService.getTaskById(id.get());
        entity.setPosition("done");
        taskService.createOrUpdateTask(entity);
        return "redirect:/home";
    }

}
