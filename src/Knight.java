import java.util.Arrays;

public class Knight extends Piece{
    private final static int[][] knightDirections = new int[][] {{2, 1}, {2, -1}, {-2, 1}, {-2, -1}, {1, 2}, {1, -2}, {-1, 2}, {-1, -2}};
    public Knight(char colour, int[] position, Board theBoard, int counter) {
        super(colour, position, theBoard, counter);
    }
    public void getMoves(){
        for (int[] direction:knightDirections){
            int x = position[0] + direction[0];
            int y = position[1] + direction[1];
            if (0<=x && x<=7 && 0<=y && y<=7){
                String square = board[x][y];
                if (square.equals(String.valueOf(enemyColour(colour)) + 'l')){
                    ((King) this.enemyPieces[15]).checks[((King) this.enemyPieces[15]).numberOfChecks] = new int[][] {getPosition()};
                    ((King) this.enemyPieces[15]).numberOfChecks += 1;
                }
                else if (square.isEmpty() || board[x][y].charAt(0) == enemyColour(colour)){
                    moves.add(new int[] {x, y});
                }
                else{
                    for (Piece i:myPieces){
                        if (i != null && Arrays.equals(i.getPosition(), new int[]{x, y})){
                            ((King) i.enemyPieces[15]).protectedSquares.add(new int[] {x, y});
                        }
                    }
                }
            }
        }
    }

    public char getPiece() {
        return 'k';
    }
}
