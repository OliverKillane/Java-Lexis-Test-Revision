package cells;

import java.util.Comparator;
import java.util.Stack;

public class BackedUpCellComparator<U> implements Comparator<BackedUpCell<U>> {
  private final Comparator<U> valueComparator;

  public BackedUpCellComparator(Comparator<U> valueComparator){
    this.valueComparator = valueComparator;
  }

  @Override
  public int compare(BackedUpCell<U> first, BackedUpCell<U> second){
    Stack<U>
        firstStack = new Stack<>(),
        secondStack = new Stack<>();

    int currentComp = 0;

    // first > second -> +ve
    // first < second -> -ve

    // comparing if one or the other or both are not set
    if (!first.isSet()){
      if (!second.isSet()){ return 0; }
      else { return -1; }
    } else { if (!second.isSet()){ return 1;} }


    // both are set
    while (true) {

      // compare values
      currentComp = valueComparator.compare(first.get(), second.get());

      if (currentComp != 0 || !first.hasBackup() && !second.hasBackup()){

        // if not equal, break out, restore cells and return
        break;
      }else if (first.hasBackup() && second.hasBackup()){

        // if both have backups, go to next items to compare
        firstStack.push(first.get());
        first.revertToPrevious();

        secondStack.push(second.get());
        second.revertToPrevious();
      }
      else {
        currentComp = first.hasBackup() ? 1 : -1;
        break;
      }
    }

    // restore the cells
    while (!firstStack.isEmpty()){
      first.set(firstStack.pop());
      second.set(secondStack.pop());
    }

    // return the comparison
    return currentComp;
  }

}
