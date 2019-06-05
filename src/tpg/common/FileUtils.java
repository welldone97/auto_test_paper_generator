package tpg.common;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

public class FileUtils {
	public static String readFile(String path){
		StringBuffer result = new StringBuffer();
		
		try {
			FileReader fileReader = new FileReader(path);
			BufferedReader br = new BufferedReader(fileReader);
			String line = null;
			while( (line=br.readLine())!= null ){
				result.append(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result.toString();
	}
	
	public static String imageProcess(Part part, HttpServletRequest request) throws IOException {
		String cd = part.getHeader("Content-Disposition");
		String[] cds = cd.split(";");
		String filename = cds[2].substring(cds[2].indexOf("=")+1).substring(cds[2].lastIndexOf("//")+1).replace("\"", "");
		String ext = filename.substring(filename.lastIndexOf(".")+1);

		System.out.println("filename:" + filename);
		System.out.println("ext:" + ext);
		
		InputStream is = part.getInputStream();
		
		Iterator<ImageReader> irs = ImageIO.getImageReadersByFormatName(ext);
		ImageReader ir = irs.hasNext()?irs.next():null;
		if(ir == null) {
			return null;
		}
			
		ir.setInput(ImageIO.createImageInputStream(is));//必须转换为ImageInputStream，否则异常
		
		ImageReadParam rp = ir.getDefaultReadParam();
		Rectangle rect = new Rectangle(0,0,500,500);
		rp.setSourceRegion(rect);
		
		int imageNum = ir.getNumImages(true);//allowSearch必须为true，否则有些图片格式imageNum为-1。
		
		System.out.println("imageNum:" + imageNum);
		String filePath = null;
		for(int imageIndex = 0;imageIndex < imageNum; imageIndex++){
			BufferedImage bi = ir.read(imageIndex,rp);
			File file = new File(request.getServletContext().getRealPath("/uploadFile"), filename);
			filePath = file.getAbsolutePath();
			ImageIO.write(bi, ext, file);
		}
		return filePath;
	}
}