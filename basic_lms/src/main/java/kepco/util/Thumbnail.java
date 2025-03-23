package kepco.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Thumbnail {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Thumbnail.class);
	
	/**  
	 * thumbnail.setThumbnail(this.basePath.resolve(FILE_PATH) + "/" + fileSaveName, this.basePath.resolve(FILE_PATH) + "/" + "thumb_" + fileSaveName, 600, 0); // 원본, 뉴파일명, 새로운 너비, 새로운 높이(0:비율값)
	 * 썸네일 생성
	 * @param orgName 원본 이미지 파일 경로
	 * @param destName 썸에릴로 저장될 이미지 파일 경로
	 * @param width 줄일 가로 길이
	 * @param height 줄일 세로 길이
	 * @return 썸네일 파일 이름
	 * @throws IOException
	 */
	public static String setThumbnail(String orgName, String destName, int width, int height) throws IOException
	{
		File orgFile = new File(orgName);
		File destFile = new File(destName);
		
		return createThumb(orgFile, destFile, width, height);
	}
	
	/**
	 * 썸네일 생성
	 * @param orgFile 원본 이미지 파일 객체
	 * @param destFile 썸네일로 저장될 이미지 파일 객체
	 * @param width 줄일 가로 길이
	 * @param height 줄일 세로 길이
	 * @return 썸네일 파일이름
	 * @throws IOException
	 */
	public static String createThumb(File orgFile, File destFile, int width, int height) throws IOException
	{
		Image srcImg = null;
		String suffix = orgFile.getName().substring(orgFile.getName().lastIndexOf('.') + 1).toLowerCase();

		if (suffix.equals("bmp") || suffix.equals("png") || suffix.equals("gif")) {
			srcImg = ImageIO.read(orgFile);
		} else {
			srcImg = new ImageIcon(orgFile.toString()).getImage();
		}
		
		int srcWidth = srcImg.getWidth(null);
		int srcHeight = srcImg.getHeight(null);
		int destWidth = -1, destHeight = -1;
		
		if((srcWidth > srcHeight) || height <= 0){	// 가로가 세로보다 클때
			
			if(srcWidth > width){
				destWidth = width;
			}else{
				destWidth = srcWidth;
			}
			
			if(srcWidth > width){		
				float w =  (float)width * 100 / (float)srcWidth;
				destHeight = (int)( srcHeight * w / 100);
			}else{
				destHeight = srcHeight;
			}
			
		}else{						// 세로가 가로보다 클때
			
			if(srcHeight > height){
				destHeight = height;
			}else{
				destHeight = srcHeight;
			}
			
			if(srcHeight > height){		
				float h =  (float)height * 100 / (float)srcHeight;
				destWidth = (int)( srcWidth * h / 100);
			}else{
				destWidth = srcWidth;
			}
			
		}
		
		Image imgTarget = srcImg.getScaledInstance(destWidth, destHeight, Image.SCALE_SMOOTH);
		int pixels[] = new int[destWidth * destHeight];
		PixelGrabber pg = new PixelGrabber(imgTarget, 0, 0, destWidth, destHeight, pixels, 0, destWidth);

		try {
			pg.grabPixels();
		} catch (InterruptedException e) {
			throw new IOException();
		} catch (Throwable e) {
			LOGGER.debug("e: {}", e);

		}

		BufferedImage destImg = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB);
		destImg.setRGB(0, 0, destWidth, destHeight, pixels, 0, destWidth);
		ImageIO.write(destImg, "jpg", destFile);

		return destFile.getName();
	}

}
