package bg.softuni.contracts.network;

public interface AsyncDownloader extends Downloader{
    void downloadOnNewThread(String fileUrl);
}
