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
public class IfCommand extends Command{
    private BoolValue expr;
    private Command Then;
    private Command Else;

    public IfCommand(BoolValue expr, Command Then, int line) {
        super(line);
        this.expr = expr;
        this.Then = Then;
    }

     public IfCommand(BoolValue expr, Command Then, Command Else, int line) {
        super(line);
        this.expr = expr;
        this.Then = Then;
        this.Else = Else;
    }
    public void execute(){
        if((boolean)this.expr.value()){
            this.Then.execute();
        }
        else{
            this.Else.execute();
        }
    }
}
