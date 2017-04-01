package main.bg.softuni.io.commands;

import main.bg.softuni.annotations.Alias;
import main.bg.softuni.annotations.Inject;
import main.bg.softuni.contracts.repository.Database;

@Alias(value = "readdb")
public class ReadDatabaseFromFileCommand extends Command{

    @Inject
    private Database repository;

    public ReadDatabaseFromFileCommand(String input, String[] data) {
        super(input, data);
    }

    @Override
    public void execute() throws Exception {
        if (this.getData().length != 2) {
            DisplayInvalidCommandMessage invalidCommandMessage =
                    new DisplayInvalidCommandMessage(this.getInput(), this.getData());
            invalidCommandMessage.execute();
            return;
        }

        String fileName = this.getData()[1];
        this.repository.loadData(fileName);
    }


}
