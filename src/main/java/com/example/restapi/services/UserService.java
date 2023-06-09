package com.example.restapi.services;

import com.example.restapi.entity.TodoEntity;
import com.example.restapi.entity.UserEntity;
import com.example.restapi.exception.UserAlreadyExistException;
import com.example.restapi.exception.UserNotFoundException;
import com.example.restapi.models.Todo;
import com.example.restapi.models.User;
import com.example.restapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public UserEntity registration(@RequestBody UserEntity user) throws UserAlreadyExistException {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new UserAlreadyExistException("Пользователь с таким именем уже существует!");
        }
        return userRepository.save(user);
    }

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(el -> users.add(User.toModel(el)));
        return users;
    }

    public User getOne(Long id) throws UserNotFoundException {
        UserEntity user = userRepository.findById(id).get();
        if (userRepository.findById(id) == null) {
            throw new UserNotFoundException("Пользователь не найден!");
        }
        return User.toModel(user);
    }

    public Set<Todo> getTodosByUserId(Long userId) {
        UserEntity user = userRepository.findById(userId).get();
        Set<TodoEntity> userTodos = user.getTodos();
        Set<Todo> userTodosDTO = new HashSet<>();
        userTodos.forEach(todoEntity -> userTodosDTO.add(Todo.toModel(todoEntity)));
        return userTodosDTO;
    }

    public Long deleteUser(Long id) {
        userRepository.deleteById(id);
        return id;
    }
}
