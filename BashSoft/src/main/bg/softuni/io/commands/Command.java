package main.bg.softuni.io.commands;

import main.bg.softuni.contracts.Executable;
import sun.plugin.dom.exception.InvalidStateException;

public abstract class Command implements Executable {

    private String input;
    private String[] data;

    protected Command(String input, String[] data) {

        this.setInput(input);
        this.setData(data);
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

    public abstract void execute() throws Exception;
}
