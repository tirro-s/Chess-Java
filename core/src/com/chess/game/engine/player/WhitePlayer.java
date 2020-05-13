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

public class WhitePlayer extends Player {
    public WhitePlayer(final Board board, final Collection<Move> whiteStandardLegalMove, final Collection<Move> blackStandardLegalMoves) {
        super(board, whiteStandardLegalMove, blackStandardLegalMoves);

    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getWhitePieces();
    }

    @Override
    public Alliance getAlliance() {
        return Alliance.WHITE;
    }

    @Override
    public Player getOpponent() {
        return this.board.blackPlayer();
    }

    @Override
    protected Collection<Move> calculateKingCastle(final Collection<Move> playerLegals, final Collection<Move> opponentLegals) {

        final List<Move> kingCastle = new ArrayList<>();
        if(this.playerKing.isFirstMove() && !isInCheck()) {
            if (!this.board.getTile(61).isTileOccupied() && !this.board.getTile(62).isTileOccupied()){
                final Tile rookTile = this.board.getTile(63);
                if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
                    if (Player.calculateAttacksOnTile(61, opponentLegals).isEmpty() &&
                            Player.calculateAttacksOnTile(62, opponentLegals).isEmpty() && rookTile.getPiece().getPieceType().isRook()) {
                        kingCastle.add(new KingSideCastleMove(this.board, this.playerKing, 62,
                                (Rook)rookTile.getPiece(), rookTile.getTileCoordinate(), 61));
                    }
                }
            }
            if (!this.board.getTile(59).isTileOccupied() && !this.board.getTile(58).isTileOccupied() && !this.board.getTile(57).isTileOccupied()){
                final Tile rookTile = this.board.getTile(56);
                if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
                    kingCastle.add(new QueenSideCastleMove(this.board, this.playerKing, 58,
                            (Rook)rookTile.getPiece(), rookTile.getTileCoordinate(), 59));
                }
            }
        }
        return ImmutableList.copyOf(kingCastle);
    }
}
