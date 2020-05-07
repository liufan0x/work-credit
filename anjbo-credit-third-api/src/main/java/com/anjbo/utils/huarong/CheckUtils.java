package com.anjbo.utils.huarong;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 通用校验规则
 * @author
 */
public class CheckUtils {
	/**
	 * 检查身份证号函数 返回值为true是身份证号符合规则 为false身份证号不符合规则
	 * select CERT_CODE from cus_indiv
	 */
	public static final boolean isIdentityId(String identityId) {
		if (identityId == null || "".equals(identityId.trim())) return false;
		identityId = identityId.trim();

		if (isEmpty(identityId)) return false;
		try {
			if (identityId.length() == 18) {
				String identityId15 = identityId.substring(0, 6) + identityId.substring(8, 17);
				// System.out.println("the identityId15 is : "+identityId15);
				if (fixPersonIDCode(identityId15).equalsIgnoreCase(identityId)) {
					return true;
				} else {
					return false;
				}
			} else if (identityId.length() == 15) {
				try {
					Long.parseLong(identityId);
					return true;
				} catch (Exception ex) {
					return false;
				}
			} else {
				return false;
			}
		} catch (Exception ex) {
			return false;
		}
	}

	public static String fixPersonIDCode(String personIDCode) {
		String retIDCode = "";
		if (personIDCode == null || personIDCode.trim().length() != 15) {
			return personIDCode;
		}
		String id17 = personIDCode.substring(0, 6) + "19" + personIDCode.substring(6, 15); // 15为身份证补\'19\'

		char[] code = { '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2' }; // 11个
		int[] factor = { 0, 2, 4, 8, 5, 10, 9, 7, 3, 6, 1, 2, 4, 8, 5, 10, 9, 7 }; // 18个;
		int[] idcd = new int[18];
		int i;
		int j;
		int sum;
		int remainder;

		for (i = 1; i < 18; i++) {
			j = 17 - i;
			idcd[i] = Integer.parseInt(id17.substring(j, j + 1));
		}

		sum = 0;
		for (i = 1; i < 18; i++) {
			sum = sum + idcd[i] * factor[i];
		}
		remainder = sum % 11;
		String lastCheckBit = String.valueOf(code[remainder]);
		return id17 + lastCheckBit;
	}

	public static boolean isEmpty(String sValue) {
		if (sValue == null) return true;
		return sValue.trim().equals("") ? true : false;
	}

	/**
	 * 检查组织机构号函数 返回值为true是组织机构号符合规则 为false组织机构号不符合规则
	 * 78901388-7
	 * X2989109-7
	 */
	public static final boolean isOrgid(String code) {
		if (code == null || "".equals(code.trim())) return false;
		code = code.trim();
		// 旧的组织机构代码证规则
		if (code.length() != 10) {
			return false;
		}
		if (code.startsWith("X")) {
			Long.parseLong(code.substring(1, 8) + code.substring(9, 10));
			return true;
		}

		// 现有的组织机构代码证规则
		int[] w_i = new int[8];
		int[] c_i = new int[8];
		int s = 0;
		char[] financecode = new char[10];

		financecode[0] = code.charAt(0);
		financecode[1] = code.charAt(1);
		financecode[2] = code.charAt(2);
		financecode[3] = code.charAt(3);
		financecode[4] = code.charAt(4);
		financecode[5] = code.charAt(5);
		financecode[6] = code.charAt(6);
		financecode[7] = code.charAt(7);
		financecode[8] = code.charAt(8);
		financecode[9] = code.charAt(9);

		if (code.equals("00000000-0")) {
			return false;
		}
		w_i[0] = 3;
		w_i[1] = 7;
		w_i[2] = 9;
		w_i[3] = 10;
		w_i[4] = 5;
		w_i[5] = 8;
		w_i[6] = 4;
		w_i[7] = 2;
		if (financecode[8] != 45) {
			return false;
		}
		int c;
		for (int i = 0; i < 10; i++) {
			c = financecode[i];
			if (c <= 122 && c >= 97) {
				return false;
			}
		}

		char fir_value = financecode[0];
		char sec_value = financecode[1];
		if (fir_value >= 65 && fir_value <= 90) c_i[0] = (fir_value + 32) - 87;
		else if (fir_value >= 48 && fir_value <= 57) c_i[0] = fir_value - 48;
		else {
			return false;
		}
		s += w_i[0] * c_i[0];
		if (sec_value >= 65 && sec_value <= 90) c_i[1] = (sec_value - 65) + 10;
		else if (sec_value >= 48 && sec_value <= 57) c_i[1] = sec_value - 48;
		else {
			return false;
		}
		s += w_i[1] * c_i[1];
		for (int j = 2; j < 8; j++) {
			if (financecode[j] < 48 || financecode[j] > 57) {
				return false;
			}
			c_i[j] = financecode[j] - 48;
			s += w_i[j] * c_i[j];
		}

		c = 11 - s % 11;
		if (financecode[9] == 88 && c == 10 || c == 11 && financecode[9] == 48 || c == financecode[9] - 48) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 验证手机号格式
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern.compile("^(13[0-9]|15[012356789]|17[3678]|18[0-9]|14[57])\\d{8}$|^170[0125789]\\d{7}$");
		Matcher m = p.matcher(mobiles);
		System.out.println(m.matches() + "---");
		return m.matches();
	}

	/**
	 * 验证真实姓名
	 * @param mobiles
	 * @return
	 */
	public static boolean isRealName(String realName) {
		// Pattern p = Pattern.compile("[\u4E00-\u9FA5]{2,5}(?:·[\u4E00-\u9FA5]{2,5})*");
		// Matcher m = p.matcher(realName);

		Pattern p = Pattern.compile("[\u4E00-\u9FA5]{2,6}(?:·[\u4E00-\u9FA5]{2,6})*");
		Matcher m = p.matcher(realName);

		Pattern p1 = Pattern.compile("[\u4E00-\u9FA5]{1,6}(?:·[\u4E00-\u9FA5]{2,6}){1,}");
		Matcher m1 = p1.matcher(realName);

		return m.matches() || m1.matches();
	}

	public static void main(String[] args) {
		// System.out.println(Check.isIdentityId("35060019780121301X"));
		// String code = "777537114";
		// //System.out.println(code.substring(1,8)+code.substring(9,10));
		// //System.err.println(Check.isOrgid("L0559189-3"));
		// //System.err.println(Check.isOrgid("78693986-0"));
		// //System.err.println(Check.isOrgid("L0337271-8"));
		// System.err.println(code.substring(0,8)+"-"+code.substring(8,9));
		// System.out.println(CheckUtils.isRealName("卡儿·马克思·马克思"));

		System.out.println(CheckUtils.isMobileNO("18200333738"));

	}
}
