package com.iqbrave.iqbrave_lms;


import com.iqbrave.iqbrave_lms.controller.*;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        UserControllerTest.class,
        CourseControllerTest.class,
        ModuleControllerTest.class,
        LessonControllerTest.class,
        EnrollmentControllerTest.class,
        LessonProgressControllerTest.class
})
public class AllControllerTests {
}
