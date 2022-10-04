import java.util.TreeSet;

public class TreeSetUser {

    public static void main(String[] args){

        System.out.println("---Users added in this order---");
        User user1 = new User("Ann", 19);
        System.out.println("User1: " + user1.getName());

        User user2 = new User("Zavier", 44);
        System.out.println("User2: " + user2.getName());

        User user3 = new User();
        user3.setName("Coyote");
        user3.setAge(15);
        System.out.println("User3: " + user3.getName());

        User user4 = new User();
        user4.setName("Henrietta");
        user4.setAge(32);
        System.out.println("User4: " + user4.getName());

        TreeSet<User> treeUser = new java.util.TreeSet<User>();
        treeUser.add(user1);
        treeUser.add(user2);
        treeUser.add(user3);
        treeUser.add(user4);

        System.out.println();

        //for loop iteration and sorted
        System.out.println("---Sorted by Name---");
        for (User user:treeUser){
            System.out.println("Name: " + user.getName() + " | Age: " + user.getAge());
        }

    }

}
