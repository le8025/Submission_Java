import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Assignment {

    public static void main (String[] args){

        //date time formatter
        String format = "mm-dd-yyy HH:mm:ss.SSS";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);

        ArrayList<User> userList = new ArrayList<>();
        userList.add(new User(1,"Voidskate", "Chaos LV"));
        userList.add(new User(2, "Chlam", "ELysium MMXXII"));
        userList.add(new User(3, "Scyllasion", "Tatarus XCVIII"));
        userList.add(new User(4, "Flameater", "Asphodel CCCXLI"));
        userList.add(new User(5, "Bass", "Greece CDLXIV"));
        userList.add(new User(6, "Charp", "Elysium LXVII"));
        userList.add(new User(7, "Sturgeon", "Greece XXXIV"));
        userList.add(new User(8, "Hellfish", "Tatarus IV"));
        userList.add(new User(9, "Projelly", "Chaos XC"));
        userList.add(new User(10, "Chrustacean", "Asphodel MMIX"));

        try{
            FileOutputStream fOut = new FileOutputStream("ArrayList.txt");

            for (User user:userList){
                fOut.write(("\nID: " + user.getId() + " | Name: " + user.getName()
                        +" | Address: "+user.getAddress() + " | Time: "
                        + (LocalDateTime.now().format(formatter))).getBytes());
            }

        }catch (Exception e){
            e.printStackTrace();
        };
    }
}
