package bg.softuni.contracts.models;

import java.util.Map;

public interface Student extends Comparable<Student> {
    String getUsername();

    void enrollInCourse(Course course);

    Map<String, Course> getEnrolledCourses();

    Map<String, Double> getMarksByCourseName();

    void setMarksInCourse(String courseName, int... scores);

    double calculateMark(int[] scores);
}
