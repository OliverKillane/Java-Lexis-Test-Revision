package cells;

public class MutableCell<T> implements Cell<T>{
  private T value;

  // take no parameters, sets value to null (value is already null);
  public MutableCell(){}


  // takes value as parameter, if value == null, throws exception
  public MutableCell(T value){
    set(value);
  }

  @Override
  public void set(T value) {
    if (value == null){
      throw new IllegalArgumentException();
    } else {
      this.value = value;
    }
  }

  @Override
  public T get() {
    return value;
  }

  @Override
  public boolean isSet() {
    return value != null;
  }
}
