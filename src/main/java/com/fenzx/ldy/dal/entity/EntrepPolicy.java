package com.fenzx.ldy.dal.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

/**
 * 创业政策
 */
@Data
@ToString
@Table
@Entity
public class EntrepPolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String publishTime;
    String title;
    @Column(length = 255*20000)
    String detail;
}
