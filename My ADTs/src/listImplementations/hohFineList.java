package listImplementations;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class hohFineList<T> implements List<T> {
  private final LockedNode<T> end = new LockedNode<>(null);
  private final LockedNode<T> start = new LockedNode<>(null, end);
  private final AtomicInteger size = new AtomicInteger(0);

  @Override
  public void add(T data) {
    LockedNode<T> previous = start;
    previous.lock();

    LockedNode<T> current = previous.getNextNode();
    current.lock();

    while (current != end) {
      previous.unlock();

      previous = current;

      current = current.getNextNode();

      current.lock();
    }

    // current and previous are locked nodes, current == end

    previous.setNextNode(new LockedNode<>(data, end));
    size.incrementAndGet();

    current.unlock();
    previous.unlock();
  }

  @Override
  public void insert(T data, int position) {
    Pair<LockedNode<T>, LockedNode<T>> pair = find(position);

    pair.first.setNextNode(new LockedNode<>(data, pair.second));
    size.incrementAndGet();

    unlockpair(pair);
  }

  @Override
  public void remove(int position) {
    Pair<LockedNode<T>, LockedNode<T>> pair = find(position);

    pair.first.setNextNode(pair.second.getNextNode());
    size.decrementAndGet();

    unlockpair(pair);
  }

  @Override
  public T get(int position) {
    Pair<LockedNode<T>, LockedNode<T>> pair = find(position);

    T data = pair.second.getData();

    unlockpair(pair);

    return data;
  }

  private Pair<LockedNode<T>, LockedNode<T>> find(int position) {
    LockedNode<T> previous = start;
    previous.lock();

    LockedNode<T> current = previous.getNextNode();
    current.lock();

    for (int index = 0; index < position; index++) {
      previous.unlock();

      previous = current;

      current = current.getNextNode();

      if (current == null) {
        throw new IllegalArgumentException("Remove position out of bounds");
      }

      current.lock();
    }

    return new Pair<>(previous, current);
  }

  private void unlockpair(Pair<LockedNode<T>, LockedNode<T>> pair) {
    pair.first.unlock();
    pair.second.unlock();
  }

  @Override
  public int size() {
    return size.get();
  }

  @Override
  public boolean isEmpty() {
    return size.get() == 0;
  }
}


class LockedNode<T> {
  private final Lock lock = new ReentrantLock();
  private final T data;
  private LockedNode<T> nextNode;

  public LockedNode(T data) {
    this.data = data;
  }

  public LockedNode(T data, LockedNode<T> nextNode) {
    this(data);
    this.nextNode = nextNode;
  }

  public T getData() {
    return data;
  }

  public LockedNode<T> getNextNode() {
    return nextNode;
  }

  public void setNextNode(LockedNode<T> nextNode) {
    this.nextNode = nextNode;
  }

  public void lock() {
    lock.lock();
  }

  public void unlock() {
    lock.unlock();
  }
}

class Pair<T, U> {
  public final T first;
  public final U second;

  public Pair(T first, U second) {
    this.first = first;
    this.second = second;
  }
}