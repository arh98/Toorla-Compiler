package symbols;

public abstract class VarSymbolTableItem extends SymbolTableItem {
    public static String var_modifier = "var_";
    protected String type;
    public boolean isDefined = false;
    public String getKey() {
        return VarSymbolTableItem.var_modifier + name;
    }

    public String getType() {
        return type;
    }
}
