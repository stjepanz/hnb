package com.hnb.app.models;

import org.hibernate.annotations.IndexColumn;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "logovi")
public class Logger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "log")
    private String log;

    @Column(name = "vrijeme")
    private LocalDateTime vrijeme;

    @Column(name = "endpoint")
    private String endpoint;

    @Column(name = "logged_user")
    private String loggedUser;

    public Logger() {
    }

    public Logger(String log, LocalDateTime vrijeme, String endpoint, String loggedUser) {
        this.log = log;
        this.vrijeme = vrijeme;
        this.endpoint = endpoint;
        this.loggedUser = loggedUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public LocalDateTime getVrijeme() {
        return vrijeme;
    }

    public void setVrijeme(LocalDateTime vrijeme) {
        this.vrijeme = vrijeme;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(String loggedUser) {
        this.loggedUser = loggedUser;
    }
}
