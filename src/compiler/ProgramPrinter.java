package compiler;

import gen.ToorlaListener;
import gen.ToorlaParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;
public class ProgramPrinter implements ToorlaListener {
    public String TAB = "    ";
    public static int Indent = 0;
    public boolean Entry;
    public String ConstructorName;
    @Override
    public void enterProgram(ToorlaParser.ProgramContext ctx) {
        System.out.println("Program Start{");
        Indent++;
    }

    @Override
    public void exitProgram(ToorlaParser.ProgramContext ctx) {
        System.out.println("}");
    }

    @Override
    public void enterClassDeclaration(ToorlaParser.ClassDeclarationContext ctx) {
        ConstructorName = ctx.className.getText();
        for (int i = 0; i < Indent; i++) {
            System.out.print(TAB);
        }
        if(ctx.classParent != null) {
            System.out.println("Class : " + ctx.className.getText() + " / Class Parent : " + ctx.classParent.getText() + " / IsEntery : " + Entry + " {");
        }else{
            if(Entry){
                System.out.println("main Class : " + ctx.className.getText() + " / IsEntery : " + Entry + " {");
            }
            else{
                System.out.println("Class : " + ctx.className.getText() + " / IsEntery : " + Entry + " {");
            }
        }
        Indent++;
    }

    @Override
    public void exitClassDeclaration(ToorlaParser.ClassDeclarationContext ctx) {
        for (int i = 0; i < Indent -1; i++) {
            System.out.print(TAB);
        }
        System.out.println("}");
        Indent--;
    }

    @Override
    public void enterEntryClassDeclaration(ToorlaParser.EntryClassDeclarationContext ctx) {
        Entry =true;
    }

    @Override
    public void exitEntryClassDeclaration(ToorlaParser.EntryClassDeclarationContext ctx) {
        Entry =false;
    }

    @Override
    public void enterFieldDeclaration(ToorlaParser.FieldDeclarationContext ctx) {
            for (int i = 0; i < Indent; i++) {
                System.out.print(TAB);
            }
        System.out.println("field : " + ctx.fieldName.getText()+ " / type : " + ctx.fieldType.getText() + " / "+ctx.access_modifier().getText());
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
        for (int i = 0; i < Indent; i++) {
            System.out.print(TAB);
        }
        if(!Entry){
            if(ctx.methodName.getText().equals(ConstructorName)){
                System.out.println("class constructor : " + ctx.methodName.getText() + " / return type : " + ctx.t.getText() + " / type : "+ ctx.methodAccessModifier.getText() + " {" );
                System.out.println(TAB+TAB+TAB+"parameters list : []");
            }else{
                String args = "parameters list : ["+ "type : " + ctx.typeP1.getText() +" / name : " + ctx.param1.getText();
                if(ctx.param2!=null){
                    args = args.concat(", type : " + ctx.typeP2.getText() +" / name : " + ctx.param2.getText());
                }
                System.out.println("class method : " + ctx.methodName.getText() + " / return type : " + ctx.t.getText() + " / type : "+ ctx.methodAccessModifier.getText() + " {" );
                System.out.println(TAB+TAB+TAB+args+"]");
            }
        }
        else{
            System.out.println(ctx.methodName.getText() + " method" + " / return type : " + ctx.t.getText() + "{" );
        }
        Indent++;
    }

    @Override
    public void exitMethodDeclaration(ToorlaParser.MethodDeclarationContext ctx) {
        for (int i = 0; i < Indent -1; i++) {
            System.out.print(TAB);
        }
        System.out.println("}");
        Indent--;
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
        ParserRuleContext parent = ctx.getParent();
        if ((
                parent instanceof ToorlaParser.OpenStatementContext
                        || parent instanceof ToorlaParser.ClosedStatementContext
                        || parent instanceof ToorlaParser.ClosedConditionalContext
                        || parent instanceof ToorlaParser.StatementClosedLoopContext
        )){
            System.out.print(TAB + TAB + TAB + "nested statement{}\n");

        }
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
        for (int i = 0; i < ctx.ID().size() ; i++) {
            for (int j = 0; j < Indent; j++) {
                System.out.print(TAB);
            }
            System.out.println("field : "+ctx.ID().get(i).getText() + " / type : local var");
        }
    }

    @Override
    public void exitStatementVarDef(ToorlaParser.StatementVarDefContext ctx) {

    }

    @Override
    public void enterStatementBlock(ToorlaParser.StatementBlockContext ctx) {

    }

    @Override
    public void exitStatementBlock(ToorlaParser.StatementBlockContext ctx) {

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
//        System.out.println("closed loop: " + ctx.getText());
        ParserRuleContext parent = ctx.getParent();
        if ((
                parent instanceof ToorlaParser.OpenConditionalContext
                        || parent instanceof ToorlaParser.StatementOpenLoopContext
                        || parent instanceof ToorlaParser.ClosedConditionalContext
                        || parent instanceof ToorlaParser.StatementClosedLoopContext
        )){
            System.out.print("nested statement{\n");
        }
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
