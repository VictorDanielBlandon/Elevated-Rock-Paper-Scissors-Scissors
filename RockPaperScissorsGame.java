import java.util.HashMap;
import java.util.Map;

public class RockPaperScissorsGame {
    private String player1Choice = "";
    private String player2Choice = "";
    
    private int player1Lives = 3;
    private int player2Lives = 3;
    private int roundsWonByPlayer1 = 0;
    private int roundsWonByPlayer2 = 0;
    private ItemSystem itemSystem = new ItemSystem();
    
    private static final Map<String, String> keyToChoice = new HashMap<>();
    static {
        keyToChoice.put("A", "rock");
        keyToChoice.put("S", "paper");
        keyToChoice.put("D", "scissors");
        keyToChoice.put("J", "rock");
        keyToChoice.put("K", "paper");
        keyToChoice.put("L", "scissors");
    }
    
    public int getPlayer1Lives() { return player1Lives; }
    public int getPlayer2Lives() { return player2Lives; }
    public int getRoundsWonByPlayer1() { return roundsWonByPlayer1; }
    public int getRoundsWonByPlayer2() { return roundsWonByPlayer2; }
    public String getPlayer1Choice() { return player1Choice; }
    public String getPlayer2Choice() { return player2Choice; }
    public String getPlayerItem(int player) { return itemSystem.getPlayerItem(player); }
    
    public void adjustRoundsWonByPlayer1(int adjustment) {
        roundsWonByPlayer1 = Math.max(0, roundsWonByPlayer1 + adjustment);
    }
    
    public void adjustRoundsWonByPlayer2(int adjustment) {
        roundsWonByPlayer2 = Math.max(0, roundsWonByPlayer2 + adjustment);
    }
    
    public String useItem(int player) {
        return itemSystem.useItem(player);
    }
    
    public void makeChoice(String key) {
        if (keyToChoice.containsKey(key)) {
            if (key.equals("A") || key.equals("S") || key.equals("D")) {
                player1Choice = keyToChoice.get(key);
            } else {
                player2Choice = keyToChoice.get(key);
            }
        }
    }
    
    public boolean bothPlayersMadeChoice() {
        return !player1Choice.isEmpty() && !player2Choice.isEmpty();
    }
    
    public GameResult determineResult() {
        GameResult result = new GameResult();
        
        if (player1Choice.equals(player2Choice)) {
            result.message = "It's a tie!";
        } else if ((player1Choice.equals("rock") && player2Choice.equals("scissors")) ||
                   (player1Choice.equals("paper") && player2Choice.equals("rock")) ||
                   (player1Choice.equals("scissors") && player2Choice.equals("paper"))) {
            result.message = "Player 1 wins this match!";
            player2Lives--;
            result.player1WonMatch = true;
        } else {
            result.message = "Player 2 wins this match!";
            player1Lives--;
            result.player2WonMatch = true;
        }
        
        if (player1Lives <= 0 || player2Lives <= 0) {
            if (player1Lives <= 0) {
                roundsWonByPlayer2++;
                result.message = "PLAYER 2 WINS THE ROUND!";
                result.roundWinner = 2;
            } else {
                roundsWonByPlayer1++;
                result.message = "PLAYER 1 WINS THE ROUND!";
                result.roundWinner = 1;
            }
            itemSystem.awardItems(result.roundWinner);
            resetLives();
        }
        
        result.player1Choice = player1Choice;
        result.player2Choice = player2Choice;
        result.player1Lives = player1Lives;
        result.player2Lives = player2Lives;
        result.roundsWonByPlayer1 = roundsWonByPlayer1;
        result.roundsWonByPlayer2 = roundsWonByPlayer2;
        
        resetChoices();
        return result;
    }
    
    public void resetGame() {
        resetChoices();
        resetLives();
        roundsWonByPlayer1 = 0;
        roundsWonByPlayer2 = 0;
        itemSystem = new ItemSystem();
    }
    
    private void resetChoices() {
        player1Choice = "";
        player2Choice = "";
    }
    
    private void resetLives() {
        player1Lives = 3;
        player2Lives = 3;
    }
    
    public static class GameResult {
        public String message;
        public String player1Choice;
        public String player2Choice;
        public int player1Lives;
        public int player2Lives;
        public int roundsWonByPlayer1;
        public int roundsWonByPlayer2;
        public boolean player1WonMatch = false;
        public boolean player2WonMatch = false;
        public int roundWinner = 0;
    }
}