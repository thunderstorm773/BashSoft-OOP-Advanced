package main.bg.softuni.io.commands;

import main.bg.softuni.annotations.Alias;
import main.bg.softuni.annotations.Inject;
import main.bg.softuni.contracts.io.DirectoryManager;

@Alias(value = "ls")
public class TraverseFoldersCommand extends Command{

    @Inject
    private DirectoryManager ioManager;

    public TraverseFoldersCommand(String input, String[] data) {
        super(input, data);
    }

    @Override
    public void execute() throws Exception {
        if (this.getData().length != 1 && this.getData().length != 2) {
            DisplayInvalidCommandMessage invalidCommandMessage =
                    new DisplayInvalidCommandMessage(this.getInput(), this.getData());
            invalidCommandMessage.execute();
            return;
        }

        if (this.getData().length == 1) {
            this.ioManager.traverseDirectory(0);
        }

        if (this.getData().length == 2) {
            this.ioManager.traverseDirectory(Integer.valueOf(this.getData()[1]));
        }
    }
}
