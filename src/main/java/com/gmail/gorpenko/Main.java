package com.gmail.gorpenko;

import com.gmail.gorpenko.compressor.Compressor;
import com.gmail.gorpenko.decompressor.Decompressor;
import com.gmail.gorpenko.exception.InputParameterException;
import com.gmail.gorpenko.exception.ShowHelpNeededException;
import com.gmail.gorpenko.util.FileUtil;

import java.io.File;

import static com.gmail.gorpenko.string.Strings.*;

/**
 * Created by Filipp on 2015-06-21.
 */
public class Main {
    private static final String COMMAND_COMPRESS = "c";
    private static final String COMMAND_DECOMPRESS = "d";

    public static void main(String[] args) {
        try {
            run(args);
        } catch (ShowHelpNeededException showHelpException) {
            showHelp();
        } catch (InputParameterException inputParameterException) {
            System.out.println(inputParameterException.getMessage());
        }
    }

    /* Visible for testing */
    static void run(String[] args) {
        if (args.length < 3) {
            throw new ShowHelpNeededException();
        }

        String command = validateAndGetCommand(args[0]);
        File sourceFile = validateAndGetCompressingFile(args[1]);
        File destinationFile = validateAndGetDestinationFile(args[2]);
        Mode compressingMode = validateAndGetCompressingMode(args);

        if (COMMAND_COMPRESS.equals(command)) {
            doCompress(sourceFile, destinationFile, compressingMode);
        } else {
            doDecompress(sourceFile, destinationFile, compressingMode);
        }
    }

    private static void doCompress(File sourceFile, File destinationFile, Mode compressingMode) {
        Compressor compressor = compressingMode.getAssociatedCompressor();
        compressor.compress(sourceFile, destinationFile);
    }

    private static void doDecompress(File sourceFile, File destinationFile, Mode compressingMode) {
        Decompressor decompressor = compressingMode.getAssociatedDecompressor();
        decompressor.decompress(sourceFile, destinationFile);
    }

    /* Visible for testing */
    static String validateAndGetCommand(String command) {
        if (COMMAND_COMPRESS.equalsIgnoreCase(command) || COMMAND_DECOMPRESS.equalsIgnoreCase(command)) {
            return command;
        }
        throw new InputParameterException(String.format(INPUT_COMMAND_INVALID, command));
    }

    /* Visible for testing */
    static File validateAndGetCompressingFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            return file;
        }
        throw new InputParameterException(String.format(INPUT_FILE_NOT_EXIST, path));
    }

    /* Visible for testing */
    static File validateAndGetDestinationFile(String destFilePath) {
        File file = new File(destFilePath);
        if (file.exists()) {
            throw new InputParameterException(String.format(INPUT_DESTINATION_FILE_ALREADY_EXIST, destFilePath));
        }

        File destinationFolder = file.getAbsoluteFile().getParentFile();
        if (FileUtil.fileNotExist(destinationFolder)) {
            throw new InputParameterException(String.format(INPUT_DESTINATION_FOLDER_NOT_EXIST, destinationFolder));
        }

        return file;
    }

    /* Visible for testing */
    static Mode validateAndGetCompressingMode(String[] args) {
        if (args.length < 4) {
            return Mode.SIMPLE;
        } else {
            String mode = args[3];
            try {
                return Mode.valueOf(mode.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new InputParameterException(String.format(INPUT_UNKNOWN_MODE, mode));
            }
        }
    }

    private static void showHelp() {
        System.out.println(HOW_TO_USE_HELP);
    }
}
