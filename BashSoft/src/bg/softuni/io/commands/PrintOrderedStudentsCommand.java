package bg.softuni.io.commands;

import bg.softuni.contracts.io.DirectoryManager;
import bg.softuni.contracts.judge.ContentComparer;
import bg.softuni.contracts.network.AsyncDownloader;
import bg.softuni.contracts.repository.Database;
import bg.softuni.io.OutputWriter;
import bg.softuni.staticData.ExceptionMessages;

public class PrintOrderedStudentsCommand extends Command{

    public PrintOrderedStudentsCommand(
            String input,
            String[] data,
            Database studentRepository,
            ContentComparer tester,
            DirectoryManager ioManager,
            AsyncDownloader downloadManager) {

        super(input, data, studentRepository, tester, ioManager, downloadManager);
    }

    private void tryParseParametersForOrder(
            String takeCommand, String takeQuantity,
            String courseName, String orderType) {
        if (!takeCommand.equals("take")) {
            OutputWriter.displayException(ExceptionMessages.INVALID_TAKE_COMMAND);
            return;
        }

        if (takeQuantity.equals("all")) {
            this.getStudentRepository().orderAndTake(courseName, orderType);
            return;
        }

        try {
            int studentsToTake = Integer.parseInt(takeQuantity);
            this.getStudentRepository().orderAndTake(courseName, orderType, studentsToTake);
        } catch (NumberFormatException nfe) {
            OutputWriter.displayException(ExceptionMessages.IVALID_TAKE_QUANTITY_PARAMETER);
        }
    }

    @Override
    public void execute() throws Exception {
        if (this.getData().length != 5) {
            DisplayInvalidCommandMessage invalidCommandMessage =
                    new DisplayInvalidCommandMessage(
                            this.getInput(), this.getData(), this.getStudentRepository(),
                            this.getTester(), this.getIoManager(), this.getDownloadManager());
            invalidCommandMessage.execute();
            return;
        }

        String courseName = this.getData()[1];
        String orderType = this.getData()[2].toLowerCase();
        String takeCommand = this.getData()[3].toLowerCase();
        String takeQuantity = this.getData()[4].toLowerCase();

        this.tryParseParametersForOrder(takeCommand, takeQuantity, courseName, orderType);
    }
}
