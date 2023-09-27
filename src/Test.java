import com.google.gson.Gson;
import model.Game;
import model.Resource;
import ui.Control;
import ui.Frame;
import ui.GameImage;
import java.awt.image.BufferedImage;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
/**
 * Test
 * 临时的启动器，以后会考虑制作一个功能更全面的来代替
 */
public class Test {
    private Resource resource;
    private GameImage gameImage;
    private Game game;
    private BufferedImage bufferedImage;
    private Frame frame;
    private Control control;

    public Test() throws IOException {
        //资源包根路径
        String path="C:/code/new_2048/resources/lingting/";
        //gson对象
        Gson gson=new Gson();
        Reader reader=new FileReader(path+"config.json");
        //解析数据
        resource=gson.fromJson(reader,Resource.class);
        //获取图片逻辑类
        gameImage=new GameImage(resource,path);
        //新建游戏逻辑
        game=new Game(4);
        //获取图片
        bufferedImage=gameImage.getImage(game.getChecks());
        //新建窗口
        frame=new Frame(bufferedImage);
        //注入监视器
        control=new Control(frame, game, new Control.Flushable() {
            public void flush() {
                //重新获取图片
                bufferedImage=gameImage.getImage(game.getChecks());
                //刷新
                frame.repaint(bufferedImage);
            }
        });
    }
    /**
     * 平平无奇的主函数，可惜的是没加上异常捕获
     */
    public static void main(String[] args) throws Exception {
        new Test();
    }
}