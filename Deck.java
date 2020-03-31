import java.util.stream.*;
import java.util.*;
import java.math.*;


public class Deck {

    private List<Card> cards;

    private int origSize;

    public Deck(Rank[] ranks, Suit[] suits, int[] values) {
        /* *** TO BE IMPLEMENTED IN ACTIVITY 2 *** */
        // Done
        cards = new ArrayList<Card>();
        Card c;
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 13; ++j) {
                c = new Card(ranks[j], suits[i], values[j]);
                cards.add(c);
            }
        }
        origSize = cards.size();
    }

    public boolean isEmpty() {
        /* *** TO BE IMPLEMENTED IN ACTIVITY 2 *** */
        // Done
        return cards.size() == 0;
    }

    public int size() {
        /* *** TO BE IMPLEMENTED IN ACTIVITY 2 *** */
        // Done
        return cards.size();
    }

    public Card deal() {
        /* *** TO BE IMPLEMENTED IN ACTIVITY 2 *** */
        // Done
        Card c = cards.get(0);
        cards.remove(0);
        return c;
    }

    @Override
    public String toString() {
        /* *** TO BE IMPLEMENTED IN ACTIVITY 2 *** */
        // Done
        int hi = origSize - this.size();
        String output = "Deck size = " + origSize + "\nUndealt cards: " +this.size()+"\n";
        output += "...";
        output = output + "\nDealt cards: "+ hi +" \n";
        output += "...";
        output = output + "\n";
        return output;
    }

    /*public void perfectShuffle() {
        // *** TO BE IMPLEMENTED IN ACTIVITY 3-4 *** 
        // Done
        int half = cards.size() / 2;
        if (cards.size() % 2 == 1) {
            half += 1;
        }
        List<Card> first_half = new ArrayList<Card>(), second_half = new ArrayList<Card>();
        first_half = cards.subList(0, half);
        second_half = cards.subList(half, cards.size() - 1);
        ArrayList<Card> end = new ArrayList<Card>();
        int ind = 0;
        boolean isB = true;
        for (int i = 0; i < cards.size() - 1; ++i) {
            if (isB) {
                isB = false;
                end.add(first_half.get(ind));
            } else {
                isB = true;
                end.add(second_half.get(ind));
                ind += 1;
            }
        }
        if (cards.size() % 2 == 1) {
            end.add(first_half.get(ind));
        } else {
            end.add(second_half.get(ind));
        }
        cards = end;
    }*/
    
    public void perfectShuffle() {
        int half = (cards.size()+1)/2;
        List<Card> first_half = new ArrayList<Card>(), second_half = new ArrayList<Card>(), end = new ArrayList<Card>();
        first_half = cards.subList(0, half);
        System.out.println(first_half.size());        
        second_half = cards.subList(half, cards.size());
        System.out.println(second_half.size());
        for (int i = 0; i < half; ++i) {
            end.add(first_half.get(i));
            end.add(second_half.get(i));
        }
        if (cards.size() % 2 == 1) {
            end.add(first_half.get(half-1));
        }
        cards = end;
    }

    public void selectionShuffle() {
        /* *** TO BE IMPLEMENTED IN ACTIVITY 3-4 *** */
        // Done
        ArrayList<Card> sCard = new ArrayList<Card>();
        int k = cards.size();
        for (int i = 0; i<k; i++) {
            int c = (int) (cards.size() * Math.random());
            sCard.add(cards.get(c));
            cards.remove(c);
        }
        cards = sCard;
    }

    public void simulatedShuffle() {
        float half1 = (float) (Math.random() * cards.size() / 4.0);
        half1 += 22;
        int half = Math.round(half1);
        if (cards.size() % 2 == 1) {
            half += 1;
        }
        List<Card> first_half = new ArrayList<Card>(), second_half = new ArrayList<Card>();
        first_half = cards.subList(0, half + 1);
        second_half = cards.subList(half, cards.size());
        ArrayList<Card> end = new ArrayList<Card>();
        int ind = 0;
        boolean isB = true;
        while (first_half.size() > 0 && second_half.size() > 0) {
            if (Math.random() <0.5) {
                end.add(first_half.get(0));
                first_half.remove(0);
            }
            else {
                end.add(second_half.get(0));
                second_half.remove(0);        
            }
        }
        if (first_half.size() == 0) {
            for (int i = 0; i<second_half.size(); i++) {
                end.add(second_half.get(i));
            }
        }
        else
            for (int i = 0; i<first_half.size(); i++)
                end.add(first_half.get(i));

        cards = end;
    }

    public void shuffle() {
        /* *** TO BE IMPLEMENTED IN ACTIVITY 3-4 *** */
        selectionShuffle();
        //Collections.shuffle(cards);
        //perfectShuffle();
        //simulatedShuffle();
        // Call the shuffle you want to use
    }
}
