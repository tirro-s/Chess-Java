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

public class Bishop extends Piece {

    private final static int[] CANDIDATE_MOVE_VECTOR_CORDINATES = {-9, -7, -7, -9};

    public Bishop(final Alliance pieceAlliance, final int piecePosition) {
        super(PieceType.BISHOP, piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final int candidateCoordinateOffset : CANDIDATE_MOVE_VECTOR_CORDINATES) {
            int candidateDestinationCordinate = this.piecePosition;

            while (BoardUtils.isValidTileCoordinate(candidateDestinationCordinate)) {

                if (isFirstCollumnExclution(candidateDestinationCordinate, candidateCoordinateOffset) || isEightCollumnExclution(candidateDestinationCordinate, candidateCoordinateOffset)){
                    break;
                }

                candidateDestinationCordinate += candidateCoordinateOffset;

                if (BoardUtils.isValidTileCoordinate(candidateDestinationCordinate)) {
                    final Tile candidateDestinationTile = board.getTile(candidateDestinationCordinate);
                        if (!candidateDestinationTile.isTileOccupied()) {
                        legalMoves.add(new MajorMove(board, this, candidateDestinationCordinate));
                    } else {
                        final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                        final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
                        if (this.pieceAlliance != pieceAlliance) {
                            legalMoves.add(new AttackMove(board, this, candidateDestinationCordinate, pieceAtDestination));
                        }
                        break;
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public Bishop movePiece(final Move move) {
        return new Bishop(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
    }

    @Override
    public String toString(){
        return PieceType.BISHOP.toString();
    }

    private static boolean isFirstCollumnExclution(final int currentPosition, final int candidateOffset) {
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -9 || candidateOffset == 7);
    }

    private static boolean isEightCollumnExclution(final int currentPosition, final int candidateOffset) {
        return BoardUtils.EIGHT_COLUMN[currentPosition] && (candidateOffset == -7 || candidateOffset == 9);
    }
}
