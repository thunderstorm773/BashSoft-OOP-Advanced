package models;

import bg.softuni.contracts.models.Course;
import bg.softuni.contracts.models.Student;
import bg.softuni.io.OutputWriter;
import bg.softuni.staticData.ExceptionMessages;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class SoftUniStudent implements Student{

    private String username;
    private LinkedHashMap<String, Course> enrolledCourses;
    private LinkedHashMap<String, Double> marksByCourseName;

    public SoftUniStudent(String username) {

        this.setUsername(username);
        this.enrolledCourses = new LinkedHashMap<>();
        this.marksByCourseName = new LinkedHashMap<>();
    }

    private void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getUsername() {
       return this.username;
    }

    @Override
    public void enrollInCourse(Course course) {
        if (this.enrolledCourses.containsKey(course.getName())) {
            OutputWriter.displayException(String.format(
                    ExceptionMessages.STUDENT_ALREADY_ENROLLED_IN_GIVEN_COURSE,
                    this.username, course.getName()));
            return;
        }

        this.enrolledCourses.put(course.getName(), course);
    }

    @Override
    public Map<String, Course> getEnrolledCourses() {
        return Collections.unmodifiableMap(this.enrolledCourses);
    }

    @Override
    public Map<String, Double> getMarksByCourseName() {
        return Collections.unmodifiableMap(this.marksByCourseName);
    }

    @Override
    public void setMarksInCourse(String courseName, int... scores) {
        if (!this.enrolledCourses.containsKey(courseName)) {
            OutputWriter.displayException(ExceptionMessages.NOT_ENROLLED_IN_COURSE);
            return;
        }

        if (scores.length > SoftUniCourse.NUMBER_OF_TASKS_ON_EXAM) {
            OutputWriter.displayException(ExceptionMessages.INVALID_NUMBER_OF_SCORES);
            return;
        }

        double mark = calculateMark(scores);
        this.marksByCourseName.put(courseName, mark);
    }

    @Override
    public double calculateMark(int[] scores) {
        double percentageOfSolvedExam = Arrays.stream(scores).sum() /
                (double) (SoftUniCourse.NUMBER_OF_TASKS_ON_EXAM * SoftUniCourse.MAX_SCORE_ON_EXAM_TASK);
        double mark = percentageOfSolvedExam * 4 + 2;
        return mark;
    }

    @Override
    public int compareTo(Student other) {
        return this.getUsername().compareTo(other.getUsername());
    }

    @Override
    public String toString() {
        return this.getUsername();
    }
}
