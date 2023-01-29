package src.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import src.graphics.Graphics;

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
                    log("Options %s unrecognized.", options);
                    return;
                }
                command.removeIf(s -> s.matches("-.*"));
                if (command.size() == 1)
                    runMakeMap(command.get(0), options.contains("v"), options.contains("s"));
                else
                    log("Command \"%s\" unrecognized.", in);
            } else if (suffix.equals("loc")) {
                if ((options = validateOptions(command, new String[] { "verbose", "force" }, "vf")) == "\0") {
                    options = command.stream().filter(s -> s.matches("-.*")).map(s -> "\"" + s + "\"")
                            .collect(Collectors.joining(", "));
                    log("Options %s unrecognized.", options);
                    return;
                }
                command.removeIf(s -> s.matches("-.*"));
                try {
                    if (command.size() == 3)
                        runMakeLoc(command.get(0), options.contains("v"), options.contains("f"),
                                Double.parseDouble(command.get(2)), Double.parseDouble(command.get(3)));
                    else
                        log("Command \"%s\" unrecognized.", in);
                } catch (Exception e) {
                    log("Command \"%s\" unrecognized.", in);
                }
            } else if (suffix.equals("con")) {
                if ((options = validateOptions(command, new String[] { "verbose" }, "v")) == "\0") {
                    options = command.stream().filter(s -> s.matches("-.*")).map(s -> "\"" + s + "\"")
                            .collect(Collectors.joining(", "));
                    log("Options %s unrecognized.", options);
                    return;
                }
                command.removeIf(s -> s.matches("-.*"));
                try {
                    if (command.size() == 2)
                        runMakeCon(command.get(0), command.get(1), options.contains("v"));
                    else
                        log("Command \"%s\" unrecognized.", in);
                } catch (Exception e) {
                    log("Command \"%s\" unrecognized.", in);
                }
            } else
                log("Command \"%s %s\" unrecognized.", head, suffix);
        } else if (head.equals("rm")) {
            String suffix = command.remove(0);
            if (suffix.equals("map")) {
                if ((options = validateOptions(command, new String[] { "verbose" }, "v")) == "\0") {
                    options = command.stream().filter(s -> s.matches("-.*")).map(s -> "\"" + s + "\"")
                            .collect(Collectors.joining(", "));
                    Graphics.log("Options %s unrecognized.", options);
                    return;
                }
                command.removeIf(s -> s.matches("-.*"));
                if (command.size() == 1)
                    runRMmap(command.get(0), options.contains("v"));
                else
                    Graphics.log("Command \"%s\" unrecognized.", in);
            } else if (suffix.equals("loc")) {
                if ((options = validateOptions(command, new String[] { "verbose" }, "v")) == "\0") {
                    options = command.stream().filter(s -> s.matches("-.*")).map(s -> "\"" + s + "\"")
                            .collect(Collectors.joining(", "));
                    Graphics.log("Options %s unrecognized.", options);
                    return;
                }
                command.removeIf(s -> s.matches("-.*"));
                if (command.size() == 1)
                    runRMloc(command.get(0), options.contains("v"));
                else
                    Graphics.log("Command \"%s\" unrecognized.", in);
            } else if (suffix.equals("con")) {
                if ((options = validateOptions(command, new String[] { "verbose" }, "v")) == "\0") {
                    options = command.stream().filter(s -> s.matches("-.*")).map(s -> "\"" + s + "\"")
                            .collect(Collectors.joining(", "));
                    Graphics.log("Options %s unrecognized.", options);
                    return;
                }
                command.removeIf(s -> s.matches("-.*"));
                if (command.size() == 2)
                    runRMcon(command.get(0), command.get(1), options.contains("v"));
                else
                    Graphics.log("Command \"%s\" unrecognized.", in);
            } else
                Graphics.log("Command \"%s %s\" unrecognized.", head, suffix);
        } else if (head.equals("find")) {
            if ((options = validateOptions(command,
                    new String[] { "ignore-order", "return-home" }, "ih")) == "\0") {
                options = command.stream().filter(s -> s.matches("-.*")).map(s -> "\"" + s + "\"")
                        .collect(Collectors.joining(", "));
                Graphics.log("Options %s unrecognized.", options);
                return;
            }
            command.removeIf(s -> s.matches("-.*"));
            if (command.size() >= 3)
                runFind(command.remove(0), command.remove(1), options.contains("i"), options.contains("h"),
                        options.contains("r"),
                        command.toArray(String[]::new));
            else
                Graphics.log("Command \"%s\" unrecognized.", in);
        } else if (head.equals("list")) {
            if ((options = validateOptions(command,
                    new String[] { "name", "raw", "omit-locations", "omit-connections" }, "nrlc")) == "\0") {
                options = command.stream().filter(s -> s.matches("-.*")).map(s -> "\"" + s + "\"")
                        .collect(Collectors.joining(", "));
                Graphics.log("Options %s unrecognized.", options);
                return;
            }
            command.removeIf(s -> s.matches("-.*"));
            if (command.size() == 1)
                runList(command.get(0), options.contains("n"), options.contains("r"), options.contains("l"),
                        options.contains("c"));
            else
                Graphics.log("Command \"%s\" unrecognized.", in);
        } else if (head.equals("set")) {
            if ((options = validateOptions(command, new String[] { "verbose" }, "v")) == "\0") {
                options = command.stream().filter(s -> s.matches("-.*")).map(s -> "\"" + s + "\"")
                        .collect(Collectors.joining(", "));
                Graphics.log("Options %s unrecognized.", options);
                return;
            }
            command.removeIf(s -> s.matches("-.*"));
            if (command.size() == 1)
                runSet(command.get(0), options.contains("v"));
            else
                Graphics.log("Command \"%s\" unrecognized.", in);
        } else
            Graphics.log("Command \"%s\" unrecognized.", head);
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

    public static void log(String s, Object... args) {
        System.out.printf(s, args);
    }

    public static void runMakeMap(String name, boolean verbose, boolean set) {
        FileRW.transWrites(name);
        if (set)
            FileRW.setActiveFile(name);
        if (verbose)
            if (set)
                Graphics.log("Map created: %s, set as active map", name);
            else
                Graphics.log("Map created: %s", name);
    }

    public static void runMakeLoc(String name, boolean verbose, boolean force, double x, double y) {
        String file = FileRW.transReads();
        if (file.matches("[^]*\\$" + name.toLowerCase() + ":[0-9.]+[0-9.]+\n[^]*")) {
            if (force) {
                FileRW.transBGones("$" + name.toLowerCase());
                FileRW.transAdds("$%s:%.2f,%.2f", name.toLowerCase(), x, y);
                if (verbose)
                    Graphics.log("(Overwrite) Created new location %s at (%.2f, %.2f)", name, x, y);
            } else {
                Graphics.log("Command: \"make loc\" failed (could not overwrite existing location)");
            }
        } else {
            FileRW.transAdds("$%s:%.2f,%.2f", name.toLowerCase(), x, y);
            if (verbose)
                Graphics.log("Created new location %s at (%.2f, %.2f)", name, x, y);
        }
    }

    public static void runMakeCon(String locationA, String locationB, boolean verbose) {
        String file = FileRW.transReads();
        if (file.matches("[^]*\\$" + locationA.toLowerCase() + ":" + locationB.toLowerCase() + "\n[^]*")) {
            Graphics.log("Connection Exists");
        } else {
            FileRW.transAdds("#%s:%s", locationA.toLowerCase(), locationB.toLowerCase());
            if (verbose)
                Graphics.log("Created new connection: %s - %s", locationA, locationB);
        }
    }

    public static void runRMmap(String name, boolean verbose) {
        FileRW.transBGones(name);
        if (verbose)
            Graphics.log("Map removed: %s", name);
    }

    public static void runRMloc(String name, boolean verbose) { 
        FileRW.transBGones("$" + name);
        if (verbose)
            Graphics.log("(From map: %s) Location removed: %s", FileRW.getActiveFile(), name);
    }

    public static void runRMcon(String locationA, String locationB, boolean verbose) {
        FileRW.transBGones("#" + locationA + "-" + locationB);
        if (verbose)
            Graphics.log("(From map: %s) Connection removed: %s-%s", FileRW.getActiveFile(), locationA, locationB);
    }

    public static void runFind(String mapName, String start, boolean ignore, boolean home, boolean raw,
            String... destinations) {
        log("run: find shortest route from %s, ignore order: %s, return home %s, raw: %s, destinations: %s",
                start, ignore, home, raw, Arrays.deepToString(destinations));
    }

    public static void runList(String mapName, boolean name, boolean raw, boolean location, boolean connection) {
        log("run: list contents of map %s, name: %s, raw: %s, location: %s, connection: %s",
                mapName.equals("") ? "active" : mapName, name, raw, location, connection);
    }

    public static void runSet(String mapName, boolean verbose) {
        FileRW.setActiveFile(mapName);
        if (verbose)
            Graphics.log("Map set: %s", mapName);
    }
}
