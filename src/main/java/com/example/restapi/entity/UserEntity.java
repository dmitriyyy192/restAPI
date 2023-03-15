package com.example.restapi.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity //чтобы jpa сделал из этой сущности таблицу
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //генерируется уникальное значение id
    private Long id;

    private String username;
    private String password;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    List<TodoEntity> todos;

    public List<TodoEntity> getTodos() {
        return todos;
    }

    public void setTodos(List<TodoEntity> todos) {
        this.todos = todos;
    }

    public UserEntity() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
