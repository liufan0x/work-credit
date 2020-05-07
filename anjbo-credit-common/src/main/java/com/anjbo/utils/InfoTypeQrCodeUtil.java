package com.anjbo.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.web.multipart.MultipartFile;

import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

public class InfoTypeQrCodeUtil {

	private static Map infoTypeQrCodeMap = new HashMap();

	private InfoTypeQrCodeUtil() {
		
	}

	/**
	 * 获得二维码信息和类型对应关系的工具map
	 * map中的key是扫描二维码得到的信息。
	 * value 是该二维码对应的资料类型名称(数据库中的)。
	 */
	public static Map getInfoTypeQrCodeMap() {
		if (infoTypeQrCodeMap == null || infoTypeQrCodeMap.isEmpty()) {
			infoTypeQrCodeMap.put("A1", "身份证明");
			infoTypeQrCodeMap.put("A3", "婚姻证明");
			infoTypeQrCodeMap.put("B2", "房产证");
			infoTypeQrCodeMap.put("B3", "原抵押借款合同及还款清单");
			infoTypeQrCodeMap.put("C1", "征信报告");
			infoTypeQrCodeMap.put("D5", "委托公证书");
			infoTypeQrCodeMap.put("E2", "新贷款银行批复");
			infoTypeQrCodeMap.put("E3", "买卖合同");
		}
		return infoTypeQrCodeMap;
	}
	
	public static String getInfoTypeFromPic(MultipartFile file) {
		String contents = null;

		MultiFormatReader formatReader = new MultiFormatReader();

		BufferedImage image;
		try {
			image = ImageIO.read(file.getInputStream());
			// 将图像数据转换为1 bit data
			LuminanceSource source = new BufferedImageLuminanceSource(image);
			Binarizer binarizer = new HybridBinarizer(source);
			// BinaryBitmap是ZXing用来表示1 bit data位图的类，Reader对象将对它进行解析
			BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
			Map hints = new HashMap();
			hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
			// 对图像进行解码
			Result result = formatReader.decode(binaryBitmap, hints);
			contents = result.toString();
			System.out.println("barcode encoding format :\t " + result.getBarcodeFormat());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
		String infoTypeName = (String) getInfoTypeQrCodeMap().get(contents);
		return infoTypeName;
	}

}
