package main.bg.softuni.contracts.repository;

import main.bg.softuni.contracts.models.Course;
import main.bg.softuni.contracts.models.Student;
import main.bg.softuni.dataStructures.SimpleSortedList;

import java.util.Comparator;

public interface Requester {
    void getStudentMarkInCourse(String courseName, String studentName);

    void getStudentsByCourse(String courseName);

    SimpleSortedList<Course> getAllCoursesSorted(Comparator<Course> cmp);

    SimpleSortedList<Student> getAllStudentsSorted(Comparator<Student> cmp);
}
