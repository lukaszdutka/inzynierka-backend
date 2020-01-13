package pl.lukaszdutka.tags;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Tag {

    String tagId;
    String key;
    String value;
    List<Tag> children;

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
        children.add(tag);
    }

    public void replaceAllChildrenWithGivenId(String id, Tag tag, boolean recursive) {
        children.replaceAll(child -> child.getTagId().equals(id) ? tag : child);
        if (recursive) {
            for (Tag child : children) {
                child.replaceAllChildrenWithGivenId(id, tag, true);
            }
        }
    }

    public static Tag empty() {
        return new InvalidTag("empty");
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }
}
