package com.fenzx.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@ToString
@Data
@Entity
@Table
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String passwd;
    private String username;
}
