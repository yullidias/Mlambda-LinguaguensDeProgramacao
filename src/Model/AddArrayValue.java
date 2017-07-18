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
public class AddArrayValue extends ArrayValue{
    private Value<?> array;
    private Value<?> value;

    public AddArrayValue(Value<?> array, Value<?> value, int line) {
        super(line);
        this.array = array;
        this.value = value;
    }

    @Override
    public Array value() {
        Value<?> array = (this.array instanceof Variable) ? ((Variable)this.array).value() : this.array;
        Value<?> value  = (this.value instanceof Variable) ? ((Variable)this.value).value() : this.value;
        if(array instanceof ArrayValue){
            Array antigoArray = ((ArrayValue)array).value();
            if(value instanceof IntValue){
                int newValue = ((IntValue)value).value();
                return antigoArray.add(newValue);
            }
            else if(value instanceof ArrayValue){
                Array newArray = (Array)value.value();
                return antigoArray.add(newArray);
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
            if(array == null)
                System.err.println(super.getLine()+": Variable not found [ "+((Variable)this.array).getName()+ " ]");
            else
                System.err.println(super.getLine()+": Invalid type [ " + array.getClass().getSimpleName() + " ]");
            System.exit(0);
        }
        
        return null;
    }
    
    
}
