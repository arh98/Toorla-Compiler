package symbols;




import java.util.LinkedList;
import java.util.List;

public class MethodSymbolTableItem extends SymbolTableItem {
    public static final String methodModifier = "method_";
    private String returnType;
    public List<String> parametersTypes;
    public List<String> parameters;
    private String accessModifier;

    public MethodSymbolTableItem(String name, String returnType, String accessModifier) {
        this.returnType = returnType;
        this.name = name;
        parametersTypes = new LinkedList<>();
        parameters = new LinkedList<>();
        this.accessModifier = accessModifier;
    }

    public void addParametertype(String type){
        parametersTypes.add(type);
    }
    public void addParameter(String type){
        parameters.add(type);
    }

    @Override
    public String toString(){
        String method = "Method: " +  "(name: " + name + ") (returnType: " + returnType + ")";
        if (parametersTypes.size() == 0){
            return method;
        }
        String types = " (parameters:";
        for (int i = 0; i< parametersTypes.size(); i++){
            types += " [" + parametersTypes.get(i) +" "+ parameters.get(i)+ "] ";
        }

        return  method + types + ")";
    }

    @Override
    public String getKey() {
        return MethodSymbolTableItem.methodModifier + name;
    }

    public List<String> getParametersTypes() {
        return parametersTypes;
    }

    public String getReturnType() {
        return returnType;
    }

    public String getAccessModifier() {
        return accessModifier;
    }

    public void setAccessModifier(String accessModifier) {
        this.accessModifier = accessModifier;
    }
}
