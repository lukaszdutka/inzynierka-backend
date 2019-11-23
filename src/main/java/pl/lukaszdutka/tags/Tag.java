package pl.lukaszdutka.tags;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Tag {

    private final UUID ID = UUID.randomUUID();
    String key;
    String value;
    private List<Tag> children;

    Tag(String key, String value) {
        if (key == null) {
            throw new IllegalArgumentException("Cannot instantiate Tag object. key is null!");
        }

        this.key = key;
        this.value = value;
        this.children = new ArrayList<>();
    }

    public abstract String getStory();

    public boolean isVariable() {
        return false;
    }

    public boolean isConstant() {
        return false;
    }

    public boolean isInvalid() {
        return false;
    }

    public boolean isChapter() {
        return false;
    }

    public String getKey() {
        return key;
    }

    public UUID getID() {
        return ID;
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

    public void replaceAllChildrenWithGivenId(UUID id, Tag tag, boolean recursive) {
        children.replaceAll(child -> child.getID().equals(id) ? tag : child);
        if (recursive) {
            for (Tag child : children) {
                child.replaceAllChildrenWithGivenId(id, tag, true);
            }
        }
    }
}
