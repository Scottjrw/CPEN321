package system;

/**
 * Created by Ao on 2017-10-22.
 */

public class Course {
    private int id;
    private int num;
    private String name;
    private  String courseFullName;
    public Course(int id, String name, int num) {
        this.id = id;
        this.name = name;
        this.num = num;
        courseFullName = name + " " + Integer.toString(num);
    }

    public int getID() {
        return this.id;
    }

    public int getNum() {
        return this.num;
    }

    public String getName() {
        return this.name;
    }

    public String getFullName(){
        return  courseFullName;
    }
}
