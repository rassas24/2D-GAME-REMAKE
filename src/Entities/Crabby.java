package Entities;
import Main.Game;

import java.awt.geom.Rectangle2D;

import static util.Constants.Directions.LEFT;
import static util.Constants.Directions.RIGHT;
import static util.Constants.PlayerConstant.IDLE;
import static util.Constants.PlayerConstant.RUNNING;
import static util.Constants.EnemyConstants.*;
import static util.HelpMethods.*;

public class Crabby extends Enemy {
    private Rectangle2D.Float attackBox;
    private int attackBoxOffsetX;

    public Crabby(float x, float y) {
        super(x, y, CRABBY_WIDTH, CRABBY_HEIGHT, CRABBY);
        initHitBox(22, 19);
        initAttackBox();

    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x,y,(int)(82*Game.SCALE),(int)(19*Game.SCALE) );
        attackBoxOffsetX = (int)(Game.SCALE * 30);
    }

    public void update(int[][] lvlData, Player player) {
        updateBehaviour(lvlData, player);
        updateAnimationTick();
        updateAttackBox();
    }

    private void updateAttackBox() {
        attackBox.x = hitbox.x - attackBoxOffsetX;
        attackBox.y = hitbox.y;
    }

    private void updateBehaviour(int[][] lvlData, Player player) {
        if (firstUpdate)
            firstUpdateCheck(lvlData);

        if (inAir)
            updateInAir(lvlData);
        else {
            switch (state) {
                case IDLE:
                    newState(RUNNING);
                    break;
                case RUNNING:
                    if (canSeePlayer(lvlData, player)) {
                        turnTowardsPlayer(player);
                        if (isPlayerCloseForAttack(player))
                            newState(ATTACKING);
                    }
                    move(lvlData);
                    break;
                case ATTACKING:
                    if (aniIndex == 0)
                        attackChecked = false;
                    if (aniIndex == 3 && !attackChecked)
                        checkEnemyHit(attackBox,player);
                    break;
                case HIT:
                    break;
            }
        }

    }
    //lift or right
    public int flipX(){
        if (walkDir == RIGHT)
            return width;
        else
            return 0;
    }

    public int flipW(){
        if (walkDir == RIGHT){
            return -1;
        }
        else
            return 1;
    }

}