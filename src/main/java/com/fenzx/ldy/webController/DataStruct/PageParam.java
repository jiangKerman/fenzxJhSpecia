package com.fenzx.ldy.webController.DataStruct;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PageParam {
    String postName;
    String workSite;
    String profess;
    int count;
    String employer;
    int curr;
    String quality;
}
