package com.artnaseef.poc.antlr;

import com.artnaseef.poc.antlr.grammar.HelloBaseListener;
import com.artnaseef.poc.antlr.grammar.HelloLexer;
import com.artnaseef.poc.antlr.grammar.HelloListener;
import com.artnaseef.poc.antlr.grammar.HelloParser;
import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.io.IOException;
import java.util.BitSet;

public class HelloMain {
    public static void main(String[] args) {
        HelloMain instance = new HelloMain();

        instance.instanceMain(args);
    }

    public void instanceMain(String[] args) {
        try {
            if (args.length > 0) {
                parseFile(args[0]);
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    private void parseFile(String filename) throws IOException {
        CharStream charStream = CharStreams.fromFileName(filename);

        HelloLexer lexer = new HelloLexer(charStream);

        CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);
        HelloParser parser = new HelloParser(commonTokenStream);

        parser.addParseListener(new MyHelloListener());
        parser.addErrorListener(new MyANTLRErrorListener());
        // parser.addParseListener(new HelloBaseListener() {
        //
        // });

        HelloParser.HelloFileContext result = parser.helloFile();

        System.out.println();
        System.out.println("STRING TREE: " + result.toStringTree());
        System.out.println("STRING: " + result.toString());

        ParseTree parseTree = result.getChild(result.getChildCount() - 1);
        System.out.println("LAST NODE = \"" + parseTree.getText() + "\"");
        // result.enterRule(new MyHelloListener());
    }

    private static class MyHelloListener extends HelloBaseListener {
        @Override
        public void enterHelloFile(HelloParser.HelloFileContext ctx) {
            System.out.println("Starting parse of a Hello File");
        }

        @Override
        public void exitHelloFile(HelloParser.HelloFileContext ctx) {
            System.out.println("Finished parse of a Hello File");
        }

        @Override
        public void enterHelloStatement(HelloParser.HelloStatementContext ctx) {
            System.out.println("enterR.. ID=" + HelloParser.ID + ", WS=" + HelloParser.WS + ", T__0=" + HelloParser.T__0);
        }

        @Override
        public void exitHelloStatement(HelloParser.HelloStatementContext ctx) {
            System.out.println("exitR");
        }

        @Override
        public void visitTerminal(TerminalNode node) {
            Token token = node.getSymbol();
            System.out.println("HAVE TOKEN type=" + token.getType() + ", text=\"" + token.getText() + "\"");
        }

        @Override
        public void visitErrorNode(ErrorNode node) {
            System.out.println("ERROR NODE \"" + node.getText() + "\"");
        }

        @Override
        public void enterEveryRule(ParserRuleContext ctx) {
            System.out.println("enter everyRule");
        }

        @Override
        public void exitEveryRule(ParserRuleContext ctx) {
            System.out.println("exit everyRule; class=" + ctx.getClass().getName());
        }
    }

    private static class MyANTLRErrorListener implements ANTLRErrorListener {
        @Override
        public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
            System.out.println("SYNTAX ERROR AT " + line + ":" + charPositionInLine + ": " + msg);
        }

        @Override
        public void reportAmbiguity(Parser recognizer, DFA dfa, int startIndex, int stopIndex, boolean exact, BitSet ambigAlts, ATNConfigSet configs) {

        }

        @Override
        public void reportAttemptingFullContext(Parser recognizer, DFA dfa, int startIndex, int stopIndex, BitSet conflictingAlts, ATNConfigSet configs) {

        }

        @Override
        public void reportContextSensitivity(Parser recognizer, DFA dfa, int startIndex, int stopIndex, int prediction, ATNConfigSet configs) {

        }
    }
}
