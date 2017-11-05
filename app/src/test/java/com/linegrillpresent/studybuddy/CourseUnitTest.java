package com.linegrillpresent.studybuddy;

import junit.framework.Assert;

import org.junit.Test;

import system.Course;
import system.InvalidCourseException;

/**
 * Created by REX on 2017/11/5.
 */

public class CourseUnitTest {
    @Test (expected = InvalidCourseException.class)
    public void course_create_negativeID() throws InvalidCourseException {
        Course a = new Course(-1,"",-1);
    }
    @Test (expected = InvalidCourseException.class)
    public void course_create_NullString() throws InvalidCourseException {
        Course a = new Course(91,null,133);
    }
    @Test (expected = InvalidCourseException.class)
    public void course_create_EmptyString() throws InvalidCourseException {
        Course a = new Course(91,"",133);
    }
    @Test
    public  void course_check_full_name() throws InvalidCourseException {
            Course a = new Course(2,"CPEN",321);
            Assert.assertEquals("CPEN 321",a.getFullName());
    }
    @Test
    public  void course_equal() throws InvalidCourseException {
        Course a = new Course(2,"CPEN",321);
        Course b = new Course(2,"CPEN",321);
        Assert.assertTrue(a.equals(b));
    }

}
