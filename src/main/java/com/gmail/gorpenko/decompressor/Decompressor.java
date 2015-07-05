package com.gmail.gorpenko.decompressor;

import java.io.File;

/**
 * author Maksim Diland
 */
public interface Decompressor {
    void decompress(File fileToDecompress, File destinationFile);
}
