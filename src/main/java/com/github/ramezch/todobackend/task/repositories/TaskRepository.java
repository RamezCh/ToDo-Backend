package com.github.ramezch.todobackend.task.repositories;

import com.github.ramezch.todobackend.task.models.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends MongoRepository<Task, String> {
}
