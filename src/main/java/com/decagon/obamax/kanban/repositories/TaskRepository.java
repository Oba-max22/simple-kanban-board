package com.decagon.obamax.kanban.repositories;

import com.decagon.obamax.kanban.model.Task;
import com.decagon.obamax.kanban.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Override
    List<Task> findAllById(Iterable<Long> iterable);
    List<Task> findAllByUser(User user);

}