package com.iqbrave.iqbrave_lms;


import com.iqbrave.iqbrave_lms.controller.CourseControllerTest;
import com.iqbrave.iqbrave_lms.controller.LessonControllerTest;
import com.iqbrave.iqbrave_lms.controller.ModuleControllerTest;
import com.iqbrave.iqbrave_lms.controller.UserControllerTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        CourseControllerTest.class,
        LessonControllerTest.class,
        ModuleControllerTest.class,
        UserControllerTest.class
})
public class AllControllerTests {
}
