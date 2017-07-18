/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author aluno
 */
 public class ConstIntValue extends IntValue{
    int value;

    public ConstIntValue(int value, int line) {
        super(line);
        this.value = value;
    }
    
    public int getValue(){
        return value;
    }

    public Integer value(){
        return value;
    }
}
