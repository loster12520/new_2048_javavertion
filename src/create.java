import ui.Frame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class create {
    //这是一个临时类，用于绘制一些简单的样式
    public static void main(String[] args) throws IOException{
        Frame frame=new Frame(image());
    }
    public static BufferedImage image() throws IOException {
        BufferedImage bufferedImage=new BufferedImage(500,500,BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D=bufferedImage.createGraphics();
        graphics2D.setColor(new Color(0xf3c2f5));
        graphics2D.draw(new RoundRectangle2D.Double(20,40,350,350,10,10));
        for (int i=0;i<4;i++)
            for (int j=0;j<4;j++)
                graphics2D.draw(new RoundRectangle2D.Double(20+(350.0/4)*i+2.5,40+(350.0/4)*j+2.5,350.0/4-5,350.0/4-5,5,5));
        graphics2D.draw(new RoundRectangle2D.Double(380,60,100,100,8,8));
        graphics2D.draw(new RoundRectangle2D.Double(380,170,100,100,8,8));
        graphics2D.draw(new RoundRectangle2D.Double(380,280,100,100,8,8));
        ImageIO.write(bufferedImage,"png",new File("C:\\code\\new_2048\\resources\\lingting\\data\\broad.png"));
        return bufferedImage;
    }
}
