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
public class AtArrayIntValue extends IntArrayValue{
    Value<?> index;

    public AtArrayIntValue(Value<?> array, Value<?> index, int line) {
        super(array, line);
        this.index = index;
    }

    @Override
    public Integer value() {
        Value<?> index = (this.index instanceof Variable) ? ((Variable)this.index).value() : this.index;
        Value<?> arraySuper = (super.array instanceof Variable) ?((Variable)super.array).value() : super.array;
        
        if (index instanceof IntValue){
            int iv = ((IntValue) index).value();
            if(arraySuper instanceof ArrayValue){
                Array array = ((ArrayValue)arraySuper).value();
                return array.At(iv);
            }
            else{
                if(arraySuper == null)
                    System.err.println(super.getLine()+": Variable not found [ "+((Variable)super.array).getName()+ " ]");
                else
                    System.err.println(super.getLine()+": Invalid type [ " + arraySuper.getClass().getSimpleName() + " ]");
                System.exit(0);
            }
        }
        else{
            if(index == null)
                System.err.println(super.getLine()+": Variable not found [ "+((Variable)this.index).getName()+ " ]");
            else
                System.err.println(super.getLine()+": Invalid type [ "  + index.getClass() +  " ]");
            System.exit(0);
        } 
        return null;
    }
    
}
