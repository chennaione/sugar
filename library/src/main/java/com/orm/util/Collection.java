package com.orm.util;
import java.util.*;

public class Collection {

    public static <T> List<T> list(T... args) {
        return Arrays.asList(args);
    }

    public static <T> Set<T> set(T... args) {
        Set<T> result = new HashSet<T>(args.length);
        result.addAll(Arrays.asList(args));
        return result;
    }

    public static <K, V> Map<K, V> map(Entry<? extends K, ? extends V>... entries) {
        Map<K, V> result = new HashMap<K, V>(entries.length);

        for (Entry<? extends K, ? extends V> entry : entries)
            if (entry.value != null)
                result.put(entry.key, entry.value);

        return result;
    }

    public static <K, V> Entry<K, V> entry(K key, V value) {
        return new Entry<K, V>(key, value);
    }

    public static class Entry<K, V> {
        K key;
        V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

}
