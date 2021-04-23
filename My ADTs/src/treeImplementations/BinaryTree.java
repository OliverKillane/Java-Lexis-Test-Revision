package treeImplementations;

public interface BinaryTree<T extends Comparable<T>> {
  void add(T data);
  void remove(T data);
  int size();
  boolean contains(T data);
  boolean isEmpty();
  String toString();
}
