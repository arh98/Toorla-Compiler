package symbols;

public class FieldSymbolTableItem extends VarSymbolTableItem {

    private String accessModifier;
    public FieldSymbolTableItem(String name, String type, String accessModifier) {
        this.name = name;
        this.accessModifier = accessModifier;
        this.type = type;
    }
    @Override public String toString(){
        return "Field: " + "(name: " + name + ") (type: " + type + ",IsDefined = True)";
       // accessModifier: accessModifier
    }

}
