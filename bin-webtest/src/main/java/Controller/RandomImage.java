package Controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RandomImage {

	private final static Object[] code = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

	private final static Random r = new Random();

	// private static Object[] code = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

	public static synchronized String randomImageWriter(final HttpServletResponse response, final HttpServletRequest request, int width, int height, int fontSize) {
		// public static synchronized BufferedImage randomImage(final HttpServletResponse response, final HttpServletRequest request, int width, int height, int fontSize) {
		int w = width;
		int h = height;
		String randomStr = "";
		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_BGR);
		Graphics g = image.getGraphics();
		g.setFont(new Font("黑体", Font.BOLD, fontSize));
		for (int i = 0; i < 4; i++) {
			int x = r.nextInt(code.length);
			randomStr += code[x].toString();
			g.setColor(new Color(r.nextInt(100) + 100, r.nextInt(100) + 100, r.nextInt(100) + 100));
			g.drawString(code[x].toString(), i == 0 ? 16 : (i + 1) * 20, 20);
		}
		for (int i = 0; i < 4; i++) {
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
}
