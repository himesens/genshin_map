package himesens.genshin_map.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

public class DealImage {
	
	
	public String saveImg(byte[] data) throws IOException{	
		String filename = "D://test3.jpg";
		File outfile = new File(filename);
		OutputStream os = new FileOutputStream(outfile);
		os.write(data);
		os.close();
		return "save success";
	}
	
	public String saveImg(byte[] data,String filename) throws IOException{
		File outfile = new File(filename);
		OutputStream os = new FileOutputStream(outfile);
		os.write(data);
		os.close();
		return "save success";
	}
	
	/*
	 * 将图片转化为缩略图
	 * 变量为图片输入值，图片保存的主目录
	 */
	public void ResizeImage(byte[] buffer,String filename) throws IOException{
		InputStream is = null;
		
		is = new ByteArrayInputStream(buffer);
		Image srcImg = ImageIO.read(is);
		
		int newWidth = srcImg.getWidth(null)/5;
		int newHeight = srcImg.getHeight(null)/5;
		
		BufferedImage buffImg = new BufferedImage(newWidth,newHeight,BufferedImage.TYPE_INT_RGB);
		buffImg.getGraphics().drawImage(srcImg,0,0,newWidth,newHeight,null);
		
		File outfile = new File(filename);
		OutputStream os = new FileOutputStream(outfile);
		ImageIO.write(buffImg,"JPG",os);
		os.close();
	}
	public void markImageByText(byte[] buffer,String filename,String logoText,Integer degree){
		InputStream is = null;
		OutputStream os = null;
		try{
			//源图片
			is = new ByteArrayInputStream(buffer);
			Image srcImg = ImageIO.read(is);
			BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null),srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);
			int width = srcImg.getWidth(null);//获取图片的宽
			int height = srcImg.getHeight(null);//获取图片的高
			//得到画笔对象
			Graphics2D g = buffImg.createGraphics();
			//设置线段锯齿处理
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.drawImage(srcImg.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH), 0, 0, null);
			//设置水印旋转
			if(null!=degree){
				g.rotate(Math.toRadians(degree),width/2,height/2);
			}
			//设置水印文字颜色
			g.setColor(new Color(0,0,0));
			//设置水印字体
			g.setFont(new Font("宋体", java.awt.Font.BOLD, width/4));
			//设置水印文字透明度
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.30f));
			//文字在图片上的坐标
			g.drawString(logoText,buffImg.getWidth()/3,buffImg.getHeight()/3);
			//释放资源
			g.dispose();
			//保存图片
			File outfile = new File(filename);
			os = new FileOutputStream(outfile);
			ImageIO.write(buffImg,"JPG",os);
			os.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public byte[] markImageByText(File file,String filename,String logoText,Integer degree){
		byte[] bytes = null;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		//扩展logo文字
		String finallogo = logoText;
		for(int i=0;i<3;i++){
			finallogo = finallogo + " " + logoText;
		}
		try{
			//源图片
			//System.out.println(url);
			Image srcImg = ImageIO.read(file);
			BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null),srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);
			int width = srcImg.getWidth(null);//获取图片的宽
			int height = srcImg.getHeight(null);//获取图片的高
			//得到画笔对象
			Graphics2D g = buffImg.createGraphics();
			//设置线段锯齿处理
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.drawImage(srcImg.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH), 0, 0, null);
			//设置水印旋转
			if(null!=degree){
				g.rotate(Math.toRadians(degree),0,0);
			}
			//设置水印文字颜色
			g.setColor(new Color(255,255,255));
			//设置水印字体
			g.setFont(new Font("宋体", java.awt.Font.BOLD, width/12));
			//设置水印文字透明度
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.20f));
			//文字在图片上的坐标
			g.drawString(finallogo,0,0);
			g.drawString(finallogo,width/5,height/5);
			g.drawString(finallogo,width/5,height/2);
			//释放资源
			g.dispose();
			//导出资源
			ImageIO.write(buffImg,"JPG",out);
			bytes = out.toByteArray();
		}catch(Exception e){
			e.printStackTrace();
		}
		return bytes;
	}
}
