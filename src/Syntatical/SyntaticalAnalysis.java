package Syntatical;

import Model.*;
import Lexical.*;
import java.io.IOException;
import java.util.HashMap;

public class SyntaticalAnalysis {
    
    private Lexeme current;
    private LexicalAnalysis lex;
    private HashMap <String, Variable> vars = new HashMap<String, Variable>();
    
   
    
    public SyntaticalAnalysis(LexicalAnalysis lex) throws IOException{
        this.lex = lex;
        this.current = lex.nextToken();
    }
    
    private void MatchToken (TokenType type) throws IOException{
        if(type == current.type){
                current = lex.nextToken();
        }
        else{ //unexpecteof e lexema nao esperado//
            if(type == TokenType.UNEXPECTED_EOF){
                errorUnexpectedEOF();
            }
            else{
                errorUnexpectedToken(current.token);
            }
        }
    }
    
    private void errorUnexpectedToken(String token){
        System.out.printf("%d: Lexema inesperado [%s]\n", lex.line(), token);        
        System.exit(0);
    }
    
    private void errorUnexpectedEOF(){
        System.out.printf("%d: %s\n", lex.line(), TokenType.UNEXPECTED_EOF);
        System.exit(0);        
    }
    
    // <load> ::= load '(' <text> ')' //
    private LoadIntValue procLoad() throws IOException{ 
        int line = lex.line();
        MatchToken(TokenType.LOAD);
        MatchToken(TokenType.PAR_OPEN);
        Value<?> v = procText();
        MatchToken(TokenType.PAR_CLOSE);
        LoadIntValue ld = new LoadIntValue(v, line);
        return ld;
    }
    
    //<if> ::= <boolexpression> '{' <statements> '}' [else '{' <statements> '}' ]
    private IfCommand procIf() throws IOException{
        int line = lex.line();
        IfCommand i;
        MatchToken(TokenType.IF);
        BoolValue bv = procBoolExpr();
        MatchToken(TokenType.CBRA_OPEN);
        Command ifc = procStatements();
        MatchToken(TokenType.CBRA_CLOSE);
        if(current.type == TokenType.ELSE){
            MatchToken(TokenType.ELSE);
            MatchToken(TokenType.CBRA_OPEN);
            Command elsec = procStatements();
            MatchToken(TokenType.CBRA_CLOSE);
            i = new IfCommand(bv, ifc, elsec, line);
        }
        else{
             i = new IfCommand(bv, ifc, line);        
        }
        return i;
    }
    
    //<while> ::= while <boolexpr> '{' <statements> '}'
    private WhileCommand procWhile() throws IOException{
        int line = lex.line();
        MatchToken(TokenType.WHILE);
        BoolValue bv = procBoolExpr();
        MatchToken(TokenType.CBRA_OPEN);
        Command c = procStatements();
        MatchToken(TokenType.CBRA_CLOSE);
        WhileCommand w = new WhileCommand(bv, c, line);
        return w;
    } 

    //<assign> ::= <expr> [ ':' <var> { ',' <var> } ] ';'
    private AssignCommand procAssign() throws IOException{
        int line = lex.line();
        Variable v;
        Value <?> iv = procExpr();
        AssignCommand ac = new AssignCommand(iv, line);
        if(current.type == TokenType.SEMI_COLON){
            MatchToken(TokenType.SEMI_COLON);
            v = procVar();
            ac.addVariable(v);
            while(current.type == TokenType.COMMA){
                MatchToken(TokenType.COMMA);
                v = procVar();
                ac.addVariable(v);
            }            
        }
        MatchToken(TokenType.DOT_COMMA);
        return ac;
    }
    
    //<print> ::= (print | println) '(' <text> ')' ';'
    private PrintCommand procPrint() throws IOException{  
        int line = lex.line();
        boolean newline = true;
       if(current.type == TokenType.PRINT){
           MatchToken(TokenType.PRINT);        
           newline = false;
       }
       else if(current.type == TokenType.PRINTLN){
           MatchToken(TokenType.PRINTLN);
       }
       else{
           showError();
       }
       MatchToken(TokenType.PAR_OPEN);
       Value<?> v = procText();
       MatchToken(TokenType.PAR_CLOSE);
       MatchToken(TokenType.DOT_COMMA); 
       PrintCommand pc = new PrintCommand(v, newline, line);
       return pc;
    }
    
    //<new> ::= new (<nzero> | <nrand> | <nfill>)
    private ArrayValue procNew() throws IOException{
        MatchToken(TokenType.NEW);
        ArrayValue av = null;
        if(current.type == TokenType.ZERO){
            av = procNZero();
        }
        else if(current.type == TokenType.RAND){
            av = procNRand();  
        } 
        else if(current.type == TokenType.FILL){
            av = procNFill();  
        }                
        else {
            showError();
        }//show error
        return av;
    }
    
    //<nzero> ::= zero '[' <expr> ']'
    private ZeroArrayValue procNZero() throws IOException{
        int line = lex.line();
        MatchToken(TokenType.ZERO);
        MatchToken(TokenType.SBRA_OPEN);
        Value<?> v = procExpr();
        MatchToken(TokenType.SBRA_CLOSE);
        ZeroArrayValue za = new ZeroArrayValue(v, line);
        return za;
    }
    
    //<nrand> ::= rand '[' <expr> ']'
    private RandArrayValue procNRand() throws IOException{
        int line = lex.line();
        MatchToken(TokenType.RAND);
        MatchToken(TokenType.SBRA_OPEN);
        Value<?> vl = procExpr();
        MatchToken(TokenType.SBRA_CLOSE);
        RandArrayValue rd = new RandArrayValue(vl,line);
        return rd;
    }
    
    //<nfill> ::= fill '[' <expr> ',' <expr> ']' 
     private FillArrayValue procNFill() throws IOException{
        int line = lex.line();
        MatchToken(TokenType.FILL);
        MatchToken(TokenType.SBRA_OPEN);
        Value<?> v1 = procExpr();
        MatchToken(TokenType.COMMA);
        Value<?> v2 = procExpr();
        MatchToken(TokenType.SBRA_CLOSE);
        FillArrayValue fa = new FillArrayValue(v1,v2,line);
        return fa;
    }
    
    //<cmd> ::= <assign> | <print> | <if> | <while> 
    private Command procCmd() throws IOException{
        Command c = null;
        if(current.type == TokenType.PLUS || current.type == TokenType.MINUS || current.type == TokenType.NUMBER 
            || current.type == TokenType.LOAD || current.type == TokenType.NEW || current.type == TokenType.VAR || current.type == TokenType.PAR_OPEN){
           
            c = (Command) procAssign();
        }
        else if(current.type == TokenType.PRINT || current.type == TokenType.PRINTLN){
           c = (Command) procPrint();
        }
        else if(current.type == TokenType.IF){
           c = (Command) procIf();
        }
        else if(current.type == TokenType.WHILE){
           c = (Command)procWhile();
        }     
        else { 
            showError();
        }//show error        
        return c;
    }
    
    //<value> ::=  (<new> | <var>) { '.' <array> } [ '.' <int> ] 
    private Value<?> procValue() throws IOException{
        Value v;
        if(current.type == TokenType.NEW){
           v = procNew();
        }
        else {
            v = procVar();
        }
        while(current.type == TokenType.DOT){
            MatchToken(TokenType.DOT);
            if(current.type == TokenType.AT || current.type == TokenType.SIZE ){
                v = procInt(v);
                break;                
            }
            else{
                v = procArray(v);
            }
        }
        return v;
    }
    
    //<array> ::= <show> | <sort> | <add> | <set> | <filter> | <remove> | <each> | <apply>
    private ArrayValue procArray(Value Array) throws IOException{
        ArrayValue av = null;
        if(current.type == TokenType.SHOW){
           av = procShow(Array);
        }
        else if(current.type == TokenType.SORT){
           av = procSort(Array);
        }
        else if(current.type == TokenType.ADD){
           av = procAdd(Array);
        }
        else if(current.type == TokenType.SET){
           av = procSet(Array);
        }
        else if(current.type == TokenType.FILTER){
           av = procFilter(Array);
        }
        else if(current.type == TokenType.REMOVE){
           av = procRemove(Array);
        }
        else if(current.type == TokenType.EACH){
           av = procEach(Array);
        }
        else if (current.type == TokenType.APPLY){
            av = procApply(Array);
        }
        else{
            showError();
        }
        return av;
    }
    
    //<show> ::= show '(' ')'
    private ShowArrayValue procShow(Value Array) throws IOException{
        int line = lex.line();
        MatchToken(TokenType.SHOW);
        MatchToken(TokenType.PAR_OPEN);        
        MatchToken(TokenType.PAR_CLOSE);
        ShowArrayValue av = new ShowArrayValue(Array, line);
        return av;
    }
    
    //<sort> ::= sort'(' ')'
    private SortArrayValue procSort(Value Array) throws IOException{
        int line = lex.line();
        MatchToken(TokenType.SORT);
        MatchToken(TokenType.PAR_OPEN);        
        MatchToken(TokenType.PAR_CLOSE);
        SortArrayValue sa = new SortArrayValue(Array, line);
        return sa;
    }
    
    //<add> ::= add '(' <expr> ')'
    private AddArrayValue procAdd(Value Array) throws IOException{
        int line = lex.line();
        MatchToken(TokenType.ADD);
        MatchToken(TokenType.PAR_OPEN);    
        Value <?> vl = procExpr();
        MatchToken(TokenType.PAR_CLOSE);
        AddArrayValue ad = new AddArrayValue(Array, vl, line);
        return ad;
    }
    
    //<set> ::= set '(' <expr> ',' <expr> ')'
    private SetArrayValue procSet(Value Array) throws IOException{
        int line = lex.line();
        MatchToken(TokenType.SET);
        MatchToken(TokenType.PAR_OPEN);    
        Value<?> v1 = procExpr();
        MatchToken(TokenType.COMMA);
        Value<?> v2 = procExpr(); 
        MatchToken(TokenType.PAR_CLOSE);
        SetArrayValue sa = new SetArrayValue(Array, v1, v2, line);
        return sa;
    }
        
    //<filter> ::= filter '(' <var> '->' <boolexpr> ')'
    private FilterArrayValue procFilter(Value Array) throws IOException{
        int line = lex.line();
        MatchToken(TokenType.FILTER);
        MatchToken(TokenType.PAR_OPEN);  
        Variable v = procVar();
        MatchToken(TokenType.ARROW);   
        BoolValue bv = procBoolExpr();
        MatchToken(TokenType.PAR_CLOSE);
        FilterArrayValue fa = new FilterArrayValue(Array, v, bv, line);
        return fa;
    }
    
    //<remove> ::= remove '(' <var> '->' <boolexpr> ')'    
     private RemoveArrayValue procRemove(Value Array) throws IOException{
        int line = lex.line();
        MatchToken(TokenType.REMOVE);
        MatchToken(TokenType.PAR_OPEN);  
        Variable v = procVar();
        MatchToken(TokenType.ARROW);   
        BoolValue boolV = procBoolExpr();
        MatchToken(TokenType.PAR_CLOSE);
        RemoveArrayValue rm = new RemoveArrayValue(Array, v, boolV, line);
        return rm;
    }    
     
    //<each> ::= each '(' <var> '->' <statements> ')'
    private EachArrayValue procEach(Value Array) throws IOException{
        int line = lex.line();
        MatchToken(TokenType.EACH);
        MatchToken(TokenType.PAR_OPEN);  
        Variable v = procVar();
        MatchToken(TokenType.ARROW);   
        Command c = procStatements();
        MatchToken(TokenType.PAR_CLOSE);
        EachArrayValue ea = new EachArrayValue(Array, v, c, line);
        return ea;
    }        
     
    //<apply> ::= apply '(' <var> '->' <statements> ')' 
    private ApplyArrayValue procApply(Value Array) throws IOException{
        int line = lex.line();
        MatchToken(TokenType.APPLY);
        MatchToken(TokenType.PAR_OPEN);  
        Variable v = procVar();
        MatchToken(TokenType.ARROW);   
        Command c = procStatements();
        MatchToken(TokenType.PAR_CLOSE);
        ApplyArrayValue aap = new ApplyArrayValue(Array, v, c, line);
        return aap;
    } 
     
    //<int> ::= <at> | <size>
    private IntValue procInt(Value array) throws IOException{
        IntValue iv = null;
        if(current.type == TokenType.AT){
           iv = procAt(array);
        }
        else if(current.type == TokenType.SIZE){
           iv = procSize(array);
        } 
        return iv;
    }
    
    //<at> ::= at '(' <expr> ')'
    private AtArrayIntValue procAt(Value array) throws IOException{
        int line = lex.line();
        MatchToken(TokenType.AT);
        MatchToken(TokenType.PAR_OPEN);  
        Value<?> expr = procExpr();
        MatchToken(TokenType.PAR_CLOSE);
        AtArrayIntValue intV = new AtArrayIntValue(array,expr,line);
        return intV;
    }   
    
    //<size> ::= size '(' ')'
    private SizeIntValue procSize(Value array) throws IOException{
        int line = lex.line();
        MatchToken(TokenType.SIZE);
        MatchToken(TokenType.PAR_OPEN);  
        MatchToken(TokenType.PAR_CLOSE);
        SizeIntValue size = new SizeIntValue(array,line);
        return size;
    }   
    
    //<boolexpr> ::= <expr> <boolop> <expr> { (and | or) <boolexpr> }
    private BoolValue procBoolExpr() throws IOException{
        int line = lex.line();
        Value<?> v1 = procExpr();
        RelOp op = procBoolOp();
        Value<?> v2 = procExpr();
        CompareBoolValue bv = new CompareBoolValue(op, v1, v2, line);
        while (current.type == TokenType.AND || current.type == TokenType.OR){
            BoolOp boolOp;
            if(current.type == TokenType.AND ){
                MatchToken(TokenType.AND);
                boolOp = BoolOp.AND;
            }
            else{
                MatchToken(TokenType.OR);
                boolOp = BoolOp.OR;
            }            
            BoolValue b1 = procBoolExpr();
            DualBoolExpr db = new DualBoolExpr(boolOp,bv,b1,line);
            return db;
        } 
        return bv;
    }
    
    //<boolop> ::= '==' | '!=' | '<' | '>' | '<=' | '>='
    private RelOp procBoolOp() throws IOException{
        RelOp op = null;
        switch (current.type) {
            case EQUAL:     MatchToken(TokenType.EQUAL);    op = RelOp.EQUAL;
                break;
            case DIFF:      MatchToken(TokenType.DIFF);     op = RelOp.NOT_EQUAL;
                break;
            case LOWER:     MatchToken(TokenType.LOWER);    op = RelOp.LOWER_THAN;
                break;
            case HIGHER:    MatchToken(TokenType.HIGHER);   op = RelOp.GREATER_THAN;
                break;
            case LOWER_EQ:  MatchToken(TokenType.LOWER_EQ); op = RelOp.LOWER_EQUAL;
                break;
            case HIGHER_EQ: MatchToken(TokenType.HIGHER_EQ);op = RelOp.GREATER_EQUAL;
                break;
            default:   showError();
                break;
        }
        return op;
    }
      
    //<expr> ::= <term> [ ('+' | '-') <term> ]
    private Value<?> procExpr() throws IOException{
        Value<?> v1 = procTerm();
        Value<?> v2 = null;  
        int line = lex.line();
        if(current.type == TokenType.PLUS || current.type == TokenType.MINUS){
            IntOp op = null;
            if(current.type == TokenType.PLUS){
                MatchToken(TokenType.PLUS);
                op = IntOp.ADD;
            }
            else if(current.type == TokenType.MINUS){
                MatchToken(TokenType.MINUS);
                op = IntOp.SUB;
            }
            v2 = procTerm();
            DualIntExpression d = new DualIntExpression(op,v1,v2,line);
            return d;
        }
        return v1;
    }
    
    //<term> ::= <factor> [ ('*' | '/' | '%') <factor> ]
    private Value<?> procTerm() throws IOException{
        int line = lex.line();
        Value <?> iv = procFactor();
        if(current.type == TokenType.MUL || current.type == TokenType.DIV || current.type == TokenType.MOD){
            DualIntExpression d = null;
            Value <?> iv1 = null;
            IntOp op = null;
            if(current.type == TokenType.MUL){ 
                MatchToken(TokenType.MUL);
                op = IntOp.MUL;
                iv1 = procFactor();
                d = new DualIntExpression(op, iv, iv1, line);
            }
            else if(current.type == TokenType.DIV){ 
                MatchToken(TokenType.DIV);
                op = IntOp.DIV;
                iv1 = procFactor();
                d = new DualIntExpression(op, iv, iv1, line);
            }
            else if(current.type == TokenType.MOD){ 
                MatchToken(TokenType.MOD);
                op = IntOp.MOD;
                iv1 = procFactor();
                d = new DualIntExpression(op, iv, iv1, line);
            }
            return d;
        }
        return iv;
    }
    
    //<factor> ::= [‘+’ | ‘-‘] <number> | <load> | <value> | '(' <expr> ')'
    private Value<?> procFactor() throws IOException{
        Value<?> iv = null;
        if(current.type == TokenType.PLUS || current.type == TokenType.MINUS){
            if(current.type == TokenType.PLUS){
                MatchToken(TokenType.PLUS);   
                iv = procNumber();
            }
            else if(current.type == TokenType.MINUS){
                MatchToken(TokenType.MINUS);
                iv = procNumber();
            }
        }
        else if(current.type == TokenType.NUMBER){
           iv = procNumber();
        }
        else if(current.type == TokenType.LOAD){
            iv = procLoad();
        }       
        else if(current.type == TokenType.NEW || current.type == TokenType.VAR ){
            iv = procValue();
        }        
        else if(current.type == TokenType.PAR_OPEN){              
            MatchToken(TokenType.PAR_OPEN);            
            iv = procExpr();
            MatchToken(TokenType.PAR_CLOSE);   
        }
        else showError();
        return iv;
    }
    
    //<text> ::= (<string> | <expr>) { ‘,’ (<string> | <expr>) }
    private Value<?> procText() throws IOException{     
        int line = lex.line();
        Value<?> v = null;
        StringConcat sc = null;
        if(current.type == TokenType.STRING){
            v = procString();     
        }
        else if (current.type == TokenType.PLUS || current.type == TokenType.MINUS || current.type == TokenType.VAR
                || current.type == TokenType.NUMBER || current.type == TokenType.VAR){
            v = procExpr();
        }
        else{
            showError();
        }
        while(current.type == TokenType.COMMA){ 
            MatchToken(TokenType.COMMA);
            Value<?> v1 = procText();
            sc = new StringConcat(v,v1,line);
            return sc;
        }
        
        return v;
    }
    
    private Variable procVar() throws IOException{
        String n = current.token;
        MatchToken(TokenType.VAR);
        Variable v;     
        if(vars.containsKey(n)){
            v = vars.get(n);
        }
        else{
            v = new Variable(n);
            vars.put(n, v);
        }
        return v;
    }
    
    private ConstStringValue procString() throws IOException{        
        int line = lex.line();
        String text = current.token;
        MatchToken(TokenType.STRING);  
        ConstStringValue csv = new ConstStringValue(text,line);
        return csv;
    }
    
    private ConstIntValue procNumber() throws IOException{
        ConstIntValue civ;
        int line = lex.line();
        String text = current.token;
        MatchToken(TokenType.NUMBER);  
        int value = Integer.parseInt(text);
        civ = new ConstIntValue(value,  line);
        return civ;
    }
    
    //<statements> ::= <cmd> { <cmd> }
    private Command procStatements() throws IOException{
        CommandBlock cb = new CommandBlock();
        Command c;
       
        while(current.type == TokenType.PLUS || current.type == TokenType.MINUS || current.type == TokenType.NUMBER || current.type == TokenType.LOAD || current.type == TokenType.NEW || current.type == TokenType.VAR || current.type == TokenType.PAR_OPEN || current.type == TokenType.PRINT || current.type == TokenType.PRINTLN || current.type == TokenType.IF || current.type == TokenType.WHILE){
            c = procCmd();
            cb.AddCommand(c);
        }    
        return cb;
    }
    
    private void showError() {
        if (this.current.type == TokenType.UNEXPECTED_EOF || this.current.type == TokenType.END_OF_FILE)
            this.errorUnexpectedEOF();
        else
            this.errorUnexpectedToken (this.current.token);
    }
    
    public Command init() throws IOException{
        Command c = procStatements();
        MatchToken(TokenType.END_OF_FILE);        
        return c;
    }
}
