package com.fenzx.ldy;

import lombok.Data;
import lombok.ToString;
import javax.persistence.*;

/**
 * 私信
 */
@ToString
@Table
@Entity
@Data
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String senderid;//发送人
    String reciverid;//接收人
    @Column(length = 2048)
    String content;//内容
    Integer isread;//已读否，0未读，1已读
    String time;//发送时间
    Integer sendcite;//发送方是否引用，0未引，1引
    Integer recivecite;//接收方是否引用，0未引，1引
}
