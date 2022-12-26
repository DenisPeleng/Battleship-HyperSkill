package battleship;

import java.util.*;

public class Menu {
    private static final Map<String, Integer> possibleShips = new LinkedHashMap<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void addShips(PlayerField playerField) {
        addPossibleShips();
        Set<String> shipNames = possibleShips.keySet();
        for (String shipName : shipNames
        ) {
            String inputLine = "";
            System.out.printf("Enter the coordinates of the %s (%d cells):\n\n", shipName, possibleShips.get(shipName));

            while (!playerField.fillShip(inputLine, shipName, possibleShips.get(shipName))) {
                inputLine = scanner.nextLine();
                System.out.println();
            }
            playerField.printField();
        }
    }

    private static void addPossibleShips() {
        possibleShips.put("Aircraft Carrier", 5);
        possibleShips.put("Battleship", 4);
        possibleShips.put("Submarine", 3);
        possibleShips.put("Cruiser", 3);
        possibleShips.put("Destroyer", 2);
    }
}
