package pl.lukaszdutka.tags;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import pl.lukaszdutka.History;
import pl.lukaszdutka.creator.JSONConnector;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TagFactory {

    private final Random R = new Random();
    private final JSONConnector JSON_CONNECTOR = JSONConnector.createStandardConfiguration();

    public Tag create(String tagString, History history) {

        Tag tag;
        if (isChapter(tagString)) {
            tag = createChapter(tagString, history);
        } else if (isVariable(tagString)) {
            tag = createVariable(tagString);
        } else if (isConstant(tagString)) {
            tag = createConstant(tagString, history);
        } else {
            tag = createInvalid(tagString);
        }

        extractChildren(tag, history)
                .forEach(tag::addChild);

        return tag;
    }

    private List<Tag> extractChildren(Tag tag, History history) {
        String[] tags = StringUtils.substringsBetween(tag.getStory(), "<", ">");
        if (tags == null) {
            return Collections.emptyList();
        }
        return Arrays.stream(tags)
                .map(string -> create(string, history))
                .collect(Collectors.toList());

    }

    private Tag createChapter(String tagString, History history) {
        return new ChapterTag(tagString, history.getAndIncrementChapterCounter());
    }

    private boolean isChapter(String tagString) {
        return "chapter".equals(tagString);
    }

    private InvalidTag createInvalid(String tagString) {
        return new InvalidTag(tagString);
    }

    private VariableTag createVariable(String variableTagString) {
        String key = VariableTag.extractKey(variableTagString);
        String value = getRandomValueForKey(key);

        return new VariableTag(variableTagString, value);
    }

    private ConstantTag createConstant(String constantTagString, History history) {
        String constant = ConstantTag.extractConstant(constantTagString);

        if (!history.hasConstant(constant)) {
            String key = ConstantTag.extractKey(constantTagString);

            String value = getRandomValueForKey(key);

            ConstantTag constantTag = new ConstantTag(constantTagString, value);

            history.putConstant(constant, constantTag);
        }

        return history.getConstant(constant);
    }

    private String getRandomValueForKey(String key) {
        List<String> values = new ArrayList<>(JSON_CONNECTOR.getListForKey(key));
        if (values.isEmpty()) {
            return key + "=INVALID";
        }
//        values.remove(previousValue);
//        if (values.isEmpty()) {
//            return previousValue;
//        }
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

    public Tag reroll(Tag previousTag, History history) {
        String previousTagString = previousTag.getTagString();

        Tag tag;
        if (isChapter(previousTagString)) {
            return null;
        } else if (isVariable(previousTagString)) {
            tag = createVariable(previousTagString);
        } else if (isConstant(previousTagString)) {
            tag = rerollConstant(previousTag, history);
        } else {
            tag = createInvalid(previousTagString);
        }

        extractChildren(tag, history)
                .forEach(tag::addChild);

        return tag;
    }

    private Tag rerollConstant(Tag previousTag, History history) {
        String constant = ConstantTag.extractConstant(previousTag.getTagString());
        String key = ConstantTag.extractKey(previousTag.getTagString());
        String value = getRandomValueForKey(key);

        ConstantTag constantTag = new ConstantTag(previousTag.getTagString(), value);
        constantTag.setTagId(previousTag.getTagId());

        history.putConstant(constant, constantTag);
        return history.getConstant(constant);
    }
}
