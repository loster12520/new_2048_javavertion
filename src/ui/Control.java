/**
 * 用于注入动作监视器的类，很简单的代码
 * 姑且丢在ui包下吧
 */
package ui;
/**
 * 导包
 */
import model.Game;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
/**
 * @author loster12520
 */
public class Control {
    /**
     * 窗口类索引
     */
    private Frame frame;
    /**
     * 逻辑类索引
     */
    private Game game;
    /**
     * 定义实现flush的方式的格式
     */
    public static interface Flushable{
        void flush();
    }
    /**
     * 实现flush的方式
     */
    private Flushable flushable;
    /**
     * 初始化
     * @param frame 配套的窗口对象
     * @param game 配套的游戏逻辑对象
     * @param flushable 配套的刷新方式
     */
    public Control(Frame frame, Game game,Flushable flushable) {
        this.frame = frame;
        this.game = game;
        this.flushable=flushable;
        //注入键盘监测器
        frame.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
                switch (e.getKeyChar()){
                    case 'a' ->
                            game.moveLeft();
                    case 'w' ->
                            game.moveUp();
                    case 'd' ->
                            game.moveRight();
                    case 's' ->
                            game.moveDown();
                    case 'q'->
                        game.reset();
                    case 'r'->
                        game.initGame();
                }
                //
                System.out.println(e.getKeyCode()+" "+e.getKeyChar());
                game.print();
                //刷新
                flushable.flush();
            }
            //空置函数
            public void keyPressed(KeyEvent e) {}
            public void keyReleased(KeyEvent e) {}
        });
    }

}
