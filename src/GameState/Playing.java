package GameState;

import Entities.EnemyManger;
import Entities.Player;
import Levels.Level;
import Levels.LevelManger;
import Main.Game;
import UI.GameOverOverLay;
import UI.LevelCompletedOverLay;
import UI.PauseOverlay;
import objects.ObjectManger.*;
import objects.ObjectManger;
import util.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import static util.Constants.ui.Enviroment.*;
public class Playing extends State implements StateMethods {
    private Player player;
    private LevelManger levelManager;
    private EnemyManger enemyManager;
    private ObjectManger objectManager;
    private PauseOverlay pauseOverlay;
    private GameOverOverLay gameOverOverLay;
    private LevelCompletedOverLay levelCompletedOverLay;
    private boolean paused = false;
    private boolean playerDying;

    private int xLvlOffset;
    private int leftBorder = (int) (0.2 * Game.GAME_WIDTH);
    private int rightBorder = (int) (0.8 * Game.GAME_WIDTH);
    //private int lvlTilesWide = LoadSave.GetLevelData()[0].length;
    //private int maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
    private int maxLvlOffsetX;

    private BufferedImage backgroundImg, bigCloud, smallCloud;
    private int[] smallCloudsPos;
    private Random rnd = new Random();
    private boolean gameOver = false;
    private boolean lvlCompleted = false;
    private int score;
    //FOR HIGHSCORE

    //FOR THE SCORE


    public Playing(Game game) {
        super(game);
        initClasses();

        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG);
        bigCloud = LoadSave.GetSpriteAtlas(LoadSave.BIG_CLOUDS);
        smallCloud = LoadSave.GetSpriteAtlas(LoadSave.SMALL_CLOUDS);
        smallCloudsPos = new int[8];
        for (int i = 0; i < smallCloudsPos.length; i++)
            smallCloudsPos[i] = (int) (90 * Game.SCALE) + rnd.nextInt((int) (100 * Game.SCALE));

        calcLvlOffset();
        loadStartLevel();
        score = 2000;
    }



    public void loadNextLevel() {
        resetAll();
        levelManager.loadNextLevel();
        player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());

    }

    private void loadStartLevel() {
        enemyManager.loadEnemies(levelManager.getCurrentLevel());
        objectManager.loadObjects(levelManager.getCurrentLevel());

    }

    private void calcLvlOffset() {
        maxLvlOffsetX = levelManager.getCurrentLevel().getLvlOffset();
    }

    private void initClasses() {
        levelManager = new LevelManger(game);
        enemyManager = new EnemyManger(this);
        objectManager = new ObjectManger(this);


        player = new Player(200, 200, (int) (78 * Game.SCALE), (int) (60 * Game.SCALE), this);
        player.loadLvlData(levelManager.getCurrentLevel().getLvlData());
        player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());

        pauseOverlay = new PauseOverlay(this);
        gameOverOverLay = new GameOverOverLay(this);
        levelCompletedOverLay = new LevelCompletedOverLay(this);
    }

    @Override
    public void update() {
        if (paused) {
            pauseOverlay.update();
        } else if (lvlCompleted) {
            levelCompletedOverLay.update();
            player.setRemainingLives(3);
        } else if (gameOver) {
            gameOverOverLay.update();
        } else if (playerDying) {
            player.update();
        } else {
            levelManager.update();
            objectManager.update();
            player.update();
            enemyManager.update(levelManager.getCurrentLevel().getLvlData(), player);
            checkCloseToBorder();
        }
    }


    private void checkCloseToBorder() {
        int playerX = (int) player.getHitbox().x;
        int diff = playerX - xLvlOffset;

        if (diff > rightBorder)
            xLvlOffset += diff - rightBorder;
        else if (diff < leftBorder)
            xLvlOffset += diff - leftBorder;

        if (xLvlOffset > maxLvlOffsetX)
            xLvlOffset = maxLvlOffsetX;
        else if (xLvlOffset < 0)
            xLvlOffset = 0;
    }


    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);

        drawClouds(g);

        levelManager.draw(g, xLvlOffset);
        player.render(g, xLvlOffset);
        enemyManager.draw(g, xLvlOffset);
        objectManager.draw(g,xLvlOffset);

        if (paused) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
            pauseOverlay.draw(g);
        } else if (gameOver)
            gameOverOverLay.draw(g);
        else if (lvlCompleted) {
            drawScore(g);
            levelCompletedOverLay.draw(g);
        }


    }

    private void drawScore(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Score: " + (score), 1420, 60);
    }
    private void drawClouds(Graphics g) {
        for (int i = 0; i < 3; i++)
            g.drawImage(bigCloud, i * BIG_CLOUD_WIDTH - (int) (xLvlOffset * 0.3), (int) (204 * Game.SCALE), BIG_CLOUD_WIDTH, BIG_CLOUD_HEIGHT, null);

        for (int i = 0; i < smallCloudsPos.length; i++)
            g.drawImage(smallCloud, SMALL_CLOUD_WIDTH * 4 * i - (int) (xLvlOffset * 0.7), smallCloudsPos[i], SMALL_CLOUD_WIDTH, SMALL_CLOUD_HEIGHT, null);
    }

    public void resetAll() {
        if (gameOver){
            score = score - 200;
        }
        gameOver = false;
        paused = false;
        lvlCompleted = false;
        playerDying = false;
        player.resetAll();
        enemyManager.resetAllEnemies();
        objectManager.resetAllObjects();
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void checkObjectHit(Rectangle2D.Float attackBox) {
        objectManager.checkObjectHit(attackBox);
        //score += 200;
    }
    public void checkPotionTouched(Rectangle2D.Float hitbox) {
        objectManager.checkObjectTouched(hitbox);
    }
    public void checkSpikesTouched(Player p){
        objectManager.checkSpikesTouched(p);
    }
    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        enemyManager.checkEnemyHit(attackBox);
    }



    @Override
    public void mouseClicked(MouseEvent e) {
        if (!gameOver)
            if (e.getButton() == MouseEvent.BUTTON1)
                player.setAttacking(true);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (gameOver)
            gameOverOverLay.keyPressed(e);
        else
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    player.setLeft(true);
                    break;
                case KeyEvent.VK_D:
                    player.setRight(true);
                    break;
                case KeyEvent.VK_SPACE:
                    player.setJump(true);
                    break;
                case KeyEvent.VK_ESCAPE:
                    paused = !paused;
                    break;
            }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (!gameOver)
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    player.setLeft(false);
                    break;
                case KeyEvent.VK_D:
                    player.setRight(false);
                    break;
                case KeyEvent.VK_SPACE:
                    player.setJump(false);
                    break;
            }

    }

    public void mouseDragged(MouseEvent e) {
        if (!gameOver)
            if (paused)
                pauseOverlay.mouseDragged(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!gameOver) {
            if (paused)
                pauseOverlay.mousePressed(e);
            else if (lvlCompleted)
                levelCompletedOverLay.mousePressed(e);
        } else
            gameOverOverLay.mousePressed(e);

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!gameOver) {
            if (paused)
                pauseOverlay.mouseReleased(e);
            else if (lvlCompleted)
                levelCompletedOverLay.mouseReleased(e);
        } else
            gameOverOverLay.mouseReleased(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (!gameOver) {
            if (paused)
                pauseOverlay.mouseMoved(e);
            else if (lvlCompleted)
                levelCompletedOverLay.mouseMoved(e);
        } else
            gameOverOverLay.mouseMoved(e);
    }

    public void setLevelCompleted(boolean levelCompleted) {
        this.lvlCompleted = levelCompleted;
    }

    public void setMaxLvlOffset(int lvlOffset) {
        this.maxLvlOffsetX = lvlOffset;
    }

    public void unpauseGame() {
        paused = false;
    }

    public void windowFocusLost() {
        player.resetDirBooleans();
    }

    public Player getPlayer() {
        return player;
    }

    public EnemyManger getEnemyManager() {
        return enemyManager;
    }

    public LevelManger getLevelManager() {
        return levelManager;
    }

    public void setPlayerDying(boolean playerDying) {
        this.playerDying = playerDying;

    }
    public void setScore(int score){
        this.score = score;
    }
    public ObjectManger getObjectManager() {
        return objectManager;
    }

}


