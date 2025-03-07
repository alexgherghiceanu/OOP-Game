package gameboard;

//Class used to test the grid
public class Test_Grid_Generation {
    public static void main(String[] args) {
        try {
            //Create the grid
            Grid grid = Grid.generateGrid(9, 9, new Cell(0, 0, CellEntityType.PLAYER, false, false));
            System.out.println(grid);
            Cell startingCell = grid.getStartingCell();
            System.out.println("Starting Cell: " + startingCell.getOx() + " " + startingCell.getOy());

            //Move in all 4 directions as much as possible
            while (true) {
            try {
                grid.goNorth();
                System.out.println("Went North");
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
                break;
            }
            }
            System.out.println(grid);
            while (true) {
                try {
                    grid.goEast();
                    System.out.println("Went East");
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                    break;
                }
            }
            System.out.println(grid);
            while (true) {
                try {
                    grid.goSouth();
                    System.out.println("Went South");
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                    break;
                }
            }
            System.out.println(grid);

            while (true) {
                try {
                    grid.goWest();
                    System.out.println("Went West");
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                    break;
                }
            }
            System.out.println(grid);

            //Prints out each cell's location and type to help debug
            for (int i = 0; i < grid.getLength(); i++) {
                for (int j = 0; j < grid.getWith(); j++) {
                    Cell cell = grid.getCell(i, j);
                    String res = cell.cellType + " Ox: " + cell.Ox + " Oy: " + cell.Oy;
                    System.out.println(res);
                }
            }


        } catch (Exception e) {
            System.out.println("Unexpected Exception during test: " + e.getMessage());
        }
    }
}
