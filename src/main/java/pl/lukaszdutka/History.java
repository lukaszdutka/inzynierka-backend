package pl.lukaszdutka;

import pl.lukaszdutka.tags.Tag;

public class History {

    private Tag origin;

    private History(Tag origin) {
        this.origin = origin;
    }

    public static History createHistory() {
//        Tag origin = new Tag("origin");
//        return new History(origin);
        return null;
    }

    public String getStory() {
        return origin.getStory();
    }
}
