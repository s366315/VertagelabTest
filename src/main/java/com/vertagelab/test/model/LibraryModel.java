package com.vertagelab.test.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "library")
@Getter
@Setter
public class LibraryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
}
