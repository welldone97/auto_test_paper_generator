package tpg.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Random;

import javax.imageio.ImageIO;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Random;

import javax.imageio.ImageIO;

public class VerifyCodeUtils {
	private static final String DEFAULT_SOURCES = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";
	private static Random random = new Random();
	
	/**使用默认的字符源来生成指定长度length的验证码
	 * @param length 期望的验证码长度
	 * @return 验证码字符串
	 */
	public static String generatorVerifyCode(int length){
		return generatorVerifyCode(length, DEFAULT_SOURCES);
	}

	/**使用指定的字符源verifyCodes生成指定长度length的验证码
	 * @param length 期望的验证码长度
	 * @param verifyCodes 字符源
	 * @return 验证码字符串
	 */
	private static String generatorVerifyCode(int length, String sources) {
		if(sources == null || sources.length() == 0){
			sources = DEFAULT_SOURCES;
		}
		
		StringBuffer stringBuffer = new StringBuffer();
		for(int i=0; i<length; i++){
			stringBuffer.append(sources.charAt(random.nextInt(sources.length())));
		}
		
		return stringBuffer.toString();
	}
	
	/**根据宽width、高height、输出文件outputFile、验证码长度verifySize，构造验证码并输出验证码图片
	 * @param width 宽
	 * @param height 高
	 * @param outputFile 输出文件
	 * @param verifySize 验证码长度
	 * @return 验证码串
	 */
	public static String outputVerifyCodeImage(int width, int height, File outputFile, int verifySize){
		String verifyCode = generatorVerifyCode(verifySize);
		outputImage(width, height, outputFile, verifyCode);
		return verifyCode;
	}

	/**根据宽width、高height、输出文件outputFile、验证码verifyCode, 输出验证码图片
	 * @param width 宽
	 * @param height 高
	 * @param outputFile 输出文件
	 * @param verifyCode 验证码
	 */
	public static void outputImage(int width, int height, File outputFile, String verifyCode) {
		if(outputFile == null){
			return;
		}
		File dir = outputFile.getParentFile();
		if(!dir.exists()){
			dir.mkdirs();
		}
		try {
			outputFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(outputFile);
			outputImage(width, height, fos, verifyCode);
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**根据宽width、高height、输出流os、验证码verifyCode，输出到os流
	 * @param width 宽
	 * @param height 高
	 * @param os 输出流
	 * @param verifyCode 验证码
	 * @throws IOException
	 */
	public static void outputImage(int width, int height, OutputStream os, String verifyCode) throws IOException {
		int verifySize = verifyCode.length();
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics2d = bufferedImage.createGraphics();
		graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Color[] colors = new Color[5];
		Color[] colorSpaces = new Color[]{
			Color.WHITE, Color.CYAN, Color.GRAY, Color.LIGHT_GRAY,
			Color.MAGENTA, Color.ORANGE, Color.PINK, Color.YELLOW
		};
		float[] fractions = new float[colors.length];
		for(int i=0; i<colors.length; i++){
			colors[i] = colorSpaces[random.nextInt(colorSpaces.length)];
			fractions[i] = random.nextFloat();
		}
		Arrays.sort(fractions);
		
		//先填背景
		Color color = getRandomColor(200, 250);
		graphics2d.setColor(color);
		graphics2d.fillRect(0, 0, width, height);
		
		//再画边框
		graphics2d.setColor(Color.GRAY);
		graphics2d.drawRect(0, 0, width, height);
		
		//绘制干扰线
		graphics2d.setColor(getRandomColor(160, 200));
		for(int i=0; i<20; i++){
			int x1 = random.nextInt(width-1);
			int y1 = random.nextInt(height-1);
			int x2 = random.nextInt(6) + 1;
			int y2 = random.nextInt(12) + 1;
			graphics2d.drawLine(x1, y1, x1 + x2 + 40, y1 + y2 + 20);
		}
		
		//添加噪点
		float yawpRate = 0.05f;
		int area = (int)(yawpRate * width * height);
		for(int i=0; i<area; i++){
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int rgb = getRandomIntColor();
			bufferedImage.setRGB(x, y, rgb);
		}
		
		//TODO SHEAR
		
		graphics2d.setColor(getRandomColor(100, 160));
		int fontSize = height - 5;
		Font font = new Font("Algerian", Font.ITALIC, fontSize);
		graphics2d.setFont(font);
		char[] chars = verifyCode.toCharArray();
		for(int i=0; i<verifySize; i++){
			AffineTransform affine = new AffineTransform();
			affine.setToRotation(Math.PI / 4 * random.nextDouble() * (random.nextBoolean() ? 1 : -1), 
					(width / verifySize) * i + fontSize/2, height/2);
			graphics2d.setTransform(affine);
			graphics2d.drawChars(chars, i, 1, ((width-10)/verifySize)*i+5, height/2+fontSize/2-5);
		}
		
		graphics2d.dispose();
		ImageIO.write(bufferedImage, "jpg", os);
	}


	private static int getRandomIntColor() {
		int[] rgb = getRandomRgb();
        int color = 0;
        for (int c : rgb) {
            color = color << 8;
            color = color | c;
        }
        return color;
	}

	private static int[] getRandomRgb() {
		 int[] rgb = new int[3];
	     for (int i = 0; i < 3; i++) {
	    	 rgb[i] = random.nextInt(255);
	     }
	     return rgb;
	}

	private static Color getRandomColor(int frontColor, int backColor) {
		if (frontColor > 255)
            frontColor = 255;
        if (backColor > 255)
            backColor = 255;
        int r = frontColor + random.nextInt(backColor - frontColor);
        int g = frontColor + random.nextInt(backColor - frontColor);
        int b = frontColor + random.nextInt(backColor - frontColor);
        return new Color(r, g, b);
	}
}