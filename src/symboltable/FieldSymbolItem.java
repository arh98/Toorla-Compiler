package symboltable;

public class FieldSymbolItem extends SymbolTableEntry{
    private String id;
    private String value;
    private boolean isDefined = false;
    private String classType;

    public FieldSymbolItem(String id,String value,int type,String classType) {
        this.classType = classType;
        this.value = value;
        this.id = id;
        if(type == 1) super.setType("ClassField");
        else if(type == 2) super.setType("ClassArrayField");
        else if(type == 3) super.setType("Field");
        else super.setType("MethodField");
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    private String setClassType(){
        if(!classType.equals("bool") && !classType.equals("int") && !classType.equals("string") ){
            classType = "class type = " + classType;
        }
        return classType;
    }
    private boolean checkIsDefined(){
        if(classType.equals("bool") || classType.equals("int") || classType.equals("string")){
            isDefined = true;
        }
        return isDefined;
    }

    @Override
    public String toString() {
        String out = super.getType() + "(name: " + this.value + ") " +"(type: [ " +setClassType() +", isDefined: " +checkIsDefined() ;
        return out +")";
    }
}
