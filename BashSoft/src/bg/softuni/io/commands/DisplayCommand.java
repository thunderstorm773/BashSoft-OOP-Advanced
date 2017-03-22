package bg.softuni.io.commands;

import bg.softuni.contracts.io.DirectoryManager;
import bg.softuni.contracts.judge.ContentComparer;
import bg.softuni.contracts.models.Course;
import bg.softuni.contracts.models.Student;
import bg.softuni.contracts.network.AsyncDownloader;
import bg.softuni.contracts.repository.Database;
import bg.softuni.dataStructures.SimpleSortedList;
import bg.softuni.exceptions.InvalidInputException;
import bg.softuni.io.OutputWriter;

import javax.naming.InvalidNameException;
import java.util.Comparator;

public class DisplayCommand extends Command {

    public DisplayCommand(
            String input,
            String[] data,
            Database studentRepository,
            ContentComparer tester,
            DirectoryManager ioManager,
            AsyncDownloader downloadManager) {

        super(input, data, studentRepository, tester, ioManager, downloadManager);
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
                    this.getStudentRepository().getAllStudentsSorted(studentComparator);
            OutputWriter.writeMessageOnNewLine(list.joinWith(System.lineSeparator()));

        } else if (entityToDisplay.equalsIgnoreCase("courses")) {
            Comparator<Course> courseComparator = this.createComparator(Course.class, sortType);
            SimpleSortedList<Course> list =
                    this.getStudentRepository().getAllCoursesSorted(courseComparator);
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
