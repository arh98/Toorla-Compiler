package symbols;
public class LocalVariableSymbolTableItem extends VarSymbolTableItem  {

    public LocalVariableSymbolTableItem(String name, String type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString() {
            return "LocalVar: " + "(name: " + name + ") (type: " + type + ")" +" (isDefined = True)";
    }

}
