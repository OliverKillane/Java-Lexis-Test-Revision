package cells;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

public class BackedUpMutableCell<T> extends MutableCell<T> implements BackedUpCell<T>{
  private final Stack<T> backup = new Stack<>();
  private final int limit;
  private final boolean boundedBackup;

  public BackedUpMutableCell(){
    super();
    boundedBackup = false;
    this.limit = 0;

  }

  public BackedUpMutableCell(int limit){
    super();
    if (limit < 0){
      throw new IllegalArgumentException();
    } else {
      this.limit = limit;
      boundedBackup = true;
    }
  }

  @Override
  public void set(T item){
    if (isSet()){
      T oldValue = get();
      super.set(item);

      // if cannot set new value, set ends before changing the backup
      backup.push(oldValue);

      // if above limit, then it must only be by 1, hence remove last element and continue
      if (boundedBackup && limit < backup.size()){
        backup.remove(limit);
      }
    } else {
      super.set(item);
    }
  }


  @Override
  public boolean hasBackup() {
    return backup.size() != 0;
  }

  @Override
  public void revertToPrevious() {
    if (!hasBackup()){
      throw new UnsupportedOperationException();
    } else {
      super.set(backup.pop());
    }
  }
}
