package com.gmail.gorpenko;

import com.gmail.gorpenko.exception.InputParameterException;
import com.gmail.gorpenko.exception.ShowHelpNeededException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class MainTest {

    private static File tempFile;

    @BeforeClass
    public static void init() throws IOException {
        tempFile = File.createTempFile("testFile", ".tmp");
        tempFile.deleteOnExit();
    }


    @Test(expected = ShowHelpNeededException.class)
    public void startWithoutParameters() {
        String[] args = {};
        Main.run(args);
    }

    @Test(expected = ShowHelpNeededException.class)
    public void startWithOneParameter() {
        String[] args = {"c"};
        Main.run(args);
    }

    @Test(expected = ShowHelpNeededException.class)
    public void startWithTwoParameters() {
        String[] args = {"c", "test.txt"};
        Main.run(args);
    }

    @Test
    public void validateAndGetCommandTest01() {
        Main.validateAndGetCommand("c");
    }

    @Test
    public void validateAndGetCommandTest02() {
        Main.validateAndGetCommand("d");
    }

    @Test(expected = InputParameterException.class)
    public void validateAndGetCommandForUnknownCommand() {
        Main.validateAndGetCommand("");
    }

    @Test
    public void validateAndGetCompressingFileTest() {
        Main.validateAndGetCompressingFile(tempFile.getAbsolutePath());
    }

    @Test(expected = InputParameterException.class)
    public void validateAndGetCompressingFileForNonExistingFile() {
        long timestamp = System.currentTimeMillis();
        String nonExistingFilePath = "/nonExistingFolder" + timestamp + "/NonExistingFile" + timestamp + ".invalid";
        Main.validateAndGetCompressingFile(nonExistingFilePath);
    }

    @Test
    public void validateAndGetDestinationFileForNonExistingFileAndExistingDirectory() {
        long timestamp = System.currentTimeMillis();
        File nonExistingFile = new File( tempFile.getParentFile(), "nonExistingFile" + timestamp + ".nonExisting" );
        Main.validateAndGetDestinationFile(nonExistingFile.getAbsolutePath());
    }

    @Test(expected = InputParameterException.class)
    public void validateAndGetDestinationFileForNonExistingFileAndNonExistingDirectory() {
        long timestamp = System.currentTimeMillis();
        File nonExistingDirectory = new File(tempFile.getParentFile(), "nonExistingFolder" + timestamp);
        File nonExistingFile = new File( nonExistingDirectory, "nonExistingFile" + timestamp + ".nonExisting" );
        Main.validateAndGetDestinationFile(nonExistingFile.getAbsolutePath());
    }

    @Test(expected = InputParameterException.class)
    public void validateAndGetDestinationFileForExistingFile() {
        Main.validateAndGetDestinationFile(tempFile.getAbsolutePath());
    }

    @Test
    public void validateAndGetCompressingModeForNotPassedMode() {
        String[] args = {"", "", ""};
        Mode gottenMode = Main.validateAndGetCompressingMode(args);

        Assert.assertEquals(Mode.SIMPLE, gottenMode);
    }

    @Test
    public void validateAndGetCompressingModeForSimpleMode() {
        String[] args = {"", "", "", "sImple"};
        Mode gottenMode = Main.validateAndGetCompressingMode(args);

        Assert.assertEquals(Mode.SIMPLE, gottenMode);
    }

    @Test
    public void validateAndGetCompressingModeForMultiMode() {
        String[] args = {"", "", "", "muLTi"};
        Mode gottenMode = Main.validateAndGetCompressingMode(args);

        Assert.assertEquals(Mode.MULTI, gottenMode);
    }

    @Test(expected = InputParameterException.class)
    public void validateAndGetCompressingModeForUnknownMode() {
        String[] args = {"", "", "", "unknownMode"};
        Main.validateAndGetCompressingMode(args);
    }
}
