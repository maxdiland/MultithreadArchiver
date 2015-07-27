package com.gmail.gorpenko.decompressor;

import javafx.util.Pair;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;


public class MultiThreadDecompressor implements Decompressor {

    public static void main(String[] args) {
        new MultiThreadDecompressor().decompress(new File("d://result2.gz"), new File("d://result2.log"));
    }

    public static final int BATCH_SIZE = 1024 * 1024;
    @Override
    public void decompress(File fileToDecompress, File destinationFile) {
        long time = System.nanoTime();
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        try {
            InputStream inputStream = new BufferedInputStream(new GZIPInputStream(new FileInputStream(fileToDecompress)));
            int readByte;
            ExecutorService executorService = Executors.newFixedThreadPool(availableProcessors);
            List<Future<Pair<Integer,ByteArrayInputStream>>> list = new LinkedList<>();
            byte data[] = new byte[BATCH_SIZE];
            while ((readByte = inputStream.read(data,0,BATCH_SIZE)) > 0){
                list.add(executorService.submit(new ThreadUnZipping(Arrays.copyOf(data, readByte))));
            }

            List<Pair<Integer,ByteArrayInputStream>> result  = new LinkedList<>();
            for (Future<Pair<Integer,ByteArrayInputStream>> future : list) {
                result.add(future.get());
            }

            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(destinationFile));

            result.forEach((pair)->{
                byte[] readData = new byte[BATCH_SIZE];
                try {
                    int readBytes = pair.getValue().read(readData);
                    out.write(Arrays.copyOf(readData,readBytes));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            out.flush();

            executorService.shutdown();
            System.out.println((System.nanoTime() - time)/1000.0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

class ThreadUnZipping implements Callable<Pair<Integer,ByteArrayInputStream>> {
    static private int index=1;
    public int numThr;
    byte data[];



    public ThreadUnZipping(byte data[]){
        this.data = data.clone();
        numThr=index++;
    }

    @Override
    public Pair<Integer,ByteArrayInputStream> call() throws Exception {
        //System.out.println("Thread #" + numThr+" start working");
        //TimeUnit.SECONDS.sleep(5);
        ByteArrayOutputStream out = new ByteArrayOutputStream(data.length);
        OutputStream outputStream = new BufferedOutputStream(out);
        outputStream.write(data);
        // System.out.println("Thread #" + numThr);
        outputStream.close();
        return new Pair<>(numThr, new ByteArrayInputStream(out.toByteArray()));
    }
}