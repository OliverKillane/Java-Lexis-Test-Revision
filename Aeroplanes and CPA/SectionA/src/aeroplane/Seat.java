package aeroplane;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class Seat {
  private static final int LAST_ROW = 50;
  private static final char LAST_SEAT = 'F';
  private static final String[] EMERGENCY_EXITS = {"1A","1F","10A","10F","30A","30F"};

  private final int row;
  private final char seat;

  public Seat(int row, char seat){
    this.row = row;
    this.seat = seat;
  }

  @Override
  public String toString(){
    return row + "" + seat;
  }

  public boolean hasNext(){
    return !(row == LAST_ROW && seat == LAST_SEAT);
  }

  public boolean isEmergencyExit(){
    // yuck change this
    return 0 <= Arrays.binarySearch(EMERGENCY_EXITS, toString());
  }

  public Seat next() {
    if (seat == LAST_SEAT && row < LAST_ROW) {
      return new Seat(row + 1, 'A');
    } else if (seat < LAST_SEAT){
      return new Seat(row, (char) (seat + 1));
    } else {
      throw new NoSuchElementException();
    }
  }

  @Override
  public boolean equals(Object other){
    if (other instanceof Seat){
      Seat otherseat = (Seat) other;
      return otherseat.row == row && otherseat.seat == seat;
    } else {
      return false;
    }
  }

  @Override
  public int hashCode(){
    return row + (LAST_ROW + 1) * seat;
  }


	// TODO: Section A, Tasks 1 and 3
	
}
