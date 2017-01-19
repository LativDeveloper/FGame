package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public class FGame extends Game {
	private static MainGameScreen _mainGameScreen;

	public Screen getScreen(ScreenType screenType) {
		switch (screenType) {
			case MainGame:
				return _mainGameScreen;
			default:
				return null;
		}
	}

	@Override
	public void create() {
		_mainGameScreen = new MainGameScreen();

		setScreen(_mainGameScreen);
	}

	@Override
	public void dispose() {
		_mainGameScreen.dispose();
	}

	public enum ScreenType {
		MainGame
	}
}
