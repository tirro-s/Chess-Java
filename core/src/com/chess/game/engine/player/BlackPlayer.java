package com.chess.game.engine.player;

import com.chess.game.engine.Alliance;
import com.chess.game.engine.board.Board;
import com.chess.game.engine.board.Move;
import com.chess.game.engine.board.Tile;
import com.chess.game.engine.pieces.Piece;
import com.chess.game.engine.pieces.Rook;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.chess.game.engine.board.Move.*;

public class BlackPlayer extends Player {
    public BlackPlayer(final Board board, final Collection<Move> whiteStandardLegalMove, final Collection<Move> blackStandardLegalMoves) {
        super(board, blackStandardLegalMoves, whiteStandardLegalMove);

    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getBlackPieces();
    }

    @Override
    public Alliance getAlliance() {
        return Alliance.BLACK;
    }

    @Override
    public Player getOpponent() {
        return this.board.whitePlayer();
    }

    @Override
    protected Collection<Move> calculateKingCastle(final Collection<Move> playerLegals, final Collection<Move> opponentLegals) {
        final List<Move> kingCastle = new ArrayList<>();
        if(this.playerKing.isFirstMove() && !isInCheck()) {
            if (!this.board.getTile(5).isTileOccupied() && !this.board.getTile(6).isTileOccupied()){
                final Tile rookTile = this.board.getTile(7);
                if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
                    if (Player.calculateAttacksOnTile(5, opponentLegals).isEmpty() &&
                            Player.calculateAttacksOnTile(5, opponentLegals).isEmpty() && rookTile.getPiece().getPieceType().isRook()) {
                        kingCastle.add(new KingSideCastleMove(this.board, this.playerKing, 6,
                                (Rook)rookTile.getPiece(), rookTile.getTileCoordinate(), 5));
                    }
                }
            }
            if (!this.board.getTile(1).isTileOccupied() && !this.board.getTile(2).isTileOccupied() && !this.board.getTile(3).isTileOccupied()){
                final Tile rookTile = this.board.getTile(0);
                if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
                    kingCastle.add(new QueenSideCastleMove(this.board, this.playerKing, 7,
                            (Rook)rookTile.getPiece(), rookTile.getTileCoordinate(), 8));
                }
            }
        }
        return ImmutableList.copyOf(kingCastle);
    }
}
