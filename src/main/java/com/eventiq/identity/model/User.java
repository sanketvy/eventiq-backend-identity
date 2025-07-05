package com.eventiq.identity.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "USERS")
public class User {

    @Id
    private String id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNo;

    private String company;

    private String role;
}
