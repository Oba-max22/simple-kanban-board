package com.decagon.obamax.kanban.repositories;

import com.decagon.obamax.kanban.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

}
