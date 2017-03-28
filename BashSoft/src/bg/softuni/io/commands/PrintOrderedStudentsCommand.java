package bg.softuni.io.commands;

import bg.softuni.annotations.Alias;
import bg.softuni.annotations.Inject;
import bg.softuni.contracts.repository.Database;
import bg.softuni.io.OutputWriter;
import bg.softuni.staticData.ExceptionMessages;

@Alias(value = "order")
public class PrintOrderedStudentsCommand extends Command{

    @Inject
    private Database repository;

    public PrintOrderedStudentsCommand(String input, String[] data) {
        super(input, data);
    }

    private void tryParseParametersForOrder(
            String takeCommand, String takeQuantity,
            String courseName, String orderType) {
        if (!takeCommand.equals("take")) {
            OutputWriter.displayException(ExceptionMessages.INVALID_TAKE_COMMAND);
            return;
        }

        if (takeQuantity.equals("all")) {
            this.repository.orderAndTake(courseName, orderType);
            return;
        }

        try {
            int studentsToTake = Integer.parseInt(takeQuantity);
            this.repository.orderAndTake(courseName, orderType, studentsToTake);
        } catch (NumberFormatException nfe) {
            OutputWriter.displayException(ExceptionMessages.IVALID_TAKE_QUANTITY_PARAMETER);
        }
    }

    @Override
    public void execute() throws Exception {
        if (this.getData().length != 5) {
            DisplayInvalidCommandMessage invalidCommandMessage =
                    new DisplayInvalidCommandMessage(this.getInput(), this.getData());
            invalidCommandMessage.execute();
            return;
        }

        String courseName = this.getData()[1];
        String orderType = this.getData()[2].toLowerCase();
        String takeCommand = this.getData()[3].toLowerCase();
        String takeQuantity = this.getData()[4].toLowerCase();

        this.tryParseParametersForOrder(takeCommand, takeQuantity, courseName, orderType);
    }
}
