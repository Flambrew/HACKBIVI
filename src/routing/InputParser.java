package src.routing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
                                Integer.parseInt(command.get(1)), Integer.parseInt(command.get(2)), null);
                    else if (command.size() == 4 && command.get(3).length() <= 3)
                        runMakeLoc(command.get(0), options.contains("v"), options.contains("f"),
                                Integer.parseInt(command.get(1)), Integer.parseInt(command.get(2)), command.get(3));
                    else
                        log("Command \"%s\" unrecognized.", in);
                } catch (Exception e) {
                    log("Command \"%s\" unrecognized.", in);
                }
            } else if (suffix.equals("con")) {
                if ((options = validateOptions(command, new String[] { "verbose", "force" }, "vf")) == "\0") {
                    options = command.stream().filter(s -> s.matches("-.*")).map(s -> "\"" + s + "\"")
                            .collect(Collectors.joining(", "));
                    log("Options %s unrecognized.", options);
                    return;
                }
                command.removeIf(s -> s.matches("-.*"));
                try {
                    if (command.size() == 2)
                        runMakeCon(command.get(0), command.get(1), options.contains("v"), options.contains("s"), -1);
                    else if (command.size() == 3)
                        runMakeCon(command.get(0), command.get(1), options.contains("v"), options.contains("s"),
                                Double.parseDouble(command.get(2)));
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
                    log("Options %s unrecognized.", options);
                    return;
                }
                command.removeIf(s -> s.matches("-.*"));
                if (command.size() == 1)
                    runRMmap(command.get(0), options.contains("v"));
                else
                    log("Command \"%s\" unrecognized.", in);
            } else if (suffix.equals("loc")) {
                if ((options = validateOptions(command, new String[] { "verbose" }, "v")) == "\0") {
                    options = command.stream().filter(s -> s.matches("-.*")).map(s -> "\"" + s + "\"")
                            .collect(Collectors.joining(", "));
                    log("Options %s unrecognized.", options);
                    return;
                }
                command.removeIf(s -> s.matches("-.*"));
                if (command.size() == 1)
                    runRMloc(command.get(0), options.contains("v"));
                else
                    log("Command \"%s\" unrecognized.", in);
            } else if (suffix.equals("con")) {
                if ((options = validateOptions(command, new String[] { "verbose" }, "v")) == "\0") {
                    options = command.stream().filter(s -> s.matches("-.*")).map(s -> "\"" + s + "\"")
                            .collect(Collectors.joining(", "));
                    log("Options %s unrecognized.", options);
                    return;
                }
                command.removeIf(s -> s.matches("-.*"));
                if (command.size() == 2)
                    runRMcon(command.get(0), command.get(1), options.contains("v"));
                else
                    log("Command \"%s\" unrecognized.", in);
            } else
                log("Command \"%s %s\" unrecognized.", head, suffix);
        } else if (head.equals("find")) {
            if ((options = validateOptions(command,
                    new String[] { "ignore-order", "return-home", "raw" }, "ihr")) == "\0") {
                options = command.stream().filter(s -> s.matches("-.*")).map(s -> "\"" + s + "\"")
                        .collect(Collectors.joining(", "));
                log("Options %s unrecognized.", options);
                return;
            }
            command.removeIf(s -> s.matches("-.*"));
            if (command.size() != 0)
                runFind(command.remove(0), options.contains("i"), options.contains("h"), options.contains("r"),
                        command.toArray(String[]::new));
            else
                log("Command \"%s\" unrecognized.", in);
        } else if (head.equals("list")) {
            if ((options = validateOptions(command,
                    new String[] { "name", "raw", "omit-locations", "omit-connections" }, "nrlc")) == "\0") {
                options = command.stream().filter(s -> s.matches("-.*")).map(s -> "\"" + s + "\"")
                        .collect(Collectors.joining(", "));
                log("Options %s unrecognized.", options);
                return;
            }
            command.removeIf(s -> s.matches("-.*"));
            if (command.size() == 0)
                runList("", options.contains("n"), options.contains("r"), options.contains("l"), options.contains("c"));
            else if (command.size() == 1)
                runList(command.get(0), options.contains("n"), options.contains("r"), options.contains("l"),
                        options.contains("c"));
            else
                log("Command \"%s\" unrecognized.", in);
        } else if (head.equals("set")) {
            if ((options = validateOptions(command, new String[] { "verbose" }, "v")) == "\0") {
                options = command.stream().filter(s -> s.matches("-.*")).map(s -> "\"" + s + "\"")
                        .collect(Collectors.joining(", "));
                log("Options %s unrecognized.", options);
                return;
            }
            command.removeIf(s -> s.matches("-.*"));
            if (command.size() == 1)
                runSet(command.get(0), options.contains("v"));
            else
                log("Command \"%s\" unrecognized.", in);
        } else if (head.equals("graph"))
            runGraph();
        else
            log("Command \"%s\" unrecognized.", head);
    }

    private static String validateOptions(ArrayList<String> command, String[] fullOptions, String options) {
        String dash = command.stream().filter(s -> s.matches("-[^-].*")).map(s -> s.substring(1))
                .collect(Collectors.joining());
        String[] full = command.stream().filter(s -> s.matches("--.*")).map(s -> s.substring(2)).toArray(String[]::new);
        dash = IntStream.range(0, full.length).filter(i -> Arrays.asList(fullOptions).contains(full[i]))
                .mapToObj(i -> ((Character) options.charAt(i)).toString()).collect(Collectors.joining()).chars()
                .distinct().mapToObj(c -> ((Character) (char) c).toString()).collect(Collectors.joining());
        return dash.matches("[" + options + "]*") ? dash : "\0";
    }

    public static void log(String s, Object... args) {
        System.out.printf(s, args);
    }

    public static void runMakeMap(String name, boolean verbose, boolean set) {
        log("run: create map %s, verbose %s, set active: %s", name, verbose, set);
    }

    public static void runMakeLoc(String name, boolean verbose, boolean force, int x, int y, String abbreviation) {
        log("run: create location %s%s at (%d, %d), verbose: %s, force: %s", name,
                abbreviation == null ? "" : " " + abbreviation, x, y, verbose, force);
    }

    public static void runMakeCon(String locationA, String locationB, boolean verbose, boolean force, double distance) {
        log("run: create connection %s-%s%s, verbose: %s, force: %s", locationA, locationB,
                distance <= 0 ? "" : " (distance: %.2f)", verbose, force);
    }

    public static void runRMmap(String name, boolean verbose) {
        log("run: delete map %s, verbose %s", name, verbose);
    }

    public static void runRMloc(String name, boolean verbose) {
        log("run: delete location %s, verbose %s", name, verbose);
    }

    public static void runRMcon(String locationA, String locationB, boolean verbose) {
        log("run: delete connection %s-%s, verbose %s", locationA, locationB, verbose);
    }

    public static void runFind(String start, boolean ignore, boolean home, boolean raw, String... destinations) {
        log("run: find shortest route from %s, ignore order: %s, return home %s, raw: %s, destinations: %s",
                start, ignore, home, raw, Arrays.deepToString(destinations));
    }

    public static void runList(String mapName, boolean name, boolean raw, boolean location, boolean connection) {
        log("run: list contents of map %s, name: %s, raw: %s, location: %s, connection: %s",
                mapName.equals("") ? "active" : mapName, name, raw, location, connection);
    }

    public static void runSet(String mapName, boolean verbose) {
        log("run: set map to %s, verbose: %s", mapName, verbose);
    }

    public static void runGraph() {
        log("run: display graph");
    }
}
