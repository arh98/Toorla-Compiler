package symbols.listeners;

import gen.ToorlaListener;
import gen.ToorlaParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import symbols.MethodSymbolTableItem;
import symbols.SymbolTable;;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import symbols.*;
public class ErrorFinder implements ToorlaListener {
    //current method for set
    MethodSymbolTableItem currentMethod;
    SymbolTable currentMethodForCheck;
    SymbolTable currentClass;
    SymbolTable currentProgram;
    String currentMethod_returnType;
    SymbolTable tempsymbolTable;
    SymbolTable currentSymbolTable;
    boolean entry;
    boolean defined_main= false;
    int blockNumber = 0;
    public static Queue<Error> errors = new LinkedList<>();
    public static Map<String, MethodSymbolTableItem> methods = new HashMap<>();
    @Override
    public void enterProgram(ToorlaParser.ProgramContext ctx) {
        currentSymbolTable = new SymbolTable(ctx.start.getLine(), 0, "program", null); //global scope
        SymbolTable.push(currentSymbolTable);
        currentProgram = SymbolTable.getSymbolTableByKey("program");

    }

    @Override
    public void exitProgram(ToorlaParser.ProgramContext ctx) {
        SymbolTable.printAll();
        System.out.println("\n\n\n\n\n\nErrors Found :");
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        printErrors(errors);
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

    @Override
    public void enterClassDeclaration(ToorlaParser.ClassDeclarationContext ctx) {
        String name = ctx.className.getText();
        int line = ctx.start.getLine();
        int column = ctx.className.getCharPositionInLine();
        if(entry && !defined_main){

            defined_main= true;
            ClassDecl mainClassSymbolItem =new MainClassSymbolItem(name);
            currentSymbolTable.insert(mainClassSymbolItem);
            tempsymbolTable = new SymbolTable(line, column, name, currentSymbolTable);
            mainClassSymbolItem.setSymbolTable(tempsymbolTable);


        }
        else {
            ClassSymbolItem classSymbolTableItem = new ClassSymbolItem(name);
            if(!currentSymbolTable.insert(classSymbolTableItem)){
                tempsymbolTable = new SymbolTable(line, column, name, currentSymbolTable);
                classSymbolTableItem.setSymbolTable(tempsymbolTable);
                if(ctx.classParent != null){
                    classSymbolTableItem.setParent(ctx.classParent.getText());
                }
            }
            else {
                errors.add(new Error(101, line, column, "class [" + name + "] has been defined already"));
                classSymbolTableItem.setName(ctx.className.getText() + "_" + line + "_" + column);
                name= classSymbolTableItem.getName();
                currentSymbolTable.insert(classSymbolTableItem);
                tempsymbolTable = new SymbolTable(line, column, name, currentSymbolTable);
                classSymbolTableItem.setSymbolTable(tempsymbolTable);
                if(ctx.classParent != null){
                    classSymbolTableItem.setParent(ctx.classParent.getText());
                }

            }

        }
        SymbolTable.push(tempsymbolTable);
        currentSymbolTable = tempsymbolTable;

        currentClass = SymbolTable.getSymbolTableByKey(ctx.className.getText());
    }

    @Override
    public void exitClassDeclaration(ToorlaParser.ClassDeclarationContext ctx) {
        currentSymbolTable = currentSymbolTable.getPreSymbolTable();
        defined_main =false;
    }

    @Override
    public void enterEntryClassDeclaration(ToorlaParser.EntryClassDeclarationContext ctx) {
        entry = true;
    }

    @Override
    public void exitEntryClassDeclaration(ToorlaParser.EntryClassDeclarationContext ctx) {
        entry = false;
    }

    @Override
    public void enterFieldDeclaration(ToorlaParser.FieldDeclarationContext ctx) {
        for (int i = 0; i < ctx.ID().size() ; i++) {
            int line = ctx.start.getLine();
            int column = ctx.fieldName.getCharPositionInLine();
            FieldSymbolTableItem fieldSymbolTableItem = new FieldSymbolTableItem(ctx.ID().get(i).getText(), ctx.fieldType.getText()
                    , ctx.access_modifier().getText() );
            if(currentSymbolTable.insert(fieldSymbolTableItem)) {
                errors.add(new Error(103, line, column, "field [" + fieldSymbolTableItem.getName() + "] has been defined already"));
                fieldSymbolTableItem.setName(ctx.fieldName.getText() + "_" + line + "_" + column);
                currentSymbolTable.insert(fieldSymbolTableItem);
            }
        }
    }

    @Override
    public void exitFieldDeclaration(ToorlaParser.FieldDeclarationContext ctx) {

    }

    @Override
    public void enterAccess_modifier(ToorlaParser.Access_modifierContext ctx) {

    }

    @Override
    public void exitAccess_modifier(ToorlaParser.Access_modifierContext ctx) {

    }


    @Override
    public void enterMethodDeclaration(ToorlaParser.MethodDeclarationContext ctx) {
        String name = ctx.methodName.getText();
        SymbolTable methodsymbolTable;
        int line = ctx.start.getLine();
        int column = ctx.start.getCharPositionInLine();

        MethodSymbolTableItem methodSymbolTableItem;
        if(entry){
            methodSymbolTableItem = new MethodSymbolTableItem(name, ctx.t.getText(), "public");
            entry= false;
        }
        else {
            methodSymbolTableItem = new MethodSymbolTableItem(name, ctx.t.getText(), ctx.methodAccessModifier.getText());
        }

        methodSymbolTableItem.setLineAndColumn(line, column);

        if (currentSymbolTable.insert(methodSymbolTableItem)) {
            errors.add(new Error(102, line, column, "method [" + name + "] has been defined already"));
            methodSymbolTableItem.setName(name + "_" + line + "_" + column);
            name= methodSymbolTableItem.getName();
            currentSymbolTable.insert(methodSymbolTableItem);
            methodsymbolTable = new SymbolTable(line, column, name, currentSymbolTable);
        }
        else {
            methodsymbolTable = new SymbolTable(line, column, name, currentSymbolTable);
            methods.put(name,methodSymbolTableItem);

        }

        SymbolTable.push(methodsymbolTable);
        currentMethod = methodSymbolTableItem;
        currentSymbolTable=methodsymbolTable;


        for (int i = 1; i < ctx.ID().size() ; i++) {
            currentMethod.addParameter(ctx.ID().get(i).getText());
            currentMethod.addParametertype(ctx.toorlaType().get(i-1).getText());
            currentSymbolTable.insert(new ParameterSymbolTableItem(ctx.ID().get(i).getText(),
                    ctx.toorlaType().get(i-1).getText()));
        }


        ///////////////////////////////////////
        currentMethodForCheck = SymbolTable.getSymbolTableByKey(ctx.methodName.getText());
        currentMethod_returnType= ctx.t.getText();
    }

    @Override
    public void exitMethodDeclaration(ToorlaParser.MethodDeclarationContext ctx) {
        currentSymbolTable = currentSymbolTable.getPreSymbolTable();
        currentMethod = null;
    }

    @Override
    public void enterClosedStatement(ToorlaParser.ClosedStatementContext ctx) {

    }

    @Override
    public void exitClosedStatement(ToorlaParser.ClosedStatementContext ctx) {

    }

    @Override
    public void enterClosedConditional(ToorlaParser.ClosedConditionalContext ctx) {

    }

    @Override
    public void exitClosedConditional(ToorlaParser.ClosedConditionalContext ctx) {

    }

    @Override
    public void enterOpenConditional(ToorlaParser.OpenConditionalContext ctx) {

    }

    @Override
    public void exitOpenConditional(ToorlaParser.OpenConditionalContext ctx) {

    }

    @Override
    public void enterOpenStatement(ToorlaParser.OpenStatementContext ctx) {

    }

    @Override
    public void exitOpenStatement(ToorlaParser.OpenStatementContext ctx) {

    }

    @Override
    public void enterStatement(ToorlaParser.StatementContext ctx) {

    }

    @Override
    public void exitStatement(ToorlaParser.StatementContext ctx) {

    }

    @Override
    public void enterStatementVarDef(ToorlaParser.StatementVarDefContext ctx) {

    }

    @Override
    public void exitStatementVarDef(ToorlaParser.StatementVarDefContext ctx) {
        if(currentMethod != null){
            for (int i = 0; i < ctx.ID().size() ; i++) {
                LocalVariableSymbolTableItem var = new LocalVariableSymbolTableItem(ctx.ID().get(i).getText(), "var");
                int line = ctx.start.getLine();
                int column = ctx.i1.getCharPositionInLine();
                if(currentSymbolTable.insert(var)) {
                    errors.add(new Error(104, line, column, "Var " + var.getName() + " has been defined already"));
                    var.setName(var.getName() + "_" + line + "_" + column);
                    currentSymbolTable.insert(var);
                }
            }

        }
    }

    @Override
    public void enterStatementBlock(ToorlaParser.StatementBlockContext ctx) {
        if (!(ctx.statement().get(0).getText().equals("begin"))) {
            blockNumber++;
            int line = ctx.start.getLine();
            int column = ctx.start.getCharPositionInLine();
            currentSymbolTable = new SymbolTable(line, column,"Block"+blockNumber, currentSymbolTable);
            SymbolTable.push(currentSymbolTable);
        }
    }

    @Override
    public void exitStatementBlock(ToorlaParser.StatementBlockContext ctx) {
        if (!(ctx.statement().get(0).getText().equals("begin"))) {
            currentSymbolTable = currentSymbolTable.getPreSymbolTable();
        }
    }

    @Override
    public void enterStatementContinue(ToorlaParser.StatementContinueContext ctx) {

    }

    @Override
    public void exitStatementContinue(ToorlaParser.StatementContinueContext ctx) {

    }

    @Override
    public void enterStatementBreak(ToorlaParser.StatementBreakContext ctx) {

    }

    @Override
    public void exitStatementBreak(ToorlaParser.StatementBreakContext ctx) {

    }


    @Override
    public void enterStatementReturn(ToorlaParser.StatementReturnContext ctx) {

    }

    @Override
    public void exitStatementReturn(ToorlaParser.StatementReturnContext ctx) {

    }

    @Override
    public void enterStatementClosedLoop(ToorlaParser.StatementClosedLoopContext ctx) {

    }

    @Override
    public void exitStatementClosedLoop(ToorlaParser.StatementClosedLoopContext ctx) {

    }

    @Override
    public void enterStatementOpenLoop(ToorlaParser.StatementOpenLoopContext ctx) {

    }

    @Override
    public void exitStatementOpenLoop(ToorlaParser.StatementOpenLoopContext ctx) {

    }

    @Override
    public void enterStatementWrite(ToorlaParser.StatementWriteContext ctx) {

    }

    @Override
    public void exitStatementWrite(ToorlaParser.StatementWriteContext ctx) {

    }


    @Override
    public void enterStatementAssignment(ToorlaParser.StatementAssignmentContext ctx) {

    }

    @Override
    public void exitStatementAssignment(ToorlaParser.StatementAssignmentContext ctx) {

    }

    @Override
    public void enterStatementInc(ToorlaParser.StatementIncContext ctx) {

    }

    @Override
    public void exitStatementInc(ToorlaParser.StatementIncContext ctx) {

    }

    @Override
    public void enterStatementDec(ToorlaParser.StatementDecContext ctx) {

    }

    @Override
    public void exitStatementDec(ToorlaParser.StatementDecContext ctx) {

    }

    @Override
    public void enterExpression(ToorlaParser.ExpressionContext ctx) {

    }

    @Override
    public void exitExpression(ToorlaParser.ExpressionContext ctx) {

    }

    @Override
    public void enterExpressionOr(ToorlaParser.ExpressionOrContext ctx) {

    }

    @Override
    public void exitExpressionOr(ToorlaParser.ExpressionOrContext ctx) {

    }

    @Override
    public void enterExpressionOrTemp(ToorlaParser.ExpressionOrTempContext ctx) {

    }

    @Override
    public void exitExpressionOrTemp(ToorlaParser.ExpressionOrTempContext ctx) {

    }

    @Override
    public void enterExpressionAnd(ToorlaParser.ExpressionAndContext ctx) {

    }

    @Override
    public void exitExpressionAnd(ToorlaParser.ExpressionAndContext ctx) {

    }

    @Override
    public void enterExpressionAndTemp(ToorlaParser.ExpressionAndTempContext ctx) {

    }

    @Override
    public void exitExpressionAndTemp(ToorlaParser.ExpressionAndTempContext ctx) {

    }

    @Override
    public void enterExpressionEq(ToorlaParser.ExpressionEqContext ctx) {

    }

    @Override
    public void exitExpressionEq(ToorlaParser.ExpressionEqContext ctx) {

    }

    @Override
    public void enterExpressionEqTemp(ToorlaParser.ExpressionEqTempContext ctx) {

    }

    @Override
    public void exitExpressionEqTemp(ToorlaParser.ExpressionEqTempContext ctx) {

    }

    @Override
    public void enterExpressionCmp(ToorlaParser.ExpressionCmpContext ctx) {

    }

    @Override
    public void exitExpressionCmp(ToorlaParser.ExpressionCmpContext ctx) {

    }

    @Override
    public void enterExpressionCmpTemp(ToorlaParser.ExpressionCmpTempContext ctx) {

    }

    @Override
    public void exitExpressionCmpTemp(ToorlaParser.ExpressionCmpTempContext ctx) {

    }

    @Override
    public void enterExpressionAdd(ToorlaParser.ExpressionAddContext ctx) {

    }

    @Override
    public void exitExpressionAdd(ToorlaParser.ExpressionAddContext ctx) {

    }

    @Override
    public void enterExpressionAddTemp(ToorlaParser.ExpressionAddTempContext ctx) {

    }

    @Override
    public void exitExpressionAddTemp(ToorlaParser.ExpressionAddTempContext ctx) {

    }

    @Override
    public void enterExpressionMultMod(ToorlaParser.ExpressionMultModContext ctx) {

    }

    @Override
    public void exitExpressionMultMod(ToorlaParser.ExpressionMultModContext ctx) {

    }

    @Override
    public void enterExpressionMultModTemp(ToorlaParser.ExpressionMultModTempContext ctx) {

    }

    @Override
    public void exitExpressionMultModTemp(ToorlaParser.ExpressionMultModTempContext ctx) {

    }

    @Override
    public void enterExpressionUnary(ToorlaParser.ExpressionUnaryContext ctx) {

    }

    @Override
    public void exitExpressionUnary(ToorlaParser.ExpressionUnaryContext ctx) {

    }


    @Override
    public void enterExpressionMethods(ToorlaParser.ExpressionMethodsContext ctx) {

    }

    @Override
    public void exitExpressionMethods(ToorlaParser.ExpressionMethodsContext ctx) {

    }

    @Override
    public void enterExpressionMethodsTemp(ToorlaParser.ExpressionMethodsTempContext ctx) {

    }

    @Override
    public void exitExpressionMethodsTemp(ToorlaParser.ExpressionMethodsTempContext ctx) {

    }

    @Override
    public void enterExpressionOther(ToorlaParser.ExpressionOtherContext ctx) {

    }

    @Override
    public void exitExpressionOther(ToorlaParser.ExpressionOtherContext ctx) {

    }

    @Override
    public void enterToorlaType(ToorlaParser.ToorlaTypeContext ctx) {

    }

    @Override
    public void exitToorlaType(ToorlaParser.ToorlaTypeContext ctx) {

    }

    @Override
    public void enterSingleType(ToorlaParser.SingleTypeContext ctx) {

    }

    @Override
    public void exitSingleType(ToorlaParser.SingleTypeContext ctx) {

    }

    public static void printErrors(Queue<Error> errs) {
        for (Error err : errs) {
            System.out.println(err.toString());
        }
    }
    public static boolean foundMethod(String e) {
        if(methods.get(e)== null){
            return false;}
        else{
            boolean found_in_public_class= (methods.get(e).
                    getAccessModifier().equals("public"));
            return found_in_public_class;
        }
    }

    @Override
    public void visitTerminal(TerminalNode terminalNode) {

    }

    @Override
    public void visitErrorNode(ErrorNode errorNode) {

    }

    @Override
    public void enterEveryRule(ParserRuleContext parserRuleContext) {

    }

    @Override
    public void exitEveryRule(ParserRuleContext parserRuleContext) {

    }
}
class Error {
    public int type;
    public int line;
    public int column;
    String text;

    public Error(int type, int line, int column, String text){
        this.column = column;
        this.line = line;
        this.text = text;
        this.type = type;
    }

    public String toString(){
        return "Error" + type + " : in line " + line + ":" + column + " , " + text;
    }

}
