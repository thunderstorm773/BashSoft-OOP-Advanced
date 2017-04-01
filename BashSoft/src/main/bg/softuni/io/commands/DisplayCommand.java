package main.bg.softuni.io.commands;

import main.bg.softuni.annotations.Alias;
import main.bg.softuni.annotations.Inject;
import main.bg.softuni.contracts.models.Course;
import main.bg.softuni.contracts.models.Student;
import main.bg.softuni.contracts.repository.Database;
import main.bg.softuni.dataStructures.SimpleSortedList;
import main.bg.softuni.exceptions.InvalidInputException;
import main.bg.softuni.io.OutputWriter;

import javax.naming.InvalidNameException;
import java.util.Comparator;

@Alias(value = "display")
public class DisplayCommand extends Command {

    @Inject
    private Database repository;

    public DisplayCommand(String input, String[] data) {
        super(input, data);
    }

    @Override
    public void execute() throws Exception {
        String[] data = this.getData();
        if (data.length != 3) {
            throw new InvalidNameException(this.getInput());
        }

        String entityToDisplay = data[1];
        String sortType = data[2];

        if (entityToDisplay.equalsIgnoreCase("students")) {
            Comparator<Student> studentComparator = this.createComparator(Student.class, sortType);
            SimpleSortedList<Student> list =
                    this.repository.getAllStudentsSorted(studentComparator);
            OutputWriter.writeMessageOnNewLine(list.joinWith(System.lineSeparator()));

        } else if (entityToDisplay.equalsIgnoreCase("courses")) {
            Comparator<Course> courseComparator = this.createComparator(Course.class, sortType);
            SimpleSortedList<Course> list =
                    this.repository.getAllCoursesSorted(courseComparator);
            OutputWriter.writeMessageOnNewLine(list.joinWith(System.lineSeparator()));

        } else {
            throw new InvalidInputException(this.getInput());
        }
    }

    private <T extends Comparable<T>> Comparator<T> createComparator(Class<T> comparatorType, String sortType) {
        if (sortType.equalsIgnoreCase("ascending")) {
            return (first, second) -> first.compareTo(second);

        } else if (sortType.equalsIgnoreCase("descending")) {
            return (first, second) -> second.compareTo(first);

        } else {
          throw new InvalidInputException(this.getInput());
        }
    }

//    private Comparator<Student> createStudentComparator(String sortType) {
//        if (sortType.equalsIgnoreCase("ascending")) {
//            return (first, second) -> first.compareTo(second);
//
//        } else if (sortType.equalsIgnoreCase("descending")) {
//            return (first, second) -> second.compareTo(first);
//
//        } else {
//            throw new InvalidInputException(this.getInput());
//        }
//    }
}
