/*
 *Project: anjbo-credit-common
 *File: test.util.MD5UtilsTest.java  <2017年11月7日>
 ****************************************************************
 * 版权所有@2017 ANJBO.COM  保留所有权利.
 ***************************************************************/
package test.util;

import org.junit.Test;

import com.anjbo.utils.MD5Utils;

/**
 * @Author KangLG 
 * @Date 2017年11月7日 上午10:00:54
 * @version 1.0
 */
public class MD5UtilsTest {

	@Test
	public void test() {
		System.out.println(MD5Utils.MD5Encode("123456"));
	}

}
