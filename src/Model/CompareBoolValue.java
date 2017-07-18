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
public class CompareBoolValue extends BoolValue{
    private RelOp op;
    private Value<?> left;
    private Value<?> right;

    public CompareBoolValue(RelOp op, Value<?> left, Value<?> right, int line) {
        super(line);
        this.op = op;
        this.left = left;
        this.right = right;
    }

    @Override
    public Object value() {
        Value<?> left = (this.left instanceof Variable) ? ((Variable)this.left).value(): this.left;
        Value<?> right = (this.right instanceof Variable) ? ((Variable)this.right).value() : this.right;
        if(left instanceof IntValue){
            if( right instanceof IntValue){
                switch(this.op){
                    case EQUAL:         return ((IntValue)left).value() == ((IntValue)right).value();
                    case NOT_EQUAL:     return ((IntValue)left).value() != ((IntValue)right).value();
                    case GREATER_EQUAL: return ((IntValue)left).value() >= ((IntValue)right).value();
                    case GREATER_THAN:  return ((IntValue)left).value() >  ((IntValue)right).value();
                    case LOWER_THAN:    return ((IntValue)left).value() <  ((IntValue)right).value();
                    case LOWER_EQUAL:   return ((IntValue)left).value() <= ((IntValue)right).value();
                }
            }
            else{
                if(right == null)
                    System.err.println(super.getLine()+": Variable not found [ "+((Variable)this.right).getName()+ " ]");
                else    
                    System.err.println(super.getLine()+": Invalid type [ " + right.getClass().getSimpleName() + " ]");
                System.exit(0);    
            }
        }
        else{
            if(left == null)
                System.err.println(super.getLine()+": Variable not found [ "+((Variable)this.left).getName()+ " ]");
            else    
                System.err.println(super.getLine()+": Invalid type [ " + left.getClass().getSimpleName() + " ]");
            System.exit(0);
        }
        return null;
    }
}
    
