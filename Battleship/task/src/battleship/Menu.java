package battleship;

import java.util.*;

import static battleship.Game.fillTurn;

public class Menu {

    private static final Scanner scanner = new Scanner(System.in);

    public static void addShips(PlayerField playerField) {
        Game.addPossibleShips();
        Map<String, Integer> possibleShips = Game.getPossibleShips();
        Set<String> shipNames = possibleShips.keySet();
        for (String shipName : shipNames
        ) {
            String inputLine = "";
            System.out.printf("Enter the coordinates of the %s (%d cells):\n\n", shipName, possibleShips.get(shipName));

            while (!Game.fillShip(inputLine, shipName, possibleShips.get(shipName), playerField)) {
                inputLine = scanner.nextLine();
                System.out.println();
            }
            playerField.printFieldWithShips();
        }
    }



    public static void startGameMenu(PlayerField playerField) {
        addShips(playerField);
        startGame(playerField);
    }

    public static void startGame(PlayerField playerField) {
        System.out.println("The game starts!\n");
        playerField.printFieldWithFog();
        takeShot(playerField);
        System.out.println();
        playerField.printFieldWithShips();
    }

    public static void takeShot(PlayerField playerField) {
        System.out.println("Take a shot!\n");
        String turnCoordinates = scanner.nextLine();
        System.out.println();
        while (!fillTurn(turnCoordinates, playerField)) {
            turnCoordinates = scanner.nextLine();
        }
    }
}
