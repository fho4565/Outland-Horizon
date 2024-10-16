package com.arc.outland_horizon.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {
    public static void writeFile(String path,String content) throws IOException {
        File file = new File(path);
        if (!file.exists()){
            if (!file.getParentFile().exists()){
                if (file.getParentFile().mkdirs()) {
                    if(file.createNewFile()){
                        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                        writer.write(content);
                        writer.flush();
                        writer.close();
                    }
                }
            }
        }
    }
}
