package inputs;

import Entities.Player;
import GameState.GameState;
import Main.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import static util.Constants.Directions;
import static util.Constants.Directions.*;

public class KeyBoardInputs implements KeyListener {

    private GamePanel gamePanel;

    public KeyBoardInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (GameState.state) {
            case Menu:
                gamePanel.getGame().getMenu().keyReleased(e);
                break;
            case Playing:
                gamePanel.getGame().getPlaying().keyReleased(e);
                break;

            default:
                break;

        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (GameState.state) {
            case Menu:
                gamePanel.getGame().getMenu().keyPressed(e);
                break;
            case Playing:
                gamePanel.getGame().getPlaying().keyPressed(e);
                break;
            case Options:
                gamePanel.getGame().getGameOptions().keyPressed(e);
                break;
            default:
                break;
        }
    }
}