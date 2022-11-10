package Hadgehogs.baseCore;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;


public class resourceReader {
    public static String readResourceTxtFile(String filename) {
        InputStream resource = resourceReader.class.getClassLoader().getResourceAsStream(filename);
        return new BufferedReader(new InputStreamReader(resource)).lines().collect(Collectors.joining(""));
    }

    public static String readResourceTxtFileWithoutJoin(String filename) {
        InputStream resource = resourceReader.class.getClassLoader().getResourceAsStream(filename);
        return new BufferedReader(new InputStreamReader(resource)).lines().collect(Collectors.joining("\n"));
    }

}
