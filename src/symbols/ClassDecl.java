package symbols;


public  abstract class ClassDecl extends SymbolTableItem{
    public SymbolTable symbolTable;
    public SymbolTable getSymbolTable() {
        return symbolTable;
    }
    
    public void setSymbolTable(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }
}
