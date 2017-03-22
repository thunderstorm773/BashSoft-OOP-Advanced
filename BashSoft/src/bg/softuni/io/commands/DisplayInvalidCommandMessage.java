package bg.softuni.io.commands;

import bg.softuni.contracts.io.DirectoryManager;
import bg.softuni.contracts.judge.ContentComparer;
import bg.softuni.contracts.network.AsyncDownloader;
import bg.softuni.contracts.repository.Database;
import bg.softuni.io.OutputWriter;

public class DisplayInvalidCommandMessage extends Command{

    public DisplayInvalidCommandMessage(
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
        String output = String.format("The command '%s' is invalid", this.getInput());
        OutputWriter.displayException(output);
    }
}
