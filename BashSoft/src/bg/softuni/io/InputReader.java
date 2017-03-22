package bg.softuni.io;

import bg.softuni.contracts.io.Interpreter;
import bg.softuni.contracts.io.Reader;
import bg.softuni.staticData.SessionData;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class InputReader implements Reader{

    private final String END_COMMAND = "quit";

    private Interpreter interpreter;

    public InputReader(Interpreter interpreter) {
        this.setInterpreter(interpreter);
    }

    public Interpreter getInterpreter() {
        return this.interpreter;
    }

    private void setInterpreter(Interpreter interpreter) {
        this.interpreter = interpreter;
    }

    @Override
    public void readCommands() throws Exception {
        OutputWriter.writeMessage(String.format("%s > ", SessionData.currentPath));

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input = reader.readLine().trim();

        while (!input.equals(END_COMMAND)) {
            this.getInterpreter().interpretCommand(input);
            OutputWriter.writeMessage(String.format("%s > ", SessionData.currentPath));

            input = reader.readLine().trim();
        }

        for (Thread thread : SessionData.threadPool) {
            thread.join();
        }
    }
}
