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
        UserEntity user = userRepository.findById(userID).get();
        if (user == null) {
            throw new UserNotFoundException("Пользователь не найден!");
        }

        TodoEntity newTodo = todo;
        List<UserEntity> todoUsers = new ArrayList<>();
        todoUsers.add(user);
        newTodo.setUsers(todoUsers);

        List<TodoEntity> userTodos = user.getTodos();
        userTodos.add(newTodo);
        user.setTodos(userTodos);

        todoRepository.save(newTodo);

        return Todo.toModel(newTodo);
    }

    public List<Todo> getTodos() {
        List<Todo> todos = new ArrayList<>();
        todoRepository.findAll().forEach(todo -> todos.add(Todo.toModel(todo)));
        return todos;
    }

    public Todo addUserToExistingTodo(Long userId, Long todoId) {
        TodoEntity todo = todoRepository.findById(todoId).get();
        UserEntity user = userRepository.findById(userId).get();

        List<UserEntity> todoUsers = todo.getUsers();
        List<TodoEntity> userTodos = user.getTodos();

        todoUsers.add(user);
        todo.setUsers(todoUsers);

        userTodos.add(todo);
        user.setTodos(userTodos);

        return Todo.toModel(todoRepository.save(todo));
    }

    public List<User> getUsersByTodoID(Long id) {
        List<UserEntity> todoUsers = todoRepository.findById(id).get().getUsers();
        List<User> todoUsersDTO = new ArrayList<>();
        todoUsers.forEach(todoUser -> todoUsersDTO.add(User.toModel(todoUser)));
        return todoUsersDTO;
    }

    public Todo completeTodo(Long id) {
        TodoEntity todo = todoRepository.findById(id).get();
        todo.setCompleted(!todo.getCompleted());
        return Todo.toModel(todoRepository.save(todo));
    }

    public Todo deleteTodoById(Long id) {
        TodoEntity todo = todoRepository.findById(id).get();

        List<Long> usersIdForRemove = new ArrayList<>();
        todo.getUsers().forEach(user -> {
            user.getTodos().remove(todo);
        });

        usersIdForRemove.forEach(userId -> userRepository.deleteById(userId));

        Todo removedTodo = Todo.toModel(todoRepository.findById(id).get());
        todoRepository.deleteById(id);

        return removedTodo;
    }
}
