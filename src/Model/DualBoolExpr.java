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
public class DualBoolExpr extends BoolValue{
    private BoolOp op;
    private BoolValue left;
    private BoolValue right;

    public DualBoolExpr(BoolOp op, BoolValue left, BoolValue right, int line) {
        super(line);
        this.op = op;
        this.left = left;
        this.right = right;
    }

    @Override
    public Object value() {
        switch(this.op){
            case AND: return ((boolean)this.left.value() && (boolean)this.right.value() );
            case OR: return ((boolean)this.left.value() || (boolean)this.right.value());
            default: return null;
        }
    }
    
  
}
