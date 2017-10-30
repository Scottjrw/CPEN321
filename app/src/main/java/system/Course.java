package system;

/**
 * Created by Ao on 2017-10-22.
 */

public class Course {
    private int course_id;
    private int course_code;
    private String course_name;
    private  String courseFullName;
    public Course(int id, String name, int num) {
        this.course_id = id;
        this.course_name = name;
        this.course_code = num;
        courseFullName = name + " " + Integer.toString(num);
    }

    public int getID() {
        return this.course_id;
    }

    public int getNum() {
        return this.course_code;
    }

    public String getName() {
        return this.course_name;
    }

    public String getFullName(){
        return  courseFullName;
    }

    @Override
    public boolean equals(Object obj) {
        Course anotherCourse = (Course) obj;
        return  (this.getFullName().equals(anotherCourse.getFullName()));
    }
}
