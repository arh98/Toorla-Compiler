package symbols;

public class MainClassSymbolItem extends ClassDecl {

    public static final String Type = "mainClass_";

    public MainClassSymbolItem(String name) {
        this.name = name;
    }

    @Override
    public String getKey() {
        return MainClassSymbolItem.Type + name;
    }

    @Override
    public String toString() {
        return "MainClass: " + "(name: " + this.name + ")" +" (parent = [])" +" (isEntry=True)";
    }

}
