package io.harjun751.monopoly;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class StatisticsCollector implements Subscriber {

    private final HashMap<Integer, Integer> landedMap;
    private final HashMap<Integer, Double> rentCollectedMap;

    public StatisticsCollector() {
        this.landedMap = new HashMap<Integer, Integer>();
        this.rentCollectedMap = new HashMap<Integer, Double>();
    }

    public void update(Player player) {
    }

    public void export() {
        String text1 = "position, name, landings\n";
        String text2 = "position, name, rent collected\n";
        Board referenceBoard = BoardBootstrapper.getBoard(1,0,new ArrayList<>());
        for (Integer key : landedMap.keySet()) {
            BoardSpace space = referenceBoard.getBoardSpace(key);
            String name = "";
            if (space == null) {
                name = "free parkin";
            } else {
                name = space.name;
            }
            text1 = text1 + (key + ", " + name + ", " + landedMap.get(key) + "\n");
        }
        for (Integer key : landedMap.keySet()) {
            BoardSpace space = referenceBoard.getBoardSpace(key);
            String name = "";
            if (space == null) {
                name = "free parkin";
            } else {
                name = space.name;
            }
            text2 = text2 + (key + ", " + name + ", " + rentCollectedMap.get(key) + "\n");
        }
        try {
            FileWriter myWriter = new FileWriter("landedmap.csv");
            myWriter.write(text1);
            myWriter.close();
            FileWriter myWriter2 = new FileWriter("rentmap.csv");
            myWriter2.write(text2);
            myWriter2.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public String returnCSV() {
        String text1 = "position, name, landings\n";
        String text2 = "position, name, rent collected\n";
        Board referenceBoard = BoardBootstrapper.getBoard(1,0,new ArrayList<>());
        for (Integer key : landedMap.keySet()) {
            BoardSpace space = referenceBoard.getBoardSpace(key);
            String name = "";
            if (space == null) {
                name = "free parking";
            } else {
                name = space.name;
            }
            text1 = text1 + (key + ", " + name + ", " + landedMap.get(key) + "\n");
        }
        for (Integer key : landedMap.keySet()) {
            BoardSpace space = referenceBoard.getBoardSpace(key);
            String name = "";
            if (space == null) {
                name = "free parking";
            } else {
                name = space.name;
            }
            text2 = text2 + (key + ", " + name + ", " + rentCollectedMap.get(key) + "\n");
        }

        String finalText = "Landed Positions: \n" + text1 + "\n Rent Collected: \n" + text2;

        return finalText;
    }

    public void update(EventType type, Object context) {
        if (type == EventType.LANDED) {
            Player player = (Player) context;
            int landedAt = player.getCurrPosition();
            if (landedMap.containsKey(landedAt)) {
                int landedValue = landedMap.get(landedAt);
                landedMap.put(landedAt, landedValue + 1);
            } else {
                landedMap.put(landedAt, 1);
            }
        } else if (type == EventType.RENTPAID) {
            HashMap<String, Object> data = (HashMap<String, Object>) context;
            double rent = (double) data.get("rent");
            int position = (int) data.get("pos");
            if (rentCollectedMap.containsKey(position)) {
                double rentValue = rentCollectedMap.get(position);
                // Add current rent collected to the total value of collected rent at that property
                rentCollectedMap.put(position, rent + rentValue);
            } else {
                rentCollectedMap.put(position, rent);
            }
        }
    }

    public Map<String, Double> getOrderedRentMap(){
        // {1:12,2:123123, ..}
        // Step one: Order Hashmap based on value
        // {2:123123, 1:12, ..}
        // Step 2: Collect Hash Map into List
        // {Sentosa Cove, MRT, ...}
        Board referenceBoard = BoardBootstrapper.getBoard(1,0,new ArrayList<>());

        List<Map.Entry<Integer, Double>> list = new ArrayList<>(rentCollectedMap.entrySet());
        list.sort(Map.Entry.<Integer, Double>comparingByValue().reversed());

        Map<String,Double> result = new LinkedHashMap<String, Double>();
        for (Map.Entry<Integer,Double> entry : list){
            PropertySpace prop = (PropertySpace) referenceBoard.getBoardSpace(entry.getKey());
            result.put(prop.getName(), entry.getValue());
        }

        return result;
    }

    public Map<String, Integer> getOrderedLandMap(){
        Board referenceBoard = BoardBootstrapper.getBoard(1,0,new ArrayList<>());

        List<Map.Entry<Integer, Integer>> list = new ArrayList<>(landedMap.entrySet());
        list.sort(Map.Entry.<Integer,Integer>comparingByValue().reversed());

        Map<String,Integer> result = new LinkedHashMap<String, Integer>();
        for (Map.Entry<Integer,Integer> entry : list){
            BoardSpace prop = (BoardSpace) referenceBoard.getBoardSpace(entry.getKey());
            if (prop==null){
                if (entry.getKey()==0){
                    result.put("Go", entry.getValue());
                } else if (entry.getKey()==10) {
                    result.put("Passing By Jail", entry.getValue());
                } else if (entry.getKey()==20) {
                    result.put("Free Parking", entry.getValue());
                } else if (entry.getKey()==30) {
                    result.put("Jail", entry.getValue());
                } else {
                    result.put("???", entry.getValue());
                }
            } else {
                if (prop instanceof ChanceComSpace){
                    ChanceComSpace space = (ChanceComSpace) prop;
                    if (space.isChanceSpace()){
                        result.put("Chance @"+prop.position, entry.getValue());
                    } else {
                        result.put("Community Chest @"+prop.position, entry.getValue());
                    }
                }
                result.put(prop.name, entry.getValue());
            }
        }

        return result;
    }

}
