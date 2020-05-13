package com.chess.game.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.chess.game.engine.board.BoardUtils;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

public class Table {

    Texture img;

    public Table() {
        img = new Texture("badlogic.jpg");
    }

    private void HandleInput() {
        if (Gdx.input.justTouched()) {

        }
    }

   public void update(float dt) {
        HandleInput();
    }

    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(img, 0,0);
        sb.end();
    }

    private void dispose() {

    }

    private class BoardPanel {
        final List<TilePanel> boardTiles;

        BoardPanel() {
            this.boardTiles = new ArrayList<>();
            for(int i = 0; i < BoardUtils.NUM_TILES; i++) {
                final TilePanel tilePanel = new TilePanel();
                this.boardTiles.add(tilePanel);
                //add(tilePanel);
            }

        }

    }

    private class TilePanel {

    }

}
