/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Aluno
 */
public class DualIntExpression extends IntValue{
    IntOp op;
    Value <?> left;
    Value <?> right;

    public DualIntExpression(IntOp op, Value<?> left, Value<?> right, int line) {
        super(line);
        this.op = op;
        this.left = left;
        this.right = right;
    }
    
    public Integer value(){
        Value<?> lt = (this.left instanceof Variable) ? ((Variable) this.left).value() : this.left;
        Value<?> rt = (this.right instanceof Variable) ? ((Variable) this.right).value() : this.right;    
        if(lt instanceof IntValue){
            if(rt instanceof IntValue){
                int opLeft = (int)((IntValue) lt).value();
                int opRight = (int)((IntValue) rt).value();
                switch(this.op){
                    case ADD: return opLeft + opRight;                    
                    case SUB: return opLeft - opRight;              
                    case MUL: return opLeft * opRight;
                    case DIV:   if(opRight == 0){
                                    System.err.println(super.getLine()+": Division by zero");
                                    System.exit(0);
                                }
                                else
                                    return opLeft / opRight;

                    case MOD:   if(opRight == 0){
                                    System.err.println(super.getLine()+": Division by zero");
                                    System.exit(0);
                                }
                                else 
                                    return opLeft % opRight;
                } 
            }
            else{
                if(rt == null)
                    System.err.println(super.getLine()+": Variable not found [ "+((Variable)this.right).getName()+ " ]");
                else    
                    System.err.println(super.getLine()+": Invalid type [ " + rt.getClass().getSimpleName() + " ]");
                System.exit(0);
            }
        }
        else{
            if(lt == null)
                System.err.println(super.getLine()+": Variable not found [ "+((Variable)this.left).getName()+ " ]");
            else    
                System.err.println(super.getLine()+": Invalid type [ " + lt.getClass().getSimpleName() + " ]");
            System.exit(0);
        }
        return null;
    }
}
