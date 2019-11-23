package pl.lukaszdutka.creator;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class JSONConnector {

    private static HashMap<String, ArrayList<String>> MAP;

    private final String PATH;
    private static final String PATH_12_STEPS = "/Users/user/IdeaProjects/MagnumOpusRest/src/main/resources/source_12_steps.json";
    private static final String PATH_STANDARD = "/Users/user/IdeaProjects/MagnumOpusRest/src/main/resources/source.json";


    public static JSONConnector createStandardConfiguration() {
        return new JSONConnector(TypeOfHistory.STANDARD);
    }

    public static JSONConnector create12StepsConfiguration() {
        return new JSONConnector(TypeOfHistory.TWELVE_STEPS);
    }

    public ArrayList<String> getListForKey(String key) {
        return MAP.get(key) != null ? MAP.get(key) : new ArrayList<>();
    }

    private JSONConnector(TypeOfHistory typeOfHistory) {
        switch (typeOfHistory) {
            case STANDARD -> PATH = PATH_STANDARD;
            case TWELVE_STEPS -> PATH = PATH_12_STEPS;
            default -> PATH = "";
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
        TWELVE_STEPS, STANDARD
    }

}
