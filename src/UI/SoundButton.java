package UI;

import util.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static util.Constants.ui.PauseButtons.*;

public class SoundButton extends PauseButton {
    private BufferedImage[][] soundImgs;
    private boolean mouseOver, mousePressed;
    private boolean muted;
    private int rowIndex,colIndex;

    public SoundButton(int x, int y,int width, int height){
        super(x,y,height,width);
        loadSoundImgs();
    }

    private void loadSoundImgs() {
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.SOUND_BUTTONS);
        soundImgs = new BufferedImage[2][3];

        for (int j = 0; j < soundImgs.length;j++){
            for(int i = 0; i < soundImgs[j].length;i++){
                soundImgs[j][i] = temp.getSubimage(i*SOUND_SIZE_DEFAULT,j*SOUND_SIZE_DEFAULT,SOUND_SIZE_DEFAULT,SOUND_SIZE_DEFAULT);

            }
        }
    }

    public void resetBools(){
        mouseOver = false;
        mousePressed = false;
    }

    public void Draw(Graphics g){
        g.drawImage(soundImgs[rowIndex][colIndex],x,y,width,height,null);
    }


    public boolean isMouseOver() {
        return mouseOver;
    }

    public boolean isMuted() {
        return muted;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public BufferedImage[][] getSoundImgs() {
        return soundImgs;
    }

    public void setSoundImgs(BufferedImage[][] soundImgs) {
        this.soundImgs = soundImgs;
    }

    public void Update(){
        if(muted)
            rowIndex = 1;
        else
            rowIndex = 0;

        colIndex = 0;
        if (mouseOver){
            colIndex = 1;
        }
        if (mousePressed)
            colIndex =2 ;
    }
}
