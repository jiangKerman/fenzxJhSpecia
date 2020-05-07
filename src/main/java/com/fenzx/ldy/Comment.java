package com.fenzx.ldy;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

/**
 * 评论
 */
@Entity
@Table
@Data
@ToString
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    Integer pid;//项目号
    String comid;//评论者名称
    @Column(length = 2048)
    String cotent;//评论内容
    String time;//评论时间
}
