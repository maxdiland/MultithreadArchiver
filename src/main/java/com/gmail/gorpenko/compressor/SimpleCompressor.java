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


public class SimpleCompressor implements Compressor {
    public static void main(String[] args) {
        new SimpleCompressor().compress(new File("d://result2.gz"), new File("d://result.gz"));
    }
    @Override
    public void compress(File fileToCompress, File destinationFile) {
        long start = System.nanoTime();
        try ( InputStream inputStream = new BufferedInputStream(new FileInputStream(fileToCompress));
                OutputStream outputStream = new GZIPOutputStream(new FileOutputStream(destinationFile), true) ) {

            byte data[] = new byte[1024];
            int readByte;
            while ( (readByte = inputStream.read(data,0,1024)) != -1 ) {
                   outputStream.write(data,0,readByte);
            }
            outputStream.flush();
            System.out.println((System.nanoTime()-start)/1000.0);
        } catch (IOException e) {
            throw new CompressionDecompressionException(e);
        }
    }
}
