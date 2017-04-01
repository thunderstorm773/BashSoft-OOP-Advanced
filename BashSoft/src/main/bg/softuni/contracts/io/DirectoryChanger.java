package main.bg.softuni.contracts.io;

public interface DirectoryChanger {
    void changeCurrentDirRelativePath(String relativePath);
    void changeCurrentDirAbsolute(String absolutePath);
}
