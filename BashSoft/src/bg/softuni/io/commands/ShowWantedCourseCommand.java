package bg.softuni.io.commands;

import bg.softuni.contracts.io.DirectoryManager;
import bg.softuni.contracts.judge.ContentComparer;
import bg.softuni.contracts.network.AsyncDownloader;
import bg.softuni.contracts.repository.Database;

public class ShowWantedCourseCommand extends Command{

    public ShowWantedCourseCommand(
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
        if (this.getData().length != 2 && this.getData().length != 3) {
            DisplayInvalidCommandMessage invalidCommandMessage =
                    new DisplayInvalidCommandMessage(
                            this.getInput(), this.getData(), this.getStudentRepository(),
                            this.getTester(), this.getIoManager(), this.getDownloadManager());
            invalidCommandMessage.execute();
            return;
        }

        if (this.getData().length == 2) {
            String courseName = this.getData()[1];
            this.getStudentRepository().getStudentsByCourse(courseName);
        }

        if (this.getData().length == 3) {
            String courseName = this.getData()[1];
            String userName = this.getData()[2];
            this.getStudentRepository().getStudentMarkInCourse(courseName, userName);
        }
    }
}
