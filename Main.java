import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Rock Paper Scissors Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 450);
            
            GamePanel gamePanel = new GamePanel();
            frame.add(gamePanel);
            
            frame.setVisible(true);
            gamePanel.requestFocus();
        });
    }
}