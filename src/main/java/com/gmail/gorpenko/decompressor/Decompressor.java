package com.gmail.gorpenko.decompressor;

import java.io.File;


public interface Decompressor {
    void decompress(File fileToDecompress, File destinationFile);
}
