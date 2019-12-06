package pl.lukaszdutka;

import pl.lukaszdutka.tags.ChapterTag;
import pl.lukaszdutka.tags.ConstantTag;
import pl.lukaszdutka.tags.Tag;
import pl.lukaszdutka.tags.TagFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class History {

    private String historyId;
    private int chapterCounter = 1;
    private Tag origin;
    private Map<String, ConstantTag> constants;

    public History(TagFactory tagFactory) {
        this(tagFactory, UUID.randomUUID().toString());
    }

    public History(TagFactory tagFactory, String historyId) {
        this.constants = new HashMap<>();
        this.historyId = historyId;
        this.origin = tagFactory.create("history", this);
    }

    public String getHistoryId() {
        return historyId;
    }

    public int getAndIncrementChapterCounter() {
        return chapterCounter++;
    }

    public void recalculateChapters() {
        this.chapterCounter = 1;
        recalculateChapters(origin);
    }

    private void recalculateChapters(Tag root) {
        for (Tag child : root.getChildren()) {
            if (child.isChapter()) {
                ((ChapterTag) child).setChapterNumber(getAndIncrementChapterCounter());
            } else {
                recalculateChapters(child);
            }
        }
    }

    Tag get() {
        return origin;
    }

    public boolean hasConstant(String key) {
        return constants.containsKey(key);
    }

    public void putConstant(String key, ConstantTag constantTag) {
        constants.put(key, constantTag);
    }

    public ConstantTag getConstant(String key) {
        return constants.get(key);
    }

    public Tag findTag(String tagId) {
        if (origin.getTagId().equals(tagId)) {
            return origin;
        }
        return findTag(tagId, origin);
    }

    private Tag findTag(String tagId, Tag parent) {
        for (Tag child : parent.getChildren()) {
            if (child.getTagId().equals(tagId)) {
                return child;
            } else {
                Tag tag = findTag(tagId, child);
                if (tag != null) {
                    return tag;
                }
            }
        }
        return null;
    }

    public void setId(String id) {
        this.historyId = id;
    }


    public void replace(Tag oldTag, Tag newTag) {
        if (oldTag.getTagId().equals(origin.getTagId())) {
            origin = newTag;
        } else {
            replace(origin, oldTag, newTag);
        }
        reset();
    }

    private void reset() {
        recalculateChapters();
    }

    private void replace(Tag parent, Tag oldTag, Tag newTag) {
        parent.getChildren().replaceAll(a -> a.getTagId().equals(oldTag.getTagId()) ? newTag : a);
        parent.getChildren().forEach(
                child -> replace(child, oldTag, newTag)
        );
    }
}
