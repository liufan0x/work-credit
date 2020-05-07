package com.anjbo.utils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class NumberToCN {
	
	 static String[] units = { "", "十", "百", "千", "万", "十万", "百万", "千万", "亿",
		"十亿", "百亿", "千亿", "万亿" };
	 static char[] numArray = { '零', '一', '二', '三', '四', '五', '六', '七', '八', '九' };

	 /**
/      * 汉语中数字大写
      */
     private static final String[] CN_UPPER_NUMBER = { "零", "壹", "贰", "叁", "肆",
             "伍", "陆", "柒", "捌", "玖" };
     /**
      * 汉语中货币单位大写，这样的设计类似于占位符
      */
     private static final String[] CN_UPPER_MONETRAY_UNIT = { "分", "角", "元",
             "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "兆", "拾",
             "佰", "仟" };
     /**
      * 特殊字符：整
      */
     private static final String CN_FULL = "整";
     /**
      * 特殊字符：负
      */
     private static final String CN_NEGATIVE = "负";
     /**
      * 金额的精度，默认值为2
      */
     private static final int MONEY_PRECISION = 2;
     /**
      * 特殊字符：零元整
      */
     private static final String CN_ZEOR_FULL = "零元" + CN_FULL;
	 
	     /**
	      * 把输入的金额转换为汉语中人民币的大写
	      * 
	      * @param numberOfMoney
	      *            输入的金额
	      * @return 对应的汉语大写
	      */
	     public static String number2CNMontrayUnit(BigDecimal numberOfMoney) {
	         StringBuffer sb = new StringBuffer();
	         // -1, 0, or 1 as the value of this BigDecimal is negative, zero, or
	         // positive.
	         int signum = numberOfMoney.signum();
	         // 零元整的情况
	         if (signum == 0) {
	             return CN_ZEOR_FULL;
	         }
	         //这里会进行金额的四舍五入
	         long number = numberOfMoney.movePointRight(MONEY_PRECISION)
	                 .setScale(0, 4).abs().longValue();
	         // 得到小数点后两位值
	         long scale = number % 100;
	         int numUnit = 0;
	         int numIndex = 0;
	         boolean getZero = false;
	         // 判断最后两位数，一共有四中情况：00 = 0, 01 = 1, 10, 11
	         if (!(scale > 0)) {
	             numIndex = 2;
	             number = number / 100;
	             getZero = true;
	         }
	         if ((scale > 0) && (!(scale % 10 > 0))) {
	             numIndex = 1;
	             number = number / 10;
	             getZero = true;
	         }
	         int zeroSize = 0;
	         while (true) {
	             if (number <= 0) {
	                 break;
	             }
	             // 每次获取到最后一个数
	             numUnit = (int) (number % 10);
	             if (numUnit > 0) {
	                 if ((numIndex == 9) && (zeroSize >= 3)) {
	                     sb.insert(0, CN_UPPER_MONETRAY_UNIT[6]);
	                 }
	                 if ((numIndex == 13) && (zeroSize >= 3)) {
	                     sb.insert(0, CN_UPPER_MONETRAY_UNIT[10]);
	                 }
	                 sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
	                 sb.insert(0, CN_UPPER_NUMBER[numUnit]);
	                 getZero = false;
	                 zeroSize = 0;
	             } else {
	                 ++zeroSize;
	                 if (!(getZero)) {
	                     sb.insert(0, CN_UPPER_NUMBER[numUnit]);
	                 }
	                 if (numIndex == 2) {
	                   if (number > 0) {
	                       sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
	                   }
	               } else if (((numIndex - 2) % 4 == 0) && (number % 1000 > 0)) {
	                   sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
	               }
	               getZero = true;
	           }
	           // 让number每次都去掉最后一个数
	           number = number / 10;
	           ++numIndex;
	       }
	       // 如果signum == -1，则说明输入的数字为负数，就在最前面追加特殊字符：负
	       if (signum == -1) {
	           sb.insert(0, CN_NEGATIVE);
	       }
	       // 输入的数字小数点后两位为"00"的情况，则要在最后追加特殊字符：整
	       if (!(scale > 0)) {
	           sb.append(CN_FULL);
	       }
	       return sb.toString();
	   }
	     
	     public static String formatInteger(int num) {
	 		char[] val = String.valueOf(num).toCharArray();
	 		int len = val.length;
	 		StringBuilder sb = new StringBuilder();
	 		for (int i = 0; i < len; i++) {
	 		String m = val[i] + "";
	 		int n = Integer.valueOf(m);
	 		boolean isZero = n == 0;
	 		String unit = units[(len - 1) - i];
	 		if (isZero) {
	 		if ('0' == val[i - 1]) {
	 		// not need process if the last digital bits is 0
	 		continue;
	 		} else {
	 		// no unit for 0
	 		sb.append(numArray[n]);
	 		}
	 		} else {
	 		sb.append(numArray[n]);
	 		sb.append(unit);
	 		}
	 		}
	 		return sb.toString();
	 		}


	     public static String formatDecimal(double decimal) {
	 		String decimals = String.valueOf(decimal);
	 		int decIndex = decimals.indexOf(".");
	 		int integ = Integer.valueOf(decimals.substring(0, decIndex));
	 		int dec = Integer.valueOf(decimals.substring(decIndex + 1));
	 		String result = formatInteger(integ) + "." + formatFractionalPart(dec);
	 		return result;
	 		}


	 		private static String formatFractionalPart(int decimal) {
	 		char[] val = String.valueOf(decimal).toCharArray();
	 		int len = val.length;
	 		StringBuilder sb = new StringBuilder();
	 		for (int i = 0; i < len; i++) {
	 		int n = Integer.valueOf(val[i] + "");
	 		sb.append(numArray[n]);
	 		}
	 		return sb.toString();
	 		}
	 		
	 		public static Map<String,Object> Separate(double money){
	 			Map<String, Object> maps = new HashMap<String,Object>();
	 			int intge=(int) (money%10);
	 			int intshi=(int) ((money/10)%10);
	 			int intbai=(int) ((money/100)%10);
	 			int intq=(int) ((money/1000)%10);
	 			int intw=(int) ((money/10000)%10);
	 			int intsw=(int) ((money/100000)%10);
	 			int intbw=(int) ((money/1000000)%10);
	 			int intqw=(int) ((money/10000000)%10);
	 			maps.put("intqw",intqw);
	 			maps.put("intbw",intbw);
	 			maps.put("intsw",intsw);
	 			maps.put("intw",intw);
	 			maps.put("intq",intq);
	 			maps.put("intbai",intbai);
	 			maps.put("intshi",intshi);
	 			maps.put("intge",intge);
	 			return maps;
	 		}
	
}
