package com.example.restapi.controllers;

import com.example.restapi.entity.TodoEntity;
import com.example.restapi.services.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping("/")
    public ResponseEntity getTodos() {
        try {
            return ResponseEntity.ok().body(todoService.getTodos());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Не удалось получить тудушки");
        }
    }
    @PostMapping()
    public ResponseEntity addUserToExistingTodo(@RequestParam Long userId, @RequestParam Long todo) {
        try{
            return ResponseEntity.ok().body(todoService.addUserToExistingTodo(userId, todo));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка!");
        }
    }
    @GetMapping("/{id}/users")
    public ResponseEntity getUsersByTodoID(@PathVariable Long id) {
        try{
            return ResponseEntity.ok().body(todoService.getUsersByTodoID(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка!");
        }
    }
    @PostMapping("/{userId}")
    public ResponseEntity createTodo(@PathVariable Long userId, @RequestBody TodoEntity todoEntity) {
        try{
            return ResponseEntity.ok(todoService.createTodo(todoEntity, userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка!");
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity completeTodo(@PathVariable Long id) {
        try{
            return ResponseEntity.ok(todoService.completeTodo(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка!");
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteTodoById(@PathVariable Long id) {
        try{
            return ResponseEntity.ok(todoService.deleteTodoById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка!");
        }
    }
}
