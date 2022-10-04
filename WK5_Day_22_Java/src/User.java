public class User implements Comparable<User>{

    String name;
    int age;


    //constructors
    public User(){

    }
    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    //getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    //implementing Comparable
    @Override
    public int compareTo(User o) {
        return this.getName().compareToIgnoreCase(o.getName());
    }

}
