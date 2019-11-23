package pl.lukaszdutka;

import pl.lukaszdutka.creator.HistoryService;

public class Main {

    public static void main(String[] args) {

        //WAŻNE:
        //konstrukcja "if" np. #IF:clue1:result#
        //wtedy redult : {"If players do have #clue1# then they can skip this"}
        HistoryService historyService = new HistoryService();
        String history = historyService.getHistory();

        System.out.println(history);
    }
}
