public enum Suit {
    CLUB("club", 1), 
    DIAMOND("diamond", 2), 
    HEART("heart", 3), 
    SPADE("spade", 4);
    
    private String name;
    private int ordinal;
    
    Suit(String name, int ordinal) {
        this.name = name;
        this.ordinal = ordinal;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    
    public int getOrdinal() {
        return this.ordinal;
    }
}
