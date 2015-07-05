package com.gmail.gorpenko.compressor;

import com.gmail.gorpenko.exception.CompressionDecompressionException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

/**
 * author Maksim Diland
 */
public class SimpleCompressor implements Compressor {
    @Override
    public void compress(File fileToCompress, File destinationFile) {
        try ( InputStream inputStream = new BufferedInputStream(new FileInputStream(fileToCompress));
                OutputStream outputStream = new GZIPOutputStream(new FileOutputStream(destinationFile), true) ) {

            int readByte;
            while ( (readByte = inputStream.read()) != -1 ) {
                outputStream.write(readByte);
            }
            outputStream.flush();

        } catch (IOException e) {
            throw new CompressionDecompressionException(e);
        }
    }
}
