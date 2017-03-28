package bg.softuni.io.commands;

import bg.softuni.annotations.Alias;
import bg.softuni.annotations.Inject;
import bg.softuni.contracts.io.DirectoryManager;

@Alias(value = "mkdir")
public class MakeDirectoryCommand extends Command{

    @Inject
    private DirectoryManager ioManager;

    public MakeDirectoryCommand(String input, String[] data) {
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

        String folderName = this.getData()[1];
        this.ioManager.createDirectoryInCurrentFolder(folderName);
    }
}
