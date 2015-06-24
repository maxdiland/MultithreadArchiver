package com.gmail.gorpenko.compressor;

import java.io.File;

/**
 * author Maksim Diland
 */
public interface Compressor {
    void compress(File fileToCompress, File destinationFile);
}
