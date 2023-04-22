package io.harjun751.monopolybackend;

import io.harjun751.monopoly.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class GameController {
    @RequestMapping(path="api/v1/board")
    @GetMapping()
    public Board getBoard(){
        Board board = BoardBootstrapper.getBoard(5,0,new ArrayList<>());
        return board;
    }
    @RequestMapping(path="api/v1/logs")
    @GetMapping()
    public List<Log> getLogs(@RequestParam String players, @RequestParam String maxTurns){
        EventLogger logger = new EventLogger();
        StatisticsCollector collector = new StatisticsCollector();
        ArrayList<Subscriber> subscriberList = new ArrayList<>();
        subscriberList.add(logger);
        subscriberList.add(collector);
        BoardGameManager manager = new BoardGameManager(1,Integer.parseInt(maxTurns),Integer.parseInt(players),subscriberList);
        manager.play();

        return logger.getLogList();
    }

    @RequestMapping(path="api/v1/stats")
    @GetMapping()
    public Map<String, Map> getRentStats(@RequestParam String players, @RequestParam String maxTurns){
        EventLogger logger = new EventLogger();
        StatisticsCollector collector = new StatisticsCollector();
        ArrayList<Subscriber> subscriberList = new ArrayList<>();
        subscriberList.add(logger);
        subscriberList.add(collector);
        BoardGameManager manager = new BoardGameManager(1,Integer.parseInt(maxTurns),Integer.parseInt(players),subscriberList);
        manager.play();

        Map<String, Map> stats = new HashMap<>();
        stats.put("rentStats", collector.getOrderedRentMap());
        stats.put("landStats", collector.getOrderedLandMap());
        return stats;
    }

    @GetMapping(value = "api/v1/statsdownload", produces=MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void test(HttpServletResponse response, @RequestParam String players, @RequestParam String maxTurns) {
        EventLogger logger = new EventLogger();
        StatisticsCollector collector = new StatisticsCollector();
        ArrayList<Subscriber> subscriberList = new ArrayList<>();
        subscriberList.add(logger);
        subscriberList.add(collector);
        BoardGameManager manager = new BoardGameManager(1,Integer.parseInt(maxTurns),Integer.parseInt(players),subscriberList);
        manager.play();

        String text = collector.returnCSV();
        try {
            response.setContentType("text/csv");
            // get your file as InputStream
            InputStream stream = new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8));
            // copy it to response's OutputStream
            FileCopyUtils.copy(stream, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException("IOError writing file to output stream");
        }

    }

}

