package main.bg.softuni.io.commands;

import main.bg.softuni.annotations.Alias;
import main.bg.softuni.annotations.Inject;
import main.bg.softuni.contracts.repository.Database;

@Alias(value = "show")
public class ShowWantedCourseCommand extends Command{

    @Inject
    private Database repository;

    public ShowWantedCourseCommand(String input, String[] data) {
        super(input, data);
    }

    @Override
    public void execute() throws Exception {
        if (this.getData().length != 2 && this.getData().length != 3) {
            DisplayInvalidCommandMessage invalidCommandMessage =
                    new DisplayInvalidCommandMessage(this.getInput(), this.getData());
            invalidCommandMessage.execute();
            return;
        }

        if (this.getData().length == 2) {
            String courseName = this.getData()[1];
            this.repository.getStudentsByCourse(courseName);
        }

        if (this.getData().length == 3) {
            String courseName = this.getData()[1];
            String userName = this.getData()[2];
            this.repository.getStudentMarkInCourse(courseName, userName);
        }
    }
}
