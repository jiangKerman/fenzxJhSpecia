package com.fenzx.test;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Reptile {
    public static void main(String[] args) throws IOException {
//        Document doc = Jsoup.connect("http://job.cqupt.edu.cn/portal/home.html").get();
//        Document doc = Jsoup.connect("https://www.baidu.com/").get();
        Connection connect = Jsoup.connect("http://job.cqupt.edu.cn/portal/home.html");
        Map<String, String> header = new HashMap<String, String>();
//        header.put("Host", "http://info.bet007.com");




        header.put("no-storee-Control"," no-store");
        header.put("Connection"," keep-alive");
        header.put("Content-Encoding"," gzip");
//        header.put("Content-Security-Policy"," default-src *; img-src data"," 'self' https","//hm.baidu.com data","; script-src 'self' https","//hm.baidu.com 'unsafe-inline' 'unsafe-eval'; style-src 'self' 'unsafe-inline'; font-src 'self'; connect-src 'self'; object-src 'self'; media-src 'self'; frame-src 'self'; plugin-types application/x-shockwave-flash");
        header.put("Content-Type"," text/html;charset=UTF-8");
//        header.put("Date"," Wed, 25 Mar 2020 14","08","H13 GMT");
//        header.put("Expires"," Wed, 25 Mar 2020 14","11","13 GMT");
        header.put("Pragma"," no-cache");
        header.put("Server"," ******");
        header.put("Transfer-Encoding"," chunked");
        header.put("X-Frame-Option"," SAMEORIGIN");
        header.put("X-Frame-Options"," SAMEORIGIN");
//        header.put("X-XSS-Protect");
        Connection data = connect.data(header);
        Document document = data.get();

        System.out.println(document.html());

    }
}
