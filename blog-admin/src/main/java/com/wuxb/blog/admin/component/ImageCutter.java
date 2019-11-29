package com.wuxb.blog.admin.component;

import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;

public class ImageCutter {
	
	private byte[] imageBytes;
	private String imageType = "jpg";
	
	public ImageCutter(String inputPath, String imageType) throws IOException {
		this(inputPath);
		if(imageType == "jpeg") {
			imageType = "jpg";
		}
		this.imageType = imageType;
	}
	
	public ImageCutter(String inputPath) throws IOException {
		FileInputStream fileInputStream = new FileInputStream(inputPath);
		imageBytes = fileInputStream.readAllBytes();
		fileInputStream.close();
	}

	public void cutCenterImage(int outputWeight, int outputHeight) throws IOException {
		Iterator<ImageReader> iterator = ImageIO.getImageReadersByFormatName(imageType);
		ImageReader imageReader = iterator.next();
		imageReader.setInput(ImageIO.createImageInputStream(new ByteArrayInputStream(imageBytes)));
		ImageReadParam imageReadParam = imageReader.getDefaultReadParam();
		
		//原始宽高
		int originWidth = imageReader.getWidth(0);
		int originHeight = imageReader.getHeight(0);
		
		//裁剪起始坐标
		int positionX = 0;
		int positionY = 0;
		//若输出宽度小于原始宽度，则执行居中裁剪
		if(outputWeight < originWidth) {
			positionX = (originWidth - outputWeight) / 2;
		}
		//若输出高度小于原始高度，则执行居中裁剪
		if(outputHeight < originHeight) {
			positionY = (originHeight - outputHeight) / 2;
		}
		
		//裁剪
		imageReadParam.setSourceRegion(new Rectangle(positionX, positionY, outputWeight, outputHeight));
		
		//保存
		BufferedImage bufferedImage = imageReader.read(0, imageReadParam);
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ImageIO.write(bufferedImage, imageType, byteArrayOutputStream);
		imageBytes = byteArrayOutputStream.toByteArray();
	}
	
	public void zoomImage(int outputWidth, int outputHeight) throws IOException {
		BufferedImage bufImg = ImageIO.read(new ByteArrayInputStream(imageBytes));
		//原始宽高
		int originWidth = bufImg.getWidth();
		int originHeight = bufImg.getHeight();
		
		//缩放比例
		double widthZoomRate = outputWidth * 1d / originWidth;
		double heightZoomRate;
		if(widthZoomRate > 0 && outputHeight == -1) {
			heightZoomRate = widthZoomRate;
		} else {
			heightZoomRate = outputHeight * 1d / originHeight;
		}
		
		//缩放
		AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(widthZoomRate, heightZoomRate), null);
		BufferedImage bufferedImage = ato.filter(bufImg, null);
		
		//保存
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ImageIO.write(bufferedImage, imageType, byteArrayOutputStream);
		imageBytes = byteArrayOutputStream.toByteArray();
	}
	
	public void zoomImageByWidth(int outputWidth) throws IOException {
		zoomImage(outputWidth, -1);
	}
	
	public void writeFile(String outputPath) throws IOException {
		FileOutputStream  fileOutputStream = new FileOutputStream(new File(outputPath));
		fileOutputStream.write(imageBytes);
		fileOutputStream.close();
	}
	
}
