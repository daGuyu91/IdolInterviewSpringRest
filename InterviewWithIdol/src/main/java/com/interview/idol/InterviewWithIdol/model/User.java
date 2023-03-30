package com.interview.idol.InterviewWithIdol.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@Entity
public class User {
    private @Id @GeneratedValue Long id;
    private String firstName;
    private String lastName;

    private String contactNumber;
}
