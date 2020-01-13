package pl.lukaszdutka.entities;

import pl.lukaszdutka.History;

public class HistoryEntity {

    private String id;
    private TagEntity origin;

    private HistoryEntity() {
    }

    public static HistoryEntity of(History history) {
        HistoryEntity historyEntity = new HistoryEntity();

        historyEntity.id = history.getHistoryId();
        historyEntity.origin = TagEntity.of(history.getOrigin());

        return historyEntity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TagEntity getOrigin() {
        return origin;
    }

    public void setOrigin(TagEntity origin) {
        this.origin = origin;
    }

    @Override
    public String toString() {
        return "HistoryEntity{" +
                "id='" + id + '\'' +
                ", origin=" + origin +
                '}';
    }
}
