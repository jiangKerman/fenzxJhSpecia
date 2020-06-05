package com.fenzx.ldy.dal.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

/**
 * 收藏项目
 * 外键 pid 对应 Project.id
 * 外键 sid 对应 Student.sid
 * 项目表（Project）与学生表(Student)为多对多关系
 */
@Table
@Entity
@Data
@ToString
public class CollectProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    Integer id;
    Integer pid;//项目id
    String sid;//收藏者学号
}
