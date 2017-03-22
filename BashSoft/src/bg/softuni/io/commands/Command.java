package bg.softuni.io.commands;

import bg.softuni.contracts.Executable;
import bg.softuni.contracts.io.DirectoryManager;
import bg.softuni.contracts.judge.ContentComparer;
import bg.softuni.contracts.network.AsyncDownloader;
import bg.softuni.contracts.repository.Database;
import sun.plugin.dom.exception.InvalidStateException;

public abstract class Command implements Executable{

    private Database studentRepository;
    private ContentComparer tester;
    private DirectoryManager ioManager;
    private AsyncDownloader downloadManager;
    private String input;
    private String[] data;

    protected Command(
            String input,
            String[] data,
            Database studentRepository,
            ContentComparer tester,
            DirectoryManager ioManager,
            AsyncDownloader downloadManager) {

        this.setInput(input);
        this.setData(data);
        this.setStudentRepository(studentRepository);
        this.setTester(tester);
        this.setIoManager(ioManager);
        this.setDownloadManager(downloadManager);
    }

    public String getInput() {
        return this.input;
    }

    protected void setInput(String input) {
        if (input == null || input.equals("")) {
            throw new InvalidStateException("Invalid input");
        }
        this.input = input;
    }

    public String[] getData() {
        return this.data;
    }

    protected void setData(String[] data) {
        if (data == null || data.length == 0) {
            throw new InvalidStateException("Invalid input");
        }
        this.data = data;
    }

    public Database getStudentRepository() {
        return this.studentRepository;
    }

    private void setStudentRepository(Database studentRepository) {
        this.studentRepository = studentRepository;
    }


    public ContentComparer getTester() {
        return this.tester;
    }

    private void setTester(ContentComparer tester) {
        this.tester = tester;
    }

    protected DirectoryManager getIoManager() {
        return this.ioManager;
    }

    private void setIoManager(DirectoryManager ioManager) {
        this.ioManager = ioManager;
    }

    public AsyncDownloader getDownloadManager() {
        return this.downloadManager;
    }

    private void setDownloadManager(AsyncDownloader downloadManager) {
        this.downloadManager = downloadManager;
    }

    public abstract void execute() throws Exception;
}
