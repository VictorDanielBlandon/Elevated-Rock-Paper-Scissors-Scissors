import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements KeyListener {
    private final RockPaperScissorsGame game;
    private JLabel player1Label, player2Label, resultLabel, instructionsLabel, livesLabel;
    private JLabel player1ItemLabel, player2ItemLabel;
    private JButton resetButton, player1UseItemButton, player2UseItemButton;
    
    public GamePanel() {
        game = new RockPaperScissorsGame();
        setLayout(new BorderLayout());
        
        instructionsLabel = new JLabel("<html>Player 1: A=Rock, S=Paper, D=Scissors | Player 2: J=Rock, K=Paper, L=Scissors<br>First to lose 3 lives loses the round!<br>Items: Q (P1) / P (P2) to use</html>");
        instructionsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        player1Label = new JLabel("Player 1: ");
        player1Label.setHorizontalAlignment(SwingConstants.CENTER);
        player1Label.setFont(new Font("Arial", Font.BOLD, 20));
        
        player2Label = new JLabel("Player 2: ");
        player2Label.setHorizontalAlignment(SwingConstants.CENTER);
        player2Label.setFont(new Font("Arial", Font.BOLD, 20));
        
        livesLabel = new JLabel(getLivesText());
        livesLabel.setHorizontalAlignment(SwingConstants.CENTER);
        livesLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        player1ItemLabel = new JLabel("Item: None");
        player1ItemLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        player2ItemLabel = new JLabel("Item: None");
        player2ItemLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        resultLabel = new JLabel("Make your choices!");
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 24));
        
        resetButton = new JButton("Reset Game");
        resetButton.addActionListener(e -> {
            showHighScores();
            game.resetGame();
            refreshUI();
        });
        
        player1UseItemButton = new JButton("Use Item (Q)");
        player1UseItemButton.addActionListener(e -> useItem(1));
        player1UseItemButton.setEnabled(false);
        
        player2UseItemButton = new JButton("Use Item (P)");
        player2UseItemButton.addActionListener(e -> useItem(2));
        player2UseItemButton.setEnabled(false);
        
        JPanel gamePanel = new JPanel(new GridLayout(7, 1));
        gamePanel.add(instructionsLabel);
        gamePanel.add(player1Label);
        gamePanel.add(player1ItemLabel);
        gamePanel.add(player2Label);
        gamePanel.add(player2ItemLabel);
        gamePanel.add(livesLabel);
        gamePanel.add(resultLabel);
        
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
        buttonPanel.add(player1UseItemButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(player2UseItemButton);
        
        add(gamePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        setFocusable(true);
        addKeyListener(this);
    }
    
    private String getLivesText() {
        return String.format("Lives: Player 1 - %d | Player 2 - %d | Rounds: %d - %d", 
                game.getPlayer1Lives(), game.getPlayer2Lives(), 
                game.getRoundsWonByPlayer1(), game.getRoundsWonByPlayer2());
    }
    
    private void refreshUI() {
        livesLabel.setText(getLivesText());
        player1Label.setText("Player 1: ");
        player2Label.setText("Player 2: ");
        player1ItemLabel.setText("Item: None");
        player2ItemLabel.setText("Item: None");
        resultLabel.setText("Make your choices!");
        player1UseItemButton.setEnabled(false);
        player2UseItemButton.setEnabled(false);
    }
    
    private void showHighScores() {
        int player1Wins = game.getRoundsWonByPlayer1();
        int player2Wins = game.getRoundsWonByPlayer2();
        
        SwingUtilities.invokeLater(() -> {
            HighScoreTable highScoreTable = new HighScoreTable(player1Wins, player2Wins);
            highScoreTable.setVisible(true);
        });
    }
    
    private void useItem(int player) {
        String item = game.useItem(player);
        if (item != null) {
            String message = "";
            boolean gameOver = false;
            
            switch (item) {
                case "sword":
                    message = "Player " + player + " used Sword! Opponent loses 1 round win!";
                    if (player == 1) {
                        game.adjustRoundsWonByPlayer2(-1);
                    } else {
                        game.adjustRoundsWonByPlayer1(-1);
                    }
                    break;
                    
                case "shield":
                    message = "Player " + player + " used Shield! Invulnerable this round!";
                    break;
                    
                case "gun":
                    message = "Player " + player + " used GUN! Instant victory!";
                    gameOver = true;
                    break;
            }
            
            resultLabel.setText(message);
            updateItemDisplays();
            
            if (gameOver) {
                resetButton.doClick();
            }
        }
    }
    
    private void updateItemDisplays() {
        String p1Item = game.getPlayerItem(1);
        String p2Item = game.getPlayerItem(2);
        
        player1ItemLabel.setText("Item: " + (p1Item != null ? p1Item : "None"));
        player2ItemLabel.setText("Item: " + (p2Item != null ? p2Item : "None"));
        
        player1UseItemButton.setEnabled(p1Item != null);
        player2UseItemButton.setEnabled(p2Item != null);
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        String key = KeyEvent.getKeyText(e.getKeyCode()).toUpperCase();
        
        if (key.equals("Q")) {
            useItem(1);
            return;
        }
        if (key.equals("P")) {
            useItem(2);
            return;
        }
        
        game.makeChoice(key);
        
        if (game.bothPlayersMadeChoice()) {
            RockPaperScissorsGame.GameResult result = game.determineResult();
            player1Label.setText("Player 1: " + result.player1Choice);
            player2Label.setText("Player 2: " + result.player2Choice);
            livesLabel.setText(getLivesText());
            resultLabel.setText(result.message);
            updateItemDisplays();
        }
        
        requestFocus();
    }
    
    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyReleased(KeyEvent e) {}
}