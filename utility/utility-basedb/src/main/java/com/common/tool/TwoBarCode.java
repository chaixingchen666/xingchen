/**
 * @author sunhong
 */
package com.common.tool;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import javax.imageio.ImageIO;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class TwoBarCode {

	private static final String CHARSET = "utf-8";
	private static final String FORMAT_NAME = "JPG";
	// 二维码尺寸
	private static final int QRCODE_SIZE = 500;
	// LOGO宽度
	private static final int WIDTH = 80;
	// LOGO高度
	private static final int HEIGHT = 80;

	private static BufferedImage createImage(String content, String imgPath, boolean needCompress) {
		return createImage(content, imgPath, QRCODE_SIZE, needCompress);
	}

	private static BufferedImage createImage(String content, String imgPath, int qrcodesize, boolean needCompress) {
		Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
		hints.put(EncodeHintType.MARGIN, 1);
		BitMatrix bitMatrix = null;
		try {
			bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, qrcodesize, qrcodesize, hints);
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int width = bitMatrix.getWidth();
		int height = bitMatrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
			}
		}
		if (imgPath == null || "".equals(imgPath)) {
			return image;
		}
		// 插入图片
		TwoBarCode.insertImage(image, imgPath, qrcodesize, needCompress);
		return image;
	}

	/**
	 * 插入LOGO
	 * 
	 * @param source
	 *            二维码图片
	 * @param imgPath
	 *            LOGO图片地址
	 * @param needCompress
	 *            是否压缩
	
	 */
	private static void insertImage(BufferedImage source, String logImgPath, boolean needCompress) {
		insertImage(source, logImgPath, QRCODE_SIZE, needCompress);
		//		File file = new File(logImgPath);
		//		if (!file.exists()) {
		//			//System.out.println("" + logImgPath + " 该文件不存在！");
		//			return;
		//		}
		//
		//		Image src = null;
		//		try {
		//			src = ImageIO.read(new File(logImgPath));
		//
		//		} catch (IOException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}
		//		int width = src.getWidth(null);
		//		int height = src.getHeight(null);
		//		if (needCompress) { // 压缩LOGO
		//			if (width > WIDTH) {
		//				width = WIDTH;
		//			}
		//			if (height > HEIGHT) {
		//				height = HEIGHT;
		//			}
		//			Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		//			BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		//			Graphics g = tag.getGraphics();
		//			g.drawImage(image, 0, 0, null); // 绘制缩小后的图
		//			g.dispose();
		//			src = image;
		//		}
		//		// 插入LOGO
		//		Graphics2D graph = source.createGraphics();
		//		int x = (QRCODE_SIZE - width) / 2;
		//		int y = (QRCODE_SIZE - height) / 2;
		//		graph.drawImage(src, x, y, width, height, null);
		//		Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
		//		graph.setStroke(new BasicStroke(3f));
		//		graph.draw(shape);
		//		graph.dispose();

	}

	private static void insertImage(BufferedImage source, String logImgPath, int qrcodesize, boolean needCompress) {
		File file = new File(logImgPath);
		if (!file.exists()) {
			//System.out.println("" + logImgPath + " 该文件不存在！");
			return;
		}

		Image src = null;
		try {
			src = ImageIO.read(new File(logImgPath));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int width = src.getWidth(null);
		int height = src.getHeight(null);
		if (needCompress) { // 压缩LOGO
			if (width > WIDTH) {
				width = WIDTH;
			}
			if (height > HEIGHT) {
				height = HEIGHT;
			}
			Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			g.drawImage(image, 0, 0, null); // 绘制缩小后的图
			g.dispose();
			src = image;
		}
		// 插入LOGO
		Graphics2D graph = source.createGraphics();
		int x = (qrcodesize - width) / 2;
		int y = (qrcodesize - height) / 2;
		graph.drawImage(src, x, y, width, height, null);
		Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
		graph.setStroke(new BasicStroke(3f));
		graph.draw(shape);
		graph.dispose();

	}

	/**
	 * 生成二维码(内嵌LOGO)
	 * 
	 * @param content
	 *            内容
	 * @param imgPath
	 *            LOGO地址
	 * @param destPath
	 *            存放目录
	 * @param needCompress
	 *            是否压缩LOGO
	 * 
	 */
	public static boolean encode(String content, String logImgPath, String destImgPath, int qrcodesize, boolean needCompress) {
		//System.out.println("encode 1:"+logImgPath);
		BufferedImage image = TwoBarCode.createImage(content, logImgPath, qrcodesize, needCompress);

		try {
			ImageIO.write(image, FORMAT_NAME, new File(destImgPath));
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public static boolean encode(String content, String logImgPath, String destImgPath, boolean needCompress) {
		//System.out.println("encode 1:"+logImgPath);
		BufferedImage image = TwoBarCode.createImage(content, logImgPath, needCompress);

		try {
			ImageIO.write(image, FORMAT_NAME, new File(destImgPath));
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 生成二维码(内嵌LOGO)
	 * 
	 * @param content
	 *            内容
	 * @param imgPath
	 *            LOGO地址
	 * @param destPath
	 *            存储地址
	 */
	public static boolean encode(String content, String logImgPath, String destImgPath) {
		return TwoBarCode.encode(content, logImgPath, destImgPath, false);
	}

	/**
	 * 生成二维码
	 * 
	 * @param content
	 *            内容
	 * @param destPath
	 *            存储地址
	 * @param needCompress
	 *            是否压缩LOGO
	 */
	public static boolean encode(String content, String destImgPath, boolean needCompress) {
		return TwoBarCode.encode(content, null, destImgPath, needCompress);
	}

	/**
	 * 生成二维码
	 * 
	 * @param content
	 *            内容
	 * @param destPath
	 *            存储地址
	 */
	public static boolean encode(String content, String destImgPath) {
		return TwoBarCode.encode(content, null, destImgPath, false);
	}

}
