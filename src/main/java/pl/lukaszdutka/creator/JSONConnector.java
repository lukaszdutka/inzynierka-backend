package pl.lukaszdutka.creator;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class JSONConnector {

    private static HashMap<String, ArrayList<String>> MAP;

    private final String PATH;
    private static final String PATH_STANDARD = "/Users/user/IdeaProjects/MagnumOpusRest/src/main/resources/source_8_steps.json";
    //    private static final String PATH_STANDARD = "/Users/user/IdeaProjects/MagnumOpusRest/src/main/resources/source.json";
    private static final String PATH_SMALL = "/Users/user/IdeaProjects/MagnumOpusRest/src/main/resources/source_small.json";

    public static JSONConnector createStandardConfiguration() {
        return new JSONConnector(TypeOfHistory.STANDARD);
    }

    public static JSONConnector createTestConfiguration() {
        return new JSONConnector(TypeOfHistory.TEST);
    }


    public ArrayList<String> getListForKey(String key) {
        return MAP.get(key) != null ? MAP.get(key) : new ArrayList<>();
    }

    private JSONConnector(TypeOfHistory typeOfHistory) {
        if (typeOfHistory == TypeOfHistory.STANDARD) {
            PATH = PATH_STANDARD;
        } else if (typeOfHistory == TypeOfHistory.TEST) {
            PATH = PATH_SMALL;
        } else {
            PATH = "";
        }
        config();
    }

    private void config() {
        configMap();
    }

    private void configMap() {
        try {
            MAP = new ObjectMapper().readValue(new File(PATH), HashMap.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private enum TypeOfHistory {
        STANDARD, TEST
    }

}
