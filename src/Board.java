import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Board extends JPanel{
    ImageIcon whitePawn = new ImageIcon("C:\\Users\\44749\\IdeaProjects\\Chess\\Assets\\WHITEPAWN.png");
    ImageIcon whiteBISHOP = new ImageIcon("C:\\Users\\44749\\IdeaProjects\\Chess\\Assets\\WHITEBISHOP.png");
    ImageIcon whiteKnight = new ImageIcon("C:\\Users\\44749\\IdeaProjects\\Chess\\Assets\\WHITEKNIGHT.png");
    ImageIcon whiteRook = new ImageIcon("C:\\Users\\44749\\IdeaProjects\\Chess\\Assets\\WHITEROOK.png");
    ImageIcon whiteQueen = new ImageIcon("C:\\Users\\44749\\IdeaProjects\\Chess\\Assets\\WHITEQUEEN.png");
    ImageIcon whiteKing = new ImageIcon("C:\\Users\\44749\\IdeaProjects\\Chess\\Assets\\WHITEKING.png");
    ImageIcon blackPawn = new ImageIcon("C:\\Users\\44749\\IdeaProjects\\Chess\\Assets\\BLACKPAWN.png");
    ImageIcon blackBishop= new ImageIcon("C:\\Users\\44749\\IdeaProjects\\Chess\\Assets\\BLACKBISHOP.png");
    ImageIcon blackKnight = new ImageIcon("C:\\Users\\44749\\IdeaProjects\\Chess\\Assets\\BLACKKNIGHT.png");
    ImageIcon blackRook = new ImageIcon("C:\\Users\\44749\\IdeaProjects\\Chess\\Assets\\BLACKROOK.png");
    ImageIcon blackQueen = new ImageIcon("C:\\Users\\44749\\IdeaProjects\\Chess\\Assets\\BLACKQUEEN.png");
    ImageIcon blackKing = new ImageIcon("C:\\Users\\44749\\IdeaProjects\\Chess\\Assets\\BLACKKING.png");
    ImageIcon geryCircle = new ImageIcon("C:\\Users\\44749\\IdeaProjects\\Chess\\Assets\\GREYCIRCLE.png");

    ImageIcon geryRing= new ImageIcon("C:\\Users\\44749\\IdeaProjects\\Chess\\Assets\\greyRing.png");
    String [][] board;
    int[][] show;
    int[] paintPromotion;
    String endGame;
    Piece[] pieces = new Piece[32];
    public void paint(Graphics g){
        this.setBackground(Color.DARK_GRAY);
        super.paint(g);
        g.setColor(new Color(130, 70, 30));
        for (int i =0; i<8; i=i+2){
            for (int j=1; j<9; j = j+2){
                g.fillRect(j*100, i*100, 100, 100);
                g.fillRect(i*100, j*100, 100, 100);}}
        if (show !=null){
            for (int[] point:show){
                if (board[point[0]][point[1]].equals("")) {
                    g.drawImage(geryCircle.getImage(), point[0] * 100+34, point[1] * 100+34, null);
                }
                else{
                    g.drawImage(geryRing.getImage(), point[0] * 100, point[1] * 100, null);
                }
            }
        }
        for (int i=0; i<board.length; i++){
            for (int j=0; j<board[0].length; j++){
                if (!board[i][j].equals("")){
                    switch (board[i][j]) {
                        case "wp" -> g.drawImage(whitePawn.getImage(),i*100, j*100, null);
                        case "wb" -> g.drawImage(whiteBISHOP.getImage(), i*100, j*100, null);
                        case "wk" -> g.drawImage(whiteKnight.getImage(), i*100, j*100, null);
                        case "wr" -> g.drawImage(whiteRook.getImage(), i*100, j*100, null);
                        case "wq" -> g.drawImage(whiteQueen.getImage(), i*100, j*100, null);
                        case "wl" -> g.drawImage(whiteKing.getImage(), i*100, j*100, null);
                        case "bp" -> g.drawImage(blackPawn.getImage(), i*100, j*100, null);
                        case "bb" -> g.drawImage(blackBishop.getImage(), i*100, j*100, null);
                        case "bk" -> g.drawImage(blackKnight.getImage(), i*100, j*100, null);
                        case "br" -> g.drawImage(blackRook.getImage(), i*100, j*100, null);
                        case "bq" -> g.drawImage(blackQueen.getImage(), i*100, j*100, null);
                        case "bl" -> g.drawImage(blackKing.getImage(), i*100, j*100, null);
                    }
                }
            }
        }
        if (paintPromotion != null){
            if (paintPromotion[1] == 7){
                g.drawImage(blackKnight.getImage(), paintPromotion[0]*100,paintPromotion[1]*100, 50, 50, null);
                g.drawImage(blackBishop.getImage(), paintPromotion[0]*100+50,paintPromotion[1]*100,50, 50, null);
                g.drawImage(blackRook.getImage(), paintPromotion[0]*100,paintPromotion[1]*100+50,50, 50, null);
                g.drawImage(blackQueen.getImage(), paintPromotion[0]*100+50,paintPromotion[1]*100+50,50, 50, null);
            }
            else{g.drawImage(whiteKnight.getImage(), paintPromotion[0]*100,paintPromotion[1]*100,50, 50, null);
                g.drawImage(whiteBISHOP.getImage(), paintPromotion[0]*100+50,paintPromotion[1]*100,50, 50, null);
                g.drawImage(whiteRook.getImage(), paintPromotion[0]*100,paintPromotion[1]*100+50,50, 50, null);
                g.drawImage(whiteQueen.getImage(), paintPromotion[0]*100+50,paintPromotion[1]*100+50,50, 50, null);}
        }
        else if (endGame != null){
            g.setFont(new Font("Comiscans", Font.PLAIN, 55));
            g.setColor(Color.BLACK);
            g.drawString(endGame, 30, 300);
        }
    }
    public void allMoves(Piece[] pieces) {
        for (Piece piece:pieces){
            if (piece == null){continue;}
            piece.moves.clear();
            if (piece.getClass() == King.class){
                ((King) piece).protectedSquares.clear();
                ((King) piece).numberOfChecks = 0;
                ((King) piece).checks = new int[2][7][2];
                ((King) piece).checkDirs = new int[2][2];
                ((King) piece).kingLine.clear();
            }
            else {
                piece.protectorLine.clear();
                if (piece.getClass() == Pawn.class){
                    ((Pawn) piece).attacked = new int[2][2];
                }
            }
        }
        List<int[]> a = new ArrayList<int[]>();
        King[] kings = new King[] {(King) pieces[15], (King) pieces[31]};
        for (King king:kings){
            for (int[] direction:King.directions) {
                int x = king.getPosition()[0] + direction[0];
                int y = king.getPosition()[1] + direction[1];
                if (0<= x&& x<=7 && 0<=y && y<=7){
                    if (Objects.equals(board[x][y], "")){
                        king.moves.add(new int[] {x, y});
                        List<int[]> line = new ArrayList<int[]>();
                        line.add(new int[] {x, y});
                        int[] dummy = new int[] {x + direction[0], y + direction[1]};
                        while (-1 < dummy[0] && dummy[0] <8 && -1 < dummy[1] && dummy[1] <8){
                            if ((board[dummy[0]][dummy[1]].equals(""))){
                                line.add(dummy.clone());
                                dummy[0] += direction[0];
                                dummy[1] +=  direction[1];
                            }
                            else if (board[dummy[0]][dummy[1]].charAt(0) == king.colour){
                                line.add(dummy);
                                line.add(direction);
                                king.kingLine.add(line.toArray(new int[line.size()][2]));
                                break;
                            }
                            else{
                                break;
                            }
                        }
                    }
                    else if (board[x][y].charAt(0) != king.colour){//enemy pieces
                        king.moves.add(new int[] {x, y});
                    }
                    else{//own pieces
                        if (-1 < x + direction[0] && -1 < y + direction[1] && 8 > x + direction[0] && 8 > y + direction[1]) {
                            king.kingLine.add(new int[][] {{x,y}, direction});
                            a.add(new int[] {x,y});
                        }
                    }
                }
            }
        }
        for (int[] move:kings[0].moves){
            for (int[] mov:kings[1].moves){
                if (Arrays.equals(mov, move)) {
                    a.add(move);
                    break;
                }
            }
        }
        for (King king:kings){
            for (int[] un:a){
                king.moves.removeIf(move -> Arrays.equals(move, un));
            }
        }
        for (Piece piece:pieces){
            if (piece !=null && piece.getClass() != King.class){
                piece.getMoves();
            }
        }
        pieces[15].getMoves();
        pieces[31].getMoves();
        for (Piece piece:pieces){
            if (piece != null && piece.getClass() != King.class) {
                if (piece.protectorLine.size() != 0) {
                    if (piece.getClass() == Knight.class) {
                        piece.moves.clear();
                    } else {
                        List<int[]> rightMoves = new ArrayList<int[]>();
                        for (int[] check : piece.protectorLine) {
                            for (int[] move : piece.moves) {
                                if (Arrays.equals(move, check)) {
                                    rightMoves.add(Arrays.stream(move).toArray().clone());
                                    break;
                                }
                            }
                        }
                        piece.moves.clear();
                        piece.moves.addAll(rightMoves);
                    }
                }
                else if (((King) piece.myPieces[15]).numberOfChecks == 2) {
                    piece.moves.clear();
                }
                else if (((King) piece.myPieces[15]).numberOfChecks == 1) {
                    List<int[]> rightMoves = new ArrayList<int[]>();
                    for (int[] check : ((King) piece.myPieces[15]).checks[0]) {
                        for (int[] move : piece.moves) {
                            if (Arrays.equals(move, check)) {
                                rightMoves.add(Arrays.stream(move).toArray().clone());
                                break;
                            }
                        }
                    }
                    piece.moves.clear();
                    piece.moves.addAll(rightMoves);
                }
                if (piece.getClass() == Pawn.class) {
                    ((Pawn) piece).enPassant = false;
                }
            }
        }
    }
    public void killPiece(int ind){
        pieces[ind] = null;
    }
    public Board(){
        this.setPreferredSize(new Dimension(800, 800));
        board = new String [][] {{"", "", "", "", "", "", "", ""}, {"", "", "", "", "", "", "", ""},
                {"", "", "", "", "", "", "", ""}, {"", "", "", "", "", "", "", ""},
                {"", "", "", "", "", "", "", ""}, {"", "", "", "", "", "", "", ""},
                {"", "", "", "", "", "", "", ""}, {"", "", "", "", "", "", "", ""}};
        createPieces();
        allMoves(pieces);
    }
    public void createPieces(){
        for (int i = 0;i<8;i++){
            this.pieces[i] = new Pawn('w', new int[] {i, 6}, this, -1);
            int j = i+16;
            this.pieces[j] = new Pawn('b', new int[] {i, 1}, this, -1);
        }
        this.pieces[8] = new Rook('w', new int[] {0, 7},   this, -1);
        this.pieces[9] = new Rook('w', new int[] {7, 7},  this, -1);
        this.pieces[10] = new Knight('w', new int[] {1, 7},  this, -1);
        this.pieces[11] = new Knight('w', new int[] {6, 7},  this, -1);
        this.pieces[12] = new Bishop('w', new int[] {2, 7},  this, -1);
        this.pieces[13] = new Bishop('w', new int[] {5, 7},  this, -1);
        this.pieces[14] = new Queen('w', new int[] {3, 7},  this, -1);
        this.pieces[15] = new King('w', new int[] {4, 7},  this, -1);

        this.pieces[24] = new Rook('b', new int[] {0, 0},   this, -1);
        this.pieces[25] = new Rook('b', new int[] {7, 0},   this, -1);
        this.pieces[26] = new Knight('b', new int[] {1, 0},  this, -1);
        this.pieces[27] = new Knight('b', new int[] {6, 0},  this, -1);
        this.pieces[28] = new Bishop('b', new int[] {2, 0},  this, -1);
        this.pieces[29] = new Bishop('b', new int[] {5, 0},  this, -1);
        this.pieces[30] = new Queen('b', new int[] {3, 0},  this, -1);
        this.pieces[31] = new King('b', new int[] {4, 0},  this, -1);
    }
}
