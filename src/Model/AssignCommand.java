package Model;

import java.util.ArrayList;
import java.util.List;

public class AssignCommand extends Command {

  private List<Variable> vars;
  private Value<?> value;

  public AssignCommand(Value<?> value, int line) {
    super(line);
    this.vars = new ArrayList<Variable>();
    this.value = value;
  }

  public void addVariable(Variable v) {
    this.vars.add(v);
  }

    public void execute() {
        Value<?> val = (this.value instanceof Variable) ? (((Variable) this.value).value()) : (this.value);        
        Value<?> newValue = null;
        
        if (val instanceof IntValue) {
            IntValue iv = (IntValue) val;
            newValue = new ConstIntValue(iv.value(), -1);
        } 
        else if (val instanceof StringValue) {
            StringValue sv = (StringValue) val;
            
            newValue = new ConstStringValue(sv.value(), -1);
        }
        else if (val instanceof ArrayValue) {
            ArrayValue av = (ArrayValue) val;
            newValue = new ConstArrayValue(av.value(), -1);
        }
        else {
          // Impossível pela gramática.
        }
        for (Variable v : this.vars) {
          v.setValue(newValue);
        }
    }
}