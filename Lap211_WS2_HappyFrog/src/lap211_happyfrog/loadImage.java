/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lap211_happyfrog;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author tranp
 */
public class loadImage
{
    public static BufferedImage image;
   

    public static void init()
    {
        image = imageLoader("res/frog.png");
       
    }

    public static BufferedImage imageLoader(String path)
    {
        try
        {
            return ImageIO.read(new File(path));
        } catch (IOException e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }
    
}