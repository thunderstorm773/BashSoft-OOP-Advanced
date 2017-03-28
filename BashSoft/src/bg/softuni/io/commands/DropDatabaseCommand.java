package bg.softuni.io.commands;

import bg.softuni.annotations.Alias;
import bg.softuni.annotations.Inject;
import bg.softuni.contracts.repository.Database;
import bg.softuni.io.OutputWriter;

@Alias(value = "dropdb")
public class DropDatabaseCommand extends Command{

    @Inject
    private Database repository;

    public DropDatabaseCommand(String input, String[] data) {
        super(input, data);
    }

    @Override
    public void execute() throws Exception {
        if (this.getData().length != 1) {
            DisplayInvalidCommandMessage invalidCommandMessage =
                    new DisplayInvalidCommandMessage(this.getInput(), this.getData());
            invalidCommandMessage.execute();
            return;
        }
        this.repository.unloadData();
        OutputWriter.writeMessageOnNewLine("Database dropped!");
    }
}
