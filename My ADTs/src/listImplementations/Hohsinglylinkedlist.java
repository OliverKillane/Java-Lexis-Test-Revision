package listImplementations;

import Utils.Tuple;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;

/**
 * A Singly linked list implemented with hand over hand fine grained locking.
 *
 * @param <T> type of data to be storedin in the list.
 */
public class Hohsinglylinkedlist<T> implements List<T> {

  private final hohsinglylinkednode<T> end = new hohsinglylinkednode<>(null);
  private final hohsinglylinkednode<T> start = new hohsinglylinkednode<>(null, end);
  private AtomicInteger size = new AtomicInteger(0);

  private Tuple<hohsinglylinkednode<T>, hohsinglylinkednode<T>> find(
      BiFunction<Integer, hohsinglylinkednode<T>, Boolean> predicate) {
    //HOH algorithm implemented

    // predicate is true when the correct reached

    // setup pre, current nodes
    hohsinglylinkednode<T> prev = start;
    prev.lock();

    // once the start node is locked, then we have a guarentee on what the next node will be.
    hohsinglylinkednode<T> current = start.getNextNode();
    current.lock();

    // hand over hand locking, continue until predicate satisfied or no more nodes.
    for (int pos = 0; !predicate.apply(pos, current) && current != end; pos++) {

      prev.unlock();
      prev = current;
      current = current.getNextNode();
      if (current == null) {
        System.out.println("We got him bois");
      }
      current.lock();
    }

    // return locked nodes
    return new Tuple<>(prev, current);
  }

  private Tuple<hohsinglylinkednode<T>, hohsinglylinkednode<T>> find(int index) {
    return find((ind, a) -> ind == index);
  }

  @Override
  public void add(T data) {

    // get last two nodes
    Tuple<hohsinglylinkednode<T>, hohsinglylinkednode<T>> position = find((a, b) -> b == end);

    // the second node in the tuple must be the last node
    assert position.second == end;

    // create new node to insert, with its next node ebing the end of the linkedlist
    hohsinglylinkednode<T> newnode = new hohsinglylinkednode<>(data, end);

    // insert the new node into the list
    position.first.setNextNode(newnode);

    // increment the size
    size.incrementAndGet();

    // unlock the nodes
    unlockpair(position);
  }

  @Override
  public void insert(T data, int index) {
    // get nodes between which to insert the new data.
    Tuple<hohsinglylinkednode<T>, hohsinglylinkednode<T>> position = find(index);

    // insert the new node
    hohsinglylinkednode<T> newnode = new hohsinglylinkednode<>(data, position.second);
    position.first.setNextNode(newnode);

    // increment the size
    size.incrementAndGet();

    // unlock the locked nodes
    unlockpair(position);
  }

  @Override
  public void remove(int index) {
    //get the node to remove (position.second) based of the index
    Tuple<hohsinglylinkednode<T>, hohsinglylinkednode<T>> position = find(index);

    position.first.setNextNode(position.second.getNextNode());

    // decrement the size
    size.decrementAndGet();

    unlockpair(position);
  }

  public void remove(T data) {
    Tuple<hohsinglylinkednode<T>, hohsinglylinkednode<T>> position = find(
        (ind, node) -> node.getData().equals(data));

    position.first.setNextNode(position.second.getNextNode());

    unlockpair(position);
  }

  private void unlockpair(Tuple<hohsinglylinkednode<T>, hohsinglylinkednode<T>> pair) {
    pair.first.unlock();
    pair.second.unlock();
  }


  @Override
  public T get(int index) {
    Tuple<hohsinglylinkednode<T>, hohsinglylinkednode<T>> position = find(index);
    T data = position.second.getData();
    unlockpair(position);
    return data;
  }

  @Override
  public int size() {
    return size.get();
  }

  @Override
  public boolean isEmpty() {
    start.lock();
    boolean result = start.getNextNode() == end;
    start.unlock();
    return result;
  }

  @Override
  public String toString() {
    String result = "";
    // lock start, then move node by node, adding the result and continuing

    // We cannot ignore locking, as some nodes may be in the process of being added (no next node yet).


    // get first two node, start and its successor
    start.lock();
    hohsinglylinkednode<T> prev = start;

    start.getNextNode().lock();
    hohsinglylinkednode<T> current = start.getNextNode();

    // hand over hand locking through the singly linked nodes
    while (current != end) {
      result += ", " + current.getData().toString();
      prev.unlock();
      prev = current;
      current = current.getNextNode();
      current.lock();
    }

    //unlock the nodes at the end
    prev.unlock();
    current.unlock();

    // return the completed string representation
    return "[" + result.substring(2) + "]";
  }
}
