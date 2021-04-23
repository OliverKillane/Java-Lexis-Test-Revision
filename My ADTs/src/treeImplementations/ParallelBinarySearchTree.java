package treeImplementations;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ParallelBinarySearchTree <T extends Comparable<T>> implements BinaryTree<T> {
  private final Lock rootLock = new ReentrantLock();
  private ParallelBinaryTreeNode<T> root;
  private AtomicInteger size = new AtomicInteger(0);

  @Override
  public void add(T data) {
    // need to do hoh locking as you traverse the tree.

    rootLock.lock();

    if (root == null) {
      root = new ParallelBinaryTreeNode<>(data);

      size.incrementAndGet();

      rootLock.unlock();
    } else {
      ParallelBinaryTreeNode<T> previous = root;
      ParallelBinaryTreeNode<T> current = getNext(data, root);

      previous.lock();
      rootLock.unlock();

      while (current != null) {
        current.lock();
        previous.unlock();
        previous = current;
        current = getNext(data, current);
      }

      insertNext(data, previous);
      size.incrementAndGet();
      previous.unlock();
    }
  }

  @Override
  public void remove(T data) {

    ParallelBinaryTreeNode<T> current = root;

    // lazy removal - mark a node as invalid and move on.
    while (current != null) {

      //lock taken to ensure validity does not change as the node is being invalidated.
      current.lock();
      if (current.isValid() && 0 == data.compareTo(current.getData())) {
        current.invalidate();

        // update the size.
        size.decrementAndGet();

        // end when data invalidated
        return;
      }
      current.unlock();
      current = getNext(data, current);
    }

    // no data was invalidated and all nodes searched.
    throw new IllegalArgumentException("Data was not in the tree");
  }

  @Override
  public int size() {
    return size.get();
  }

  @Override
  public boolean contains(T data) {
    ParallelBinaryTreeNode<T> current = root;

    while (current != null) {
      current.lock();
      if (current.isValid() && 0 == data.compareTo(current.getData())) {

        // end when data invalidated
        return true;
      }
      current.unlock();
      current = getNext(data, current);
    }

    // no valid nodes were found with the data and hence the data is not contained.
    return false;
  }

  private ParallelBinaryTreeNode<T> getNext(T data, ParallelBinaryTreeNode<T> parent) {
    return data.compareTo(parent.getData()) < 0 ? parent.getRight() : parent.getLeft();
  }

  private void insertNext(T data, ParallelBinaryTreeNode<T> parent) {
    ParallelBinaryTreeNode<T> child = new ParallelBinaryTreeNode<>(data);

    if (data.compareTo(parent.getData()) < 0) {
      parent.setRight(child);
    } else {
      parent.setLeft(child);
    }
  }

  @Override
  public boolean isEmpty() {
    return size() == 0;
  }

  @Override
  public String toString() {
    return getNodeString(root);
  }

  public static <A> String getNodeString(ParallelBinaryTreeNode<A> node) {
    String result = "";

    if (node.getRight() != null) {
      result += getNodeString(node.getRight());
    }

    // no node locking as consistency cannot be achieved, even if nodes were locked, they may
    // have been invalidated at the time of return.
    if (node.isValid()) {
      result += node.getData().toString();
    }

    if (node.getLeft() != null) {
      result += getNodeString(node.getLeft());
    }

    return result;
  }
}
