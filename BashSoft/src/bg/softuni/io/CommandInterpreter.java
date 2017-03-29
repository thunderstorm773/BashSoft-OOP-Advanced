package bg.softuni.io;

import bg.softuni.annotations.Alias;
import bg.softuni.annotations.Inject;
import bg.softuni.contracts.Executable;
import bg.softuni.contracts.io.DirectoryManager;
import bg.softuni.contracts.io.Interpreter;
import bg.softuni.contracts.judge.ContentComparer;
import bg.softuni.contracts.network.AsyncDownloader;
import bg.softuni.contracts.repository.Database;
import bg.softuni.io.commands.DisplayInvalidCommandMessage;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class CommandInterpreter implements Interpreter {

    private static final String COMMANDS_LOCATION = "src/bg/softuni/io/commands";
    private static final String COMMANDS_PACKAGE = "bg.softuni.io.commands.";

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
        File commandsFolder = new File(COMMANDS_LOCATION);
        Executable executable = null;
        boolean isCommandExist = false;

        for (File file : commandsFolder.listFiles()) {
            if (!file.isFile() || !file.getName().endsWith(".java")) {
                continue;
            }

            try {
                String className = file.getName()
                        .substring(0, file.getName().lastIndexOf('.'));
                Class<Executable> exeClass = (Class<Executable>)
                        Class.forName(COMMANDS_PACKAGE + className);

                if (!exeClass.isAnnotationPresent(Alias.class)) {
                    continue;
                }

                Alias alias = exeClass.getAnnotation(Alias.class);
                String value = alias.value();
                if (!value.equalsIgnoreCase(commandName)) {
                    continue;
                }

                isCommandExist = true;
                Constructor exeCtor = exeClass.getConstructor(String.class, String[].class);
                executable = (Executable) exeCtor.newInstance(input, data);
                this.injectDependencies(executable, exeClass);

            }catch (ReflectiveOperationException rfe) {
                rfe.printStackTrace();
            }
        }

        if (!isCommandExist) {
            executable = new DisplayInvalidCommandMessage(input, data);
        }

        return executable;
    }

    private void injectDependencies(Executable executable, Class<Executable> exeClass)
        throws ReflectiveOperationException{

        Field[] exeFields = exeClass.getDeclaredFields();
        for (Field fieldToSet : exeFields) {
            if (!fieldToSet.isAnnotationPresent(Inject.class)) {
                continue;
            }

            fieldToSet.setAccessible(true);
            Field[] theseFields = CommandInterpreter.class.getDeclaredFields();
            for (Field thisField : theseFields) {
                if (!thisField.getType().equals(fieldToSet.getType())) {
                    continue;
                }

                thisField.setAccessible(true);
                fieldToSet.set(executable, thisField.get(this));
            }
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
