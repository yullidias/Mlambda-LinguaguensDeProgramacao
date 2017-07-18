/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Scanner;

/**
 *
 * @author aluno
 */
public class LoadIntValue extends IntValue{
    private Value<?> text;

    public LoadIntValue(Value<?> text, int line) {
        super(line);
        this.text = text;
    }
    
    public Integer value(){
        String text= null;

        Value<?> val = (this.text instanceof Variable) ? ((Variable)this.text).value(): (this.text);
      
        if(val instanceof StringValue){
            StringValue sv = (StringValue)val;
            text = sv.value();
        }
        else if (val instanceof IntValue){
            IntValue iv = (IntValue)val;
            text = "" + iv.value();
        }
        else if (val instanceof ArrayValue){ 
            ArrayValue av = (ArrayValue)val;
            text = av.value().toString();
        }
        System.out.print(text);
        Scanner s = new Scanner(System.in);
        int i = s.nextInt();
        return i;
    }
}

