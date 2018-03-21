public class Function {
    private String name;
    private String symbol;

    Function(String name, String symbol){
        this.name=name;
        this.symbol=symbol;
    }

    public String getName() {
        return this.name;
    }

    public String getSymbol() {
        return this.symbol;
    }

    @Override
    public String toString() {
        return name;
    }
}
