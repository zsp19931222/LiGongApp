package com.example.app4.util;

import java.util.HashMap;
import java.util.Map;

public class MapUtil {
    public static Map<String, String> getMap(Map<String, String> map) {
        if (map == null) {
            map = new HashMap<>();
        }
        return map;
    }
}
