package com.caboooom.userservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "users")
public class User {
    @Id
    @Column
    private String id;
    @Column
    private String name;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private String provider;

    @Column
    private String auth;

    public User() {

    }

    public User(String id, String name, String email, String password, String provider) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.provider = provider;
        this.auth = "ROLE_USER";
    }
}
