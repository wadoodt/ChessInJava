import java.util.*;

public class Rook extends Piece{
    final static int[][] directions = new int[][] {{0,1}, {0, -1}, {-1, 0}, {1, 0}};
    public Rook(char colour, int[] position, Board theBoard, int counter) {
        super(colour, position, theBoard, counter);
    }
    public void getMoves(){
        for (int i=0; i<4; i++){
            List<int[]> line = new ArrayList<>();
            line.add(this.getPosition());
            int[] dummy = new int[] {position[0] + directions[i][0], position[1] + directions[i][1]};
            while (0<=dummy[0] && dummy[0]<=7 && 0<=dummy[1] && dummy[1]<=7) {
                String square = board[dummy[0]][dummy[1]];
                if (square.isEmpty()){
                    line.add(dummy.clone());
                    moves.add(dummy.clone());
                }
                else if (square.charAt(0) == enemyColour(colour)){
                    moves.add(dummy.clone());
                    if  (square.charAt(1)=='l'){
                        ((King) this.enemyPieces[15]).checks[((King) this.enemyPieces[15]).numberOfChecks] = line.toArray(new int[line.size()][2]);
                        ((King) this.enemyPieces[15]).checkDirs[((King) this.enemyPieces[15]).numberOfChecks] = directions[i].clone();
                        ((King) this.enemyPieces[15]).numberOfChecks += 1;
                    }
                    else {
                        outerScope:
                        for (int[][] n:((King) enemyPieces[15]).kingLine) {
                            if (Arrays.equals(n[n.length-2], dummy) && Arrays.equals(n[n.length-1], new int[]{-directions[i][0], -directions[i][1]})) {
                                for (Piece piece : enemyPieces) {
                                    if (piece != null && Arrays.equals(piece.getPosition(),  dummy)) {
                                        for (int u=0; u<n.length-2; u++){
                                            piece.protectorLine.add(Arrays.stream(n[u]).toArray().clone());
                                        }
                                        piece.protectorLine.addAll(line);
                                        break outerScope;
                                    }
                                }
                            }
                        }
                    }
                    break;
                }
                else{
                    ((King) enemyPieces[15]).protectedSquares.add(dummy.clone());
                    break;
                }
                dummy[0] += directions[i][0];
                dummy[1] += directions[i][1];
            }
        }
    }

    public char getPiece() {
        return 'r';
    }
}

