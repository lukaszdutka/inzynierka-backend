package pl.lukaszdutka.tags;

public class VariableTag extends Tag {

    VariableTag(String tagString, String value) {
        super(tagString, value);
    }

    static String extractKey(String variableKeyString) {
        return variableKeyString;
    }

    @Override
    public String getStory() {
        return value;
    }

    @Override
    public String getTagString() {
        return super.getTagString();
    }
}
