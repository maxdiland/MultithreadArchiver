package com.gmail.gorpenko.decompressor;

import com.gmail.gorpenko.exception.CompressionDecompressionException;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;

/**
 * author Maksim Diland
 */
public class SimpleDecompressor implements Decompressor {

    @Override
    public void decompress(File fileToDecompress, File destinationFile) {
        try ( InputStream inputStream = new BufferedInputStream(new GZIPInputStream(new FileInputStream(fileToDecompress)));
              OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(destinationFile)) ) {

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
