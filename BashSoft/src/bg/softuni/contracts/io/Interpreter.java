package bg.softuni.contracts.io;

import java.io.IOException;

public interface Interpreter {
    void interpretCommand(String input) throws IOException;
}
