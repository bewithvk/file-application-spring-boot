package com.ikea.filehandling.service;

import java.io.File;
import java.nio.file.*;
import java.util.ArrayList;

public class FileService {
    public ArrayList<String> listFilesForFolder(final File folder, String namePattern) {
        ArrayList<String> files = new ArrayList<>();
        String pattern = namePattern; //"*.{txt,doc}";

        FileSystem fs = FileSystems.getDefault();
        final PathMatcher matcher = fs.getPathMatcher("glob:" + pattern);

        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry, namePattern);
            } else {
                if (matcher.matches(Paths.get(fileEntry.getName()))) {
                    files.add(fileEntry.getName());
                }
            }
        }
        return files;
    }

}
