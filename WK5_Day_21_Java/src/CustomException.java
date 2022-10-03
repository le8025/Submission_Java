public class CustomException extends Exception{

    CustomException(String message){
        super(message);
        System.out.println(message);
    }
}

