package com.anjbo.chromejs;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.anjbo.chromejs.utils.Constants;

public class DefaultChromeNotify implements ChromeNotify {
	private static final Log log = LogFactory.getLog(DefaultChromeNotify.class);
	public void tooManyChrome() {
		log.warn(" Too many chrome ? Please check it");

	}
	public void noChrome() {
		log.warn("No chrome or chrome has some problem ? Please check it ");
	}
	 
	public void writeUrlTag(String tag,String time,String filePath) {
		  Properties prop = new Properties();
		  try {
		  InputStream fis = getClass().getClassLoader().getResourceAsStream(filePath);
		  if(fis==null) fis = ClassLoader.getSystemResourceAsStream(filePath);
		  prop.load(fis);
		  OutputStream fos = new FileOutputStream(filePath);
		  prop.setProperty(tag, time);
		  prop.store(fos, tag);
		  } catch (IOException e) {
			  e.printStackTrace();
		  }
	}
	
	public static void main(String[] args) {
		  ChromeNotify d=new DefaultChromeNotify();
		  //d.writeUrlTag("4","11",Constants.CHROME_UPDATING_FILE);
		  System.out.println(d.getValue("4",Constants.CHROME_UPDATING_FILE));
		  System.out.println("OK");
		  }
	 
	public String getValue(String tag, String filePath) {
		Properties props = new Properties();
		String value="";
		  try {
			  InputStream fis = getClass().getClassLoader().getResourceAsStream(filePath);
			  if(fis==null) fis = ClassLoader.getSystemResourceAsStream(filePath);
		  props.load(fis);
		  value = props.getProperty(tag);
		
		  } catch (Exception e) {
		  e.printStackTrace();
		  }
		  return value;
	}

}
