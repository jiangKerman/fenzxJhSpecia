package com.fenzx.ldy.dal.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

/**
 * 招聘信息(含实习，宣讲/双选)
 */
@ToString
@Data
@Entity
@Table
public class RecruitmentInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;//标题
    @Column(length = 1024)
    private String workSite;//工作地点
    private String employers;//雇主
    private String publishTime;//发布时间
    private String deliveryLink;//来源链接
    @Column(length = 255*20000)
    private String detail;//详情
    private String dataSource;//数据来源
    private String tel;//联系电话
    private String unitSite;//单位网址
    private String email;//简历申请邮箱
    private String quality;//性质，实习还是招聘还是宣讲会
    private String preachTime;//宣讲时间
    private String preachSite;//宣讲地址
}
