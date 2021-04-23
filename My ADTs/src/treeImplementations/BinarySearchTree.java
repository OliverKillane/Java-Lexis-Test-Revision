package treeImplementations;

public class BinarySearchTree<T extends Comparable<T>> implements BinaryTree<T> {
  private int size = 0;
  private BinaryTreeNode<T> root = null;

  @Override
  public void add(T data) {

    // check that the tree is not empty
    if (isEmpty()) {
      root = new BinaryTreeNode<>(data);
    } else {
      BinaryTreeNode<T> previous = root;
      BinaryTreeNode<T> current = getNext(data, previous);

      while (current != null) {
        previous = current;
        current = getNext(data, previous);
      }

      insertNext(data, previous);
      size++;
    }
  }

  @Override
  public void remove(T data) {
    if (!isEmpty()) {
      BinaryTreeNode<T> current = root;

      while (data.compareTo(current.getData()) != 0 || !current.isValid()) {
        current = getNext(data, current);

        if (current == null) {
          throw new IllegalArgumentException("Data to remove was not present in the tree");
        }
      }

      current.invalidate();
      size--;
    }
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean contains(T data) {
    if (!isEmpty()) {

      // not empty, so root != null
      BinaryTreeNode<T> current = root;

      // go until node found, skipping over invalid nodes
      while (data.compareTo(current.getData()) != 0 || !current.isValid()) {
        current = getNext(data, current);

        // if we leave a leaf node, we know we did not find the data
        if (current == null) {
          return false;
        }
      }

      // while loop exited, current node has data and is valid
      return true;
    } else {

      // list is empty
      return false;
    }
  }

  @Override
  public boolean isEmpty() {
    return root == null || !root.isValid();
  }

  private BinaryTreeNode<T> getNext(T data, BinaryTreeNode<T> parent) {
    return data.compareTo(parent.getData()) < 0 ? parent.getLeft() : parent.getRight();
  }

  private void insertNext(T data, BinaryTreeNode<T> parent) {
    BinaryTreeNode<T> child = new BinaryTreeNode<>(data);

    if (data.compareTo(parent.getData()) < 0) {
      parent.setRight(child);
    } else {
      parent.setLeft(child);
    }
  }
}
