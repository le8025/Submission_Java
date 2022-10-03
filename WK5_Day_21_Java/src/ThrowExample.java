import javax.swing.*;

public class ThrowExample {

    static String[] testArr = {"hi", "this is", "an array", "of", "strings"};

    public static void main (String[] args){
        checkIdx(0); //in array
        checkIdx(7); //not in array
        checkIdx(3); //in array

    }
    public static void checkIdx (int index){

        try{
            if (index >= testArr.length){
                throw new CustomException("Index does not exist in array");
            }else{
                System.out.println("Position " + index
                + "| Element: " + testArr[index]);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
