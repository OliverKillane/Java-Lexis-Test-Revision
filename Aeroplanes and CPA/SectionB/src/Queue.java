public class Queue<T> implements QueueInterface<T>{
	
	private Node<T> first;
	private Node<T> last;
	
	public boolean isEmpty() {
		return last == null;
	}
	
	//post: Adds the given item to the queue
	public void enqueue(T item) {
		if (!isEmpty()){
			last.setNext(new Node<>(item));
			last = last.getNext();
		} else {
			last = first = new Node<>(item);
		}
	}
	
	//post: Removes and returns the head of the queue. It throws an 
	//      exception if the queue is empty.
	public T dequeue() throws QueueException {
		if (!isEmpty()){
			T firstData = first.getItem();
			first = first.getNext();
			return firstData;
		} else {
			throw new QueueException("Queue is empty");
		}
	}
}
