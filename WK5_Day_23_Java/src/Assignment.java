import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Assignment {

    public static void main (String[] args){

        User user1 = new User("Betty", 21, "Avenue GHJ");
        User user2 = new User("Candice", 16, "Street HT6");
        User user3 = new User("Bob", 6, "Blk 445");
        User user4 = new User("Adrian", 15, "Street 1234");
        User user5 = new User("Calvin", 19, "Blk 00987");
        User user6 = new User("Ann", 23, "Avenue OOPS");

        ArrayList<User> userArr1 = new ArrayList<>();
        userArr1.add(user1);
        userArr1.add(user2);
        userArr1.add(user3);

        ArrayList<User> userArr2 = new ArrayList<>();
        userArr2.add(user4);
        userArr2.add(user5);
        userArr2.add(user6);

        //hashmap with arraylist
        HashMap<String, ArrayList<User>> userMap = new HashMap<>();
        userMap.put("list1", userArr1);
        userMap.put("list2", userArr2);

        System.out.println("---HashMap with ArrayList---");
        for (String key:userMap.keySet()){ //taking the keys e.g. list1, list2
            ArrayList<User> users = userMap.get(key); //to get the arraylist through keys
            System.out.println("---" + key + "---");
            for (User user:users){
                System.out.println("Name: " + user.getName()
                                + " | Age: " + user.getAge()
                                + " | Address: " + user.getAddress());
            }
        }

//        System.out.println("---HashMap---");
//        for (User user:userMap.values()){
//            System.out.println("Name: " + user.getName()
//                                + " | Age: " + user.getAge()
//                                + " | Address: " + user.getAddress());
//        }

        System.out.println();
        ArrayList<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);
        userList.add(user5);
        userList.add(user6);

        //below 20
        System.out.println("---Below 20 ArrayList---");
        List<User> below20 = userList.stream().filter((u)->u.getAge()<20).collect(Collectors.toList());
        for(User user:below20){
            System.out.println("Name: " + user.getName() + " | Age: " + user.getAge());
        }

        System.out.println();
        //name starts with a and limit firstUser
        System.out.println("---Name starting with 'A' ArrayList---");
        List<User> a1 = userList.stream().filter((u)->u.getName().startsWith("A")).limit(1).collect(Collectors.toList());
        for(User user:a1){
            System.out.println("First result: " + user.getName());
        }

    }
}
