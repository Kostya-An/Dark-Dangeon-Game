package future.code.dark.dungeon;

import future.code.dark.dungeon.controller.MovementController;
import future.code.dark.dungeon.service.GameMaster;

import javax.swing.*;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static future.code.dark.dungeon.config.Configuration.*;

public class GameFrame extends JPanel implements ActionListener {

    private final GameMaster gameMaster;
    Timer timer;
    JFrame frame;

    public GameFrame(JFrame frame) {
        timer = new Timer(1000 / GAME_FRAMES_PER_SECOND, this);
        this.gameMaster = GameMaster.getInstance();
        this.frame = frame;
        frame.setSize(gameMaster.getMap().getWidth() * SPRITE_SIZE, gameMaster.getMap().getHeight() * SPRITE_SIZE);
        frame.setLocationRelativeTo(null);
        timer.start();
        frame.addKeyListener(new MovementController(gameMaster.getPlayer()));
    }

    @Override
    public void paint(Graphics graphics) {
        gameMaster.renderFrame(graphics);
        gameMaster.renderEnemiesAndPlayer(graphics);
        if(gameMaster.win){
            frame.setSize(new ImageIcon(VICTORY_SPRITE).getIconWidth(), new ImageIcon(VICTORY_SPRITE).getIconHeight());
        }
        if(gameMaster.gameOver){
            frame.setSize(new ImageIcon(GAME_OVER_SPRITE).getIconWidth(), new ImageIcon(GAME_OVER_SPRITE).getIconHeight());
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) { // Always triggered by Timer
        repaint();
    }
}