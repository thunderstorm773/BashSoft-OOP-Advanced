package bg.softuni.io.commands;

import bg.softuni.annotations.Alias;
import bg.softuni.annotations.Inject;
import bg.softuni.contracts.judge.ContentComparer;

@Alias(value = "cmp")
public class CompareFilesCommand extends Command{

    @Inject
    private ContentComparer tester;

    public CompareFilesCommand(String input, String[] data) {
        super(input, data);
    }

    @Override
    public void execute() throws Exception {
        if (this.getData().length != 3) {
            DisplayInvalidCommandMessage invalidCommandMessage =
                    new DisplayInvalidCommandMessage(this.getInput(), this.getData());
            invalidCommandMessage.execute();
            return;
        }

        String firstPath = this.getData()[1];
        String secondPath = this.getData()[2];
        this.tester.compareContent(firstPath, secondPath);
    }
}
