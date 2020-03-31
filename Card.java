public class Card {
    Rank rank;
    Suit suit;
    int value;
    public Card(Rank rank, Suit suit, int value) {
        /* *** TO BE IMPLEMENTED IN ACTIVITY 1 *** */
        // Done
        this.rank = rank;
        this.suit = suit;
        this.value = value;
    }
    
    public Suit getSuit() {
        /* *** TO BE IMPLEMENTED IN ACTIVITY 1 *** */
        // Done
        return suit;
    }
    
    public Rank getRank() {
        /* *** TO BE IMPLEMENTED IN ACTIVITY 1 *** */
        // Done
        return rank;
    }
    
    public int getValue() {
        /* *** TO BE IMPLEMENTED IN ACTIVITY 1 *** */
        // Done
        return value;
        
    }
    
    public String toString() {
        // Done
        String s = "";
        s += rank.toString();
        s += " ";
        s+= suit.toString();
        return s;
    }
}
