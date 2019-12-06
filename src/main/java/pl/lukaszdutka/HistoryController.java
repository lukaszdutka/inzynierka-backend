package pl.lukaszdutka;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.lukaszdutka.creator.HistoryService;
import pl.lukaszdutka.entities.TagEntity;
import pl.lukaszdutka.tags.Tag;

@RestController
@CrossOrigin
@Log4j2
public class HistoryController {

    private final HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @RequestMapping(path = "/history/{historyId}", produces = "application/json; charset=UTF-8")
    public ResponseEntity<TagEntity> getHistory(@PathVariable String historyId) {
        Tag origin = historyService.getHistory(historyId).get();

        return ResponseEntity.ok(TagEntity.of(origin));
    }

    @RequestMapping(path = "/history/{historyId}/{tagId}", produces = "application/json; charset=UTF-8")
    public ResponseEntity<TagEntity> getRerolledTag(@PathVariable String historyId, @PathVariable String tagId) {
        log.info("INFO getRerolledTag, clicked history:[" + historyId + "], tag:[" + tagId + "]");
        Tag history = historyService.rerollTag(historyId, tagId).get();

        TagEntity historyEntity = TagEntity.of(history);
        log.info("INFO getRerolledTag, returns: " + historyEntity);

        return ResponseEntity.ok(historyEntity);
    }


}
