package com.cheuks.bin.base.web.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
public class DefaultRandomImage implements RandomImage {

	private final static Object[] code = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O",
			"P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

	private final static Random r = new Random();

	private int width = 100;
	private int height = 30;
	private int fontSize = 18;
	private int codeCount = 4;
	private int straightLineCount = 8;
	private String font = "黑体";

	public synchronized String randomImageWriter(final HttpServletResponse response, final HttpServletRequest request) {
		int w = width;
		int h = height;
		String randomStr = "";
		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_BGR);
		Graphics g = image.getGraphics();
		g.setFont(new Font(font, Font.BOLD, fontSize));
		for (int i = 0; i < codeCount; i++) {
			int x = r.nextInt(code.length);
			randomStr += code[x].toString();
			g.setColor(new Color(r.nextInt(100) + 100, r.nextInt(100) + 100, r.nextInt(100) + 100));
			g.drawString(code[x].toString(), i == 0 ? 16 : (i + 1) * 20, 20);
		}
		for (int i = 0; i < straightLineCount; i++) {
			g.setColor(new Color(r.nextInt(100) + 100, r.nextInt(100) + 100, r.nextInt(100) + 100));
			g.drawLine(r.nextInt(w), r.nextInt(h), r.nextInt(w), r.nextInt(h));
		}
		g.dispose();
		// request.getSession().setAttribute("verificationCode", randomStr);
		try {
			ImageIO.write(image, "jpg", response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		// return image;
		return randomStr;
	}

	public int getWidth() {
		return width;
	}

	public RandomImage setWidth(int width) {
		this.width = width;
		return this;
	}

	public int getHeight() {
		return height;
	}

	public RandomImage setHeight(int height) {
		this.height = height;
		return this;
	}

	public int getFontSize() {
		return fontSize;
	}

	public RandomImage setFontSize(int fontSize) {
		this.fontSize = fontSize;
		return this;
	}

	public int getCodeCount() {
		return codeCount;
	}

	public RandomImage setCodeCount(int codeCount) {
		this.codeCount = codeCount;
		return this;
	}

	public int getStraightLineCount() {
		return straightLineCount;
	}

	public RandomImage setStraightLineCount(int straightLineCount) {
		this.straightLineCount = straightLineCount;
		return this;
	}

}
