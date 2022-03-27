package pl.marchel.battleships.gameController.gameStage;

public class Utils {
    public static int[] coordinateToArrayFormat(String coordinate) throws Exception{
        int[] result = new int[2];
        coordinate = coordinate.toLowerCase();
        if (!coordinate.matches("[a-j]([1-9]|10)")) {
            throw new Exception("Wrong format of coordinate: " + coordinate);
        }
        result[0] = (int) coordinate.charAt(0) - (int) 'a';
        result[1] = Integer.parseInt(coordinate.substring(1)) - 1;
        return result;
    }
}
