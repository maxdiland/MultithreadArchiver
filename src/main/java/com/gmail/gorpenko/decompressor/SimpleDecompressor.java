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


public class SimpleDecompressor implements Decompressor {
    public static void main(String[] args) {
        new SimpleDecompressor().decompress(new File("d://result2.gz"), new File("d://result2S.log"));
    }
    @Override
    public void decompress(File fileToDecompress, File destinationFile) {
        long time = System.nanoTime();
        try ( InputStream inputStream = new BufferedInputStream(new GZIPInputStream(new FileInputStream(fileToDecompress)));
              OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(destinationFile)) ) {

            int readByte;
            while ( (readByte = inputStream.read()) != -1 ) {
                outputStream.write(readByte);
            }
            outputStream.flush();
            System.out.println((System.nanoTime() - time)/1000.0);
        } catch (IOException e) {
            throw new CompressionDecompressionException(e);
        }
    }
}
