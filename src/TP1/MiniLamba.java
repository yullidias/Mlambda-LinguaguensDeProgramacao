package TP1;

import Lexical.Lexeme;
import Lexical.LexicalAnalysis;
import Lexical.TokenType;
import Lexical.LexicalException;
import Syntatical.SyntaticalAnalysis;
import Model.Command;

public class MiniLamba {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java mlambda [MiniLambda File]");
            return;
        }
        try (LexicalAnalysis l = new LexicalAnalysis(args[0])) {
           
            SyntaticalAnalysis S = new SyntaticalAnalysis(l);
            Command c;
            c = S.init();
            c.execute();            
            
            Lexeme lex;
            /*while (checkType((lex = l.nextToken()).type)) {
                System.out.printf("(\"%s\", %s)\n", lex.token, lex.type);
            }
            
            switch (lex.type) {
                case INVALID_TOKEN:
                    System.out.printf("%02d: Lexema invÃ¡lido [%s]\n", l.line(), lex.token);
                    break;
                case UNEXPECTED_EOF:
                    System.out.printf("%02d: Fim de arquivo inesperado\n", l.line());
                    break;
                default:
                    System.out.printf("(\"%s\", %s)\n", lex.token, lex.type);
                    break;
            }
            */
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }

    private static boolean checkType(TokenType type) {
        return !(type == TokenType.END_OF_FILE ||
                 type == TokenType.INVALID_TOKEN ||
                 type == TokenType.UNEXPECTED_EOF);
    }
}

