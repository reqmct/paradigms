package expression.generic;


import java.util.Map;

public class Main {

    private static final Map<String, String> modes = Map.of(
            "-i", "i",
            "-d", "d",
            "-bi", "bi",
            "-u", "u",
            "-l", "l",
            "-s", "s"
    );

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.out.println("Incorrect size of args");
            return;
        }
        if (!modes.containsKey(args[0])) {
            System.out.println("Incorrect first argument");
            return;
        }
        Object[][][] result = new GenericTabulator().tabulate(modes.get(args[0]), args[1], -1,
                8, -6, 8, -6, 1);
        for (int x = 0; x < result.length; x++) {
            for (int y = 0; y < result[x].length; y++) {
                for (int z = 0; z < result[x][y].length; z++) {
                    System.out.print(result[x][y][z] + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }
}
