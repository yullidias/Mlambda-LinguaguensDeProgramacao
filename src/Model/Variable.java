/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;


 public class Variable extends Value< Value<?> >{
    private String name;
    private Value<?> value;
    
    public Variable(String nome){
        super(-1);
        this.name = nome;
        this.value = null;
    }

    public void setValue(Value<?> v){
        this.value = v;
    }
    
    public String getName() {
        return this.name;
    }

    public Value<?> getValue() {
        return value;
    }
    
    public Value<?> value() {
        return this.value;
    }

}
