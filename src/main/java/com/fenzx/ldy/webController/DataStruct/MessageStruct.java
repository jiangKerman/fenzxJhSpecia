package com.fenzx.ldy.webController.DataStruct;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class MessageStruct {
    String content;
    String senderName;
    String time;
    String id;
    String isread;
}
