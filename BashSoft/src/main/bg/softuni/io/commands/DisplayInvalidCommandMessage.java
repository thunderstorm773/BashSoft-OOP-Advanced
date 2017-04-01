package main.bg.softuni.io.commands;

import main.bg.softuni.io.OutputWriter;

public class DisplayInvalidCommandMessage extends Command{

    public DisplayInvalidCommandMessage(String input, String[] data) {
        super(input, data);
    }

    @Override
    public void execute() throws Exception {
        String output = String.format("The command '%s' is invalid", this.getInput());
        OutputWriter.displayException(output);
    }
}
