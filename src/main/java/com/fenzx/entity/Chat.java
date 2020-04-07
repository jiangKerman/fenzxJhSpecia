package com.fenzx.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@ToString
@Data
@Entity
@Table
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer problemId;
    private String content;
    private String time;
    private String type;//这个聊天是老师说的话还是学生说的
}
