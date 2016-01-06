package com.prayer.shane.course001.demo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class PropDemo {

    public static void main(String args[]) throws IOException {
        /**
         * 1.低级流
         */
        final InputStream in = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("shane/course001/prop.properties");
        /**
         * 2.高级流
         */
        URL url1 = Thread.currentThread().getContextClassLoader().getResource("shane/course001/prop.properties");
        /**
         * 3.URL -> File
         */
        System.out.println(url1);
        File file = new File(url1.getPath()); // InputStream -> URL -> Absolute
                                              // Path -> String -> File
        System.out.println(file.getAbsolutePath());
        /**
         * 4.InputStream (In) -> File (Out) final InputStream in = new
         * FileInputStream(file);
         */
        /*final File file1 = new File("out.properties");
        final OutputStream out = new FileOutputStream(file1);
        int bytes = 0;
        byte[] buffer = new byte[8192];
        // in.read(); 一个字节一个字节读取
        // in.read(byte[])；全部读取
        while ((bytes = in.read(buffer, 0, 8192)) != -1) {
            out.write(buffer, 0, bytes);
            out.flush();
        }
        out.close();*/
        /**
         * 5.InputStream -> Properies
         */
        Properties prop = new Properties();
        prop.load(in);
        /**
         * 6.可以直接遍历
         */
        for(final Object key: prop.keySet()){
            // String value = prop.get(key); Compile Error
            String value = prop.getProperty(key.toString());
            System.out.println(" Key = " + key + ", Value = " + value);
        }
    }
}


