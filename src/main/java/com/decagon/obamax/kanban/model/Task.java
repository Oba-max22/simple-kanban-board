package com.decagon.obamax.kanban.model;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "kanban")
public class Task {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "body",nullable = false)
    private String body;

    @Column(name = "position", nullable = false)
    private String position;
}
