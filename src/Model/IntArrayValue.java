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
public abstract class IntArrayValue extends IntValue{
    protected Value<?> array;

    public IntArrayValue(Value<?> array, int line) {
        super(line);
        this.array = array;
    }

    @Override
    public abstract Integer value();
    
    
}
