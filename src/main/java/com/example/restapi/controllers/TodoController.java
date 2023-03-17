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

    @PostMapping("/{userId}")
    public ResponseEntity createTodo(@RequestParam("userId") Long userId, @RequestBody TodoEntity todoEntity) {
        try{
            return ResponseEntity.ok(todoService.createTodo(todoEntity, userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка!");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity completeTodo(@RequestParam Long id) {
        try{
            return ResponseEntity.ok(todoService.completeTodo(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка!");
        }
    }

}
