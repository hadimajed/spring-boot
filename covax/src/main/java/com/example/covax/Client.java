package com.example.covax;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "full_name", nullable = false)
    private String name;
    @Column(name = "registered_on")
    private LocalDate date;
    @Column(name = "next_shot")
    private LocalDate nextShot;
    @Column(name = "number_of_shots")
    private Integer shots = 0;

    public Client() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getNextShot() {
        return nextShot;
    }

    public void setNextShot(LocalDate nextShot) {
        this.nextShot = nextShot;
    }

    public Integer getShots() {
        return shots;
    }

    public void setShots(Integer shots) {
        this.shots = shots;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", nextShot=" + nextShot +
                ", shots=" + shots +
                '}';
    }
}
