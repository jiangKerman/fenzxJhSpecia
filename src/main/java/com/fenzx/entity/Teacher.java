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
    //专长：擅长的问题类型  职业规划 企业生态 就业咨询 国家政策 行业动态 面试技巧 其他
    private String expertise;
}
