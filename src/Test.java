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

public class Test {
    private Resource resource;
    private GameImage gameImage;
    private Game game;
    private BufferedImage bufferedImage;
    private Frame frame;
    private Control control;

    public Test() throws IOException {
        String path="C:/code/new_2048/resources/lingting/";
        Gson gson=new Gson();
        Reader reader=new FileReader(path+"config.json");
        resource=gson.fromJson(reader,Resource.class);
        gameImage=new GameImage(resource,path);
        game=new Game(4);
        bufferedImage=gameImage.getImage(game.getChecks());
        frame=new Frame(bufferedImage);
        control=new Control(frame, game, new Control.Flushable() {
            public void flush() {
                bufferedImage=gameImage.getImage(game.getChecks());
                frame.repaint(bufferedImage);
            }
        });
    }
    public static void main(String[] args) throws Exception {
        new Test();
    }
}