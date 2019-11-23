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

import java.util.UUID;

@RestController
@CrossOrigin
@Log4j2
public class HistoryController {

    private final HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @RequestMapping(path = "/history", produces = "application/json; charset=UTF-8")
    public ResponseEntity<TagEntity> getHistory() {
        Tag history = historyService.getHistoryTag();

        return ResponseEntity.ok(TagEntity.of(history));
    }

    @RequestMapping(path = "/history/{id}", produces = "application/json; charset=UTF-8")
    public ResponseEntity<TagEntity> getRerolledTag(@PathVariable String id) {
        log.info("INFO getRerolledTag, clicked: " + id);
        Tag history = historyService.rerollTag(UUID.fromString(id));

        TagEntity historyEntity = TagEntity.of(history);
        log.info("INFO getRerolledTag, returns: " + historyEntity);

        return ResponseEntity.ok(historyEntity);
    }


}
