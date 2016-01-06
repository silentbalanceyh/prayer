package com.prayer.shane.course001.prcoess;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Course001 {
	public static void main(String[] args) throws IOException{
		final InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("shane/course001/req.properties");
		final OutputStream out = new FileOutputStream("C:/data.txt");
		
		Properties prop = new Properties();
		prop.load(in);
		byte[] bufferKey;
		byte[] bufferVal;
		int propSize;
		int flag = 1;
		propSize = prop.size();
		
		for(final Object key : prop.keySet()){
			String value = prop.getProperty(key.toString());
			bufferKey = key.toString().getBytes();
			bufferVal = value.getBytes();
			out.write(bufferKey);
			out.write("=".getBytes());
			if(isArray(value)){
				out.write("[".getBytes());
				out.write(bufferVal);
				out.write("]".getBytes());
			}else{
				out.write(bufferVal);
			}
			if (flag < propSize){
				out.write(", ".getBytes());
				flag++;
			}
			out.flush();
		}
		
		in.close();
		out.close();
	}
	
	static public boolean isArray(String strValue){
		if(strValue.contains(",") && !strValue.substring(0, 1).equals("{")){
		//	strValue.charAt(0) = '{'
		//	strValue.indexOf(',') >= 0;
			return true;
		}
		return false;		
	}
}
