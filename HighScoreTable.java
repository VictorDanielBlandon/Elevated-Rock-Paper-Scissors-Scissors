import javax.swing.*;
import java.awt.*;

public class HighScoreTable extends JFrame {
    public HighScoreTable(int player1Wins, int player2Wins) {
        setTitle("High Scores");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel(new GridLayout(3, 1));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("Game Statistics", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        JLabel player1Label = new JLabel("Player 1 Wins: " + player1Wins, SwingConstants.CENTER);
        player1Label.setFont(new Font("Arial", Font.PLAIN, 16));
        
        JLabel player2Label = new JLabel("Player 2 Wins: " + player2Wins, SwingConstants.CENTER);
        player2Label.setFont(new Font("Arial", Font.PLAIN, 16));
        
        panel.add(titleLabel);
        panel.add(player1Label);
        panel.add(player2Label);
        
        add(panel);
    }
}