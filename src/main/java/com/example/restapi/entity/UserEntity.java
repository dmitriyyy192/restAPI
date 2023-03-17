package com.example.restapi.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Table(name = "users")
@Entity //чтобы jpa сделал из этой сущности таблицу
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //генерируется уникальное значение id
    private Long id;

    private String username;
    private String password;
    @JoinTable(
            name = "user_todo",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "todo_id")}
    )
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<TodoEntity> todos;

    public UserEntity() {
    }

    public Set<TodoEntity> getTodos() {
        return todos;
    }

    public void setTodos(Set<TodoEntity> todos) {
        this.todos = todos;
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
