package future.code.dark.dungeon.domen;



import future.code.dark.dungeon.config.Configuration;
import future.code.dark.dungeon.service.GameMaster;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static future.code.dark.dungeon.config.Configuration.EXIT_CHARACTER;
import static future.code.dark.dungeon.config.Configuration.GHOST_ANIMATION;


public class Enemy extends DynamicObject {
    Timer timer;
    public Enemy(int xPosition, int yPosition) {
        super(xPosition, yPosition, Arrays.stream(GHOST_ANIMATION).toList());

         timer = new Timer(1000 / 40 * 30, e -> {
            System.out.println("Действие");

//            List<Direction> list = new ArrayList<>();
//            char[][] chars = GameMaster.getInstance().getMap().getMap();
//            if (GameMaster.getInstance().getPlayer().getXPosition() > this.xPosition
//                    && chars[getYPosition()][getXPosition() + 1] != WALL_CHARACTER
//                    && chars[getYPosition()][getXPosition() + 1] != EXIT_CHARACTER) {
//                list.add(Direction.RIGHT);
//            } else if (GameMaster.getInstance().getPlayer().getXPosition() < this.xPosition
//                    && chars[getYPosition()][getXPosition() - 1] != WALL_CHARACTER
//                    && chars[getYPosition()][getXPosition() - 1] != EXIT_CHARACTER) {
//                list.add(Direction.LEFT);
//            }
//            if (GameMaster.getInstance().getPlayer().getYPosition() > this.yPosition
//                    && chars[getYPosition() + 1][getXPosition()] != WALL_CHARACTER
//                    && chars[getYPosition() + 1][getXPosition()] != EXIT_CHARACTER) {
//                list.add(Direction.DOWN);
//            } else if (GameMaster.getInstance().getPlayer().getYPosition() < this.yPosition
//                    && chars[getYPosition() - 1][getXPosition()] != WALL_CHARACTER
//                    && chars[getYPosition() - 1][getXPosition()] != EXIT_CHARACTER) {
//                list.add(Direction.UP);
//            }
            Direction[] d = new Direction[2];
            int pY = GameMaster.getInstance().getPlayer().yPosition;
            int pX = GameMaster.getInstance().getPlayer().xPosition;
            d[0] = pY > getYPosition() ? Direction.DOWN : pY == getYPosition() ? null : Direction.UP;
            d[1] = pX > getXPosition() ? Direction.RIGHT : pX == getXPosition() ? null : Direction.LEFT;
            System.out.println(choseDir(d[0], d[1]));
            try{
                super.move(choseDir(d[0], d[1]), 1);
            }catch (Exception ignored){}
            if (collision(GameMaster.getInstance().getPlayer())) {
                GameMaster.getInstance().setGameOver(true);
            }
        });
        timer.start();
    }
    private Boolean isAllowedSurface(int x, int y) {
        char ch = GameMaster.getInstance().getMap().getMap()[y][x];
        return ch != Configuration.WALL_CHARACTER && ch != EXIT_CHARACTER;
    }
    private Boolean isAllowedSurface(Direction d) {
        char ch = 0;
        try {
            switch (d){
                case UP -> ch = GameMaster.getInstance().getMap().getMap()[yPosition-1][xPosition];
                case DOWN -> ch = GameMaster.getInstance().getMap().getMap()[yPosition+1][xPosition];
                case LEFT -> ch = GameMaster.getInstance().getMap().getMap()[yPosition][xPosition-1];
                default -> ch = GameMaster.getInstance().getMap().getMap()[yPosition][xPosition+1];
            }
        }catch (Exception e){
            timer.stop();
        }
        return ch != Configuration.WALL_CHARACTER && ch != EXIT_CHARACTER;
    }
    Direction choseDir(Direction d1, Direction d2){
        if(d1 == null){
            if(isAllowedSurface(d2)){
                return d2;
            }else {
                return dir(d2);
            }
        }else{
            if(isAllowedSurface(d1)){
                return d1;
            }else{
                if(d2 != null && isAllowedSurface(d2)){
                    return d2;
                }
                return dir(d1);
            }
        }

    }
    Direction dir(Direction d){
        if(isAllowedSurface(neighbors(d)[0])){
            return neighbors(d)[0];
        } else if (isAllowedSurface(neighbors(d)[1])) {
            return neighbors(d)[1];
        }else {
            return further(d);
        }
    }
    Direction[] neighbors(Direction d){
        switch (d){
            case UP:
            case DOWN:
                return new Direction[]{ Direction.LEFT, Direction.RIGHT };
            case LEFT:
            default:
                return new Direction[]{ Direction.UP, Direction.DOWN };
        }
    }
    Direction further(Direction d){
        switch (d){
            case UP: return Direction.DOWN;
            case DOWN: return Direction.UP;
            case LEFT: return Direction.RIGHT;
            default: return Direction.LEFT;
        }
    }
}
