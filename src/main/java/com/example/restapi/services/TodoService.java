package com.example.restapi.services;

import com.example.restapi.entity.TodoEntity;
import com.example.restapi.entity.UserEntity;
import com.example.restapi.exception.UserNotFoundException;
import com.example.restapi.models.Todo;
import com.example.restapi.models.User;
import com.example.restapi.repository.TodoRepository;
import com.example.restapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private UserRepository userRepository;

    public Todo createTodo(TodoEntity todo, Long userID) throws UserNotFoundException {
        if (userRepository.findById(userID).get() == null) {
            throw new UserNotFoundException("Пользователь не найден!");
        }

        TodoEntity newTodo = new TodoEntity();
        List<UserEntity> todoUsers = new ArrayList<>();

        todoUsers.add(userRepository.findById(userID).get());
        newTodo.setTitle(todo.getTitle());
        newTodo.setCompleted(todo.getCompleted());
        newTodo.setUsers(todoUsers);

        List<TodoEntity> userTodos = userRepository.findById(userID).get().getTodos();
        userTodos.add(newTodo);
        userRepository.findById(userID).get().setTodos(userTodos);

        todoRepository.save(newTodo);

        return Todo.toModel(newTodo);
    }

    public List<Todo> getTodos() {
        List<Todo> todos = new ArrayList<>();
        todoRepository.findAll().forEach(todo -> todos.add(Todo.toModel(todo)));
        return todos;
    }

    public Todo completeTodo(Long id) {
        TodoEntity todo = todoRepository.findById(id).get();
        todo.setCompleted(!todo.getCompleted());
        return Todo.toModel(todoRepository.save(todo));
    }
}
