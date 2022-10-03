import java.util.Scanner;

public class ChildClass extends AbstClass implements Interface1, Interface2 {

    @Override //from abstract class
    protected int abstAdd(int num1, int num2) {
        return num1 + num2;
    }

    @Override //from interface1
    public int intMulti(int num1, int num2){
        return num1 * num2;
    }

    @Override //from interface2
    public int intMod(int num1, int num2){
        return num1 / num2;
    }


    public void childPrint(){
        System.out.println("This line is only available in Child Class (not inherited)");
    }

    public static void main(String[] args){

//        AbstClass childAbs = new ChildClass();
//        Interface1 childIntr1 = new ChildClass();
//        Interface2 childIntr2 = new ChildClass();

        ChildClass child = new ChildClass();

        child.childPrint();
        System.out.println("abstAdd: " + child.abstAdd(4, 5));
        System.out.println("intMulti: " + child.intMulti(4, 5));
        System.out.println("intMod: " + child.intMod(5, 2));

    }
}
