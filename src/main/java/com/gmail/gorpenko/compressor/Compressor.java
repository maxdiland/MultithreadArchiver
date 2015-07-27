package com.gmail.gorpenko.compressor;

import java.io.File;


public interface Compressor {
    void compress(File fileToCompress, File destinationFile);
}
