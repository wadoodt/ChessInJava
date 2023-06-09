import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    Board board;


    Frame(String title){
        super(title);
        this.board = new Board();
        this.setIconImage(board.blackKnight.getImage().getScaledInstance(22, 25, 0));
        setLayout(new BorderLayout());
        setSize(816, 839);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        add(board, BorderLayout.WEST);
        addMouseListener(new MouseInput(board, board.pieces));
    }
}
