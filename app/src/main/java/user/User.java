package user;

import java.util.List;

import system.Course;

/**
 * Created by Ao on 2017-10-15.
 */

public interface User {

    public enum UserType {
        STUDENT, TA, PROFESSOR
    }

    UserType getUserType();

    void setToken(String token) throws InvalidUserException;

    String getToken();

    void setName(String name) throws InvalidUserException;

    String getName();

    void setUsername(String username) throws InvalidUserException;

    String getUsername();

    void setEmail(String Email) throws InvalidUserException;

    String getEmail();

    void setNumberOfGroups(int groups) throws InvalidUserException;

    int getNumberOfGroups();

    List<String> getGroups();

    List<Course> getCourses();

    void setNumberOfCourses(int courses) throws InvalidUserException;

    int getNumberOfCourses();
    //int getNumberOfGroups();
}
