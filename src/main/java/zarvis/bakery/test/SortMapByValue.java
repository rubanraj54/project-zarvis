package zarvis.bakery.test;

import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeMap;

public class SortMapByValue {

    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        map.put("a", 10);
        map.put("b", 30);
        map.put("c", 50);
        map.put("d", 40);
        map.put("e", 20);
        System.out.println(map);

        TreeMap<String, Integer> sortedMap = sortMapByValue(map);
        System.out.println(sortedMap);
        map.put("ruban",5);
        System.out.println(sortMapByValue(map));
    }

    public static TreeMap<String, Integer> sortMapByValue(HashMap<String, Integer> map){
        Comparator<String> comparator = new ValueComparator(map);
        //TreeMap is a map sorted by its keys.
        //The comparator is used to sort the TreeMap by keys.
        TreeMap<String, Integer> result = new TreeMap<String, Integer>(comparator);
        result.putAll(map);

        return result;
    }
}