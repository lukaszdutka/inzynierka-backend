package pl.lukaszdutka.tags;

public class InvalidTag extends Tag {

    InvalidTag(String tagString) {
        super(tagString, getUndefined(tagString));
    }

    private static String getUndefined(String tagString) {
        return "[" + tagString + "=UNDEFINED]";
    }

    @Override
    public String getStory() {
        return "[" + key + "=UNDEFINED]";
    }

    @Override
    public boolean isInvalid() {
        return true;
    }

    @Override
    public String getTagString() {
        return super.getTagString();
    }
}
