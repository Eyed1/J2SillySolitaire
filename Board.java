import java.util.*;

public abstract class Board {
	public Board() {

	}
	public void newGame() {
        
    }
    
    public void resetDeck() {
        
    }

    public int size() {
        return 0;
    }

    public int deckSize() {
        return 0;
    }

    public Card cardAt(int k) {
    	return null;
    }

    @Override
    public String toString() {
        return "";
    }

    public boolean gameIsWon() {
       	return false;
    }

    
    public boolean isLegal(List<Integer> selectedCards) {
        return false;
    }

    public boolean anotherPlayIsPossible() {
        return false;
    }

    public boolean containsPairSum(List<Integer> selectedCards) {
        return false;
    }

    public boolean containsE(List<Integer> selectedCards) {
        return false;
    }

    public List<Integer> findPlayIsPossible() {
        return null;
    }

    public void replaceSelectedCards(List<Integer> selectedCards) {

    }

    
    public boolean playIfPossible() {
    	return false;
    }
}