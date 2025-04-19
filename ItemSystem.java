import java.util.Random;

public class ItemSystem {
    private Random random = new Random();
    private String player1Item = null;
    private String player2Item = null;
    
    private final String[] ITEMS = {"sword", "shield", "gun"};
    private final int[] PROBABILITIES = {45, 45, 10};
    
    public String awardRandomItem() {
        int total = 0;
        for (int prob : PROBABILITIES) {
            total += prob;
        }
        
        int randomNum = random.nextInt(total);
        int cumulativeProbability = 0;
        
        for (int i = 0; i < ITEMS.length; i++) {
            cumulativeProbability += PROBABILITIES[i];
            if (randomNum < cumulativeProbability) {
                return ITEMS[i];
            }
        }
        
        return "shield";
    }
    
    public void awardItems(int winner) {
        if (winner == 1) {
            player1Item = awardRandomItem();
        } else if (winner == 2) {
            player2Item = awardRandomItem();
        }
    }
    
    public String useItem(int player) {
        if (player == 1 && player1Item != null) {
            String item = player1Item;
            player1Item = null;
            return item;
        } else if (player == 2 && player2Item != null) {
            String item = player2Item;
            player2Item = null;
            return item;
        }
        return null;
    }
    
    public String getPlayerItem(int player) {
        return player == 1 ? player1Item : player2Item;
    }
}