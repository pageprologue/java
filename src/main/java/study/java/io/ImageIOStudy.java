package study.java.io;

import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Color;

public class ImageIOStudy {

    public void readImageToByte(String fileName) {
        BufferedImage image;
        int width;
        int height;
        
        try {
            File input = new File(fileName);
            image = ImageIO.read(input);
            width = image.getWidth();
            height = image.getHeight();
            int pixel = width * height;
            
            int count = 0;

            String[] colorText = new String[pixel];
            
            for(int i = 0; i < height; i++) {
                for(int j = 0; j < width; j++) {
                    Color c = new Color(image.getRGB(j, i));
                    colorText[count] = c.toString();

                    System.out.println("No: " + count + " Red: " + c.getRed() +"  Green: " + c.getGreen() + " Blue: " + c.getBlue());
                    count++;
                }
            }
   
         } catch (Exception e) {
             e.printStackTrace();
         }
    }
}