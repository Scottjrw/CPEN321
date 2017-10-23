package system;

/**
 * Created by Ao on 2017-10-22.
 */

public class Course {
    private int id;
    private int num;
    private String name;

    public Course(int id, String name, int num) {
        this.id = id;
        this.name = name;
        this.num = num;
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
}
