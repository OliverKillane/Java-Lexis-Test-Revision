package Lists;

public class SinglyLinkedNode<T> {
  private final T data;
  private SinglyLinkedNode<T> nextNode;

  public SinglyLinkedNode(T data){
    this.data = data;
  }

  public SinglyLinkedNode(T data, SinglyLinkedNode<T> nextNode){
    this(data);
    setNextNode(nextNode);
  }

  public void setNextNode(SinglyLinkedNode<T> nextNode) {
    this.nextNode = nextNode;
  }

  public SinglyLinkedNode<T> getNextNode() {
    return nextNode;
  }

  public T getData() {
    return data;
  }
}
