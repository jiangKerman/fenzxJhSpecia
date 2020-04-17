package com.fenzx.test;

import com.fenzx.entity.Teacher;
import com.google.gson.Gson;

public class test {
    public static void main(String[] args) {
        Gson gson=new Gson();
        Teacher teacher=new Teacher();
        teacher.setEmail("asfsdf");
        teacher.setTid("001");

        String json = gson.toJson(teacher);


        System.out.println(json);
    }
}
