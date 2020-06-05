package com.fenzx.ldy.dal.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

/**
 * 收藏招聘(含实习、宣讲/双选)
 * 外键 recid 对应 RecruitmentInfo.id
 * 外键 sid 对应 Student.sid
 * 招聘信息表（RecruitmentInfo）与学生表(Student)为多对多关系
 */
@Data
@Entity
@Table
@ToString
public class CollectRec {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    Integer id;
    Integer recid;//招聘信息id
    String sid;//收藏者学号
}
