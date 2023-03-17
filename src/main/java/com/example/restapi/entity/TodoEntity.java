package com.example.restapi.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Table(name = "todos")
@Entity
public class TodoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Boolean completed;
    @ManyToMany(mappedBy = "todos", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<UserEntity> users;

    public TodoEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Set<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(Set<UserEntity> users) {
        this.users = users;
    }
}
