package listImplementations;

import Utils.Tuple;

public class SinglyLinkedList<T> implements List<T> {

  private SinglyLinkedNode<T> start;
  private int size = 0;


  private Tuple<SinglyLinkedNode<T>, SinglyLinkedNode<T>> find(int index) {
    SinglyLinkedNode<T> previous = start, current = start.getNextNode();
    for
    (
        int position = 1;
        position < index;
        previous = current, current = current.getNextNode(), position++
    ) {
      ;
    }
    return new Tuple<>(previous, current);
  }

  @Override
  public void add(T data) {
    if (isEmpty()) {
      start = new SinglyLinkedNode<>(data);
    } else {
      SinglyLinkedNode<T> current = start;
      for (; current.getNextNode() != null; current = current.getNextNode()) {
        ;
      }
      current.setNextNode(new SinglyLinkedNode<>(data));
    }
    size++;
  }

  @Override
  public void insert(T data, int index) {
    if (validind(index)) {
      SinglyLinkedNode<T> newNode = new SinglyLinkedNode<>(data);
      if (index == 0) {
        newNode.setNextNode(start);
        start = newNode;
      } else {
        Tuple<SinglyLinkedNode<T>, SinglyLinkedNode<T>> pos = find(index);
        pos.first.setNextNode(newNode);
        newNode.setNextNode(pos.second);
      }
      size++;
    } else if (index == size) {
      add(data);
    } else {
      throw new IllegalArgumentException();
    }
  }

  @Override
  public void remove(int index) {
    if (validind(index)) {
      if (index == 0) {
        start = start.getNextNode();
      } else {
        Tuple<SinglyLinkedNode<T>, SinglyLinkedNode<T>> pos = find(index);
        pos.first.setNextNode(pos.second.getNextNode());
      }
      size--;
    } else {
      throw new IllegalArgumentException();
    }
  }

  private boolean validind(int index) {
    return index < size && index >= 0;
  }

  @Override
  public T get(int index) {
    if (validind(index)) {
      SinglyLinkedNode<T> current = start;
      for (
          int position = 0;
          position < index;
          position++, current = current.getNextNode()
      ) {
        ;
      }
      return current.getData();
    } else {

      throw new IllegalArgumentException();
    }
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean isEmpty() {
    return start == null;
  }

  @Override
  public String toString() {
    StringBuilder result =
        new StringBuilder()
            .append("Singly Linked List (")
            .append(size)
            .append("): ");

    for (SinglyLinkedNode<T> current = start; current != null; current = current.getNextNode()) {
      result.append(current.getData().toString()).append(", ");
    }

    return result.toString();
  }

}
