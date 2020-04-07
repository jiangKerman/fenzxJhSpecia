package com.fenzx.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@ToString
@Data
@Entity
@Table
public class Student {
    //    @Id
    private String openid;//微信号id
    @Id
    private String sid;//学号
    private String name;
    private String tel;
    private String gender;//性别
    private String department;
    private String major;
    @Column(length = 300)
    private String avatar;
    private String passwd;//用于web端学生的登陆
}
