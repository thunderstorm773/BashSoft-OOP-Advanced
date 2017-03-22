package bg.softuni.contracts.repository;

import java.util.HashMap;

public interface DataSorter {
    void printSortedStudents(
            HashMap<String, Double> courseData, String comparisonType, int numberOfStudents);
}
