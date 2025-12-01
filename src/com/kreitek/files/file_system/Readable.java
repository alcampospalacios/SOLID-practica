package com.kreitek.files.file_system;

public interface Readable {
    void open();
    void close();
    byte[] read(int numberOfBytesToRead);
    void setPosition(int numberOfBytesFromBeginning);
}
