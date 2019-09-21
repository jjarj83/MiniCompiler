import java.io.IOException;
import java.util.ArrayList;

public class Lexer
{
    private static final char EOF =  0;

    private Parser         yyparser; // parent parser object
    private java.io.Reader reader;   // input stream
    public int             lineno;   // line number

    public Lexer(java.io.Reader reader, Parser yyparser) throws java.io.IOException
    {
        this.reader   = reader;
        this.yyparser = yyparser;
        lineno = -123;
    }

    public char NextChar() throws IOException
    {
        // http://tutorials.jenkov.com/java-io/readers-writers.html
        int data = reader.read();
        if(data == -1)
        {
            return EOF;
        }
        return (char)data;
    }
    public int Fail()
    {
        return -1;
    }

    public int yylex() throws java.io.IOException
    {
        int state = 0;
        String s = "";

        while(true)
        {
            char c = ' ';
            switch(state)
            {
                case 0:
                    c = NextChar();
                    if(c == ';') { state= 1; continue; }
                    if(c == '(') { state= 2; continue; }
                    if(c == ')') { state= 3; continue; }
                    if(c == ',') { state= 4; continue; }
                    if(c == '{') { state= 5; continue; }
                    if(c == '}') { state= 6; continue; }
                    if(c == '=') { state= 7; continue; }
                    if(c == '+' | c == '-' | c==  '/' | c== '*') { state= 8; continue; }
                    if(c == '\\') { state= 9; continue; }
                    if(c == '<' | c == '>' | c== '!') { state= 10; continue; }
                    if (Character.isDigit(c)) { state= 11; continue; }
                    if (Character.isLetter(c)) { state= 12; continue; }
                    if(c == EOF) { state=99; continue; }
                    // return Fail();

                case 1:
                    yyparser.yylval = new ParserVal((Object)";");   // set token-attribute to yyparser.yylval
                    return Parser.SEMI;                             // return token-name

                case 99:
                    return EOF;                                     // return end-of-file symbol

                case 2:
                    yyparser.yylval = new ParserVal((Object)"(");   // set token-attribute to yyparser.yylval
                    return Parser.LPAREN;                           // return token-name

                case 3:
                    yyparser.yylval = new ParserVal((Object)")");   // set token-attribute to yyparser.yylval
                    return Parser.RPAREN;                           // return token-name

                case 4:
                    yyparser.yylval = new ParserVal((Object)",");   // set token-attribute to yyparser.yylval
                    return Parser.COMMA;                           // return token-name

                case 5:
                    yyparser.yylval = new ParserVal((Object)"{");   // set token-attribute to yyparser.yylval
                    return Parser.BEGIN;                           // return token-name

                case 6:
                    yyparser.yylval = new ParserVal((Object)"}");   // set token-attribute to yyparser.yylval
                    return Parser.END;                           // return token-name

                case 7:
                    yyparser.yylval = new ParserVal((Object)"=");   // set token-attribute to yyparser.yylval
                    return Parser.ASSIGN;                           // return token-name

                case 8:
                    yyparser.yylval = new ParserVal((Object) c);   // set token-attribute to yyparser.yylval
                    return Parser.OP;                           // return token-name

                case 9:
                    c = NextChar();
                    if (c == 'n') {
                        lineno++;
                        state = 0;
                    } else
                        state = 99;
                    break;

                case 10:
                    s = s + c;
                    c = NextChar();
                    if (c == '=')
                        s = s + c;

                    yyparser.yylval = new ParserVal((Object) s);
                    s = "";
                    return Parser.RELOP;

                case 11:
                    s = s + c;
                    c = NextChar();
                    if (Character.isDigit(c)) { s = s + c; }
                    else if (c == '.') { state = 12; }
                    else {
                        yyparser.yylval = new ParserVal((Object) s);
                        s = "";
                        return Parser.NUM;
                    }
                    break;

                case 12:







            }
        }
    }
}
