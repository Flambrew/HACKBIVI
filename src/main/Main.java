package src.main;

import src.routing.Map;

public class Main {

    private static Map activeMap;

    public static void main(String[] args) {
        //init the graphics
    }

    private static void swapMap(Map map) {
        activeMap = map;
    }

    public static Map getActiveMap() {
        return activeMap;
    }
}   
