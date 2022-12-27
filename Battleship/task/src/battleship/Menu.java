package battleship;

import java.util.*;

import static battleship.Game.fillTurn;
import static battleship.Game.printFiledForPlayer;

public class Menu {

    private static final Scanner scanner = new Scanner(System.in);
    private static PlayerField activePlayer;
    private static PlayerField foePlayer;

    public static void addShips(PlayerField playerField) {
        Game.addPossibleShips();
        Map<String, Integer> possibleShips = Game.getPossibleShips();
        Set<String> shipNames = possibleShips.keySet();
        for (String shipName : shipNames
        ) {
            String inputLine;
            System.out.printf("Enter the coordinates of the %s (%d cells):\n\n", shipName, possibleShips.get(shipName));
            do {
                inputLine = scanner.nextLine();
                System.out.println();
            }
            while (!Game.fillShip(inputLine, shipName, possibleShips.get(shipName), playerField));
            playerField.printFieldWithShips();
            System.out.println();
        }
    }


    public static void startGameMenu() {
        activePlayer = new PlayerField("Player1");
        foePlayer = new PlayerField("Player2");
        for (int i = 0; i < 2; i++) {
            System.out.printf("%s, place your ships on the game field\n\n", activePlayer.getPlayerName());
            activePlayer.printFieldWithFog();
            addShips(activePlayer);
            passMoveToAnotherPlayer();
        }
        startGame();
    }

    public static void startGame() {
        while (Game.isGameNotEnded(activePlayer)) {
            printFiledForPlayer(activePlayer, foePlayer);
            System.out.println();
            System.out.printf("%s, it's your turn:\n\n", activePlayer.getPlayerName());
            takeShot(foePlayer);
            System.out.println();
            passMoveToAnotherPlayer();
        }

    }

    public static void takeShot(PlayerField foePlayer) {
        String turnCoordinates = scanner.nextLine();
        System.out.println();
        while (!fillTurn(turnCoordinates, foePlayer)) {
            turnCoordinates = scanner.nextLine();
        }
    }

    private static void passMoveToAnotherPlayer() {
        System.out.println("Press Enter and pass the move to another player\n");
        scanner.nextLine();
        PlayerField temp = activePlayer;
        activePlayer = foePlayer;
        foePlayer = temp;
    }
}
