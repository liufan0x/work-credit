package com.anjbo.utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImgUtil {
	private static Logger log = LoggerFactory.getLogger(ImgUtil.class);

	/**
	 * 旋转图片
	 * 
	 * @param src
	 *            原图片
	 * @param angel
	 *            旋转角度
	 * @return
	 */
	public static BufferedImage rotateSubImg(BufferedImage src, int angel) {
		BufferedImage img = null;
		try {
			/* 旋转操作 */
			// 计算新的图片大小
			Rectangle rect_des = CalcRotatedSize(new Rectangle(new Dimension(src.getWidth(), src.getHeight())), angel);
			img = new BufferedImage(rect_des.width, rect_des.height, src.getColorModel().getTransparency());
			Graphics2D graphics2d = (Graphics2D) img.getGraphics();
			graphics2d.setBackground(Color.WHITE);
			graphics2d.clearRect(0, 0, rect_des.width, rect_des.height);
			graphics2d.translate((rect_des.width - src.getWidth()) / 2, (rect_des.height - src.getHeight()) / 2);
			graphics2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			// 设置旋转角度和旋转中心
			graphics2d.rotate(Math.toRadians(angel), src.getWidth() / 2, src.getHeight() / 2);
			graphics2d.drawImage(src, null, null);
			graphics2d.dispose();
		} catch (Exception e) {
			log.info("图片旋转时异常", e);
			e.printStackTrace();
		}
		return img;
	}

	/**
	 * 图片截取
	 * 
	 * @param src
	 * @param dest
	 * @param startX
	 * @param startY
	 * @param subWidth
	 * @param subHeight
	 */
	public static BufferedImage subImage(BufferedImage src, int startX, int startY, int subWidth, int subHeight) {
		BufferedImage image = null;
		try {
			int width = src.getWidth();
			int height = src.getHeight();
			int outWidth = width;// 总宽
			int outHeight = height;// 总高
			outWidth = subWidth + (startX > 0 ? startX : -startX);
			outHeight = subHeight + (startY > 0 ? startY : -startY);
			image = new BufferedImage(outWidth, outHeight, BufferedImage.TYPE_INT_RGB);
			Graphics2D graphics2d = (Graphics2D) image.getGraphics();
			graphics2d.setBackground(Color.WHITE);
			graphics2d.clearRect(0, 0, outWidth, outHeight);
			graphics2d.drawImage(src, startX < 0 ? -startX : 0, startY < 0 ? -startY : 0, width, height, null);
			return image.getSubimage(startX < 0 ? 0 : startX, startY < 0 ? 0 : startY, subWidth, subHeight);
		} catch (Exception e) {
			log.info("图片裁剪异常", e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 写出图片
	 * 
	 * @param outPath
	 * @param img
	 * @return
	 */
	public static boolean writeImg(String outPath, BufferedImage img) {
		File file = new File(outPath);
		try {
			if (file.exists())
				file.delete();
			ImageIO.write(img, "jpg", file);
			return true;
		} catch (Exception e) {
			log.info("写图片异常", e);
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 计算旋转后大小
	 * 
	 * @param src
	 * @param angel
	 * @return
	 */
	private static Rectangle CalcRotatedSize(Rectangle src, int angel) {
		if (angel < 0) {
			angel = angel + 360;
		}
		if (angel >= 90) {
			if (angel / 90 % 2 == 1) {
				int temp = src.height;
				src.height = src.width;
				src.width = temp;
			}
			angel = angel % 90;
		}

		double r = Math.sqrt(src.height * src.height + src.width * src.width) / 2;
		double len = 2 * Math.sin(Math.toRadians(angel) / 2) * r;
		double angel_alpha = (Math.PI - Math.toRadians(angel)) / 2;
		double angel_dalta_width = Math.atan((double) src.height / src.width);
		double angel_dalta_height = Math.atan((double) src.width / src.height);

		int len_dalta_width = (int) (len * Math.cos(Math.PI - angel_alpha - angel_dalta_width));
		int len_dalta_height = (int) (len * Math.cos(Math.PI - angel_alpha - angel_dalta_height));
		int des_width = src.width + len_dalta_width * 2;
		int des_height = src.height + len_dalta_height * 2;
		return new java.awt.Rectangle(new Dimension(des_width, des_height));
	}
}
