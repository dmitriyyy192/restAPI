package com.example.restapi.services;

import com.example.restapi.entity.TodoEntity;
import com.example.restapi.entity.UserEntity;
import com.example.restapi.exception.UserNotFoundException;
import com.example.restapi.models.Todo;
import com.example.restapi.repository.TodoRepository;
import com.example.restapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private UserRepository userRepository;

    public Todo createTodo(TodoEntity todo, Long userID) throws UserNotFoundException {
        UserEntity user = userRepository.findById(userID).get();
        if (user == null) {
            throw new UserNotFoundException("Пользователь не найден!");
        }

        TodoEntity newTodo = todoRepository.save(todo);

        Set<TodoEntity> userTodos = user.getTodos();
        userTodos.add(newTodo);
        user.setTodos(userTodos);

        Set<UserEntity> todoUsers = newTodo.getUsers();
        todoUsers.add(user);
        newTodo.setUsers(todoUsers);

        return Todo.toModel(newTodo);
    }

    public Todo completeTodo(Long id) {
        TodoEntity todo = todoRepository.findById(id).get();
        todo.setCompleted(!todo.getCompleted());
        return Todo.toModel(todoRepository.save(todo));
    }
}
