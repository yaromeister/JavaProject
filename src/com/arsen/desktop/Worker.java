package com.arsen.desktop;

import java.util.HashMap;
import java.util.Map;

public class Worker {

    private Map<String,String> workerProperties = new HashMap<String, String>();

    public Map GetMap()
    {
        return workerProperties;
    }

    public String GetMapKeyValue(String key)
    {
        return workerProperties.get(key);
    }

    public void SetMap(Map mapToSet)
    {
        workerProperties = mapToSet;
    }

    public void SetMapKeyValue(String key, String value)
    {
        workerProperties.put(key, value);
    }

}
