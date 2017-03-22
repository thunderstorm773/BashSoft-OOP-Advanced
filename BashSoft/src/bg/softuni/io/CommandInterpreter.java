package bg.softuni.io;

import bg.softuni.contracts.Executable;
import bg.softuni.contracts.io.DirectoryManager;
import bg.softuni.contracts.io.Interpreter;
import bg.softuni.contracts.judge.ContentComparer;
import bg.softuni.contracts.network.AsyncDownloader;
import bg.softuni.contracts.repository.Database;
import bg.softuni.io.commands.*;

import java.io.IOException;

public class CommandInterpreter implements Interpreter {

    private ContentComparer tester;
    private Database repository;
    private AsyncDownloader downloadManager;
    private DirectoryManager ioManager;

    public CommandInterpreter(ContentComparer tester, Database repository,
                              AsyncDownloader downloadManager, DirectoryManager ioManager) {
        this.setTester(tester);
        this.setRepository(repository);
        this.setDownloadManager(downloadManager);
        this.setIoManager(ioManager);
    }

    public ContentComparer getTester() {
        return this.tester;
    }

    private void setTester(ContentComparer tester) {
        this.tester = tester;
    }

    public Database getRepository() {
        return this.repository;
    }

    private void setRepository(Database repository) {
        this.repository = repository;
    }

    public AsyncDownloader getDownloadManager() {
        return this.downloadManager;
    }

    private void setDownloadManager(AsyncDownloader downloadManager) {
        this.downloadManager = downloadManager;
    }

    public DirectoryManager getIoManager() {
        return this.ioManager;
    }

    private void setIoManager(DirectoryManager ioManager) {
        this.ioManager = ioManager;
    }

    public Executable parseCommand(String input, String[] data, String commandName) throws IOException {
        switch (commandName) {
            case "open":
               return new OpenFileCommand(
                        input,
                        data,
                        this.getRepository(),
                        this.getTester(),
                        this.getIoManager(),
                        this.getDownloadManager());
            case "mkdir":
               return new MakeDirectoryCommand(
                        input,
                        data,
                        this.getRepository(),
                        this.getTester(),
                        this.getIoManager(),
                        this.getDownloadManager());
            case "ls":
                return new TraverseFoldersCommand(
                        input,
                        data,
                        this.getRepository(),
                        this.getTester(),
                        this.getIoManager(),
                        this.getDownloadManager());
            case "cmp":
                return new CompareFilesCommand(
                        input,
                        data,
                        this.getRepository(),
                        this.getTester(),
                        this.getIoManager(),
                        this.getDownloadManager());
            case "cdrel":
                return new ChangeRelativePathCommand(
                        input,
                        data,
                        this.getRepository(),
                        this.getTester(),
                        this.getIoManager(),
                        this.getDownloadManager());
            case "cdabs":
                return new ChangeAbsolutePathCommand(
                        input,
                        data,
                        this.getRepository(),
                        this.getTester(),
                        this.getIoManager(),
                        this.getDownloadManager());
            case "readdb":
                return new ReadDatabaseFromFileCommand(
                        input,
                        data,
                        this.getRepository(),
                        this.getTester(),
                        this.getIoManager(),
                        this.getDownloadManager());
            case "help":
                return new GetHelpCommand(
                        input,
                        data,
                        this.getRepository(),
                        this.getTester(),
                        this.getIoManager(),
                        this.getDownloadManager());
            case "show":
                return new ShowWantedCourseCommand(
                        input,
                        data,
                        this.getRepository(),
                        this.getTester(),
                        this.getIoManager(),
                        this.getDownloadManager());
            case "filter":
                return new PrintFilteredStudentsCommand(
                        input,
                        data,
                        this.getRepository(),
                        this.getTester(),
                        this.getIoManager(),
                        this.getDownloadManager());
            case "order":
                return new PrintOrderedStudentsCommand(
                        input,
                        data,
                        this.getRepository(),
                        this.getTester(),
                        this.getIoManager(),
                        this.getDownloadManager());
            case "download":
                return new DownloadFileCommand(
                        input,
                        data,
                        this.getRepository(),
                        this.getTester(),
                        this.getIoManager(),
                        this.getDownloadManager());
            case "downloadasync":
                return new DownloadFileOnNewThreadCommand(
                        input,
                        data,
                        this.getRepository(),
                        this.getTester(),
                        this.getIoManager(),
                        this.getDownloadManager());
            case "dropdb":
                return new DropDatabaseCommand(
                        input,
                        data,
                        this.getRepository(),
                        this.getTester(),
                        this.getIoManager(),
                        this.getDownloadManager());
            case "display":
                return new DisplayCommand(
                        input,
                        data,
                        this.getRepository(),
                        this.getTester(),
                        this.getIoManager(),
                        this.getDownloadManager());
            default:
                return new DisplayInvalidCommandMessage(
                            input, data, this.getRepository(),
                            this.getTester(), this.getIoManager(), this.getDownloadManager());
        }
    }

    @Override
    public void interpretCommand(String input) throws IOException {
        String[] data = input.split("\\s+");
        String commandName = data[0].toLowerCase();
        try {
            Executable command = parseCommand(input, data, commandName);
            command.execute();
        } catch (Throwable t) {
            OutputWriter.writeMessageOnNewLine(t.getMessage());
        }
    }
}
