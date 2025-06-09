package com.project.Utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import javafx.fxml.FXMLLoader;

public class FileHandler {
    private static String folder = "/FXML/";

    public static String ReadFile(String Path) {
        ClassLoader cl = FileHandler.class.getClassLoader();

        try (InputStream is = cl.getResourceAsStream(Path)) {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String res = br.lines().collect(Collectors.joining("\n"));
            // System.out.println("Data : \n" + res);
            return res;
        } catch (Exception e) {
            System.out.println("********************* Error **********************");
            e.printStackTrace();
            return null;
        }

    }

    public static FXMLLoader loadFXML(String FileName) {
        return new FXMLLoader(FileHandler.class.getResource(folder + FileName + ".fxml"));
    }
}
