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
public class ApplyArrayValue extends ArrayValue{
    private Value<?> array;
    private Variable var;
    private Command cmd;

    public ApplyArrayValue(Value<?> array, Variable var, Command cmd, int line) {
        super(line);
        this.array = array;
        this.var = var;
        this.cmd = cmd;
    }

    @Override
    public Array value() {
        Value<?> array = this.array instanceof Variable ? ((Variable)this.array).value(): this.array;
        if(array instanceof ArrayValue){
            Array antigo = ((ArrayValue) array).value();
                ConstIntValue ci;
                for(int i=0; i< antigo.size(); i++){
                    ci = new ConstIntValue(antigo.At(i),-1);
                    var.setValue(ci);
                    cmd.execute();
                    if(var.value() instanceof IntValue){
                        antigo.set(i, ((IntValue)var.value()).value());
                    }
                    else{
                        if(var == null)
                            System.err.println(super.getLine()+": Variable not found [ "+(this.var).getName()+ " ]");
                        else
                            System.err.println(super.getLine()+": Invalid type [ " + var.getClass().getSimpleName() + " ]");
                        System.exit(0);     
                    }
                    
                }
                return antigo;
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

