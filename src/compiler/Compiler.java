package compiler;

import gen.ToorlaLexer;
import gen.ToorlaListener;
import gen.ToorlaParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import symbols.listeners.ErrorFinder;
import symbols.listeners.Indexer;
import java.io.IOException;
public class Compiler {
    public static void main(String[] args) throws IOException {
            CharStream stream = CharStreams.fromFileName("./sample/error.tl");
            ToorlaLexer lexer = new ToorlaLexer(stream);
            TokenStream tokens = new CommonTokenStream(lexer);
            ToorlaParser parser = new ToorlaParser(tokens);
            parser.setBuildParseTree(true);
            ParseTree tree = parser.program();
            ParseTreeWalker walker = new ParseTreeWalker();//
            // phase 1
//            ToorlaListener listener = new ProgramPrinter();
            // phase 2
//            ToorlaListener listener = new Indexer();
            // phase 3
            ToorlaListener listener = new ErrorFinder();
            walker.walk(listener, tree);


    }
}
