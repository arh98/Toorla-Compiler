package symbols;

public abstract class SymbolTableItem {
    protected String name;
    public  int lineNumber;
    public int column ;
    public abstract String getKey();
    public String getName()
    {
        return name;
    }
    public void setName( String name )
    {
        this.name = name;
    }
    //phase - 3
    public void setLineAndColumn(int line, int column){
        this.lineNumber = line;
        this.column = column;
    }
}
