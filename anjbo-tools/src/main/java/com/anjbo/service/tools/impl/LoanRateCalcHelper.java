/**
 * 
 */
package com.anjbo.service.tools.impl;

/**
 * @author Kevin Chang
 * 
 */
public class LoanRateCalcHelper {
	public static Object[] lilv_array;
	// 利率列表
	public static String[] rates;

//	static {
//		rates = new String[] {"2015年8月26日基准利率", "9折", "95折", "98折", "上浮5%", "上浮7%", "上浮10%", "上浮15%",
//				"上浮20%", "上浮25%", "上浮30%", "上浮35%" };
//		lilv_array = new Object[rates.length];
//		// 2015年8月26日基准利率
//		Double[] sdArr1 = new Double[4];
//		sdArr1[0] = 4.6;// 商贷1年
//		sdArr1[1] = 5.0;// 商贷1～3年
//		sdArr1[2] = 5.0;// 商贷 3～5年
//		sdArr1[3] = 5.15;// 商贷 5-30年
//		Double[] gjjArr1 = new Double[2];
//		gjjArr1[0] = 2.75;// 公积金 1～5年
//		gjjArr1[1] = 3.25;// 公积金 5-30年
//		Object[] objArr1 = new Object[2];
//		objArr1[0] = sdArr1;
//		objArr1[1] = gjjArr1;
//		lilv_array[0] = objArr1;
//		// 利率下限(9折)
//		Double[] sdArr_3 = new Double[4];
//		sdArr_3[0] = 4.14;// 商贷1年
//		sdArr_3[1] = 4.5;// 商贷1～3年
//		sdArr_3[2] = 4.5;// 商贷 3～5年
//		sdArr_3[3] = 4.64;// 商贷 5-30年
//		Double[] gjjArr_3 = new Double[2];
//		gjjArr_3[0] = 2.48;// 公积金 1～5年
//		gjjArr_3[1] = 2.93;// 公积金 5-30年
//		Object[] objArr_3 = new Object[2];
//		objArr_3[0] = sdArr_3;
//		objArr_3[1] = gjjArr_3;
//		lilv_array[1] = objArr_3;
//		// 利率下限(95折)
//		Double[] sdArr_2 = new Double[4];
//		sdArr_2[0] = 4.37;// 商贷1年
//		sdArr_2[1] = 4.75;// 商贷1～3年
//		sdArr_2[2] = 4.75;// 商贷 3～5年
//		sdArr_2[3] = 4.89;// 商贷 5-30年
//		Double[] gjjArr_2 = new Double[2];
//		gjjArr_2[0] = 2.61;// 公积金 1～5年
//		gjjArr_2[1] = 3.09;// 公积金 5-30年
//		Object[] objArr_2 = new Object[2];
//		objArr_2[0] = sdArr_2;
//		objArr_2[1] = gjjArr_2;
//		lilv_array[2] = objArr_2;
//		// 利率下限(98折)
//		Double[] sdArr_1 = new Double[4];
//		sdArr_1[0] = 4.51;// 商贷1年
//		sdArr_1[1] = 4.9;// 商贷1～3年
//		sdArr_1[2] = 4.9;// 商贷 3～5年
//		sdArr_1[3] = 5.05;// 商贷 5-30年
//		Double[] gjjArr_1 = new Double[2];
//		gjjArr_1[0] = 2.70;// 公积金 1～5年
//		gjjArr_1[1] = 3.19;// 公积金 5-30年
//		Object[] objArr_1 = new Object[2];
//		objArr_1[0] = sdArr_1;
//		objArr_1[1] = gjjArr_1;
//		lilv_array[3] = objArr_1;
//		
//		// 上浮5%
//		Double[] sdArr2 = new Double[4];
//		sdArr2[0] = 4.83;// 商贷1年
//		sdArr2[1] = 5.25;// 商贷1～3年
//		sdArr2[2] = 5.25;// 商贷 3～5年
//		sdArr2[3] = 5.41;// 商贷 5-30年
//		Double[] gjjArr2 = new Double[2];
//		gjjArr2[0] = 2.89;// 公积金 1～5年
//		gjjArr2[1] = 3.41;// 公积金 5-30年
//		Object[] objArr2 = new Object[2];
//		objArr2[0] = sdArr2;
//		objArr2[1] = gjjArr2;
//		lilv_array[4] = objArr2;
//		
//		// 上浮7%
//		Double[] sdArr3 = new Double[4];
//		sdArr3[0] = 4.92;// 商贷1年
//		sdArr3[1] = 5.35;// 商贷1～3年
//		sdArr3[2] = 5.35;// 商贷 3～5年
//		sdArr3[3] = 5.51;// 商贷 5-30年
//		Double[] gjjArr3 = new Double[2];
//		gjjArr3[0] = 2.94;// 公积金 1～5年
//		gjjArr3[1] = 3.48;// 公积金 5-30年
//		Object[] objArr3 = new Object[2];
//		objArr3[0] = sdArr3;
//		objArr3[1] = gjjArr3;
//		lilv_array[5] = objArr3;
//		
//		// 上浮10%
//		Double[] sdArr4 = new Double[4];
//		sdArr4[0] = 5.06;// 商贷1年
//		sdArr4[1] = 5.5;// 商贷1～3年
//		sdArr4[2] = 5.5;// 商贷 3～5年
//		sdArr4[3] = 5.67;// 商贷 5-30年
//		Double[] gjjArr4 = new Double[2];
//		gjjArr4[0] = 3.03;// 公积金 1～5年
//		gjjArr4[1] = 3.58;// 公积金 5-30年
//		Object[] objArr4 = new Object[2];
//		objArr4[0] = sdArr4;
//		objArr4[1] = gjjArr4;
//		lilv_array[6] = objArr4;
//		
//		// 上浮15%
//		Double[] sdArr5 = new Double[4];
//		sdArr5[0] = 5.29;// 商贷1年
//		sdArr5[1] = 5.75;// 商贷1～3年
//		sdArr5[2] = 5.75;// 商贷 3～5年
//		sdArr5[3] = 5.92;// 商贷 5-30年
//		Double[] gjjArr5 = new Double[2];
//		gjjArr5[0] = 3.16;// 公积金 1～5年
//		gjjArr5[1] = 3.74;// 公积金 5-30年
//		Object[] objArr5 = new Object[2];
//		objArr5[0] = sdArr5;
//		objArr5[1] = gjjArr5;
//		lilv_array[7] = objArr5;
//		
//		// 上浮20%
//		Double[] sdArr6 = new Double[4];
//		sdArr6[0] = 5.52;// 商贷1年
//		sdArr6[1] = 6.0;// 商贷1～3年
//		sdArr6[2] = 6.0;// 商贷 3～5年
//		sdArr6[3] = 6.18;// 商贷 5-30年
//		Double[] gjjArr6 = new Double[2];
//		gjjArr6[0] = 3.3;// 公积金 1～5年
//		gjjArr6[1] = 3.9;// 公积金 5-30年
//		Object[] objArr6 = new Object[2];
//		objArr6[0] = sdArr6;
//		objArr6[1] = gjjArr6;
//		lilv_array[8] = objArr6;
//		
//		// 上浮25%
//		Double[] sdArr7 = new Double[4];
//		sdArr7[0] = 5.75;// 商贷1年
//		sdArr7[1] = 6.25;// 商贷1～3年
//		sdArr7[2] = 6.25;// 商贷 3～5年
//		sdArr7[3] = 6.44;// 商贷 5-30年
//		Double[] gjjArr7 = new Double[2];
//		gjjArr7[0] = 3.44;// 公积金 1～5年
//		gjjArr7[1] = 4.06;// 公积金 5-30年
//		Object[] objArr7 = new Object[2];
//		objArr7[0] = sdArr7;
//		objArr7[1] = gjjArr7;
//		lilv_array[9] = objArr7;
//		
//		// 上浮30%
//		Double[] sdArr8 = new Double[4];
//		sdArr8[0] = 5.98;// 商贷1年
//		sdArr8[1] = 6.5;// 商贷1～3年
//		sdArr8[2] = 6.5;// 商贷 3～5年
//		sdArr8[3] = 6.70;// 商贷 5-30年
//		Double[] gjjArr8 = new Double[2];
//		gjjArr8[0] = 3.58;// 公积金 1～5年
//		gjjArr8[1] = 4.23;// 公积金 5-30年
//		Object[] objArr8 = new Object[2];
//		objArr8[0] = sdArr8;
//		objArr8[1] = gjjArr8;
//		lilv_array[10] = objArr8;
//		
//		// 上浮35%
//		Double[] sdArr9 = new Double[4];
//		sdArr9[0] = 6.21;// 商贷1年
//		sdArr9[1] = 6.75;// 商贷1～3年
//		sdArr9[2] = 6.75;// 商贷 3～5年
//		sdArr9[3] = 6.95;// 商贷 5-30年
//		Double[] gjjArr9 = new Double[2];
//		gjjArr9[0] = 3.71;// 公积金 1～5年
//		gjjArr9[1] = 4.39;// 公积金 5-30年
//		Object[] objArr9 = new Object[2];
//		objArr9[0] = sdArr9;
//		objArr9[1] = gjjArr9;
//		lilv_array[11] = objArr9;
//
//	}

	public static Double[] calcLoanRate(int loanType, int year, int rateIndex) {
		Object[] loanFloatArr = (Object[]) lilv_array[rateIndex];
		if (loanType == 1) {// 商贷
			Double[] sdArr = (Double[]) loanFloatArr[0];
			int rangeIndex = getRangeByYear(loanType, year);
			return new Double[] { sdArr[rangeIndex] };
		} else if (loanType == 2) {// 公积金
			loanFloatArr = (Object[]) lilv_array[0];//公积金贷款利率不受利率折扣影响 （limh于2015-12-21添加）
			Double[] gjjArr = (Double[]) loanFloatArr[1];
			int rangeIndex = getRangeByYear(loanType, year);
			return new Double[] { gjjArr[rangeIndex] };
		} else if (loanType == 3) {// 综合贷
			Double[] sdArr = (Double[]) loanFloatArr[0];
			int sdRangeIndex = getRangeByYear(1, year);

			loanFloatArr = (Object[]) lilv_array[0];//公积金贷款利率不受利率折扣影响 （limh于2015-12-21添加）
			Double[] gjjArr = (Double[]) loanFloatArr[1];
			int gjjRangeIndex = getRangeByYear(2, year);
			return new Double[] { sdArr[sdRangeIndex], gjjArr[gjjRangeIndex] };
		}
		return null;
	}

	private static int getRangeByYear(int loanType, int year) {
		int indexNum = 0;
		if (loanType == 1) {// 商贷
			if (year == 1) {
				indexNum = 0;
			} else if (year > 1 && year <= 3) {
				indexNum = 1;
			} else if (year > 3 && year <= 5) {
				indexNum = 2;
			} else {
				indexNum = 3;
			}
		} else if (loanType == 2) {// 公积金
			if (year > 5) {
				indexNum = 1;
			} else {
				indexNum = 0;
			}
		}
		return indexNum;
	}

	public static String[] getRates() {
		return rates;
	}

}
