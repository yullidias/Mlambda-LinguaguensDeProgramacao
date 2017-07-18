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
public class RemoveArrayValue extends ArrayValue{
    private Value<?> array;
    private Variable var;
    private BoolValue cond;

    public RemoveArrayValue(Value<?> array, Variable var, BoolValue cond, int line) {
        super(line);
        this.array = array;
        this.var = var;
        this.cond = cond;
    }

    @Override
    public Array value() {
        Value<?> array = (this.array instanceof Variable) ? ((Variable)this.array).value() : this.array;

        if(array instanceof ArrayValue){
                Array atual = (Array) array.value();
                Array newArray = new Array(0);
                ConstIntValue ci;
                for(int i=0; i<atual.size(); i++){
                    ci = new ConstIntValue(atual.At(i),-1);
                    var.setValue(ci);
                    if(!(boolean)cond.value())
                        newArray = newArray.add(atual.At(i));
                }
                return newArray;
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

