package bg.softuni.repository;

import bg.softuni.contracts.models.Course;
import bg.softuni.contracts.models.Student;
import bg.softuni.contracts.repository.DataFilter;
import bg.softuni.contracts.repository.DataSorter;
import bg.softuni.contracts.repository.Database;
import bg.softuni.dataStructures.SimpleSortedList;
import bg.softuni.staticData.SessionData;
import bg.softuni.io.OutputWriter;
import bg.softuni.staticData.ExceptionMessages;
import models.SoftUniCourse;
import models.SoftUniStudent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StudentsRepository implements Database {

    private boolean isDataInitialized = false;
    private LinkedHashMap<String, Course> courses;
    private LinkedHashMap<String, Student> students;
    private DataFilter filter;
    private DataSorter sorter;

    public StudentsRepository(DataFilter filter, DataSorter sorter) {
        this.setFilter(filter);
        this.setSorter(sorter);
    }

    public boolean getIsDataInitialized() {
        return this.isDataInitialized;
    }

    private void setDataInitialized(boolean dataInitialized) {
        isDataInitialized = dataInitialized;
    }

    public Map<String, Course> getCourses() {
        return Collections.unmodifiableMap(this.courses);
    }

    public Map<String, Student> getStudents() {
        return Collections.unmodifiableMap(this.students);
    }

    public DataFilter getFilter() {
        return this.filter;
    }

    private void setFilter(DataFilter filter) {
        this.filter = filter;
    }

    public DataSorter getSorter() {
        return this.sorter;
    }

    private void setSorter(DataSorter sorter) {
        this.sorter = sorter;
    }

    private void readData(String fileName) throws IOException {
        String regex = "([A-Z][a-zA-Z#\\+]*_[A-Z][a-z]{2}_\\d{4})\\s+([A-Za-z]+\\d{2}_\\d{2,4})\\s([\\s0-9]+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher;

        String path = SessionData.currentPath + "\\" + fileName;
        List<String> lines = Files.readAllLines(Paths.get(path));

        for (String line : lines) {
            matcher = pattern.matcher(line);

            if (!line.isEmpty() && matcher.find()) {
                String courseName = matcher.group(1);
                String studentName = matcher.group(2);
                String scoresStr = matcher.group(3);


                try {
                    String[] splitScores = scoresStr.split("\\s+");
                    int[] scores = new int[splitScores.length];

                    for (int i = 0; i < splitScores.length; i++) {
                        scores[i] = Integer.parseInt(splitScores[i]);
                    }

                    if (Arrays.stream(scores).anyMatch(score -> score > 100 || score < 0)) {
                        OutputWriter.displayException(ExceptionMessages.INVALID_SCORE);
                        continue;
                    }

                    if (scores.length > SoftUniCourse.NUMBER_OF_TASKS_ON_EXAM) {
                        OutputWriter.displayException(ExceptionMessages.INVALID_NUMBER_OF_SCORES);
                        continue;
                    }

                    if (!this.getStudents().containsKey(studentName)) {
                        this.students.put(studentName, new SoftUniStudent(studentName));
                    }

                    if (!this.getCourses().containsKey(courseName)) {
                        this.courses.put(courseName, new SoftUniCourse(courseName));
                    }

                    Course course = this.getCourses().get(courseName);
                    Student student = this.getStudents().get(studentName);
                    student.enrollInCourse(course);
                    student.setMarksInCourse(courseName, scores);
                    course.enrollStudent(student);
                } catch (NumberFormatException nfe) {
                    OutputWriter.displayException(nfe.getMessage() + " at line: " + line);
                }
            }
        }

        this.setDataInitialized(true);
        OutputWriter.writeMessageOnNewLine("Data read.");
    }

    private boolean isQueryForCoursePossible(String courseName) {
        if (!this.getIsDataInitialized()) {
            OutputWriter.displayException(ExceptionMessages.DATA_NOT_INITIALIZED);
            return false;
        }

        if (!this.getCourses().containsKey(courseName)) {
            OutputWriter.displayException(ExceptionMessages.NON_EXISTING_COURSE);
            return false;
        }

        return true;
    }

    private boolean isQueryForStudentPossible(String courseName, String studentName) {
        if (!isQueryForCoursePossible(courseName)) {
            return false;
        }

        if (!this.getCourses().get(courseName).getStudentsByName().containsKey(studentName)) {
            OutputWriter.displayException(ExceptionMessages.NON_EXISTING_STUDENT);
            return false;
        }

        return true;
    }

    @Override
    public void loadData(String fileName) throws IOException {
        if (this.getIsDataInitialized()) {
            OutputWriter.displayException(ExceptionMessages.DATA_ALREADY_INITIALIZED);
            return;
        }

        this.students = new LinkedHashMap<>();
        this.courses = new LinkedHashMap<>();
        this.readData(fileName);
    }

    @Override
    public void unloadData() {
        if (!this.getIsDataInitialized()) {
            OutputWriter.displayException(ExceptionMessages.DATA_NOT_INITIALIZED);
        }

        this.students = null;
        this.courses = null;
        this.setDataInitialized(false);
    }

    @Override
    public void getStudentMarkInCourse(String courseName, String studentName) {
        if (!isQueryForStudentPossible(courseName, studentName)) {
            return;
        }

        double mark = this.getCourses().get(courseName).getStudentsByName()
                .get(studentName).getMarksByCourseName().get(courseName);
        OutputWriter.printStudent(studentName, mark);
    }

    @Override
    public void getStudentsByCourse(String courseName) {
        if (!isQueryForCoursePossible(courseName)) {
            return;
        }

        OutputWriter.writeMessageOnNewLine(courseName + ":");
        for (Map.Entry<String, Student> student : this.getCourses().get(courseName).getStudentsByName().entrySet()) {
            this.getStudentMarkInCourse(courseName, student.getKey());
        }
    }

    @Override
    public SimpleSortedList<Course> getAllCoursesSorted(Comparator<Course> cmp) {
        SimpleSortedList<Course> courseSortedList =
                new SimpleSortedList<>(Course.class, cmp);
        courseSortedList.addAll(this.courses.values());
        return courseSortedList;
    }

    @Override
    public SimpleSortedList<Student> getAllStudentsSorted(Comparator<Student> cmp) {
        SimpleSortedList<Student> studentSortedList =
                new SimpleSortedList<>(Student.class, cmp);
        studentSortedList.addAll(this.students.values());
        return studentSortedList;
    }

    @Override
    public void filterAndTake(String courseName, String filter) {
        int studentsToTake = this.getCourses().get(courseName).getStudentsByName().size();
        filterAndTake(courseName, filter, studentsToTake);
    }

    @Override
    public void filterAndTake(String courseName, String filter, int studentsToTake) {

        if (!isQueryForCoursePossible(courseName)) {
            return;
        }

        LinkedHashMap<String, Double> marks = new LinkedHashMap<>();
        for (Map.Entry<String, Student> entry : this.getCourses().get(courseName).getStudentsByName().entrySet()) {
            marks.put(entry.getKey(), entry.getValue().getMarksByCourseName().get(courseName));
        }

        this.getFilter().printFilteredStudents(
                marks, filter, studentsToTake);
    }

    @Override
    public void orderAndTake(String courseName, String orderType, int studentsToTake) {
        if (!isQueryForCoursePossible(courseName)) {
            return;
        }

        LinkedHashMap<String, Double> marks = new LinkedHashMap<>();
        for (Map.Entry<String, Student> entry : this.getCourses().get(courseName).getStudentsByName().entrySet()) {
            marks.put(entry.getKey(), entry.getValue().getMarksByCourseName().get(courseName));
        }

        this.getSorter().printSortedStudents(
                marks, orderType, studentsToTake);
    }

    @Override
    public void orderAndTake(String courseName, String orderType) {
        int studentsToTake = this.getCourses().get(courseName).getStudentsByName().size();
        orderAndTake(courseName, orderType, studentsToTake);
    }
}
