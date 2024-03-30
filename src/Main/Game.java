package Main;

import Entities.Player;
import GameState.GameState;
import GameState.Menu;
import GameState.Playing;
import Levels.LevelManger;
import UI.audioOptions;
import GameState.GameOptions;
import inputs.KeyBoardInputs;
import inputs.MouseInputs;
import util.LoadSave;

import java.awt.*;


public class Game implements Runnable {

    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;

    private Playing playing;
    private audioOptions audioOptions;
    private GameOptions gameOptions;
    private Menu menu;

    public final static int TILES_DEFAULT_SIZE = 32;
    public final static float SCALE = 1.9f;
    public final static int TILES_IN_WIDTH = 26;
    public final static int TILES_IN_HEIGHT = 14;
    public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
    public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;

    public Game() {
        initClasses();

        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.setFocusable(true);
        gamePanel.requestFocus();

        startGameLoop();
    }

    private void initClasses() {

        audioOptions = new audioOptions();
        playing = new Playing(this);
        menu = new Menu(this);
        gameOptions = new GameOptions(this);

    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {
        switch (GameState.state) {
            case Menu:
                menu.update();
                getPlaying().setScore(2000);
                break;
            case Playing:
                playing.update();
                break;
            case Options:
                gameOptions.update();
                break;
            case Quit:
            default:
                System.exit(0);
                break;

        }
    }

    public void render(Graphics g) {
        switch (GameState.state) {
            case Menu:
                menu.draw(g);
                break;
            case Playing:
                playing.draw(g);
                break;
            case Options:
                gameOptions.draw(g);
            default:
                break;
        }
    }


    public void run() {

        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;

        long previousTime = System.nanoTime();

        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();

        double deltaU = 0;
        double deltaF = 0;

        while (true) {
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaU >= 1) {
                update();
                updates++;
                deltaU--;
            }

            if (deltaF >= 1) {
                gamePanel.repaint();
                frames++;
                deltaF--;
            }

            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;

            }
        }

    }

    public void windowFocusLost() {
        if (GameState.state == GameState.Playing)
            playing.getPlayer().resetDirBooleans();
    }

    public Menu getMenu() {
        return menu;
    }

    public Playing getPlaying() {
        return playing;
    }
    public audioOptions getAudioOptions() {
        return audioOptions;
    }

    public GameOptions getGameOptions() {
        return gameOptions;
    }
}