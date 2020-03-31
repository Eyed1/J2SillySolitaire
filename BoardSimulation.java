public class BoardSimulation {
    public static void main(String[] args) {
        final int GAMES_TO_PLAY = 1000000;
        ElevensBoard board = new ElevensBoard();
        int wins = 0;

        // Use auto-play methods to play the game GAMES_TO_PLAY times...

        double percentWon = (int)(100.0 * wins / GAMES_TO_PLAY);
        System.out.println("Games won:    " + wins);
        System.out.println("Games played: " + GAMES_TO_PLAY);
        System.out.println("Percent won:  " + percentWon + "%");
        
        // Do the same for Tens and Thirteens...
    }    
}
