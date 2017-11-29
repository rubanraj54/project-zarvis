package zarvis.bakery.test;

import java.util.Comparator;
import java.util.HashMap;

// a comparator that compares Strings
class ValueComparator implements Comparator<String> {

    HashMap<String, Integer> map = new HashMap<String, Integer>();

    public ValueComparator(HashMap<String, Integer> map){
        this.map.putAll(map);
    }

    @Override
    public int compare(String a, String b) {
        int cmp = map.get(a).compareTo(map.get(b));
        if (cmp == 0)
            cmp = a.compareTo(b);
        return cmp;
    }
}