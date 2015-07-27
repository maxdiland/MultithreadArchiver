package com.gmail.gorpenko.compressor;


import javafx.util.Pair;

import java.io.File;
import java.io.*;
import java.util.Arrays;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;
import java.util.zip.GZIPOutputStream;


public class MultiThreadCompressor implements Compressor  {

    public static final int BATCH_SIZE = 1024 * 1024;

    public static void main(String[] args) {
        new MultiThreadCompressor().compress(new File("d://xxx-80-access.log"), new File("d://result2.gz"));
    }

    @Override
    public void compress(File fileToCompress, File destinationFile) {
        long time = System.nanoTime();
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        try(InputStream inputStream = new BufferedInputStream(new FileInputStream(fileToCompress))) {
            int readByte;
            ExecutorService executorService = Executors.newFixedThreadPool(availableProcessors);
            List<Future<Pair<Integer,ByteArrayInputStream>>>  list = new LinkedList<>();
            byte data[] = new byte[BATCH_SIZE];
            while ((readByte = inputStream.read(data,0,BATCH_SIZE)) > 0){
                //inputStream.skip(readByte);
                list.add(executorService.submit(new ThreadZipping(Arrays.copyOf(data,readByte))));
            }

            List<Pair<Integer,ByteArrayInputStream>> result  = new LinkedList<>();
            for (Future<Pair<Integer,ByteArrayInputStream>> future : list) {
                result.add(future.get());
            }


            //Collections.sort(result,(o1,o2)->o1.getKey().compareTo(o2.getKey()));
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

 class ThreadZipping implements Callable<Pair<Integer,ByteArrayInputStream>>{
   static private int index=1;
     public int numThr;
     byte data[];

     public ThreadZipping(byte data[]){
         this.data = data.clone();
         numThr=index++;
    }

     @Override
     public Pair<Integer,ByteArrayInputStream> call() throws Exception {
        //System.out.println("Thread #" + numThr+" start working");
         //TimeUnit.SECONDS.sleep(5);

         ByteArrayOutputStream out = new ByteArrayOutputStream(data.length);
         GZIPOutputStream gzipOutputStream = new GZIPOutputStream(out);
         gzipOutputStream.write(data);
        // System.out.println("Thread #" + numThr);
         gzipOutputStream.close();
         return new Pair<>(numThr, new ByteArrayInputStream(out.toByteArray()));
        }
  }