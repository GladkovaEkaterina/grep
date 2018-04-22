package com.tools;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Prog {
    @Option(name = "-i", usage = "Ignore register")
    private boolean ign = false;

    @Option(name = "-v", usage = "Invert statement")
    private boolean inv = false;

    @Option(name = "-r", usage = "Complex statement (regular expression)")
    private boolean reg = false;

    @Argument
    private String[] str;

    private String word = null;
    private String fname = null;

    public Prog(String[] args) {
        CmdLineParser cmd = new CmdLineParser(this);
        try {
            cmd.parseArgument(args);
            word = str[0];
            fname = str[1];
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            cmd.printUsage(System.out);
        }
    }

    public ArrayList<String> run() throws IOException {
        BufferedReader fl = new BufferedReader(new FileReader(fname));
        ArrayList<String> res = new ArrayList<String>();
        String tmp;
        if (reg) {
            while ((tmp = fl.readLine()) != null) {
                if (!tmp.matches(word) && inv)
                    res.add(tmp);
                if (tmp.matches(word) && !inv)
                    res.add(tmp);
            }
        } else {
            if (ign)
                word = word.toLowerCase();
            while ((tmp = fl.readLine()) != null) {
                String line = tmp;
                if (ign)
                    line = tmp.toLowerCase();
                if (inv && !line.contains(word))
                    res.add(tmp);
                if (!inv && line.contains(word))
                    res.add(tmp);
            }
        }
        return res;
    }
}
