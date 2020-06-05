package com.fenzx.ldy.antiRobot;

import java.util.ArrayList;
import java.util.List;

public class BlackList {
    private static List<String> list=new ArrayList<>();
    static {
        list.add("Java");
        list.add("Python");
        list.add("php");
        list.add("Python");
    }
    public static boolean verify(String UA){
        for(String bl:list){
            if(bl.equals(UA)){
              return false;
            }
        }
        return true;
    }
}
