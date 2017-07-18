/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;

/**
 *
 * @author Aluno
 */
public class CommandBlock extends Command {
    private ArrayList<Command> commands;

    public CommandBlock() {
        super(-1);
        this.commands = new ArrayList<Command>();
    }
    public void AddCommand(Command c){
        commands.add(c);
    }
    public void execute(){
        for(Command c: commands){
            c.execute();
        }
    }
    
}
