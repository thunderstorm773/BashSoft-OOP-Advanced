package main.bg.softuni.io.commands;

import main.bg.softuni.annotations.Alias;
import main.bg.softuni.annotations.Inject;
import main.bg.softuni.contracts.network.AsyncDownloader;

@Alias(value = "downloadasync")
public class DownloadFileOnNewThreadCommand extends Command{

    @Inject
    private AsyncDownloader downloadManager;

    public DownloadFileOnNewThreadCommand(String input, String[] data) {
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

        String fileUrl = this.getData()[1];
        this.downloadManager.downloadOnNewThread(fileUrl);
    }
}
