package Entities;

import Main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class Entity {
    protected float x,y;
    protected int width , height;
    protected Rectangle2D.Float hitbox;
    protected int aniTick, aniIndex;
    protected int state;
    protected float walkSpeed;
    Entity(float x, float y,int width,int height){
        this.x =x;
        this.y =y;
        this.height = height;
        this.width = width;

    }

    protected void drawHitBox(Graphics g, int xLvlOffSet){
        g.setColor(Color.RED);
        g.drawRect((int)hitbox.x - xLvlOffSet, (int)hitbox.y,(int)hitbox.width, (int)hitbox.height);
    }

    protected void initHitBox(int width, int height) {
        hitbox = new Rectangle2D.Float(x,y,(int)(width* Game.SCALE),(int)(height*Game.SCALE));
    }

    protected void updateHitBox(){
        hitbox.x = (int) x;
        hitbox.y = (int) y;
    }

    public Rectangle2D.Float getHitbox(){
        return hitbox;
    }
    public int getState() {
        return state;
    }

}
