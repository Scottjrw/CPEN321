package com.linegrillpresent.studybuddy;

import org.junit.Test;

import user.InvalidUserException;
import user.Student;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class StudentClassUnitTest {
    @Test (expected = InvalidUserException.class)
    public void exception_null_name() throws InvalidUserException {
        Student s = Student.getInstance();
        s.setName(null);
    }
    @Test (expected = InvalidUserException.class)
    public void exception_empty_name() throws InvalidUserException {
        Student s = Student.getInstance();
        s.setName("");
    }

    @Test(expected = InvalidUserException.class)
    public void exception_null_username() throws InvalidUserException {
        Student s = Student.getInstance();
        s.setUsername(null);
    }
    @Test(expected = InvalidUserException.class)
    public void exception_empty_username() throws InvalidUserException {
        Student s = Student.getInstance();
        s.setUsername("");
    }

    @Test(expected = InvalidUserException.class)
    public void exception_null_Email() throws InvalidUserException {
        Student s = Student.getInstance();
        s.setEmail(null);
    }
    @Test(expected = InvalidUserException.class)
    public void exception_empty_Email() throws InvalidUserException {
        Student s = Student.getInstance();
        s.setEmail("");
    }


}