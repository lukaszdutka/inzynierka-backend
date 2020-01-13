package pl.lukaszdutka.tags;

import org.junit.jupiter.api.Test;
import pl.lukaszdutka.History;
import pl.lukaszdutka.creator.HistoryService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TagFactoryTest {

    private static final String historyId = "historyId";
    private HistoryService historyService = new HistoryService(new TagFactory());

    private final List<String> weapons = List.of("Sword", "Bow");
    private final List<String> people = List.of("Man", "Woman");

    @Test
    void create() {
        History h = historyService.getHistory(historyId);

        Tag origin = h.getOrigin();
        Tag constantPerson = origin.getChildren().get(0);
        Tag variableWeapon = origin.getChildren().get(1);

        assertTrue(oneOfPeople(constantPerson));
        assertTrue(OneOfWeapons(variableWeapon));
    }

    private boolean OneOfWeapons(Tag tag) {
        return weapons.contains(tag.getStory());
    }

    private boolean oneOfPeople(Tag tag) {
        return people.contains(tag.getStory());
    }

    @Test
    void rerollVariable() {
        History history = historyService.getHistory(historyId);

        Tag historyTag = history.getOrigin();
        Tag child = getChildFromTag(historyTag, 1);
        String weaponBefore = child.getStory();

        History rerolledHistory = historyService.rerollTag(historyId, child.getTagId());
        String weaponAfter = getChildFromTag(rerolledHistory.getOrigin(), 1).getStory();

        assertTrue(child instanceof VariableTag);
        assertNotEquals(weaponBefore, weaponAfter);
    }

    @Test
    void rerollConstant() {
        History history = historyService.getHistory(historyId);

        Tag historyTag = history.getOrigin();
        Tag child = getChildFromTag(historyTag, 0);
        String personBefore = child.getStory();

        History rerolledHistory = historyService.rerollTag(historyId, child.getTagId());
        String personAfter = getChildFromTag(rerolledHistory.getOrigin(), 0).getStory();

        assertTrue(child instanceof ConstantTag);
        assertNotEquals(personBefore, personAfter);
    }

    private Tag getChildFromTag(Tag historyTag, int child) {
        return historyTag.getChildren().get(child);
    }


}
