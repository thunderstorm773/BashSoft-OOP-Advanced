package bg.softuni.contracts.repository;

import bg.softuni.contracts.models.Course;
import bg.softuni.contracts.models.Student;
import bg.softuni.dataStructures.SimpleSortedList;

import java.util.Comparator;

public interface Requester {
    void getStudentMarkInCourse(String courseName, String studentName);

    void getStudentsByCourse(String courseName);

    SimpleSortedList<Course> getAllCoursesSorted(Comparator<Course> cmp);

    SimpleSortedList<Student> getAllStudentsSorted(Comparator<Student> cmp);
}
