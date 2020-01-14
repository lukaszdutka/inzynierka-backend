package pl.lukaszdutka.tags;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Tag {

    private String tagId;
    String key;
    String value;
    private List<Tag> children;

    Tag(String key, String value) {
        this(key, value, UUID.randomUUID().toString());
    }

    Tag(String key, String value, String tagId) {
        if (key == null) {
            throw new IllegalArgumentException("Cannot instantiate Tag object. key is null!");
        }

        this.key = key;
        this.value = value;
        this.tagId = tagId;
        this.children = new ArrayList<>();
    }

    public abstract String getStory();

    public String getKey() {
        return key;
    }

    public String getTagId() {
        return tagId;
    }

    public String getTagString() {
        return key;
    }

    public void setChildren(List<Tag> children) {
        this.children = children;
    }

    public List<Tag> getChildren() {
        return children;
    }

    void addChild(Tag tag) {
        if (children.size() < countChildren()) {
            children.add(tag);
        }
    }

    protected int countChildren() {
        return (int) value.chars().filter(c -> c == '<').count();
    }

    public static Tag empty() {
        return new InvalidTag("empty");
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }
}
