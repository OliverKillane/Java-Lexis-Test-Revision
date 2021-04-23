package listImplementations;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class hohsinglylinkednode<T> {
  private final Lock lock = new ReentrantLock();
  private final T data;
  private hohsinglylinkednode<T> nextNode;

  public hohsinglylinkednode(T data) {
    this.data = data;
  }

  public hohsinglylinkednode(T data, hohsinglylinkednode<T> nextNode) {
    this(data);
    this.nextNode = nextNode;
  }

  public T getData() {
    return data;
  }

  public hohsinglylinkednode<T> getNextNode() {
    return nextNode;
  }

  public void setNextNode(hohsinglylinkednode<T> nextNode) {
    this.nextNode = nextNode;
  }

  public void lock() {
    lock.lock();
  }

  public void unlock() {
    lock.unlock();
  }
}
