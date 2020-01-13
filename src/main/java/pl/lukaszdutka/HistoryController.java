package pl.lukaszdutka;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.lukaszdutka.creator.HistoryService;
import pl.lukaszdutka.entities.HistoryEntity;

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
    public ResponseEntity<HistoryEntity> getHistory() {
        log.info("INFO getHistory()");
        History history = historyService.getHistory(UUID.randomUUID().toString());

        HistoryEntity historyEntity = HistoryEntity.of(history);

        log.info(historyEntity);

        return ResponseEntity.ok(historyEntity);
    }

    @RequestMapping(path = "/history/{historyId}", produces = "application/json; charset=UTF-8")
    public ResponseEntity<HistoryEntity> getHistory(@PathVariable String historyId) {
        log.info("INFO getHistory(), clicked history:[" + historyId + "]");
        History history = historyService.getHistory(historyId);

        HistoryEntity historyEntity = HistoryEntity.of(history);

        log.info(historyEntity);
        return ResponseEntity.ok(historyEntity);
    }

    @RequestMapping(path = "/history/{historyId}/tag/{tagId}", produces = "application/json; charset=UTF-8")
    public ResponseEntity<HistoryEntity> getRerolledTag(@PathVariable String historyId, @PathVariable String tagId) {
        log.info("INFO getRerolledTag, clicked history:[" + historyId + "], tag:[" + tagId + "]");

        History history = historyService.rerollTag(historyId, tagId);
        HistoryEntity historyEntity = HistoryEntity.of(history);

        log.info(historyEntity);
        return ResponseEntity.ok(historyEntity);
    }


}
