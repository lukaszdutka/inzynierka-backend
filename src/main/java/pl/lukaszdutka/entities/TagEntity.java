package pl.lukaszdutka.entities;

import pl.lukaszdutka.tags.Tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TagEntity {

    private static final String[] COLOR = {"#fff176", "#ffd54f", "#ffb74d", "#ffeb3b", "#ffc107", "ff9800"}; // zolto-pomaranczowe
    private static final String[] SECONDARY_COLOR = {"#ff8a65", "#ff7043", "#ff5722"};
    private String id;
    private String textInside;
    private String color;
    private List<TagEntity> children;
    private boolean clickable;
    private String secondaryColor;

    private TagEntity() {
    }

    public static TagEntity of(Tag tag) {
        Map<String, String> savedColors = new HashMap<>();
        return of(tag, savedColors, 0);
    }

    private static TagEntity of(Tag tag, Map<String, String> savedColors, int depth) {
        TagEntity tagEntity = new TagEntity();

        tagEntity.setId(tag.getTagId());
        tagEntity.setTextInside(tag.getStory());
        tagEntity.setColor(getColor(depth));
        tagEntity.setClickable(!tag.isChapter());

        if (tag.isConstant()) {
            if (savedColors.containsKey(tag.getTagId())) {
                tagEntity.setSecondaryColor(
                        savedColors.get(tag.getTagId()));
            } else {
                tagEntity.setSecondaryColor(SECONDARY_COLOR[depth % SECONDARY_COLOR.length]);
                savedColors.put(tag.getTagId(),
                        tagEntity.getSecondaryColor());
            }
        }

        List<TagEntity> children = new ArrayList<>();

        for (Tag child : tag.getChildren()) {
            children.add(TagEntity.of(child, savedColors, depth + 1));
        }
        tagEntity.setChildren(children);

        return tagEntity;
    }

    private static String getColor(int depth) {
        return COLOR[depth % COLOR.length];
    }

    private void setSecondaryColor(String secondaryColor) {
        this.secondaryColor = secondaryColor;
    }

    private void setId(String id) {
        this.id = id;
    }

    private void setTextInside(String textInside) {
        this.textInside = textInside;
    }

    private void setColor(String color) {
        this.color = color;
    }

    private void setChildren(List<TagEntity> children) {
        this.children = children;
    }

    public String getId() {
        return id;
    }

    public String getTextInside() {
        return textInside;
    }

    public String getColor() {
        return color;
    }

    public List<TagEntity> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        return "TagEntity{" +
                "id='" + id + '\'' +
                ", textInside='" + textInside + '\'' +
                ", color='" + color + '\'' +
                ", children=" + children +
                ", clickable=" + clickable +
                ", secondaryColor='" + secondaryColor + '\'' +
                '}';
    }

    public boolean isClickable() {
        return clickable;
    }

    private void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

    private String getSecondaryColor() {
        return secondaryColor;
    }
}
