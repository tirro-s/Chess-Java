package com.chess.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.chess.game.engine.board.Board;
import com.chess.game.gui.Table;

public class Chess extends ApplicationAdapter {
	private SpriteBatch batch;
	private Table table;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		table = new Table();

		Gdx.gl.glClearColor(1, 0, 0, 1);

		Board board = Board.createStandardBoard();
		System.out.println(board);
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		table.update(Gdx.graphics.getDeltaTime());
		table.render(batch);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
