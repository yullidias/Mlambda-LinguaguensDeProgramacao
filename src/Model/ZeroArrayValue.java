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
public class ZeroArrayValue extends ArrayValue{
    private Value<?> size;

    public ZeroArrayValue(Value<?> size, int line) {
        super(line);
        this.size = size;
    }
    
    @Override
    public Array value() {
        Value<?> size = (this.size instanceof Variable) ? ((Variable)this.size).value() : this.size;
        if(size instanceof IntValue ){
           int newSize = ((IntValue) size).value();
           Array newArray = new Array(newSize);
           for(int i=0; i< newSize; i++){
               newArray.set(i,0);
           }
           return newArray;
        }
        else{
            if(size == null)
                System.err.println(super.getLine()+": Variable not found [ "+((Variable)this.size).getName()+ " ]");
            else
                System.err.println(super.getLine()+": Invalid type [ " + size.getClass().getSimpleName() + " ]");
            System.exit(0);
        }
        return null;
    }
}
