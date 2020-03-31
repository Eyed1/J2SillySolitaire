public class StandardDeck extends Deck {
    // Most games will use a standard deck
    private static final Suit[] SUITS = {Suit.CLUB, Suit.DIAMOND, Suit.HEART, Suit.SPADE};
    private static final Rank[] RANKS = {Rank.ACE, Rank.TWO, Rank.THREE, Rank.FOUR, Rank.FIVE, Rank.SIX, Rank.SEVEN, Rank.EIGHT, Rank.NINE, Rank.TEN, Rank.JACK, Rank.QUEEN, Rank.KING};
    private static final int[] POINT_VALUES = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
    
    public StandardDeck() {
        super(RANKS, SUITS, POINT_VALUES);
    }
}
