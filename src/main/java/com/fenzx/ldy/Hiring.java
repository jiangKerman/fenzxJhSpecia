package com.fenzx.ldy;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

/**
 * 招聘岗位
 * 外键recid对应RecruitmentInfo.id
 * 招聘信息表(RecruitmentInfo)与本表关系为一对多
 */
@ToString
@Data
@Entity
@Table
public class Hiring {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer recid;//外键,招聘信息
    private String postName;//岗位名称
    @Column(length = 1024)
    private String profesRequire;//专业要求
    private String numberPost;//需求人数
    private String salary;//薪资
    private String education;//学历要求
}
