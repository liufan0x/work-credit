package com.anjbo.utils.ccb;

import java.text.SimpleDateFormat;
import java.util.Date;

/** 
 * 建行工具类-生成ID
 * @author limh
 *
 */
public class CCBUtil {
	  private static long tmpID = 0;
	  public synchronized static String getID() {
	        long ltime = 0;
	        ltime = Long.valueOf(new SimpleDateFormat("yyMMddHHmmssSSS")
	                .format(new Date()).toString()).longValue() * 10000;
	        if (tmpID < ltime) {
	            tmpID = ltime;
	        } else {
	        	String id = String.valueOf(tmpID);
	        	String subid = id.substring(17,19);
	        	Integer flg = Integer.parseInt(subid);
	        	if(flg+1 > 99)
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
	            tmpID = tmpID + 1;
	            ltime = tmpID;
	        }
	        return String.valueOf(ltime);
	    }
}
