package com.fenzx.ldy.dal.entity;

import lombok.Data;
import lombok.ToString;

/**
 * 招聘信息临时数据结构，无需存入数据库
 */
@Data
@ToString
public class Rec {
    private String id;
    private String title;//标题
    private String employers;//雇主
    private String publishTime;//发布时间
}
