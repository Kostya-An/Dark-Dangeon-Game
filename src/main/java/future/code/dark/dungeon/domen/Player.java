package future.code.dark.dungeon.domen;


import future.code.dark.dungeon.service.GameMaster;

import java.util.Arrays;

import static future.code.dark.dungeon.config.Configuration.PLAYER_ANIMATION;

public class Player extends DynamicObject {
    private static final int stepSize = 1;
    private int coins = 0;

    public Player(int xPosition, int yPosition) {
        super(xPosition, yPosition, Arrays.stream(PLAYER_ANIMATION).toList());
    }

    public void move(Direction direction) {
        super.move(direction, stepSize);
        if(GameMaster.getInstance().getCoins().stream().anyMatch(this::collision)){
            coins++;
            GameMaster.getInstance().deleteCoin(this.xPosition, this.yPosition);
        }
        if(collision(GameMaster.getInstance().getExit())){
            if(GameMaster.getInstance().getCoins().size() == 0){
                GameMaster.getInstance().setWin(true);
            }
        }
        if(GameMaster.getInstance().getEnemies().stream().anyMatch(this::collision)){
            GameMaster.getInstance().setGameOver(true);
        }
    }

    @Override
    public String toString() {
        return "Player{[" + xPosition + ":" + yPosition + "]}";
    }


    public int getCoins() {
        return coins;
    }
}
