package com.hahn.system.hahnitsupport.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void run(String... args) throws Exception {
        // Insertion des rôles
        jdbcTemplate.update("INSERT INTO role (name) VALUES ('EMPLOYEE')");
        jdbcTemplate.update("INSERT INTO role (name) VALUES ('IT_SUPPORT')");

        // Encodage des mots de passe
        String employeePassword = passwordEncoder.encode("password123");
        String itSupportPassword = passwordEncoder.encode("password123");

        // Insertion des employés
        jdbcTemplate.update(
                "INSERT INTO employee (username, password, full_name) VALUES ('employee1', ?, 'Alice Employee')",
                employeePassword
        );
        jdbcTemplate.update(
                "INSERT INTO employee (username, password, full_name) VALUES ('itsupport1', ?, 'Bob IT Support')",
                itSupportPassword
        );

        // Association des rôles aux employés
        // On suppose que l'ID 1 correspond à 'employee1' et l'ID 2 à 'itsupport1'
        jdbcTemplate.update("INSERT INTO employee_roles (employee_id, roles_id) VALUES (1, 1)"); // employee1 => EMPLOYEE
        jdbcTemplate.update("INSERT INTO employee_roles (employee_id, roles_id) VALUES (2, 2)"); // itsupport1 => IT_SUPPORT

        // Insertion d'exemples de tickets créés par 'employee1'
        jdbcTemplate.update(
                "INSERT INTO ticket (title, description, priority, category, creation_date, status, created_by_id) " +
                        "VALUES ('Network Issue', 'Unable to connect to VPN', 'HIGH', 'NETWORK', CURRENT_TIMESTAMP, 'NEW', 1)"
        );
        jdbcTemplate.update(
                "INSERT INTO ticket (title, description, priority, category, creation_date, status, created_by_id) " +
                        "VALUES ('Software Bug', 'Application crashes on launch', 'MEDIUM', 'SOFTWARE', CURRENT_TIMESTAMP, 'NEW', 1)"
        );

        System.out.println("Database initialized with sample roles, employees, and tickets.");
    }
}