package symbols;


public class ClassSymbolItem extends ClassDecl {
    public static final String Type = "class_";
    public String parent = null;
    public ClassSymbolItem(String name) {
        this.name = name;
    }
    public void setParent(String parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        String out;
        if(parent != null) {
            out = "Class: " + "(name: " + this.name + ") " + "(parent : " + parent + ") " + "(isEntry=False";
        }else {
            return "Class: " + "(name: " + this.name + ") " + " (isEntry=False)";
        }

        return out +")";
    }


    @Override
    public String getKey() {
        return ClassSymbolItem.Type + name;
    }


}
