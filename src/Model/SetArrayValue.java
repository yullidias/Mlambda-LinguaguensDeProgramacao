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
public class SetArrayValue extends ArrayValue{
    private Value<?> array;
    private Value<?> index;
    private Value<?> value;

    public SetArrayValue(Value<?> array, Value<?> index, Value<?> value, int line) {
        super(line);
        this.array = array;
        this.index = index;
        this.value = value;
    }

    @Override
    public Array value() {
        Value<?> array = (this.array instanceof Variable) ? ((Variable)this.array).value() : this.array;
        Value<?> index = (this.index instanceof Variable) ? ((Variable)this.index).value() : this.index;
        Value<?> value = (this.value instanceof Variable) ? ((Variable)this.value).value() : this.value;
        
        if( array instanceof ArrayValue){
            if( index instanceof IntValue){
                if( value instanceof IntValue){
                    int newIndex = ((IntValue) index).value();
                    int newValue = ((IntValue) value).value();
                    Array newArray = (Array)array.value();
                    newArray.set(newIndex, newValue);
                    return newArray;
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
                if(index == null)
                    System.err.println(super.getLine()+": Variable not found [ "+((Variable)this.index).getName()+ " ]");
                else
                    System.err.println(super.getLine()+": Invalid type [ " + index.getClass().getSimpleName() + " ]");
                System.exit(0); 
            }
        }
        else{
            if(array == null)
                System.err.println(super.getLine()+": Variable not found [ "+((Variable)this.array).getName()+ " ]");
            else
                System.out.println(super.getLine() +": Invalid type [ " + array.getClass().getSimpleName() + " ]");
            System.exit(0); 
        }
        return null;
    }
}
