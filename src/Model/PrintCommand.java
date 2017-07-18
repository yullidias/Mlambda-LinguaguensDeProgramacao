/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Aluno
 */
public class PrintCommand extends Command {
    private Value<?> value;
    private boolean newline;

    public Value<?> getValue() {
        return value;
    }

    public void setValue(Value<?> value) {
        this.value = value;
    }

    public boolean isNewline() {
        return newline;
    }

    public void setNewline(boolean newline) {
        this.newline = newline;
    }

    public PrintCommand(Value<?> value, boolean newline, int line) {
        super(line);
        this.value = value;
        this.newline = newline;
    }
    
    public void execute(){
        String text = "";
        Value<?> value = (this.value instanceof  Variable) ? ( (Variable) this.value ).value(): (this.value);
        if(value instanceof StringValue){
            StringValue sr = (StringValue)value;
            text += sr.value();
        }
        else if(value instanceof IntValue){
            IntValue iv = (IntValue)value;
            text += String.valueOf(iv.value());
        }
        else if(value instanceof Variable){
            Variable vb = (Variable) value;
            text += String.valueOf(vb.getValue());
        }
        else {
            if(value == null)
                System.err.println(super.getLine()+": Variable not found [ "+((Variable)this.value).getName()+ " ]");
            else
                System.err.println(super.getLine()+": Invalid type [ " + value.getClass().getSimpleName() + " ]");
            System.exit(0);
        }
        System.out.print(text);
        if(newline){
            System.out.println();
        }
        
    }
}
