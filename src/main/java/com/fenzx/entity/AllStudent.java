package com.fenzx.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@ToString
@Data
@Entity
@Table
public class AllStudent {
    @Id
    private String sid;
    private String name;
    private String gender;
    private String department;
    private String major;


}
