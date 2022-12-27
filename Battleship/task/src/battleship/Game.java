package battleship;

import java.util.*;

import static battleship.PlayerField.getNumberByLetter;

public class Game {
    public static Map<String, Integer> getPossibleShips() {
        return possibleShips;
    }

    private static final Map<String, Integer> possibleShips = new LinkedHashMap<>();

    public static boolean fillShip(String userCoordinatesInput, String nameShip, int lengthOfShip, PlayerField playerField) {
        List<List<String>> aliveShips = playerField.getAliveShips();
        String[][] field = playerField.getFieldWithShips();
        String[] userCoordinatesInputArr = userCoordinatesInput.split(" ");
        if (userCoordinatesInputArr.length != 2) {
            System.out.println("Error! Wrong ship location! Try again:\n");
            return false;
        }

        String beginningCoordinate = userCoordinatesInputArr[0];
        String endingCoordinate = userCoordinatesInputArr[1];
        if (playerField.isInvalidCoordinates(beginningCoordinate) || playerField.isInvalidCoordinates(endingCoordinate)) {
            System.out.println("Error! Wrong ship location! Try again:\n");
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
        List<String> aliveShipCoordinates = new ArrayList<>();
        for (int i = beginningCoordinateX; i <= endingCoordinateX; i++) {
            for (int j = beginningCoordinateY; j <= endingCoordinateY; j++) {
                field[i][j] = "O";
                aliveShipCoordinates.add(i + "," + j);
            }

        }
        aliveShips.add(aliveShipCoordinates);
        playerField.setAliveShips(aliveShips);
        playerField.setFieldWithShips(field);
        return true;
    }

    public static boolean fillTurn(String turnCoordinates, PlayerField foePlayer) {
        String[][] fieldWithShips = foePlayer.getFieldWithShips();
        int turnCoordinateX = getNumberByLetter(turnCoordinates.split("")[0]);
        int turnCoordinateY = Integer.parseInt(turnCoordinates.substring(1)) - 1;
        if (!foePlayer.isCoordinateInField(turnCoordinateX) || !foePlayer.isCoordinateInField(turnCoordinateY)) {
            System.out.println("Error! You entered the wrong coordinates! Try again:\n");
            return false;
        }
        if (fieldWithShips[turnCoordinateX][turnCoordinateY].contains("O")) {
            fillHit(turnCoordinateX, turnCoordinateY, foePlayer);
        } else if (fieldWithShips[turnCoordinateX][turnCoordinateY].contains("M")) {
            fillMissed(turnCoordinateX, turnCoordinateY, foePlayer);
        } else if (fieldWithShips[turnCoordinateX][turnCoordinateY].contains("X")) {
            fillHit(turnCoordinateX, turnCoordinateY, foePlayer);
        } else {
            fillMissed(turnCoordinateX, turnCoordinateY, foePlayer);
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

    public static boolean isGameNotEnded(PlayerField playerField) {
        String[][] fieldWithShips = playerField.getFieldWithShips();
        for (String[] fieldWithShip : fieldWithShips) {
            for (String s : fieldWithShip) {
                if (s.contains("O")) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void fillMissed(int turnCoordinateX, int turnCoordinateY, PlayerField foePlayer) {
        String[][] fieldWithShips = foePlayer.getFieldWithShips();
        String[][] fieldWithFog = foePlayer.getFieldWithFog();
        fieldWithFog[turnCoordinateX][turnCoordinateY] = "M";
        fieldWithShips[turnCoordinateX][turnCoordinateY] = "M";
        foePlayer.setFieldWithShips(fieldWithShips);
        foePlayer.setFieldWithFog(fieldWithFog);
        System.out.println("You missed!\n");
    }

    private static void fillHit(int turnCoordinateX, int turnCoordinateY, PlayerField foePlayer) {
        String[][] fieldWithShips = foePlayer.getFieldWithShips();
        String[][] fieldWithFog = foePlayer.getFieldWithFog();
        fieldWithFog[turnCoordinateX][turnCoordinateY] = "X";
        fieldWithShips[turnCoordinateX][turnCoordinateY] = "X";
        foePlayer.setFieldWithShips(fieldWithShips);
        foePlayer.setFieldWithFog(fieldWithFog);
        List<List<String>> aliveShips = foePlayer.getAliveShips();
        for (List<String> aliveShipCoordinates : aliveShips
        ) {
            for (int i = 0; i < aliveShipCoordinates.size(); i++) {
                if (aliveShipCoordinates.get(i).contains(turnCoordinateX + "," + turnCoordinateY)) {
                    aliveShipCoordinates.remove(i);
                    if (!isGameNotEnded(foePlayer)) {
                        System.out.println("You sank the last ship. You won. Congratulations!");
                    } else if (aliveShipCoordinates.isEmpty()) {
                        System.out.println("You sank a ship!\n");
                    }else {
                        System.out.println("You hit a ship!\n");
                    }
                    return;
                }
            }
        }
    }

    public static void printFiledForPlayer(PlayerField playerOwnField, PlayerField playerFoeField) {
        playerFoeField.printFieldWithFog();
        for (int i = 0; i <= 20; i++) {
            System.out.print("-");
        }
        System.out.println();
        playerOwnField.printFieldWithShips();
    }
}

