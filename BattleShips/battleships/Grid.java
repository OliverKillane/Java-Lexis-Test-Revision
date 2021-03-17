import java.util.Arrays;

public class Grid {

  private static final int WIDTH = 10;
  private static final int HEIGHT = 10;

  private final Piece[][] grid = new Piece[HEIGHT][WIDTH];

  public Grid() {
    for (Piece[] row : grid) {
      Arrays.setAll(row, p -> Piece.WATER);
    }

    // TODO: Question 2a.
  }

  private static String renderGrid(Piece[][] grid) {
    StringBuilder sb = new StringBuilder();
    sb.append(" 0123456789\n");
    for (int i = 0; i < grid.length; i++) {
      sb.append((char) ('A' + i));
      for (int j = 0; j < grid[i].length; j++) {
        if (grid[i][j] == null) {
          return "!";
        }
        switch (grid[i][j]) {
          case SHIP:
            sb.append('#');
            break;
          case DAMAGED_SHIP:
            sb.append('*');
            break;
          case MISS:
            sb.append('o');
            break;
          case WATER:
            sb.append('.');
            break;
        }

      }
      sb.append('\n');
    }

    return sb.toString();
  }

  public boolean canPlace(Coordinate c, int size, boolean isDown) {

    // check bounds, use of ternary for deciding whether to check down or across.
    if (!validCoor(c) || isDown ? c.getRow() + size
        >= grid.length : c.getColumn() + size >= grid[0].length) {
      return false;
    }

    if (isDown) {
      for (int row = c.getRow(), col = c.getColumn(); row < size + c.getRow(); row++) {
        if (grid[row][col] != Piece.WATER) {
          return false;
        }
      }
    } else {
      for (int col = c.getColumn(), row = c.getRow(); col < size + c.getColumn(); col++) {
        if (grid[row][col] != Piece.WATER) {
          return false;
        }
      }
    }

    // passed all, it must be true
    return true; // TODO: delete this line for Question 2b.
  }

  public void placeShip(Coordinate c, int size, boolean isDown) {
    if (canPlace(c, size, isDown)){
      if (isDown){
        for (int row = c.getRow(), col = c.getColumn(); row < size + c.getRow(); row++){
          grid[row][col] = Piece.SHIP;
        }
      } else {
        for (int col = c.getColumn(), row = c.getRow(); col < size + c.getColumn(); col++){
          grid[row][col] = Piece.SHIP;
        }
      }
    }
    // TODO: Question 2c.
  }

  public boolean wouldAttackSucceed(Coordinate c) {
    return getGridValue(c) == Piece.SHIP;
  }

  public void attackCell(Coordinate c) {
    setGridValue(c, wouldAttackSucceed(c) ? Piece.DAMAGED_SHIP : Piece.MISS);
  }

  private Piece getGridValue(Coordinate c){
      return grid[c.getRow()][c.getColumn()];
  }

  private void setGridValue(Coordinate c, Piece p){
      grid[c.getRow()][c.getColumn()] = p;
  }

  private boolean validCoor(Coordinate c){
    return c.getRow() < grid.length || c.getColumn() < grid[0].length;
  }

  public boolean areAllSunk() {
    // no ships exist -> or at least all are either water, mises or damaged ships
    return Arrays.stream(grid).flatMap(Arrays::stream).noneMatch(p -> p == Piece.SHIP);
  }

  public String toPlayerString() {
    return renderGrid(grid).replace('#','.');
  }

  @Override
  public String toString() {
    return renderGrid(grid);
  }
}
