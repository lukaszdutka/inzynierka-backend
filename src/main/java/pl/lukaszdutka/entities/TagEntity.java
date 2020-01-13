package pl.lukaszdutka.entities;

import pl.lukaszdutka.tags.ChapterTag;
import pl.lukaszdutka.tags.ConstantTag;
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
    private String secondaryColor;
    private boolean clickable;
    private List<TagEntity> children;

    private TagEntity() {
    }

    public static TagEntity of(Tag tag) {
        Map<String, String> savedColors = new HashMap<>();
        return of(tag, savedColors, 0);
    }

    private static TagEntity of(Tag tag, Map<String, String> savedColors, int depth) {
        TagEntity tagEntity = new TagEntity();

        tagEntity.id = tag.getTagId();
        tagEntity.textInside = tag.getStory();
        tagEntity.color = getColor(depth);
        tagEntity.clickable = isClickable(tag);

        if (tag instanceof ConstantTag) {
            if (savedColors.containsKey(tag.getTagId())) {
                tagEntity.secondaryColor = savedColors.get(tag.getTagId());
            } else {
                tagEntity.secondaryColor = getSecondaryColor(savedColors.size());
                savedColors.put(tag.getTagId(), tagEntity.secondaryColor);
            }
        }

        List<TagEntity> children = new ArrayList<>();

        for (Tag child : tag.getChildren()) {
            children.add(TagEntity.of(child, savedColors, depth + 1));
        }
        tagEntity.setChildren(children);

        return tagEntity;
    }

    private static boolean isClickable(Tag tag) {
        return !(tag instanceof ChapterTag);
    }

    private static String getColor(int depth) {
        return COLOR[depth % COLOR.length];
    }

    private static String getSecondaryColor(int colorIndex) {
        return SECONDARY_COLOR[colorIndex % SECONDARY_COLOR.length];
    }

    public String getSecondaryColor() {
        return secondaryColor;
    }

    public void setSecondaryColor(String secondaryColor) {
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

    public boolean isClickable() {
        return clickable;
    }

    private void setClickable(boolean clickable) {
        this.clickable = clickable;
    }


    @Override
    public String toString() {
        return "TagEntity{" +
                "id='" + id + '\'' +
                ", textInside='" + textInside + '\'' +
                ", color='" + color + '\'' +
                ", secondaryColor='" + secondaryColor + '\'' +
                ", children=" + children +
                ", clickable=" + clickable +
                '}';
    }
}
