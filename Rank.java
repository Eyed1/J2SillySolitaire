public enum Rank {
    ACE("ace", 1), 
    TWO("2", 2), 
    THREE("3", 3), 
    FOUR("4", 4), 
    FIVE("5", 5), 
    SIX("6", 6), 
    SEVEN("7", 7), 
    EIGHT("8", 8), 
    NINE("9", 9), 
    TEN("10", 10), 
    JACK("jack", 11), 
    QUEEN("queen", 12), 
    KING("king", 13);
    
    private String name;
    private int ordinal;
    
    Rank(String name, int ordinal) {
        this.name = name;
        this.ordinal = ordinal;
    }
    
    @Override
    public String toString() {
        return name;
    }
    
    public int getOrdinal() {
        return this.ordinal;
    }
}
