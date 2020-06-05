package com.fenzx.ldy.webController.DataStruct;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class PageProjectParam {
    String key;
    int field;
    int count;
    int curr;
}
