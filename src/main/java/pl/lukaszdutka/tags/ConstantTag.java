package pl.lukaszdutka.tags;

//constant means that if there cannot exist two ConstantTag objects with same "constant" field. Second one should be first one.

import java.util.UUID;

public class ConstantTag extends Tag {

    private String constant;

    ConstantTag(String tagString, String value) {
        this(tagString, value, UUID.randomUUID().toString());
    }

    ConstantTag(String tagString, String value, String id) {
        super(extractKey(tagString), value, id);
        this.constant = extractConstant(tagString);
    }

    @Override
    public String getStory() {
        return value;
    }

    static String extractConstant(String tagString) {
        return tagString.split("\\.")[0];
    }

    static String extractKey(String tagString) {
        return tagString.split("\\.")[1];
    }

    @Override
    public String getTagString() {
        return constant + "." + key;
    }

    public String getConstant() {
        return constant;
    }

}
