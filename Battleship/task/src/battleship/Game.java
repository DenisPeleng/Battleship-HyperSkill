package battleship;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import static battleship.PlayerField.getNumberByLetter;

public class Game {
    public static Map<String, Integer> getPossibleShips() {
        return possibleShips;
    }

    private static final Map<String, Integer> possibleShips = new LinkedHashMap<>();

    public static boolean fillShip(String userCoordinatesInput, String nameShip, int lengthOfShip, PlayerField playerField) {
        String[][] field = playerField.getFieldWithShips();
        String[] userCoordinatesInputArr = userCoordinatesInput.split(" ");
        if (userCoordinatesInputArr.length != 2) {
            return false;
        }

        String beginningCoordinate = userCoordinatesInputArr[0];
        String endingCoordinate = userCoordinatesInputArr[1];
        if (playerField.isInvalidCoordinates(beginningCoordinate) || playerField.isInvalidCoordinates(endingCoordinate)) {
            System.out.println("Error! Wrong ship location! Try again:");
            return false;
        }
        int beginningCoordinateX = getNumberByLetter(userCoordinatesInputArr[0].split("")[0]);
        int beginningCoordinateY = Integer.parseInt(userCoordinatesInputArr[0].substring(1)) - 1;
        int endingCoordinateX = getNumberByLetter(userCoordinatesInputArr[1].split("")[0]);
        int endingCoordinateY = Integer.parseInt(userCoordinatesInputArr[1].substring(1)) - 1;
        if ((Math.abs(beginningCoordinateX - endingCoordinateX) + 1) != lengthOfShip && (Math.abs(beginningCoordinateY - endingCoordinateY) + 1) != lengthOfShip) {
            System.out.printf("Error! Wrong length of the %s! Try again:\n\n", nameShip);
            return false;

        }
        if (beginningCoordinateX > endingCoordinateX) {
            int temp = endingCoordinateX;
            endingCoordinateX = beginningCoordinateX;
            beginningCoordinateX = temp;
        }
        if (beginningCoordinateY > endingCoordinateY) {
            int temp = endingCoordinateY;
            endingCoordinateY = beginningCoordinateY;
            beginningCoordinateY = temp;
        }
        String[][] tempArr = Arrays.copyOf(field, field.length);
        if ((beginningCoordinateX != endingCoordinateX) && (beginningCoordinateY != endingCoordinateY)) {
            System.out.println("Error! Wrong ship location! Try again:\n");
            return false;
        }
        for (int i = beginningCoordinateX; i <= endingCoordinateX; i++) {
            for (int j = beginningCoordinateY; j <= endingCoordinateY; j++) {
                if (playerField.isInvalidCellToFill(i, j)) {
                    System.out.println("Error! You placed it too close to another one. Try again:\n");
                    field = Arrays.copyOf(tempArr, tempArr.length);
                    playerField.setFieldWithShips(field);
                    return false;
                }
            }
        }
        for (int i = beginningCoordinateX; i <= endingCoordinateX; i++) {
            for (int j = beginningCoordinateY; j <= endingCoordinateY; j++) {
                field[i][j] = "O";
            }

        }
        playerField.setFieldWithShips(field);
        return true;
    }

    public static boolean fillTurn(String turnCoordinates, PlayerField playerField) {
        String[][] fieldWithShips = playerField.getFieldWithShips();
        String[][] fieldWithFog = playerField.getFieldWithFog();
        int turnCoordinateX = getNumberByLetter(turnCoordinates.split("")[0]);
        int turnCoordinateY = Integer.parseInt(turnCoordinates.substring(1)) - 1;
        if (!playerField.isCoordinateInField(turnCoordinateX) || !playerField.isCoordinateInField(turnCoordinateY)) {
            System.out.println("Error! You entered the wrong coordinates! Try again:\n");
            return false;
        }

        if (fieldWithShips[turnCoordinateX][turnCoordinateY].contains("O")) {
            fieldWithFog[turnCoordinateX][turnCoordinateY] = "X";
            fieldWithShips[turnCoordinateX][turnCoordinateY] = "X";
            playerField.setFieldWithShips(fieldWithShips);
            playerField.setFieldWithFog(fieldWithFog);
            playerField.printFieldWithFog();
            System.out.println("You hit a ship!\n");
        } else {

            fieldWithFog[turnCoordinateX][turnCoordinateY] = "M";
            fieldWithShips[turnCoordinateX][turnCoordinateY] = "M";
            playerField.setFieldWithShips(fieldWithShips);
            playerField.setFieldWithFog(fieldWithFog);
            playerField.printFieldWithFog();
            System.out.println("You missed!\n");
        }

        return true;
    }

    public static void addPossibleShips() {
        possibleShips.put("Aircraft Carrier", 5);
        possibleShips.put("Battleship", 4);
        possibleShips.put("Submarine", 3);
        possibleShips.put("Cruiser", 3);
        possibleShips.put("Destroyer", 2);
    }
}
