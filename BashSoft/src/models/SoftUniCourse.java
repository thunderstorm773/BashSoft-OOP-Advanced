package models;

import bg.softuni.contracts.models.Course;
import bg.softuni.contracts.models.Student;
import bg.softuni.io.OutputWriter;
import bg.softuni.staticData.ExceptionMessages;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class SoftUniCourse implements Course{

    public static final int NUMBER_OF_TASKS_ON_EXAM = 5;
    public static final int MAX_SCORE_ON_EXAM_TASK = 100;

    private String name;
    private LinkedHashMap<String, Student> studentsByName;

    public SoftUniCourse(String name) {
        this.setName(name);
        this.studentsByName = new LinkedHashMap<>();
    }

    private void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Map<String, Student> getStudentsByName() {
        return Collections.unmodifiableMap(this.studentsByName);
    }

    @Override
    public void enrollStudent(Student student) {
        if (this.studentsByName.containsKey(student.getUsername())) {
            OutputWriter.displayException(String.format(
                    ExceptionMessages.STUDENT_ALREADY_ENROLLED_IN_GIVEN_COURSE,
                    student.getUsername(), this.name));
            return;
        }

        this.studentsByName.put(student.getUsername(), student);
    }

    @Override
    public int compareTo(Course other) {
        return this.getName().compareTo(other.getName());
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
