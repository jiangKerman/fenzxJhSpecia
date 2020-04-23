package com.fenzx.Config;

import org.hibernate.dialect.MySQL8Dialect;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("deprecation")
public class MysqlConfig extends MySQL8Dialect {
    @Override
    public String getTableTypeString() {
//        return super.getTableTypeString();
        return "ENGINE=InnoDB DEFAULT CHARSET=utf8";
    }
}
