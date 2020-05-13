package com.chess.game.engine.pieces;

import com.chess.game.engine.Alliance;
import com.chess.game.engine.board.Board;
import com.chess.game.engine.board.BoardUtils;
import com.chess.game.engine.board.Move;
import com.chess.game.engine.board.Move.MajorMove;
import com.chess.game.engine.board.Move.AttackMove;
import com.chess.game.engine.board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Knight extends Piece{

    private final static int[] CANDIDATE_MOVE_CORDINATES = {-17, -15, -10, -6, 6, 10, 15, 17};


    public Knight(final Alliance pieceAlliance, final int piecePosition) {
        super(PieceType.KNIGHT, piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for (final int currentCandidateOffset : CANDIDATE_MOVE_CORDINATES) {
            final int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;
            if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {

                if (isFirstCollumnExclution(this.piecePosition, currentCandidateOffset) || isSecondCollumnExclution(this.piecePosition, currentCandidateOffset) ||
                    isSeventhCollumnExclution(this.piecePosition, currentCandidateOffset) || isEightCollumnExclution(this.piecePosition, currentCandidateOffset)){
                    continue;
                }

                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                if (!candidateDestinationTile.isTileOccupied()) {
                    legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                } else {
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
                    if (this.pieceAlliance != pieceAlliance) {
                        legalMoves.add(new AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public Knight movePiece(final Move move) {
        return new Knight(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
    }

    @Override
    public String toString(){
        return PieceType.KNIGHT.toString();
    }

    private static boolean isFirstCollumnExclution(final int currentPosition, final int candidateOffset) {
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -17 || candidateOffset == -10 || candidateOffset == 6 || candidateOffset == 15);
    }

    private static boolean isSecondCollumnExclution(final int currentPosition, final int candidateOffset) {
        return BoardUtils.SECOND_COLUMN[currentPosition] && (candidateOffset == -10 || candidateOffset == 6);
    }

    private static boolean isSeventhCollumnExclution(final int currentPosition, final int candidateOffset) {
        return BoardUtils.SEVENTH_COLUMN[currentPosition] && (candidateOffset == -6 || candidateOffset == 10);
    }

    private static boolean isEightCollumnExclution(final int currentPosition, final int candidateOffset) {
        return BoardUtils.EIGHT_COLUMN[currentPosition] && (candidateOffset == 17 || candidateOffset == 10 || candidateOffset == -6 || candidateOffset == -15);
    }

}
