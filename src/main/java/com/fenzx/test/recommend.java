package com.fenzx.test;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class recommend {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\jiangKerman\\Desktop\\recruit.csv"));
        String line;
        Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("target file directory"), StandardCharsets.UTF_8));
        while ((line = br.readLine()) != null)
        {
            writer.write(line + "\n");
            System.out.println(line);
        }
        br.close();
        writer.close();
    }
}
