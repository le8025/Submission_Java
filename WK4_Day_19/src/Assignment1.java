public class Assignment1 {
    int globInt = 43;

    public static void main(String[] args){

        //Task 1
        Assignment1 assignment1 = new Assignment1();
        System.out.println("Global int is " + assignment1.globInt);
        if (assignment1.globInt > 10){
            System.out.println(assignment1.globInt + " is greater than 10.");
        }else{
            System.out.println(assignment1.globInt + " is smaller than 10.");
        }
    }
}
