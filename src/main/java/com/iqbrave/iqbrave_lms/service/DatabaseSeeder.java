package com.iqbrave.iqbrave_lms.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import com.iqbrave.iqbrave_lms.entity.*;
import com.iqbrave.iqbrave_lms.repository.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Profile("!test")
@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final ModuleRepository moduleRepository;
    private final LessonRepository lessonRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final PasswordEncoder passwordEncoder;

    public DatabaseSeeder(UserRepository userRepository,
                          CourseRepository courseRepository,
                          ModuleRepository moduleRepository,
                          LessonRepository lessonRepository,
                          EnrollmentRepository enrollmentRepository,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.moduleRepository = moduleRepository;
        this.lessonRepository = lessonRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        seedUsers();
        seedCoursesAndModules();
        seedLessons();
        seedEnrollments();
    }

    private void seedUsers() {
        if (userRepository.count() == 0) {
            User admin = new User(null, "admin@test.com", "Admin", passwordEncoder.encode("admin123"), Role.ADMIN);
            User instructor = new User(null, "instructor@test.com", "Instructor", passwordEncoder.encode("instructor123"), Role.INSTRUCTOR);
            User student = new User(null, "student@test.com", "Student", passwordEncoder.encode("student123"), Role.STUDENT);

            userRepository.saveAll(Arrays.asList(admin, instructor, student));
        }
    }

    private void seedCoursesAndModules() {
        if (courseRepository.count() == 0) {
            Course javaCourse = new Course(null, "Java Basics", "Introduction to Java");
            Course springCourse = new Course(null, "Spring Boot", "Spring Boot Fundamentals");
            courseRepository.saveAll(Arrays.asList(javaCourse, springCourse));

            CourseModule m1 = new CourseModule(null, "Java Fundamentals", "Core Java topics", javaCourse, null);
            CourseModule m2 = new CourseModule(null, "OOP in Java", "Object Oriented Programming", javaCourse, null);
            CourseModule m3 = new CourseModule(null, "Spring Core", "Spring basics", springCourse, null);
            CourseModule m4 = new CourseModule(null, "Spring Security", "Security with Spring", springCourse, null);
            moduleRepository.saveAll(Arrays.asList(m1, m2, m3, m4));
        }
    }

    private void seedLessons() {
        if (lessonRepository.count() == 0) {
            CourseModule javaFundamentals = moduleRepository.findByTitle("Java Fundamentals").orElseThrow();
            CourseModule springCore = moduleRepository.findByTitle("Spring Core").orElseThrow();

            Lesson l1 = new Lesson(null, "Hello World Program", "First Java Program", javaFundamentals);
            Lesson l2 = new Lesson(null, "Variables & Data Types", "Understanding variables", javaFundamentals);
            Lesson l3 = new Lesson(null, "Spring Boot Setup", "Setting up Spring Boot", springCore);

            lessonRepository.saveAll(Arrays.asList(l1, l2, l3));
        }
    }

    private void seedEnrollments() {
        if (enrollmentRepository.count() == 0) {
            User student = userRepository.findByEmail("student@test.com").orElseThrow();
            Course javaCourse = courseRepository.findByTitle("Java Basics").orElseThrow();

            Enrollment enrollment = new Enrollment(null, student, javaCourse);
            enrollmentRepository.save(enrollment);
        }
    }
}
