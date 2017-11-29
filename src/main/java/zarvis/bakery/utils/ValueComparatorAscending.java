package zarvis.bakery.utils;

import java.util.Comparator;
import java.util.HashMap;

// a comparator that compares Strings
class ValueComparatorAscending implements Comparator<String> {

    HashMap<String, Integer> map = new HashMap<String, Integer>();

    public ValueComparatorAscending(HashMap<String, Integer> map){
        this.map.putAll(map);
    }

    @Override
    public int compare(String s1, String s2) {
        if(map.get(s1) <= map.get(s2)){
            return -1;
        }else{
            return 1;
        }
    }
}