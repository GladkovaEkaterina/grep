package com.gladkova.katja;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProgTest {
    @Rule
    TemporaryFolder fld = new TemporaryFolder();
    private String path = null;

    @BeforeAll
    public void setUp() throws IOException {
        File tst = null;
        try {
            fld.create();
            tst = fld.newFile("input");
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter wr = new PrintWriter(tst);
        path = tst.getAbsolutePath();
        wr.println("11\ntEst1\n222\nres");
        wr.flush();
    }

    @Test
    public void simple() {
        String[] args = {"E", path};
        ArrayList<String> strings = null;
        try {
            strings = new Prog(args).run();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Set<String> set = new HashSet<String>(strings);
        Assert.assertEquals(new HashSet<String>(Arrays.asList(new String[]{"tEst1"})), set);
    }

    @Test
    public void simpleRev() {
        String[] args = {"-v", "E", path};
        ArrayList<String> strings = null;
        try {
            strings = new Prog(args).run();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Set<String> set = new HashSet<String>(strings);
        Assert.assertEquals(new HashSet<String>(Arrays.asList("11", "222", "res")), set);
    }

    @Test
    public void simpleLow() {
        String[] args = {"-i", "E", path};
        ArrayList<String> strings = null;
        try {
            strings = new Prog(args).run();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Set<String> set = new HashSet<String>(strings);
        Assert.assertEquals(new HashSet<String>(Arrays.asList("tEst1", "res")), set);
    }

    @Test
    public void simpleReg() {
        String[] args = {"-r", "\\d+", path};
        ArrayList<String> strings = null;
        try {
            strings = new Prog(args).run();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Set<String> set = new HashSet<String>(strings);
        Assert.assertEquals(new HashSet<String>(Arrays.asList("11", "222", "tEst1")), set);
    }
}