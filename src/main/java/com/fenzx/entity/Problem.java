package com.fenzx.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Table
@Entity
@ToString
@Data
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String tid;
    private String sid;
    private String title;
    private Integer resolved;//0未分配   1已分配解决中     2已解决
    private String time;//问题的发布时间
    private String freeTime;//提问人的空闲时间
    private String problemType;
    private String detail;
}
