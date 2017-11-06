package com.linegrillpresent.studybuddy;

/**
 * Created by REX on 2017/11/5.
 */

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        CourseUnitTest.class,
        StudentClassUnitTest.class,
        UtilityClassUnitTest.class
})

public class JunitTestSuite {
}