package com.anjbo.utils.ccb;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	
	 private final static String[] hexDigits = {
	        "0", "1", "2", "3", "4", "5", "6", "7",
	        "8", "9", "a", "b", "c", "d", "e", "f"};

	   /*public static String byteArrayToHex(byte[] byteArray) {
	        String hs = "";   
	        String stmp = "";   
	        for (int n = 0; n < b.length; n++) {   
	            stmp = (Integer.toHexString(b[n] & 0XFF));   
	            if (stmp.length() == 1) {   
	                hs = hs + "0" + stmp;   
	            } else {   
	                hs = hs + stmp;   
	            }   
	            if (n < b.length - 1) {   
	                hs = hs + "";   
	            }   
	        }   
	        // return hs.toUpperCase();   
	        return hs;

	      // 首先初始化一个字符数组，用来存放每个16进制字符

	      char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9', 'A','B','C','D','E','F' };

	 

	      // new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））

	      char[] resultCharArray =new char[byteArray.length * 2];

	      // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去

	      int index = 0;

	      for (byte b : byteArray) {

	         resultCharArray[index++] = hexDigits[b>>> 4 & 0xf];

	         resultCharArray[index++] = hexDigits[b& 0xf];

	      }

	      // 字符数组组合成字符串返回

	      return new String(resultCharArray);

	}*/
	   
	    /**
	     * 转换字节数组为16进制字串
	     * @param b 字节数组
	     * @return 16进制字串
	     */
	    private static String byteArrayToString(byte[] b) {
	        StringBuffer resultSb = new StringBuffer();
	        for (int i = 0; i < b.length; i++) {
	            resultSb.append(byteToHexString(b[i]));//若使用本函数转换则可得到加密结果的16进制表示，即数字字母混合的形式
	            //resultSb.append(byteToNumString(b[i]));//使用本函数则返回加密结果的10进制数字字串，即全数字形式
	        }
	        return resultSb.toString();
	    }

	    private static String byteToHexString(byte b) {
	        int n = b;
	        if (n < 0) {
	            n = 256 + n;
	        }
	        int d1 = n / 16;
	        int d2 = n % 16;
	        return hexDigits[d1] + hexDigits[d2];
	    }
	   
	   public static String fileMD5(String inputFile) throws IOException {
		      // 缓冲区大小（这个可以抽出一个参数）
		      int bufferSize = 256 * 1024;
		      FileInputStream fileInputStream = null;
		      DigestInputStream digestInputStream = null;
		      try {
		         // 拿到一个MD5转换器（同样，这里可以换成SHA1）
		         MessageDigest messageDigest =MessageDigest.getInstance("MD5");
		         // 使用DigestInputStream
		         fileInputStream = new FileInputStream(inputFile);
		         digestInputStream = new DigestInputStream(fileInputStream,messageDigest);
		         // read的过程中进行MD5处理，直到读完文件
		         byte[] buffer =new byte[bufferSize];
		         while (digestInputStream.read(buffer) > 0);
		         // 获取最终的MessageDigest
		         messageDigest= digestInputStream.getMessageDigest();
		         // 拿到结果，也是字节数组，包含16个元素
		         byte[] resultByteArray = messageDigest.digest();
		         // 同样，把字节数组转换成字符串
		         return byteArrayToString(resultByteArray);
		      } catch (NoSuchAlgorithmException e) {
		         return null;
		      } finally {
		         try {
		            digestInputStream.close();
		         } catch (Exception e) {
		         }
		         try {
		            fileInputStream.close();
		         } catch (Exception e) {
		         }
		      }
		   }
	   
	   public static void main(String[] args)throws Exception {
		String file = "D:/jihua.jpg";
		System.out.println(MD5.fileMD5(file).toUpperCase());
//		UUID uuid = UUID.randomUUID();
//		System.out.println(uuid.toString().replaceAll("-","").toUpperCase());
	}
}
