package bg.softuni.io.commands;

import bg.softuni.contracts.io.DirectoryManager;
import bg.softuni.contracts.judge.ContentComparer;
import bg.softuni.contracts.network.AsyncDownloader;
import bg.softuni.contracts.repository.Database;
import bg.softuni.staticData.SessionData;

import java.awt.*;
import java.io.File;

public class OpenFileCommand extends Command{

    public OpenFileCommand(
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
        if (this.getData().length != 2) {
            DisplayInvalidCommandMessage invalidCommandMessage =
                    new DisplayInvalidCommandMessage(
                            this.getInput(), this.getData(), this.getStudentRepository(),
                            this.getTester(), this.getIoManager(), this.getDownloadManager());
            invalidCommandMessage.execute();
            return;
        }

        String fileName = this.getData()[1];
        String filePath = SessionData.currentPath + "\\" + fileName;
        File file = new File(filePath);
        Desktop.getDesktop().open(file);
    }
}
