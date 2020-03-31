import java.util.*;

public class ThirteensBoard extends Board {
    private Card[] cards;
    private Deck d;
    public ThirteensBoard() {
        newGame();
        /* *** TO BE IMPLEMENTED IN ACTIVITY 7 *** */
    }

    public void newGame() {
        resetDeck();
        cards = new Card[10];
        for (int i = 0; i < cards.length; ++i) {
            cards[i] = d.deal();
        }
        while (!anotherPlayIsPossible()) {
            resetDeck();
            cards = new Card[10];
            for (int i = 0; i < cards.length; ++i) {
                cards[i] = d.deal();
            }
        }
        /* *** TO BE IMPLEMENTED IN ACTIVITY 7 *** */
    }
    
    public void resetDeck() {
        d = new StandardDeck();
        d.shuffle();
    }

    public int size() {
        /* *** TO BE IMPLEMENTED IN ACTIVITY 7 *** */
        return cards.length;
    }

    public int deckSize() {
        /* *** TO BE IMPLEMENTED IN ACTIVITY 7 *** */
        return d.size();
    }

    public Card cardAt(int k) {
        /* *** TO BE IMPLEMENTED IN ACTIVITY 7 *** */
        return cards[k];
    }

    @Override
    public String toString() {
        /* *** TO BE IMPLEMENTED IN ACTIVITY 7 *** */
        String s = "the cards on the board...\n";
        for (int i = 0; i<cards.length-1; i++) {
          s += cards[i].toString();
          s+= "\n";
        }
        s+= cards[cards.length-1];
        return s;
    }

    // The following methods enforce the rules for this game
    public boolean gameIsWon() {
        /* *** TO BE IMPLEMENTED IN ACTIVITY 7 *** */
        for (int i = 0; i < cards.length; ++i) {
            if (cards[i] != null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Determines if the selected cards form a valid group for removal.
     * In Tens, the legal groups are (1) a pair of non-face cards
     * whose values add to 10, and (2) a group of three cards consisting of
     * a jack, a queen, and a king in some order.
     * @param selectedCards the list of the indices of the selected cards.
     * @return true if the selected cards form a valid group for removal;
     *         false otherwise.
     */
    public boolean isLegal(List<Integer> selectedCards) {
        /* *** TO BE IMPLEMENTED IN ACTIVITY 9 *** */
        return containsPairSum(selectedCards) || containsE(selectedCards);
    }

    /**
     * Determine if there are any legal plays left on the board.
     * In Tens, there is a legal play if the board contains
     * (1) a pair of non-face cards whose values add to 10, or (2) a group
     * of three cards consisting of a jack, a queen, and a king in some order.
     * @return true if there is a legal play left on the board;
     *         false otherwise.
     */
    public boolean anotherPlayIsPossible() {
        /* *** TO BE IMPLEMENTED IN ACTIVITY 9 *** */
        List<Integer> board = new ArrayList<Integer>();
        boolean ret = false;
        for (int i = 0; i < cards.length; ++i) {
            for (int j = i + 1; j < cards.length; ++j) {
                board = new ArrayList<Integer>();
                board.add(i);
                board.add(j);
                ret |= containsPairSum(board);
            }
        }
        if (ret) {
            return true;
        }
        for (int i = 0; i < cards.length; ++i) {
            board = new ArrayList<Integer>();
            board.add(i);
            ret |= containsE(board);
        }
        return ret;
    }

    /**
     * Check for an 10-pair in the selected cards.
     * @param selectedCards selects a subset of this board.  It is list
     *                      of indexes into this board that are searched
     *                      to find an 10-pair.
     * @return true if the board entries in selectedCards
     *              contain an 10-pair; false otherwise.
     */
    public boolean containsPairSum(List<Integer> selectedCards) {
        /* *** TO BE IMPLEMENTED IN ACTIVITY 9 *** */
        if (selectedCards.size() != 2) {
            return false;
        }
        if (cards[selectedCards.get(0)] == null || cards[selectedCards.get(1)] == null) {
            return false;
        }

        /*for (int i = 0; i < selectedCards.size(); ++i) {
            for (int j = i + 1; j < selectedCards.size(); ++j) {*/
                if (cards[selectedCards.get(0)].getValue() + cards[selectedCards.get(1)].getValue() == 13) {
                    return true;
                }
           /* }
        }*/
        
        return false;
    }

    /**
     * Check for a quartet in the selected cards.
     * @param selectedCards selects a subset of this board.  It is list
     *                      of indexes into this board that are searched
     *                      to find a quartet.
     * @return true if the board entries in selectedCards
     *              include all kings, all queens, all jacks, or all tens; false otherwise.
     */
    public boolean containsE(List<Integer> selectedCards) {
        /* *** TO BE IMPLEMENTED IN ACTIVITY 9 *** */
        if (selectedCards.size() < 1 || selectedCards == null) {
            return false;
        }

        if (cards[selectedCards.get(0)] == null) {
            return false;
        }
        /*for (int i = 0; i < selectedCards.size(); ++i) {
            for (int j = i + 1; j < selectedCards.size(); ++j) {
                for (int k = j + 1; k < selectedCards.size(); ++k) {*/
                    if (cards[selectedCards.get(0)].getRank().toString().equals("king")) {
                        return true;
                    }
                /*}
            }
        }*/
        return false;
    }

    public List<Integer> findPlayIsPossible() {
        /* *** TO BE IMPLEMENTED IN ACTIVITY 11 *** */
        List<Integer> car = new ArrayList<Integer>();
        for (int i = 0; i < cards.length; ++i) {
            for (int j = i + 1; j < cards.length; ++j) {
                car = new ArrayList<Integer>();
                car.add(i);
                car.add(j);
                if (containsPairSum(car)) {
                    return car;
                }
            }
        }
        for (int i = 0; i < cards.length; ++i) {
            car = new ArrayList<Integer>();
            car.add(i);
            if (containsE(car)) {
                return car;
            }         
        }
        return null;
    }

    public void replaceSelectedCards(List<Integer> selectedCards) {
        /* *** TO BE IMPLEMENTED IN ACTIVITY 11 *** */
        for (int i = 0; i < selectedCards.size(); ++i) {
            if (d.size() == 0) {
                cards[selectedCards.get(i)] = null;
            } else {
                cards[selectedCards.get(i)] = d.deal();
            }
        }
    }

    /**
     * Do a play (if there is one)
     * @return Successfully played a play or not
     */
    public boolean playIfPossible() {
        /* *** TO BE IMPLEMENTED IN ACTIVITY 11 *** */
        List<Integer> l = findPlayIsPossible();
        if (l != null) {
            replaceSelectedCards(l);
            return true;
        }
        return false;
    }
}
