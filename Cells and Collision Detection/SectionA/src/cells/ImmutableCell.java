package cells;

import java.util.ArrayDeque;
import java.util.Deque;

public class ImmutableCell<T> implements Cell<T>{
  private final T value;

  public ImmutableCell(T value){
    if (value == null){
      throw new IllegalArgumentException();
    } else {
      this.value = value;
    }
  }

  @Override
  public void set(T value) {
    throw new UnsupportedOperationException();
  }

  @Override
  public T get() {
    return value;
  }

  @Override
  public boolean isSet() {
    // value must always be true as if not, constructor failed, so object was never created.
    return true;
  }

  @Override
  public boolean equals(Object other){
    if (other instanceof ImmutableCell){
      return value.equals(((ImmutableCell<T>) other).get());
    } else {
      return false;
    }
  }

  @Override
  public int hashCode(){
    return value.hashCode();
  }
}
