package com.example.Munchies.model.entity;

import com.example.Munchies.model.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="employee")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="employee_id")
    private Long employeeId;
    @Column(name="employee_firstname")
    private String employeeFirstName;
    @Column(name="employee_lastname")
    private String employeeLastName;
    @Column(name="employee_email")
    private String employeeEmail;
    @Column(name="employee_password")
    private String employeePassword;
    @Column(name="employee_role")
    private Role employeeRole = Role.USER;
    @Column(name="employee_created")
    private LocalDateTime employeeCreated;
    @Column(name="employee_updated")
    private LocalDateTime employeeUpdated;
}
