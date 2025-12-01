package com.kreitek.service;

import com.kreitek.files.File;
import com.kreitek.files.error.InvalidFileFormatException;

public class AudioConverter {

    public File convertMp3ToWav(File file) {
        if (!"mp3".equalsIgnoreCase(file.getExtension())) {
            throw new InvalidFileFormatException("El fichero debe ser mp3");
        }

        String newFileName = getNameWithoutExtension(file.getName()) + ".wav";
        File result = new File(file.getParent(), newFileName);
        result.open();
        // Lógica de conversión de mp3 a wav. Se lee de este fichero y se escribe en result
        result.close();
        return result;
    }

    public File convertWavToMp3(File file) {
        if (!"wav".equalsIgnoreCase(file.getExtension())) {
            throw new InvalidFileFormatException("El fichero debe ser wav");
        }

        String newFileName = getNameWithoutExtension(file.getName()) + ".mp3";
        File result = new File(file.getParent(), newFileName);
        result.open();
        // Lógica de conversión de wav a mp3. Se lee de este fichero y se escribe en result
        result.close();
        return result;
    }

    private String getNameWithoutExtension(String fileName) {
        int indexOfLastDot = fileName.lastIndexOf(".");
        if (indexOfLastDot > 0) {
            return fileName.substring(0, indexOfLastDot);
        }
        return fileName;
    }
}
