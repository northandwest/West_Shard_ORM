package com.bucuoa.west.orm.app.utils;


import java.util.HashMap;
import java.util.Map;

public class MapBuilder<E, T> {

    private Map<E, T> map = new HashMap<E, T> ();// Maps.newHashMap();

    private MapBuilder() {
    }

    public static MapBuilder<String, String> builder() {
        return new MapBuilder<String, String>();
    }

    public MapBuilder<E, T> put(E key, T value) {
        map.put(key, value);
        return this;
    }

    public Map<E, T> build() {
        return map;
    }
}
