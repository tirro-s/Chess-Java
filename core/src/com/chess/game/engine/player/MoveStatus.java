package com.chess.game.engine.player;

import com.chess.game.engine.board.Move;

enum MoveStatus {
    DONE {
        @Override
        boolean isDone(){
            return true;
        }
    },
    ILLIGAL_MOVE {
        @Override
        boolean isDone() {
            return false;
        }
    },
    LEAVES_PLAYER_IN_CHECK {
        @Override
        boolean isDone() {
            return false;
        }
    };


    abstract boolean isDone();
}
