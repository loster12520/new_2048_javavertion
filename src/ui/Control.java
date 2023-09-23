package ui;

import model.Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Control {
    private Frame frame;
    private Game game;
    public static interface Flushable{
        void flush();
    }
    private Flushable flushable;
    public Control(Frame frame, Game game,Flushable flushable) {
        this.frame = frame;
        this.game = game;
        this.flushable=flushable;
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
                System.out.println(e.getKeyCode()+" "+e.getKeyChar());
                game.print();
                flushable.flush();
            }
            public void keyPressed(KeyEvent e) {}
            public void keyReleased(KeyEvent e) {}
        });
    }

}
