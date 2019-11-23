package pl.lukaszdutka.tags;

import org.springframework.stereotype.Service;
import pl.lukaszdutka.creator.JSONConnector;

import java.util.*;

@Service
public class TagFactory {

    private final Map<String, ConstantTag> CONSTANTS = new HashMap<>();
    private final Random R = new Random();
    private final JSONConnector JSON_CONNECTOR = JSONConnector.createStandardConfiguration();

    private int chapterNumber = 1;

    public Tag create(String tagString, Tag parent) {
        return create(tagString, parent, false, "");
    }

    public Tag create(String tagString, Tag parent, boolean override, String previousValue) {

        Tag tag;
        if (isChapter(tagString)) {
            tag = createChapter(tagString);
        } else if (isVariable(tagString)) {
            tag = createVariable(tagString, previousValue);
        } else if (isConstant(tagString)) {
            tag = createConstant(tagString, override, previousValue);
        } else {
            tag = createInvalid(tagString);
        }

        addChildToParent(tag, parent);
        return tag;
    }

    private Tag createChapter(String tagString) {
        return new ChapterTag(tagString, chapterNumber++);
    }

    private boolean isChapter(String tagString) {
        return "chapter".equals(tagString);
    }

    private InvalidTag createInvalid(String tagString) {
        return new InvalidTag(tagString);
    }

    private void addChildToParent(Tag child, Tag parent) {
        if (parent != null) {
            parent.addChild(child);
        }
    }

    private VariableTag createVariable(String variableTagString, String previousValue) {
        String key = VariableTag.extractKey(variableTagString);
        String value = getRandomValueForKeyExcept(key, previousValue);

        return new VariableTag(variableTagString, value);
    }

    private ConstantTag createConstant(String constantTagString, boolean override, String previousValue) {
        String constant = ConstantTag.extractConstant(constantTagString);

        if (!CONSTANTS.containsKey(constant) || override) {
            String key = ConstantTag.extractKey(constantTagString);

            String value = getRandomValueForKeyExcept(key, previousValue);

            ConstantTag constantTag = new ConstantTag(constantTagString, value);

            CONSTANTS.put(constant, constantTag);
        }

        return CONSTANTS.get(constant);
    }

    private String getRandomValueForKeyExcept(String key, String previousValue) {
        List<String> values = new ArrayList<>(JSON_CONNECTOR.getListForKey(key));
        if (values.isEmpty()) {
            return key + "=INVALID";
        }
        values.remove(previousValue);
        if (values.isEmpty()) {
            return previousValue;
        }
        return values.get(R.nextInt(values.size()));
    }

    private static int countDots(String str) {
        char dot = '.';
        return (int) str.chars().filter(c -> c == dot).count();
    }

    private static boolean isVariable(String tag) {
        return countDots(tag) == 0;
    }

    private static boolean isConstant(String tag) {
        return countDots(tag) == 1;
    }

    public void resetFactory() {
        chapterNumber = 1;
        CONSTANTS.clear();
    }
}
