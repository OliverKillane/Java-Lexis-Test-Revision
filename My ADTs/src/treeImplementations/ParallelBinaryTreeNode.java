package treeImplementations;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ParallelBinaryTreeNode<T> {
  private final Lock lock = new ReentrantLock();
  private ParallelBinaryTreeNode<T> left;
  private ParallelBinaryTreeNode<T> right;
  private T data;
  private boolean valid = true;


  public ParallelBinaryTreeNode(T data) {
    this.data = data;
  }

  // locking methods
  public void lock() {
    lock.lock();
  }

  public void unlock() {
    lock.unlock();
  }

  // getter methods
  public T getData() {
    return data;
  }

  public boolean isValid() {
    return valid;
  }

  public ParallelBinaryTreeNode<T> getLeft() {
    return left;
  }

  public ParallelBinaryTreeNode<T> getRight() {
    return right;
  }

  // setters
  public void setRight(ParallelBinaryTreeNode<T> right) {
    this.right = right;
  }

  public void setLeft(ParallelBinaryTreeNode<T> left) {
    this.left = left;
  }

  public void invalidate() {
    valid = false;
  }
}
