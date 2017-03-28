package bg.softuni.io.commands;

import bg.softuni.annotations.Alias;
import bg.softuni.annotations.Inject;
import bg.softuni.contracts.network.AsyncDownloader;

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
