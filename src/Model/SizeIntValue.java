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
public class SizeIntValue extends IntArrayValue{

    public SizeIntValue(Value<?> array, int line) {
        super(array, line);
    }

    @Override
    public Integer value() {
        Value<?> array = super.array instanceof Variable ? ((Variable)super.array).value() : super.array;
        
        if(array instanceof ArrayValue){
            Array a = (Array)array.value();
            return a.size();
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
