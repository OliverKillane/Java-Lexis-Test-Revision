package treeImplementations;

public class BinaryTreeNode<T> {
  final private T data;
  private BinaryTreeNode<T> left;
  private BinaryTreeNode<T> right;
  private boolean valid = true;

  public BinaryTreeNode(T data) {
    this.data = data;
  }


  // Getters for data, successor nodes.
  public T getData() {
    return data;
  }

  public boolean isValid() {
    return valid;
  }

  public BinaryTreeNode<T> getLeft() {
    return left;
  }

  public BinaryTreeNode<T> getRight() {
    return right;
  }

  // setters for subtrees
  public void setLeft(BinaryTreeNode<T> left) {
    this.left = left;
  }

  public void setRight(BinaryTreeNode<T> right) {
    this.right = right;
  }

  public void invalidate() {
    valid = false;
  }
}
