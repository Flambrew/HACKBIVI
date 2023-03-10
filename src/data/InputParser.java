package src.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import src.graphics.GraphicsManager;
import src.routing.AStar;
import src.routing.City;

public class InputParser {
    public static void parse(String in) {
        ArrayList<String> command = new ArrayList<>(Arrays.asList(in.toLowerCase().split(" ")));
        String head = command.remove(0), options;
        if (head.equals("make")) {
            String suffix = command.remove(0);
            if (suffix.equals("map")) {
                if ((options = validateOptions(command, new String[] { "verbose", "set" }, "vs")) == "\0") {
                    options = command.stream().filter(s -> s.matches("-.*")).map(s -> "\"" + s + "\"")
                            .collect(Collectors.joining(", "));
                    GraphicsManager.log("Options %s unrecognized.", options);
                    return;
                }
                command.removeIf(s -> s.matches("-.*"));
                if (command.size() == 1)
                    runMakeMap(command.get(0), options.contains("v"), options.contains("s"));
                else
                    GraphicsManager.log("Command \"%s\" unrecognized.", in);
            } else if (suffix.equals("loc")) {
                if ((options = validateOptions(command, new String[] { "verbose", "force" }, "vf")) == "\0") {
                    options = command.stream().filter(s -> s.matches("-.*")).map(s -> "\"" + s + "\"")
                            .collect(Collectors.joining(", "));
                    GraphicsManager.log("Options %s unrecognized.", options);
                    return;
                }
                command.removeIf(s -> s.matches("-.*"));
                try {
                    if (command.size() == 3)
                        runMakeLoc(command.get(0), options.contains("v"), options.contains("f"),
                                Double.parseDouble(command.get(1)), Double.parseDouble(command.get(2)));
                    else
                        GraphicsManager.log("Command \"%s\" unrecognized.", in);
                } catch (Exception e) {
                    e.printStackTrace();
                    GraphicsManager.log("Command \"%s\" unrecognized.", in);
                }
            } else if (suffix.equals("con")) {
                if ((options = validateOptions(command, new String[] { "verbose" }, "v")) == "\0") {
                    options = command.stream().filter(s -> s.matches("-.*")).map(s -> "\"" + s + "\"")
                            .collect(Collectors.joining(", "));
                    GraphicsManager.log("Options %s unrecognized.", options);
                    return;
                }
                command.removeIf(s -> s.matches("-.*"));
                try {
                    if (command.size() == 2)
                        runMakeCon(command.get(0), command.get(1), options.contains("v"));
                    else
                        GraphicsManager.log("Command \"%s\" unrecognized.", in);
                } catch (Exception e) {
                    GraphicsManager.log("Command \"%s\" unrecognized.", in);
                }
            } else
                GraphicsManager.log("Command \"%s %s\" unrecognized.", head, suffix);
        } else if (head.equals("rm")) {
            String suffix = command.remove(0);
            if (suffix.equals("map")) {
                if ((options = validateOptions(command, new String[] { "verbose" }, "v")) == "\0") {
                    options = command.stream().filter(s -> s.matches("-.*")).map(s -> "\"" + s + "\"")
                            .collect(Collectors.joining(", "));
                    GraphicsManager.log("Options %s unrecognized.", options);
                    return;
                }
                command.removeIf(s -> s.matches("-.*"));
                if (command.size() == 1)
                    runRMmap(command.get(0), options.contains("v"));
                else
                    GraphicsManager.log("Command \"%s\" unrecognized.", in);
            } else if (suffix.equals("loc")) {
                if ((options = validateOptions(command, new String[] { "verbose" }, "v")) == "\0") {
                    options = command.stream().filter(s -> s.matches("-.*")).map(s -> "\"" + s + "\"")
                            .collect(Collectors.joining(", "));
                    GraphicsManager.log("Options %s unrecognized.", options);
                    return;
                }
                command.removeIf(s -> s.matches("-.*"));
                if (command.size() == 1)
                    runRMloc(command.get(0), options.contains("v"));
                else
                    GraphicsManager.log("Command \"%s\" unrecognized.", in);
            } else if (suffix.equals("con")) {
                if ((options = validateOptions(command, new String[] { "verbose" }, "v")) == "\0") {
                    options = command.stream().filter(s -> s.matches("-.*")).map(s -> "\"" + s + "\"")
                            .collect(Collectors.joining(", "));
                    GraphicsManager.log("Options %s unrecognized.", options);
                    return;
                }
                command.removeIf(s -> s.matches("-.*"));
                if (command.size() == 2)
                    runRMcon(command.get(0), command.get(1), options.contains("v"));
                else
                    GraphicsManager.log("Command \"%s\" unrecognized.", in);
            } else
                GraphicsManager.log("Command \"%s %s\" unrecognized.", head, suffix);
        } else if (head.equals("find")) {
            if (command.size() == 3)
                runFind(command.remove(0), command.remove(0), command.remove(0));
            else
                GraphicsManager.log("Command \"%s\" unrecognized.", in);
        } else if (head.equals("list")) {
            if ((options = validateOptions(command,
                    new String[] { "name", "omit-locations", "omit-connections" }, "nlc")) == "\0") {
                options = command.stream().filter(s -> s.matches("-.*")).map(s -> "\"" + s + "\"")
                        .collect(Collectors.joining(", "));
                GraphicsManager.log("Options %s unrecognized.", options);
                return;
            }
            command.removeIf(s -> s.matches("-.*"));
            if (command.size() == 0)
                runList(options.contains("n"), options.contains("l"), options.contains("c"));
            else
                GraphicsManager.log("Command \"%s\" unrecognized.", in);
        } else if (head.equals("open")) {
            if ((options = validateOptions(command, new String[] { "verbose" }, "v")) == "\0") {
                options = command.stream().filter(s -> s.matches("-.*")).map(s -> "\"" + s + "\"")
                        .collect(Collectors.joining(", "));
                GraphicsManager.log("Options %s unrecognized.", options);
                return;
            }
            command.removeIf(s -> s.matches("-.*"));
            if (command.size() == 1)
                runSet(command.get(0), options.contains("v"));
            else
                GraphicsManager.log("Command \"%s\" unrecognized.", in);
        } else
            GraphicsManager.log("Command \"%s\" unrecognized.", head);
    }

    // ignore how atrocious this method looks, i just learned the basics of lambdas
    // and im practicing their use, im not making this the norm
    private static String validateOptions(ArrayList<String> command, String[] fullOptions, String options) {
        String dash = command.stream().filter(s -> s.matches("-[^-].*")).map(s -> s.substring(1))
                .collect(Collectors.joining());
        String[] full = command.stream().filter(s -> s.matches("--.*")).map(s -> s.substring(2)).toArray(String[]::new);
        dash = (dash + IntStream.range(0, full.length).filter(i -> Arrays.asList(fullOptions).contains(full[i]))
                .mapToObj(i -> ((Character) options.charAt(i)).toString()).collect(Collectors.joining())).chars()
                .distinct().mapToObj(c -> ((Character) (char) c).toString()).collect(Collectors.joining());
        return dash.matches("[" + options + "]*") ? dash : "\0";
    }

    public static void runMakeMap(String name, boolean verbose, boolean set) {
        FileRW.initFile(name);
        if (set)
            FileRW.setActiveFile(name);
        if (verbose)
            if (set)
                GraphicsManager.log("Map created: %s, set as active map", name);
            else
                GraphicsManager.log("Map created: %s", name);
    }

    public static void runMakeLoc(String name, boolean verbose, boolean force, double x, double y) {
        x *= 1000;
        y *= 1000;
        String file = FileRW.readActiveFile();
        if (file.matches(".*\\$" + name.toLowerCase() + ":[0-9.]+,[0-9.]+\\n.*")) {
            if (force) {
                FileRW.cutFromFile("$" + name.toLowerCase());
                FileRW.appendToActive("$%s:%.2f,%.2f", name.toLowerCase(), x, y);
                if (verbose)
                    GraphicsManager.log("(Overwrite) Created new location %s at (%.2f, %.2f)", name, x, y);
            } else {
                GraphicsManager.log("Command: \"make loc\" failed (could not overwrite existing location)");
            }
        } else {
            FileRW.appendToActive("$%s:%.2f,%.2f", name.toLowerCase(), x, y);
            if (verbose)
                GraphicsManager.log("Created new location %s at (%.2f, %.2f)", name, x, y);
        }
    }

    public static void runMakeCon(String locationA, String locationB, boolean verbose) {
        String file = FileRW.readActiveFile();
        if (file.matches(".*\\$" + locationA.toLowerCase() + ":" + locationB.toLowerCase() + "\n.*")) {
            GraphicsManager.log("Connection Exists");
        } else {
            FileRW.appendToActive("#%s:%s", locationA.toLowerCase(), locationB.toLowerCase());
            if (verbose)
                GraphicsManager.log("Created new connection: %s - %s", locationA, locationB);
        }
    }

    public static void runRMmap(String name, boolean verbose) {
        FileRW.deleteFile(name);
        if (verbose)
            GraphicsManager.log("Map removed: %s", name);
    }

    public static void runRMloc(String name, boolean verbose) {
        FileRW.cutFromFile("$" + name);
        if (verbose)
            GraphicsManager.log("(From map: %s) Location removed: %s", FileRW.getActiveFile(), name);
    }

    public static void runRMcon(String locationA, String locationB, boolean verbose) {
        FileRW.cutFromFile("#" + locationA + "-" + locationB);
        if (verbose)
            GraphicsManager.log("(From map: %s) Connection removed: %s-%s", FileRW.getActiveFile(), locationA,
                    locationB);
    }

    public static void runFind(String mapName, String start, String destination) {
        String file = FileRW.readActiveFile();
        ArrayList<City> cities = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();

        // make the locations
        for (String str : file.split("\n"))
            if (str.charAt(0) == '$') {
                cities.add(new City(str.substring(1, str.indexOf(":")),
                        Double.parseDouble(str.substring(str.indexOf(":") + 1, str.indexOf(","))),
                        Double.parseDouble(str.substring(str.indexOf(",") + 1))));
                names.add(str.substring(1, str.indexOf(":")));
            }

        // make the connections
        for (String str : file.split("\n"))
            if (str.charAt(0) == '#') {
                City loca = cities.get(names.indexOf(str.substring(str.indexOf("#") + 1, str.indexOf(":"))));
                City locb = cities.get(names.indexOf(str.substring(str.indexOf(":") + 1)));
                loca.addAdjacentCity(locb, (int) Math
                        .sqrt(Math.pow(loca.getX() - locb.getX(), 2) + Math.pow(loca.getY() - locb.getY(), 2)));
                locb.addAdjacentCity(loca, (int) Math
                        .sqrt(Math.pow(loca.getX() - locb.getX(), 2) + Math.pow(loca.getY() - locb.getY(), 2)));
            }

        AStar.calculateShortestPath(cities.get(names.indexOf(start)));

        GraphicsManager.log("%s", AStar.path(cities.get(names.indexOf(destination))));

    }

    public static void runList(boolean name, boolean location, boolean connection) {
        String file = FileRW.readActiveFile();
        if (name)
            file = FileRW.getActiveFile() + file;
        if (location)
            while (true)
                try {
                    file = file.substring(0, file.indexOf("$")) + file.substring(file.indexOf("\n", file.indexOf("$")));
                } catch (Exception e) {
                    break;
                }
        if (connection)
            while (true)
                try {
                    file = file.substring(0, file.indexOf("#")) + file.substring(file.indexOf("\n", file.indexOf("#")));
                } catch (Exception e) {
                    break;
                }
        for (String str : file.split("\n")) {
            GraphicsManager.log(str);
        }
    }

    public static void runSet(String mapName, boolean verbose) {
        FileRW.setActiveFile(mapName);
        if (verbose)
            GraphicsManager.log("Map set: %s", mapName);
    }
}
