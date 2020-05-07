package com.anjbo.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;


/**
 * 文件辅助类
 * @author fuchen
 *
 */
public class FileUtil {
	
	/**
	 * 根据http url转换为流
	 * @param path
	 * @return
	 */
	public static byte[] httpConverBytes(String path) throws Exception {  
		BufferedInputStream in = null;  
        ByteArrayOutputStream out = null;  
        URLConnection conn = null;  
  
        try {  
            URL url = new URL(path);  
            conn = url.openConnection();  
            in = new BufferedInputStream(conn.getInputStream());  
  
            out = new ByteArrayOutputStream(1024);  
            byte[] temp = new byte[1024];  
            int size = 0;  
            while ((size = in.read(temp)) != -1) {  
                out.write(temp, 0, size);  
            }  
            byte[] content = out.toByteArray();  
            return content;  
        } catch (Exception e) {  
        	throw e;
        } finally {  
        	closeInputStream(in);
        	closeOutputStream(out);
        } 
    }
	
	/**
	 * 字节转kb
	 * @param bytes
	 * @return
	 */
	public static Long byteToKb(Long bytes) {
		BigDecimal filesize = new BigDecimal(bytes); 
		BigDecimal kilobyte = new BigDecimal(1024);
		Long value = filesize.divide(kilobyte, 2, BigDecimal.ROUND_UP).longValue();  
		return value;
	}
	
	/**
	 * 关闭InputStream
	 * @param inputStream
	 * @throws IOException
	 */
	public static void closeInputStream(InputStream inputStream) throws IOException {
		try {
			if (inputStream != null) {
				inputStream.close();
			}
		} catch (IOException e) {
			throw e;
		}
	}
	
	/**
	 * 关闭OutputStream
	 * @param outputStream
	 * @throws IOException
	 */
	public static void closeOutputStream(OutputStream outputStream) throws IOException {
		try {
			if (outputStream != null) {
				outputStream.close();
			}
		} catch (IOException e) {
			throw e;
		}
	}
	
	/**
     * 用于base64格式
     */
    public static String getContentExtName(String content) {
        //String extName = "jpg";
        String [] b = content.split(";");
        String [] c = b[0].split("/");
        return c[1];
    }
	
}
