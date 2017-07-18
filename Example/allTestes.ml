5 : a;
2: b;
new zero [a] : array;
println(array.size());
array.show();
array.set(b,a);
array.show();
new rand [a] : array2;

println(array2.show().at(b));
array2.sort().show();
array2.show();
new fill[a+b, b%a] : array3;
array3.show();

array2.add(array).add(array3): ar;
ar.show();

7:y;
array.each( y -> 3: i; array.show();); # para cada elemento do array, associar y e executar os cmds
println(i);
println("Antigo");
array2.show();
println("new");
1:y;
println("remove");
array2.remove( y -> y % 2  == 0).show();
array2.show();
println("filter");
array2.filter( y -> y % 2  == 0).show();
array2.show();
println("");
println("Apply");
array2.apply( y -> i:y ;).show();
array2.show();
println("FimApply");
ar.apply( y -> 10: i;);
println(i);


2:n;
3:x;
4:j;

println("while");
if x < j{
    while n != 4 and n != 5{
        n + 1: n;
        println(n);
    }
}
new fill[11,2] :array;
array.show();

#teste falha de segmentação
array.set(11,3);
array.show();