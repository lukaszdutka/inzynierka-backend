package pl.lukaszdutka.creator;

import org.springframework.stereotype.Service;
import pl.lukaszdutka.History;
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
        if (!histories.containsKey(historyId)) {
            return null;
        }
        History history = histories.get(historyId);

        Tag tag = history.findTag(tagId);
        if (tag == null) {
            return null;
        }

        Tag rerolledTag = tagFactory.reroll(tag, history);

        history.replace(tag, rerolledTag);

        return history;
    }

}
