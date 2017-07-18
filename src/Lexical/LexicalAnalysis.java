package Lexical;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;

public class LexicalAnalysis implements AutoCloseable {

    private int line;
    private int estado, c;
    private PushbackInputStream input;
    private SymbolTable st = new SymbolTable();

    public LexicalAnalysis(String filename) throws LexicalException {
        try {
            input = new PushbackInputStream(new FileInputStream(filename));
        } catch (Exception e) {
            throw new LexicalException("Unable to open file");
        }

        line = 1;
    }

    public void close() throws Exception {
        input.close();
    }

    public int line() {
        return this.line;
    }

    public Lexeme nextToken() throws IOException {
         Lexeme lex = new Lexeme("", TokenType.END_OF_FILE);
         estado = 1;
         while(estado != 9 && estado != 10){
            c = input.read();
            switch(estado){
                case 1:
                    if(c == -1){
                        estado = 10;
                    }
                    else if( c == ' ' || c == '\t' || c == '\r' || c == '\n'){
                        if(c == '\n')
                            line++;
                        estado = 1;
                    }
                    else if(c == '#'){
                        estado = 2;
                    }
                    else if(Character.isDigit(c)){
                        lex.token += (char) c;
                        estado = 3;
                    }
                    else if(c == '!'){
                        lex.token += (char) c;
                        estado = 4;
                    }
                    else if(c == '='){
                        lex.token += (char) c;
                        estado = 4;
                        
                    }
                    else if( c == '<' || c == '>'){
                        lex.token += (char) c;
                        estado = 5;
                    }
                    else if(c == '-'){
                        lex.token += (char) c;
                        estado = 6;
                    }
                    else if(Character.isLetter(c)){
                        lex.token += (char) c;
                        estado = 7;
                    }
                    else if(c == '\"'){
                        estado = 8;
                    }
                    else if(c == ';' || c == ':' || c == '.' || c == ',' || c == '(' || c == ')' || c == '{' || c == '}' 
                              || c == '[' || c == ']' || c == '+' || c == '*' || c == '/' || c == '%'){
                        lex.token += (char) c;
                        estado = 9;
                    }
                    else{
                        lex.type = TokenType.INVALID_TOKEN;
                        lex.token += (char) c;
                        estado = 10;
                    }
                break;
                case 2:
                    if( c == '\n'){
                        line++;
                        estado = 1;
                    }
                break;
                case 3:
                    if(Character.isDigit(c)){
                        lex.token += (char) c;
                    }
                    else{
                        if(c != -1){ 
                            input.unread(c);
                        }
                        lex.type = TokenType.NUMBER;
                        estado = 10;
                    }
                break;
                case 4:
                    
                    if(c == '='){
                        lex.token += (char) c;
                        estado = 9;
                    }
                    else{
                        
                        if(c == -1){
                            lex.type = TokenType.UNEXPECTED_EOF;
                        }
                        else{
                            lex.type = TokenType.INVALID_TOKEN;
                        }
                        estado = 10;
                    }
                break;
                case 5:
                    if( c == -1){
                        lex.type = TokenType.UNEXPECTED_EOF;
                        estado = 10;
                    }
                    else{
                        if(c == '='){
                            lex.token += (char) c;
                        }
                        else{
                            input.unread(c);
                        }
                        estado = 9;
                    }
                break;
                case 6:
                    if (c == -1){
                        lex.type = TokenType.UNEXPECTED_EOF;
                        estado = 10;
                    }
                    else{
                        if(c == '>'){
                            lex.token += (char)c;
                        }
                        else{
                            input.unread(c);
                        }
                        estado = 9;
                    }
                break;
                case 7:
                    if(Character.isDigit(c) || Character.isLetter(c)){
                        lex.token += (char) c;
                    }
                    else{
                        if(c != -1){
                            input.unread(c);  
                            estado = 9;
                        }
                        else{
                            lex.type = TokenType.UNEXPECTED_EOF;
                            estado = 10;
                        }
                    }
                break;
                case 8:
                    if(c == '\"'){
                        estado = 10;
                        lex.type = TokenType.STRING;
                    }
                    else{
                        if(c == -1){
                            lex.type = TokenType.UNEXPECTED_EOF;
                            estado = 10;
                        }
                        else{
                            lex.token += (char) c;
                        }
                    }
                break;
            }
         }
         if(estado == 9){
             if(st.contains(lex.token)){
                 lex.type = st.find(lex.token);
             }
             else{
                 lex.type = TokenType.VAR;
             }
         }
         return lex;
    }
}
