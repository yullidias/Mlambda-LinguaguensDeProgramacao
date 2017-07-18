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
public class StringConcat extends StringValue{
    private Value<?> left;
    private Value<?> right;

    public StringConcat(Value<?> left, Value<?> right, int line) {
        super(line);
        this.left = left;
        this.right = right;
    }

    @Override
    public String value() {
        Value<?> left = (this.left instanceof Variable) ? ((Variable) this.left).value() : this.left;
        Value<?> right = (this.right instanceof Variable) ? ((Variable) this.right).value() : this.right;
        
        return String.valueOf(left.value()) + String.valueOf(right.value());
    }

    
    

}
