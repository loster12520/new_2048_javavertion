package ui;

import model.Resource;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameImage {
    private Resource resource;
    private String path;
    private BufferedImage background_image;
    private BufferedImage broad_image;
    private List<BufferedImage> chests_image;

    public GameImage(Resource resource, String path) throws IOException {
        this.resource = resource;
        this.path = path+"data/";

        background_image= ImageIO.read(new File(this.path+resource.getTexture().getBackground().getPath()));
        if (background_image.getWidth()!=resource.getTexture().getBackground().getWidth() &&
                background_image.getHeight()!=resource.getTexture().getBackground().getHeight())
            background_image=toBufferedImage(
                    background_image.getScaledInstance(resource.getTexture().getBackground().getWidth(),
                            resource.getTexture().getBackground().getHeight(),Image.SCALE_DEFAULT));
        background_image=img_alpha(
                background_image,
                (int)(resource.getTexture().getBackground().getClarity()*100));

        broad_image=ImageIO.read(new File(this.path+resource.getTexture().getBroad().getPath()));
        if (broad_image.getWidth()!=resource.getTexture().getBroad().getWidth() &&
                broad_image.getHeight()!=resource.getTexture().getBroad().getHeight())
            broad_image=toBufferedImage(
                    broad_image.getScaledInstance(resource.getTexture().getBroad().getWidth(),
                            resource.getTexture().getBroad().getHeight(),Image.SCALE_DEFAULT));
//        broad_image=img_alpha(broad_image,(int)(resource.getTexture().getBroad().getClarity()*100));

        chests_image=new ArrayList<>();
        for (Resource.Texture.Chest chest :resource.getTexture().getChests()){
            BufferedImage bufferedImage=ImageIO.read(new File(this.path+chest.getPath()));
            if (chest.getWidth()!=bufferedImage.getWidth() && chest.getHeight()!= bufferedImage.getHeight())
                bufferedImage=toBufferedImage(bufferedImage.getScaledInstance(
                        chest.getWidth().intValue(),
                        chest.getHeight().intValue(),
                        Image.SCALE_DEFAULT));
            chests_image.add(bufferedImage);
        }
    }

    public BufferedImage getImage(Integer[][] data){
        BufferedImage bufferedImage=new BufferedImage(
                resource.getTexture().getBackground().getWidth(),
                resource.getTexture().getBackground().getHeight(),
                BufferedImage.TYPE_3BYTE_BGR
        );
        Graphics2D graphics2D=bufferedImage.createGraphics();

        //loading image
        graphics2D.drawImage(background_image,null,
                resource.getTexture().getBackground().getX(),
                resource.getTexture().getBackground().getY());
        graphics2D.drawImage(broad_image,null,
                resource.getTexture().getBroad().getX(),
                resource.getTexture().getBroad().getY());
        for (int i=0;i<data.length;i++)
            for (int j=0;j<data[i].length;j++)
                graphics2D.drawImage(
                        getChestImage(data[i][j]),
                        null,
                        (int)(resource.getTexture().getBroad().getChest().getX()+
                                j * resource.getTexture().getBroad().getChest().getSide()+
                                (j-1)* resource.getTexture().getBroad().getChest().getInterval()),
                        (int) (resource.getTexture().getBroad().getChest().getY()+
                                i * resource.getTexture().getBroad().getChest().getSide()+
                                (i-1)* resource.getTexture().getBroad().getChest().getInterval())
                );
        graphics2D.dispose();
        return bufferedImage;
    }
    private BufferedImage getChestImage(Integer integer){
        if (integer<=0)
            return null;
        else{
            Integer i=integer,n=0;
            while (i>2){
                i>>=1;
                n++;
            }
            return chests_image.get(n);
        }
    }
    private static BufferedImage img_alpha(Image img,int alpha) {
        try {
            BufferedImage bufferedImage=toBufferedImage(img);
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();
            //创建一个包含透明度的图片,半透明效果必须要存储为png合适才行，存储为jpg，底色为黑色
            BufferedImage back=new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
            for (int j = 0; j < height; j++) {
                for (int i = 0; i < width; i++) {
                    int rgb = bufferedImage.getRGB(i, j);
                    if (rgb==0)
                        continue;
                    Color color = new Color(rgb);
                    Color newcolor = new Color(color.getRed(), color.getGreen(),color.getBlue(), alpha);
                    back.setRGB(i,j,newcolor.getRGB());
                }
            }
            return back;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private static BufferedImage toBufferedImage(Image image) {
        BufferedImage bufferedImage=new BufferedImage(
                image.getWidth(null),
                image.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);
        Graphics graphics=bufferedImage.createGraphics();
        graphics.drawImage(image,0,0,null);
        return bufferedImage;
    }
}
