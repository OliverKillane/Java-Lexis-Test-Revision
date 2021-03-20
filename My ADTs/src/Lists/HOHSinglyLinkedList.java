package Lists;

import Utils.Tuple;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;

public class HOHSinglyLinkedList<T> implements List<T>{
  private final HOHSinglyLinkedNode<T>
      start = new HOHSinglyLinkedNode<>(null),
      end = new HOHSinglyLinkedNode<>(null);
  private AtomicInteger size = new AtomicInteger(0);

  private Tuple<HOHSinglyLinkedNode<T>,HOHSinglyLinkedNode<T>> find(int index){
    //HOH algorithm implemented

    // setup pre, current nodes
    HOHSinglyLinkedNode<T> prev = start, current = start.getNextNode();
    prev.lock();
    current.lock();

    // hand over hand
    for(int pos = 0; pos < index && current != end; pos++){
      prev.unlock();
      prev = current;
      current = current.getNextNode();
      current.lock();
    }

    // return locked nodes
    return new Tuple<>(prev, current);
  }

  @Override
  public void add(T data) {
    end.lock();

  }

  @Override
  public void insert(T data, int position) {

  }

  @Override
  public void remove(int position) {

  }

  @Override
  public T get(int index) {
    return null;
  }

  @Override
  public int size() {
    return size.get();
  }

  @Override
  public boolean isEmpty() {
    return false;
  }

  private
}
