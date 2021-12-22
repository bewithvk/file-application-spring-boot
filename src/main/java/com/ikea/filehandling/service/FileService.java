package com.ikea.filehandling.service;

import java.io.File;
import java.util.ArrayList;



public class FileService {
    public ArrayList<String> listFilesForFolder(final File folder, String namePattern) {
        ArrayList<String> files = new ArrayList<>();

        String strPattern = namePattern;//"^[a-zA-Z0-9._ -]+\\.(doc|pdf|csv|xls)$";

        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry, namePattern);
            } else {
                if (fileEntry.getName().equals(strPattern)){
                    files.add(fileEntry.getName());
                }else{
                    files.add("No file matches!");
                }

            }
        }
        return files;
    }

}
