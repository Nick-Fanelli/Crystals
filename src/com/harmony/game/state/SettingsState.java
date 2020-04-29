package com.harmony.game.state;

import com.harmony.game.graphics.Display;
import com.harmony.game.graphics.Font;
import com.harmony.game.gui.Button;
import com.harmony.game.utils.Input;
import com.harmony.game.utils.Vector2f;

import java.awt.*;

public class SettingsState extends State {

    public static boolean isFullscreen = false;
    public static boolean isBackgroundMusic = false;

    private static Button backButton = new Button(new Vector2f(10, 10), 40, 40, "X", 32);
    private static Button fullScreenButton = new Button(new Vector2f(Display.width / 2f - 180, Display.height / 2f - 180), 360, 78,
            "Fullscreen: Off", 32);
    private static Button backgroundMusicButton = new Button(new Vector2f(Display.width / 2f - 210, Display.height / 2f - 82),
            420, 78, "Background Music: On", 32);

    @Override
    public void onCreate() {
    }

    @Override
    public void update() {
        if(Input.hoverRectangle(backButton.getRectangle()) || Input.hoverRectangle(fullScreenButton.getRectangle()) ||
        Input.hoverRectangle(backgroundMusicButton.getRectangle()))
            Display.setCursor(Cursor.HAND_CURSOR);
        else Display.setCursor(Cursor.DEFAULT_CURSOR);

        if(backButton.isPressed()) GameStateManager.setCurrentState(GameStateManager.MENU_STATE);

        if(fullScreenButton.isPressed()) {
            isFullscreen = !isFullscreen;
            Display.setFullScreen(isFullscreen);
        }

        if(backgroundMusicButton.isPressed()) isBackgroundMusic = !isBackgroundMusic;

        fullScreenButton.setText(isFullscreen ? "Fullscreen: On" : "Fullscreen Off");
        backgroundMusicButton.setText(isBackgroundMusic ? "Background Music: Off" : "Background Music: On");

        backButton.update();
        fullScreenButton.update();
        backgroundMusicButton.update();
    }

    @Override
    public void draw(Graphics2D g) {
        Font.STANDARD_FONT.centerTextHorizontal(g, "Settings", 100, 64);

        backButton.draw(g);
        fullScreenButton.draw(g);
        backgroundMusicButton.draw(g);
    }

    @Override
    public void onDestroy() {
    }
}
