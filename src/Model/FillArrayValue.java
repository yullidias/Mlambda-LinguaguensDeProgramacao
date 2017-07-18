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
public class FillArrayValue extends ArrayValue{
    private Value<?> size;
    private Value<?> value;

    public FillArrayValue(Value<?> size, Value<?> value, int line) {
        super(line);
        this.size = size;
        this.value = value;
    }

    @Override
    public Array value() {
        Value<?> size = (this.size instanceof Variable) ? ((Variable)this.size).value() : this.size;
        Value<?> value = (this.value instanceof Variable) ? ((Variable)this.value).value() : this.value;
        
        if(size instanceof IntValue ){
            if(value instanceof IntValue){
                int newSize = (Integer) size.value();
                int newValue = (Integer) value.value();
                Array array = new Array(newSize);
                for(int i=0; i< newSize; i++){
                    array.set(i, newValue);
                }
                return array;
            }
            else{
                if(value == null)
                    System.err.println(super.getLine()+": Variable not found [ "+((Variable)this.value).getName()+ " ]");
                else
                    System.err.println(super.getLine()+": Invalid type [ " + value.getClass().getSimpleName() + " ]");
                System.exit(0);
            }
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
