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
public abstract class ArrayValue extends Value{

    public ArrayValue(int line) {
        super(line);
    }
    
    public abstract Array value();
}
