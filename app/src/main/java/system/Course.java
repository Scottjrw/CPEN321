package system;

import java.util.List;

/**
 * Created by Ao on 2017-10-22.
 */

public class Course {
    private int course_id;
    private int course_code;
    private String course_name;
    private  String courseFullName;
    private List<String> groups;

    public Course(int id, String name, int code) throws InvalidCourseException {
        if(id < 0 || name == null || code <0 || name.isEmpty()){ throw new InvalidCourseException();}
        this.course_id = id;
        this.course_name = name;
        this.course_code = code;
        courseFullName = name + " " + Integer.toString(code);
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

    public void addGroups(String group){
        groups.add(group);
    }
    public List<String> getGroups(){
        return groups;
    }
    @Override
    public boolean equals(Object obj) {
        Course anotherCourse = (Course) obj;
        return  (this.getFullName().equals(anotherCourse.getFullName()));
    }
}
