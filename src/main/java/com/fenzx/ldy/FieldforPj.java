package com.fenzx.ldy;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

/**
 * 领域与创业项目对应表
 * 是领域表(Field)与项目表(Project)多对多关系的中间表
 */
@Table
@Entity
@ToString
@Data
public class FieldforPj {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    Integer fid;
    Integer pid;
}
