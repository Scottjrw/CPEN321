package com.linegrillpresent.studybuddy;

import org.junit.Assert;
import org.junit.Test;

import user.InvalidUserException;
import user.Student;
import user.User;

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
    @Test
    public void initialize_student() throws  InvalidUserException{
        Student s = Student.getInstance();
        s.setEmail("abc@gmail.com");
        s.setName("haha");
        s.setUsername("test1");
        s.setNumberOfCourses(2);
        s.setNumberOfGroups(9999);
        s.setToken("hwaihoahasda241345");
        s.isSet = true;

        Assert.assertEquals("abc@gmail.com",s.getEmail());
        Assert.assertEquals("haha",s.getName());
        Assert.assertEquals("test1",s.getUsername());
        Assert.assertEquals(2,s.getNumberOfCourses());
        Assert.assertEquals(9999,s.getNumberOfGroups());
        Assert.assertEquals("hwaihoahasda241345",s.getToken());
        Assert.assertEquals(User.UserType.STUDENT,s.getUserType());

    }

}