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
public abstract class IntValue extends Value{

    public IntValue(int line) {
        super(line);
    }

    public abstract Integer value();
        

}
