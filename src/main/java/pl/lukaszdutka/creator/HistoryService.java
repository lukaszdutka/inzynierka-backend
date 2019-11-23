package pl.lukaszdutka.creator;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import pl.lukaszdutka.tags.ChapterTag;
import pl.lukaszdutka.tags.Tag;
import pl.lukaszdutka.tags.TagFactory;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class HistoryService {

    private static final String DEFAULT_HISTORY_TAG = "history";

    private final TagFactory TAG_FACTORY = new TagFactory();

    private final ImmutableList<Tag> EMPTY_LIST = ImmutableList.of();

    private Tag history;

    public Tag getHistoryTag() {
        getHistory();
        return history;
    }

    public String getHistory() {
        TAG_FACTORY.resetFactory();
        Tag mainTag = TAG_FACTORY.create(DEFAULT_HISTORY_TAG, null);
        return processTag(mainTag);
    }

    private String processTag(Tag parent) {
        if (DEFAULT_HISTORY_TAG.equals(parent.getKey())) {
            history = parent;
        }

        String story = parent.getStory();

        List<Tag> children = getAllTags(story, parent);

        //for each tag replace it with processTag(tag);
        for (Tag child : children) {
            story = replaceTag(story, child);
        }

        return story;
    }

    private List<Tag> getAllTags(String historyWithTags, Tag parent) {
        String[] tagsArray = StringUtils.substringsBetween(historyWithTags, "<", ">");
        if (tagsArray == null) {
            return EMPTY_LIST;
        }

        return Arrays.stream(tagsArray)
                .map(string -> TAG_FACTORY.create(string, parent))
                .collect(Collectors.toList());
    }

    private String replaceTag(String history, Tag tag) {
        String processedTag = processTag(tag);
        return StringUtils.replaceOnce(history, withBrackets(tag.getTagString()), processedTag);
    }

    private String withBrackets(String tag) {
        return "<" + tag + ">";
    }

    public Tag rerollTag(UUID id) {
        Tag parent = findParent(id);
        if (parent != null) {
            Tag tag = parent.getChildren().stream()
                    .filter(child -> child.getID().equals(id))
                    .findFirst()
                    .orElseThrow(
                            () -> new RuntimeException("tag is null"));


            if (tag.isChapter()) {
                return history;
            }

            Tag newTag = TAG_FACTORY.create(tag.getTagString(), null, true, tag.getStory());
            processTag(newTag);
            history.replaceAllChildrenWithGivenId(id, newTag, true);

            recalculateChapters(history);

            return history;
        } else {
            return getHistoryTag();
        }
    }

    private void recalculateChapters(Tag root) {
        chapterNumber = 1;
        recalculateChaptersRec(root);
        chapterNumber = 1;
    }

    private static int chapterNumber = 1;

    private void recalculateChaptersRec(Tag root) {
        for (Tag child : root.getChildren()) {
            if (child.isChapter()) {
                ((ChapterTag) child).setChapterNumber(chapterNumber);
                chapterNumber += 1;
            } else {
                recalculateChaptersRec(child);
            }
        }
    }

    private Tag findParent(UUID id) {
        if (history.getID().equals(id)) {
            return null;
        }
        if (history.getChildren().stream()
                .anyMatch(tag -> tag.getID().equals(id))) {
            return history;
        }
        return findParentRecursive(history, id);
    }

    private Tag findParentRecursive(Tag parent, UUID id) {
        if (parent.getChildren().stream()
                .anyMatch(tag -> tag.getID().equals(id))) {
            return parent;
        } else {
            for (Tag child : parent.getChildren()) {
                Tag tag = findParentRecursive(child, id);
                if (tag != null) {
                    return tag;
                }
            }
        }
        return null;
    }
}
