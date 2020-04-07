package com.fenzx.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Data
@ToString
@Entity
@Table
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;//唯一标识自增
    private String tid;//教师工号
    private String name;
    private String tel;
    private String passwd;
    private String email;
    private String expertise;//专长：擅长的问题类型
}
