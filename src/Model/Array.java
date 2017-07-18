/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Arrays;

/**
 *
 * @author Aluno
 */
public class Array {
    int size;
    int array[];

    public Array(int size) {
        this.size = size;
        this.array = new int[size];
    }
    
    public int At(int index){
        if(index >= 0 && index < size)
            return array[index];
        else{
            System.err.println("Segmentation fault" );
            System.exit(0); 
            return -1;
        }
    }
    
    public int size(){  
        return size;
    }
    
    public void show(){
        System.out.print("[");
        for(int i=0; i<size; i++){
            if(i == 0)
                System.out.printf("%3d",array[i]);
            else
                System.out.printf(" ,%3d",array[i]);
        }
        System.out.println("]");
    } 
    
    public void set(int index, int value){
        if(index >= 0 && index < size)
            array[index] = value;
        else{
            System.err.println("Segmentation fault" );
            System.exit(0);
        }
    }
            
    public Array sort(){
        Array newArray = new Array(size);
        for(int i=0; i<size; i++){
            newArray.array[i] = this.array[i];
        }
        Arrays.sort(newArray.array);
        return newArray;
    }
    
    public Array add(int value){
        Array newArray = new Array(size+1);
        for(int i=0; i<size; i++){
            newArray.array[i] = this.array[i];
        }
        newArray.set(size,value);
        return newArray;
    }
    
    public Array add(Array array){
        Array newArray = new Array(size + array.size());
        for(int i=0; i< size; i++){ //copia primeiro vetor
            newArray.array[i] = this.array[i];
        }
        for(int i=0; i<array.size(); i++){ //copia vetor passado por paramentro
            newArray.array[i+size] = array.array[i];
        }
        return newArray;
    }
    
    public String toString(){
        return Arrays.toString(array);
    }
}
