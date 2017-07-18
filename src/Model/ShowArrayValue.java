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
public class ShowArrayValue extends ArrayValue{
    Value<?> array;

    public ShowArrayValue(Value<?> array, int line) {
        super(line);
        this.array = array;
    }

    @Override
    public Array value() {
        Value array = this.array instanceof Variable ? ((Variable)this.array).value(): this.array;
        if(array instanceof ArrayValue){
            ((Array)array.value()).show();
            return (Array)array.value();
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
