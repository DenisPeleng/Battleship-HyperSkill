package battleship;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayerField {
    private String[][] field = new String[10][10];
    private final String[] alphabet = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};

    PlayerField() {
        String defaultFogOfWar = "~";
        for (String[] strings : field) {
            Arrays.fill(strings, defaultFogOfWar);
        }
    }

    public void printField() {
        System.out.print("  ");
        for (int i = 1; i <= field.length; i++) {
            System.out.printf("%s ", i);

        }
        System.out.println();
        for (int i = 0; i < field.length; i++) {
            String[] strings = field[i];
            System.out.printf("%s ", alphabet[i]);
            for (String string : strings) {
                System.out.printf("%s ", string);
            }
            System.out.println();
        }
        System.out.println();
    }

    public boolean fillShip(String userCoordinatesInput, String nameShip, int lengthOfShip) {
        String[] userCoordinatesInputArr = userCoordinatesInput.split(" ");
        if (userCoordinatesInputArr.length != 2) {
            return false;
        }

        String beginningCoordinate = userCoordinatesInputArr[0];
        String endingCoordinate = userCoordinatesInputArr[1];
        if (isInvalidCoordinates(beginningCoordinate) || isInvalidCoordinates(endingCoordinate)) {
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
                if (!isCellValidToFill(i, j)) {
                    System.out.println("Error! You placed it too close to another one. Try again:\n");
                    field = Arrays.copyOf(tempArr, tempArr.length);
                    return false;
                }
            }
        }
        for (int i = beginningCoordinateX; i <= endingCoordinateX; i++) {
            for (int j = beginningCoordinateY; j <= endingCoordinateY; j++) {
                field[i][j] = "O";
            }

        }
        return true;
    }

    private boolean isInvalidCoordinates(String coordinate) {
        if (coordinate.length() > 3 || coordinate.length() < 2) {
            return false;
        }
        String[] coordinatesArr = coordinate.split("");
        int x = getNumberByLetter(coordinatesArr[0]);
        int y = Integer.parseInt(coordinatesArr[1]) - 1;
        if (isCoordinateInField(x)) {
            return false;
        }
        return isCoordinateInField(y);

    }

    private boolean isCoordinateInField(int coordinate) {
        return coordinate >= 0 && coordinate < 10;
    }

    private int getNumberByLetter(String letter) {
        for (int i = 0; i < alphabet.length; i++) {
            if (alphabet[i].contains(letter.toUpperCase())) {
                return i;
            }
        }
        return -1;
    }

    private boolean isCellValidToFill(int x, int y) {
        List<Integer> coordinatesXToCheck = new ArrayList<>();
        List<Integer> coordinatesYToCheck = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            if (isCoordinateInField(x + i)) {
                coordinatesXToCheck.add(x + i);
            }
            if (isCoordinateInField(y + i)) {
                coordinatesYToCheck.add(y + i);
            }
        }
        for (int tempX : coordinatesXToCheck
        ) {
            for (int tempY : coordinatesYToCheck
            ) {
                if (!field[tempX][tempY].contains("~")) {
                    return false;
                }
            }
        }
        return true;

    }
}
