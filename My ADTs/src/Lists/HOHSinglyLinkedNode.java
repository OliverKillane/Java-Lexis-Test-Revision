package Lists;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class HOHSinglyLinkedNode<T> {
  private final Lock lock = new ReentrantLock();
  private final T data;
  private HOHSinglyLinkedNode<T> nextNode;

  public HOHSinglyLinkedNode(T data) {
    this.data = data;
  }

  public HOHSinglyLinkedNode(T data, HOHSinglyLinkedNode<T> nextNode) {
    this(data);
    this.nextNode = nextNode;
  }

  public T getData() {
    return data;
  }

  public HOHSinglyLinkedNode<T> getNextNode() {
    return nextNode;
  }

  public void setNextNode(HOHSinglyLinkedNode<T> nextNode) {
    this.nextNode = nextNode;
  }

  public void lock() {
    lock.lock();
  }

  public void unlock() {
    lock.unlock();
  }
}
