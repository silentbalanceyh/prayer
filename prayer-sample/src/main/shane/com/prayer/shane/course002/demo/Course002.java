package com.prayer.shane.course002.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Course002 {
    public static void main(String[] args) throws IOException {
        // URL url =
        // Thread.currentThread().getContextClassLoader().getResource("shane/course002/prop.properties");
        // System.out.println(url);
        final InputStream in = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("shane/course002/prop.properties");
        final BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        // System.out.println(br.readLine());
        String strLine;
        Map<String,String> map = new HashMap<>();
        strLine = br.readLine();
        while (strLine != null) {
            String[] strArr;
            // String strLineU8 = new String(strLine.getBytes("utf-8"),"utf-8");
            // System.out.println(strLineU8);
            strArr = strLine.split("=");
            map.put(strArr[0], strArr[1]);
            strLine = br.readLine();
        }
        for (final Object key : map.keySet()) {
            System.out.println(key + " = " + map.get(key));
        }

        in.close();
        br.close();
    }
}
