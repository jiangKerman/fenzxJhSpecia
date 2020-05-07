package com.fenzx.ldy;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

/**
 * 领域
 * 用于创建创业项目
 */
@Data
@ToString
@Table
@Entity
public class Field {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String fName;
}
