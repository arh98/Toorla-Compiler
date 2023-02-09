package symbols;

public class ParameterSymbolTableItem extends VarSymbolTableItem  {

    public ParameterSymbolTableItem(String name, String type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString() {
        return "parameter: " + "(name: " + name + ") (type: " + type + ")";
    }
}
