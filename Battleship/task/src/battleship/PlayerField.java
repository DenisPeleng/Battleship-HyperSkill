package battleship;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayerField {
    private String[][] fieldWithShips = new String[10][10];
    private String[][] fieldWithFog = new String[10][10];
    private static final String[] alphabet = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};

    PlayerField() {
        String defaultFogOfWar = "~";
        for (String[] strings : fieldWithShips) {
            Arrays.fill(strings, defaultFogOfWar);
        }
        for (String[] strings : fieldWithFog) {
            Arrays.fill(strings, defaultFogOfWar);
        }
    }

    public String[][] getFieldWithFog() {
        return fieldWithFog;
    }

    public void setFieldWithFog(String[][] fieldWithFog) {
        this.fieldWithFog = fieldWithFog;
    }

    public String[][] getFieldWithShips() {
        return fieldWithShips;
    }

    public void setFieldWithShips(String[][] fieldWithShips) {
        this.fieldWithShips = fieldWithShips;
    }

    public void printFieldWithShips() {
        printField(fieldWithShips);
    }

    public void printFieldWithFog() {
        printField(fieldWithFog);
    }

    private void printField(String[][] field) {
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


    public boolean isInvalidCoordinates(String coordinate) {
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

    public boolean isCoordinateInField(int coordinate) {
        return coordinate >= 0 && coordinate < 10;
    }

    public static int getNumberByLetter(String letter) {
        for (int i = 0; i < alphabet.length; i++) {
            if (alphabet[i].contains(letter.toUpperCase())) {
                return i;
            }
        }
        return -1;
    }

    public boolean isInvalidCellToFill(int x, int y) {
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
                if (!fieldWithShips[tempX][tempY].contains("~")) {
                    return true;
                }
            }
        }
        return false;

    }


}
