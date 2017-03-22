package bg.softuni.io.commands;

import bg.softuni.contracts.io.DirectoryManager;
import bg.softuni.contracts.judge.ContentComparer;
import bg.softuni.contracts.network.AsyncDownloader;
import bg.softuni.contracts.repository.Database;

public class TraverseFoldersCommand extends Command{

    public TraverseFoldersCommand(
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
        if (this.getData().length != 1 && this.getData().length != 2) {
            DisplayInvalidCommandMessage invalidCommandMessage =
                    new DisplayInvalidCommandMessage(
                            this.getInput(), this.getData(), this.getStudentRepository(),
                            this.getTester(), this.getIoManager(), this.getDownloadManager());
            invalidCommandMessage.execute();
            return;
        }

        if (this.getData().length == 1) {
            this.getIoManager().traverseDirectory(0);
        }

        if (this.getData().length == 2) {
            this.getIoManager().traverseDirectory(Integer.valueOf(this.getData()[1]));
        }
    }
}
