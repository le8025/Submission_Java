public class Assignment2 {

    String[] globStringArr = {"hello", "this", "is a", "string", "array"};
    long[] globLongArr = {102L, 250L, 333L};

    public static void main(String[] args){

        //Task 2
        Assignment2 assignment2 = new Assignment2();

        System.out.println("String Array: ");
        for (String stringEle: assignment2.globStringArr){
            System.out.println(stringEle);
        }

        System.out.println();
        System.out.println("Long Array: ");
        for (Long longEle: assignment2.globLongArr){
            System.out.println(longEle);
        }
    }
}
