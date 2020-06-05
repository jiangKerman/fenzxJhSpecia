package com.fenzx.ldy.dal.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

/**
 * 创业项目
 */
@Entity
@Table
@ToString
@Data
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String stuNo;//发布者
    String pName;//项目名称
    @Column(length = 255*20000)
    String survey;//概况
    String evolve;//进展
    String publishTime;//发布时间
    String remark;//备注
}
