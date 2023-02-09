package symbols;
import java.util.*;

public class SymbolTable {
    private SymbolTable pre;
    public String name;
    private static SymbolTable top;
    private Map<String, SymbolTableItem> items;
    private static Map<String, SymbolTable> symbolTables = new HashMap<>();
    private static Stack<SymbolTable> stack = new Stack<>();
    private static Queue<SymbolTable> queue = new LinkedList<>();
    //p3
    public int line;
    public int column;
    public SymbolTable( String name, SymbolTable pre) {
        this.name = name;
        this.pre = pre;
        this.items = new HashMap<>();
    }
    public SymbolTable( int line , int column,String name, SymbolTable pre) {
        this.name = name;
        this.pre = pre;
        this.items = new HashMap<>();
        //p3
        this.column = column;
        this.line = line;
    }


    public static void push(SymbolTable symbolTable) {
        if (top != null)
            stack.push(top);
        top = symbolTable;
        queue.offer(symbolTable);
        symbolTables.put(symbolTable.name , symbolTable);
    }
    public boolean insert(SymbolTableItem item) {
        //p3
        if (isAlreadyExists(item)) {
            return true;
        }
        //p2
        items.put(item.getKey(), item);
        return false;
    }
    public SymbolTable getPreSymbolTable() {
        return pre;
    }

    @Override
    public String toString() {
            if (pre != null) {
                return "=============  " + name + " =============\n" +
                        printItems() +
                        "==================================================================\n";
            } else {
                return "\n=============  " + name + "  =============  \n" +
                        printItems() +
                        "==================================================================\n";
            }
        }

    public String printItems() {
        String itemsStr = "";
        for (Map.Entry<String, SymbolTableItem> entry : items.entrySet()) {
            SymbolTableItem symbolTableItem = entry.getValue();
            itemsStr += "Key = " + entry.getKey() + "  | Value = " + symbolTableItem + "\n";
        }
        return itemsStr;
    }

    public static void printAll() {
        while (!queue.isEmpty()) {
            System.out.println(queue.poll());
        }
    }
    //phase 3
    public static SymbolTable getSymbolTableByKey(String key) {
        return symbolTables.get(key);
    }
    public boolean isAlreadyExists(SymbolTableItem item) {
        SymbolTable pre = this;
        while (pre != null) {
            if (pre.items.containsKey(item.getKey())) {
                if (item instanceof FieldSymbolTableItem) {
                    if (!(pre.items.get(item.getKey()) instanceof FieldSymbolTableItem)) {
                        return false;
                    }
                }
                if(item instanceof LocalVariableSymbolTableItem) {
                    if (!(pre.items.get(item.getKey()) instanceof LocalVariableSymbolTableItem)) {
                        return false;
                    }
                }
                return true;
            }
            pre = pre.pre;
        }
        return false;
    }

}
