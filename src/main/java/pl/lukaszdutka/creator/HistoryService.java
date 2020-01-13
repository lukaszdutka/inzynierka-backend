package pl.lukaszdutka.creator;

import org.springframework.stereotype.Service;
import pl.lukaszdutka.History;
import pl.lukaszdutka.tags.ConstantTag;
import pl.lukaszdutka.tags.Tag;
import pl.lukaszdutka.tags.TagFactory;

import java.util.HashMap;
import java.util.Map;

@Service
public class HistoryService {

    private final TagFactory tagFactory;
    private final Map<String, History> histories = new HashMap<>();

    public HistoryService(TagFactory tagFactory) {
        this.tagFactory = tagFactory;
    }

    public History getHistory(String id) {
        return histories.computeIfAbsent(id, key -> new History(tagFactory, key));
    }

    public History rerollTag(String historyId, String tagId) {
        if (historyDoesNotExist(historyId)) {
            return History.empty();
        }
        if (tagDoesNotExistWithinHistory(historyId, tagId)) {
            return histories.get(historyId);
        }

        History history = histories.get(historyId);
        Tag tag = history.findTag(tagId);

        Tag newTag = tagFactory.reroll(tag, history);

        if (newTag instanceof ConstantTag) {
            ConstantTag newTagConstant = (ConstantTag) newTag;
            history.putConstant(newTagConstant.getConstant(), newTagConstant);
        }

        history.replace(tag, newTag);

        return history;
    }

    private boolean tagDoesNotExistWithinHistory(String historyId, String tagId) {
        return histories.get(historyId).findTag(tagId) == null;
    }

    private boolean historyDoesNotExist(String historyId) {
        return !histories.containsKey(historyId);
    }


}
