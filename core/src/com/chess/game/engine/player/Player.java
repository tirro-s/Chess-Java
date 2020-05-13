package com.chess.game.engine.player;

import com.chess.game.engine.Alliance;
import com.chess.game.engine.board.Board;
import com.chess.game.engine.board.Move;
import com.chess.game.engine.pieces.King;
import com.chess.game.engine.pieces.Piece;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class Player {
    protected final Board board;
    protected final King playerKing;
    protected final Collection<Move> legalMoves;
    private final boolean isInChack;

    Player(Board board, Collection<Move> legalMoves, Collection<Move> opponentMoves) {
        this.board = board;
        this.playerKing = establishKing();
        this.legalMoves = ImmutableList.copyOf(Iterables.concat(legalMoves, calculateKingCastle(legalMoves, opponentMoves)));
        this.isInChack = !Player.calculateAttacksOnTile(this.playerKing.getPiecePosition(), opponentMoves).isEmpty();
    }

    public King getPlayerKing() {
        return this.playerKing;
    }

    public Collection<Move> getLegalMoves() {
        return this.legalMoves;
    }

    protected static Collection<Move> calculateAttacksOnTile(int piecePosition, Collection<Move> moves) {
        final List<Move> attackMoves = new ArrayList<>();
        for(final Move move : moves) {
            if(piecePosition == move.getDestinationCoordinate()) {
                attackMoves.add(move);
            }
        }
        return ImmutableList.copyOf(attackMoves);
    }

    private King establishKing() {
        for(final Piece piece : getActivePieces()) {
            if (piece.getPieceType().isKing()) {
                return (King) piece;
            }
        }
        throw new RuntimeException("Не валидная доска");
    }

    public boolean isMoveLegal(final Move move) {
        return this.legalMoves.contains(move);
    }

    public boolean isInCheck(){
        return this.isInChack;
    }

    public boolean isInCheckMate(){ //ПРОВЕРИТЬ И СРАВНИТЬ С ПАТОМ
        return this.isInChack && !hasEscapeMoves();
    }

    public boolean isInStaleMate(){ //ПРОВЕРИТЬ
        return !this.isInChack && !hasEscapeMoves();
    }

    protected boolean hasEscapeMoves() {
        for(final Move move : this.legalMoves) {
            final MoveTransition transition = makeMove(move);
            if (transition.getMoveStatus().isDone()) {
                return true;
            }
        }
        return false;
    }

    public boolean isCastled(){
        return false;
    }

    public MoveTransition makeMove(final Move move){
        if (!isMoveLegal(move)) {
            return new MoveTransition(this.board, move, MoveStatus.ILLIGAL_MOVE);
        }

        final Board transitionBoard = move.execute();

        final Collection<Move> kinhAttacks = Player.calculateAttacksOnTile(transitionBoard.currentPlayer().getOpponent().getPlayerKing().getPiecePosition(),
                transitionBoard.currentPlayer().getLegalMoves());

        if (!kinhAttacks.isEmpty()) {
            return new MoveTransition(this.board, move, MoveStatus.LEAVES_PLAYER_IN_CHECK);
        }
        return new MoveTransition(transitionBoard, move, MoveStatus.DONE);
    }

    public abstract Collection<Piece> getActivePieces();
    public abstract Alliance getAlliance();
    public abstract Player getOpponent();
    protected abstract Collection<Move> calculateKingCastle(Collection<Move> playerLegals, Collection<Move> opponentLegals);

}
