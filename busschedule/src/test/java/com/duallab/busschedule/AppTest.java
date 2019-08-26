package com.duallab.busschedule;


import org.junit.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    @Test
    public void checkCreatingFileOfResult() throws Exception{
        File inputDataFile = new File(this.getClass().getResource("/inputData.txt").getFile());
        String[] args = new String[] {inputDataFile.getPath()};
        App.main(args);
        Path target = Paths.get("result.txt");
        assertTrue(target.toFile().exists());
    }

    @Test(expected = Exception.class)
    public void checkExceptionForBadParameters() throws Exception{
        File inputDataFile = new File(this.getClass().getResource("/NoInputData.txt").getFile());
        String[] args = new String[] {"BadPath"};
        App.main(args);
        Path target = Paths.get("result.txt");
        assertTrue(target.toFile().exists());
    }
}
