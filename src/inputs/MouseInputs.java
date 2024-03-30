package inputs;

import GameState.GameState;
import Main.Game;
import Main.GamePanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseInputs implements MouseListener, MouseMotionListener {

    private GamePanel gamePanel;

    public MouseInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        switch (GameState.state) {
            case Playing:
                gamePanel.getGame().getPlaying().mouseDragged(e);
                break;
            case Options:
                gamePanel.getGame().getGameOptions().mouseDragged(e);
                break;
            default:
                break;

        }

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        switch (GameState.state) {
            case Menu:
                gamePanel.getGame().getMenu().mouseMoved(e);
                break;
            case Playing:
                gamePanel.getGame().getPlaying().mouseMoved(e);
                break;
            case Options:
                gamePanel.getGame().getGameOptions().mouseMoved(e);
                break;
            default:
                break;

        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        switch (GameState.state) {
            case Playing:
                gamePanel.getGame().getPlaying().mouseClicked(e);
                break;
            default:
                break;

        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (GameState.state) {
            case Menu:
                gamePanel.getGame().getMenu().mousePressed(e);
                break;
            case Playing:
                gamePanel.getGame().getPlaying().mousePressed(e);
                break;
            case Options:
                gamePanel.getGame().getGameOptions().mousePressed(e);
                break;
            default:
                break;

        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (GameState.state) {
            case Menu:
                gamePanel.getGame().getMenu().mouseReleased(e);
                break;
            case Playing:
                gamePanel.getGame().getPlaying().mouseReleased(e);
                break;
            case Options:
                gamePanel.getGame().getGameOptions().mouseReleased(e);
                break;
            default:
                break;

        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}