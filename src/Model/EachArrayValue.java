package Model;
public class EachArrayValue extends ArrayValue{
    private Value<?> array;
    private Variable var;
    private Command cmd;

    public EachArrayValue(Value<?> array, Variable var, Command cmd, int line) {
        super(line);
        this.array = array;
        this.var = var;
        this.cmd = cmd;
    }

    @Override
    public Array value() {
        Value<?> array = (this.array instanceof Variable) ? ((Variable)this.array).value() : this.array;
        if(array instanceof ArrayValue){
                Array atual = (Array) array.value();
                ConstIntValue ci;
                for(int i=0; i<atual.size(); i++){
                    ci = new ConstIntValue(atual.At(i),-1);
                    var.setValue(ci);
                    if(var.value() instanceof IntValue){
                        atual.set(i, ((ConstIntValue)var.value()).value);
                    }
                    else{
                        if(var.value() == null)
                            System.err.println(super.getLine()+": Variable not found [ "+(this.var).getName()+ " ]");
                        else
                            System.err.println(super.getLine()+": Invalid type [ " + var.value().getClass().getSimpleName() + " ]");
                        System.exit(0);
                    }
                    cmd.execute();
                }
                return atual;
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
