package com.example.restapi.services;

import com.example.restapi.entity.TodoEntity;
import com.example.restapi.entity.UserEntity;
import com.example.restapi.exception.UserAlreadyExistException;
import com.example.restapi.exception.UserNotFoundException;
import com.example.restapi.models.Todo;
import com.example.restapi.models.User;
import com.example.restapi.repository.TodoRepository;
import com.example.restapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private TodoService todoService;

    public UserEntity registration(@RequestBody UserEntity user) throws UserAlreadyExistException {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new UserAlreadyExistException("Пользователь с таким именем уже существует!");
        }
        return userRepository.save(user);
    }

    public User updateUsernameById(UserEntity updateUser, Long userId) {

        Optional<UserEntity> byId = userRepository.findById(userId);

        if(byId.isPresent()){
            UserEntity user = byId.get();
            if(updateUser.getUsername() != null) {
                user.setUsername(updateUser.getUsername());
            } else {
                throw new MissingFormatArgumentException("Невозможно обновить передаваемое поле username, тк оно является пустым!");
            }
            return User.toModel(userRepository.save(user));
        } else {
            throw new NoSuchElementException("Пользователь с id = " + userId + " не найден!");
        }
    }

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(el -> users.add(User.toModel(el)));
        return users;
    }

    public User getOne(Long id) throws NoSuchElementException {
        Optional<UserEntity> byId = userRepository.findById(id);
        if(byId.isPresent()) {
            return User.toModel(byId.get());
        } else {
            throw new NoSuchElementException("Пользователь с id = " + id + " не найден!");
        }
    }

    public List<Todo> getTodosByUserId(Long userId) {

        Optional<UserEntity> byId = userRepository.findById(userId);

        if(byId.isPresent()) {
            List<TodoEntity> userTodos = byId.get().getTodos();
            List<Todo> userTodosDTO = new ArrayList<>();
            userTodos.forEach(todoEntity -> userTodosDTO.add(Todo.toModel(todoEntity)));
            return userTodosDTO;
        } else {
            throw new NoSuchElementException("Пользователь с id = " + userId + " не найден!");
        }
    }

    public User deleteUser(Long id) {
        Optional<UserEntity> byId = userRepository.findById(id);

        if (byId.isPresent()) {
            UserEntity user = byId.get();
            List<TodoEntity> todosIdForRemove = new ArrayList<>();
            user.getTodos().forEach(todo -> {
                todo.getUsers().remove(user);
                todoRepository.save(todo);
                if (todo.getUsers().size() == 0) {
                    todosIdForRemove.add(todo);
                }
            });

            todoRepository.deleteAll(todosIdForRemove);

            User removedUser = User.toModel(userRepository.findById(id).get());
            userRepository.deleteById(id);

            return removedUser;
        } else {
            throw new NoSuchElementException("Пользователь с id = " + id + " не найден!");
        }
    }
}
