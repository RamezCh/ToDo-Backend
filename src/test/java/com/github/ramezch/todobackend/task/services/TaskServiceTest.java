package com.github.ramezch.todobackend.task.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.ramezch.todobackend.gpt.models.GPTAnswer;
import com.github.ramezch.todobackend.gpt.services.OpenAIService;
import com.github.ramezch.todobackend.task.models.Task;
import com.github.ramezch.todobackend.task.models.TaskDTO;
import com.github.ramezch.todobackend.task.models.TaskStatus;
import com.github.ramezch.todobackend.task.repositories.TaskRepository;
import com.github.ramezch.todobackend.task.utils.IdService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    private TaskRepository repo;
    private IdService idService;
    private TaskService service;
    private OpenAIService ai;

    private Task task1, task2;

    @BeforeEach
    void setUp() {
        repo = mock(TaskRepository.class);
        idService = mock(IdService.class);
        ai = mock(OpenAIService.class);
        service = new TaskService(repo, idService, ai);

        task1 = new Task("1", "eat apple", TaskStatus.OPEN);
        task2 = new Task("2", "wash hands", TaskStatus.OPEN);
    }

    @Test
    void getTasks_returnAllTasks_whenNotEmpty() {
        // GIVEN
        List<Task> expected = List.of(task1, task2);
        when(repo.findAll()).thenReturn(expected);
        // WHEN
        List<Task> actual = service.getTasks();
        // THEN
        verify(repo).findAll();
        assertIterableEquals(expected, actual);
    }

    @Test
    void getTasks_returnEmptyList_whenEmpty() {
        // GIVEN
        List<Task> expected = List.of();
        when(repo.findAll()).thenReturn(expected);
        // WHEN
        List<Task> actual = service.getTasks();
        // THEN
        verify(repo).findAll();
        assertIterableEquals(expected, actual);
    }

    @Test
    void getTaskById_returnTask_whenExist() {
        // GIVEN
        Optional<Task> expected = Optional.ofNullable(task1);
        when(repo.findById("1")).thenReturn(expected);
        // WHEN
        Optional<Task> actual = service.getTaskById("1");
        // THEN
        verify(repo).findById("1");
        assertEquals(expected, actual);

    }

    @Test
    void getTaskById_returnEmptyOptional_whenEmpty() {
        // GIVEN
        Optional<Task> expected = Optional.empty();
        when(repo.findById("3")).thenReturn(expected);
        // WHEN
        Optional<Task> actual = service.getTaskById("3");
        // THEN
        verify(repo).findById("3");
        assertEquals(expected, actual);

    }

    @Test
    void addTask_returnsCorrectDescription_whenSpellingIsCorrect() throws JsonProcessingException {
        // GIVEN
        TaskDTO taskDTO = new TaskDTO("eat apple", TaskStatus.OPEN);
        GPTAnswer gptAnswer = new GPTAnswer(true, "eat apple");
        when(ai.checkSpelling(taskDTO.description())).thenReturn(gptAnswer);
        when(idService.randomId()).thenReturn("1");
        Task expected = new Task("1", "eat apple", TaskStatus.OPEN);
        when(repo.save(expected)).thenReturn(expected);

        // WHEN
        Task actual = service.addTask(taskDTO);

        // THEN
        verify(ai).checkSpelling(taskDTO.description());
        verify(idService).randomId();
        verify(repo).save(actual);
        assertEquals(expected, actual);
    }

    @Test
    void addTask_returnsCorrectedDescription_whenSpellingIsIncorrect() throws JsonProcessingException {
        // GIVEN
        TaskDTO taskDTO = new TaskDTO("eat aplle", TaskStatus.OPEN);
        GPTAnswer gptAnswer = new GPTAnswer(false, "eat apple");
        Task expected = new Task("4", "eat apple", TaskStatus.OPEN);
        when(ai.checkSpelling(taskDTO.description())).thenReturn(gptAnswer);
        when(idService.randomId()).thenReturn("4");
        when(repo.save(expected)).thenReturn(expected);

        // WHEN
        Task actual = service.addTask(taskDTO);

        // THEN
        verify(ai).checkSpelling(taskDTO.description());
        verify(idService).randomId();
        verify(repo).save(expected);
        assertEquals(expected, actual);
    }

    @Test
    void updateTask_returnsUpdatedTaskWithCorrectDescription_whenSpellingIsCorrect() throws JsonProcessingException {
        // GIVEN
        Task task = new Task("1", "eat apple", TaskStatus.OPEN);
        GPTAnswer gptAnswer = new GPTAnswer(true, "eat apple");
        when(repo.findById("1")).thenReturn(Optional.of(task));
        when(ai.checkSpelling(task.description())).thenReturn(gptAnswer);
        Task expected = new Task("1", "eat apple", TaskStatus.OPEN);
        when(repo.save(expected)).thenReturn(expected);

        // WHEN
        Task actual = service.updateTask("1", task);

        // THEN
        verify(repo).findById("1");
        verify(ai).checkSpelling(task.description());
        verify(repo).save(actual);
        assertEquals(expected, actual);
    }

    @Test
    void updateTask_throwsException_whenTaskDoesNotExist() {
        // GIVEN
        Task task = new Task("1", "eat apple", TaskStatus.OPEN);
        when(repo.findById("1")).thenReturn(Optional.empty());

        // WHEN & THEN
        assertThrows(NoSuchElementException.class, () -> service.updateTask("1", task));

        verify(repo).findById("1");
    }

    @Test
    void deleteTask_whenExist() {
        // GIVEN
        String id = "1";
        when(repo.findById(id)).thenReturn(Optional.ofNullable(task1));
        doNothing().when(repo).deleteById(id);
        // WHEN
        service.deleteTask(id);
        // THEN
        verify(repo).findById(id);
        verify(repo).deleteById(id);
    }

    @Test
    void deleteTask_throwException_whenNotExist() {
        // GIVEN
        String id = "3";
        when(repo.findById(id)).thenThrow(new NoSuchElementException("Task with ID: " + id + " not found"));

        // WHEN & THEN
        assertThrows(NoSuchElementException.class, () -> service.deleteTask(id));

        verify(repo).findById(id);
    }
}