package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public class FGame extends Game {
	private static MainGameScreen _mainGameScreen;
	private static MainMenuScreen _mainMenuScreen;

	public Screen getScreen(ScreenType screenType) {
		switch (screenType) {
			case MainGame:
				return _mainGameScreen;
			case MainMenu:
				return _mainMenuScreen;
			default:
				return null;
		}
	}

	@Override
	public void create() {
		_mainGameScreen = new MainGameScreen();
		_mainMenuScreen = new MainMenuScreen();

		setScreen(_mainGameScreen);
	}

	@Override
	public void dispose() {
		_mainGameScreen.dispose();
		_mainMenuScreen.dispose();
	}

	public enum ScreenType {
		MainGame,
		MainMenu
	}
}
