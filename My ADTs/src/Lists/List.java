package Lists;

public interface List<T> {
  void add(T data);
  void insert(T data, int position);
  void remove(int position);
  T get(int index);
  int size();
  boolean isEmpty();
  String toString();
}
