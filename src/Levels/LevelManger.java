package Levels;

import GameState.GameState;
import Main.Game;
import util.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static Main.Game.TILES_SIZE;


public class LevelManger {

    private Game game;
    private BufferedImage[] levelSprite;
    private ArrayList<Level> levels;
    private int lvlIndex = 0;
    private int selectedLevelIndex = 0;

    public LevelManger(Game game) {
        this.game = game;
        importOutsideSprites();
        levels = new ArrayList<>();
        buildAllLevels();
    }
    public void loadNextLevel() {
        lvlIndex++;
        if (lvlIndex>=levels.size()){
            lvlIndex = 0;
            GameState.state = GameState.Menu;
        }
        Level newLevel  = levels.get(lvlIndex);
        game.getPlaying().getEnemyManager().loadEnemies(newLevel);
        game.getPlaying().getPlayer().loadLvlData(newLevel.getLvlData());
        game.getPlaying().setMaxLvlOffset(newLevel.getLvlOffset());
    }
    private void buildAllLevels() {
        BufferedImage[] allLevels = LoadSave.GetAllLevels();
        for (BufferedImage img: allLevels)
            levels.add(new Level(img));
    }

    private void importOutsideSprites() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
        levelSprite = new BufferedImage[48];
        for (int j = 0; j < 4; j++)
            for (int i = 0; i < 12; i++) {
                int index = j * 12 + i;
                levelSprite[index] = img.getSubimage(i * 32, j * 32, 32, 32);
            }
    }

    public void draw(Graphics g, int lvlOffset) {
        for (int j = 0; j < Game.TILES_IN_HEIGHT; j++)
            for (int i = 0; i < levels.get(lvlIndex).getLvlData()[0].length; i++) {
                int index = levels.get(lvlIndex).getSpriteIndex(i, j);
                g.drawImage(levelSprite[index], Game.TILES_SIZE * i - lvlOffset, Game.TILES_SIZE * j, Game.TILES_SIZE, Game.TILES_SIZE, null);
            }
    }

    public void update() {

    }

    public Level getCurrentLevel() {
        return levels.get(lvlIndex);
    }

    public int getAmountOfLevels(){
        return levels.size();
    }


    public void drawLevelSelectionScreen(Graphics g) {
        // Draw the level selection screen using your level sprites
        for (int i = 0; i < levels.size(); i++) {
            int x = i * Game.GAME_WIDTH / levels.size();
            int y = Game.GAME_HEIGHT / 2;
            g.drawImage(levelSprite[i], x, y, Game.GAME_WIDTH / levels.size(), Game.GAME_HEIGHT / 2, null);
        }

        // Highlight the selected level
        int selectedX = selectedLevelIndex * Game.GAME_WIDTH / levels.size();
        g.setColor(Color.RED);
        g.drawRect(selectedX, Game.GAME_HEIGHT / 2, Game.GAME_WIDTH / levels.size(), Game.GAME_HEIGHT / 2);
    }

    // Add a method to handle level selection
    public void selectLevel(int mouseX, int mouseY) {
        int levelIndex = mouseX / (Game.GAME_WIDTH / levels.size());

        if (mouseY >= Game.GAME_HEIGHT / 2 && mouseY <= Game.GAME_HEIGHT) {
            // Set the selected level index
            selectedLevelIndex = levelIndex;
        }
    }

    // Modify loadNextLevel to load the selected level
    public void loadSelectedLevel() {
        if (selectedLevelIndex >= 0 && selectedLevelIndex < levels.size()) {
            Level selectedLevel = levels.get(selectedLevelIndex);
            // Load the selected level
            game.getPlaying().getEnemyManager().loadEnemies(selectedLevel);
            game.getPlaying().getPlayer().loadLvlData(selectedLevel.getLvlData());
            game.getPlaying().setMaxLvlOffset(selectedLevel.getLvlOffset());
        }
    }


}
