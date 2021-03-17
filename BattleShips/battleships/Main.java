import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Grid grid = makeInitialGrid();

        int attacks = 0;
        Coordinate attackCoor;


        while (!grid.areAllSunk()){
            attacks++;
            System.out.print(grid.toPlayerString());
            System.out.print("PLEASE ENTER ATTACK:   ");
            attackCoor = Util.parseCoordinate(input.nextLine());
            if (grid.wouldAttackSucceed(attackCoor)){
                System.out.println("Direct Hit!");
            }
            grid.attackCell(attackCoor);
        }

        System.out.println("You used " + attacks + " attempts to destroy all the Computer's ships");
        System.out.print(grid.toString());

        // TODO: Question 4.
    }

    private static Grid makeInitialGrid() {
        Grid grid = new Grid();

        String[] coords = { "A7", "B1", "B4", "D3", "F7", "H1", "H4" };
        int[] sizes = { 2, 4, 1, 3, 1, 2, 5 };
        boolean[] isDowns = { false, true, true, false, false, true, false };

        for (int i = 0; i < coords.length; i++) {
            Coordinate c = Util.parseCoordinate(coords[i]);
            grid.placeShip(c, sizes[i], isDowns[i]);
        }

        return grid;
    }
}
