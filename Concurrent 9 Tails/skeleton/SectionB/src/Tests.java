import java.util.Iterator;

public class Tests {
  public static void main(String[] args){
    PriorityQueue<Integer> test = new PriorityQueue<>();
    test.add(7);
    test.add(69);
    test.add(4);

    for (Iterator<Object> it = test.iterator(); it.hasNext(); ) {
      int item = (int) it.next();
      System.out.println(item);
    }

    NineTailsWeightedGraph a = new NineTailsWeightedGraph();
    System.out.println(a.indexToConfiguration(512));
  }
}
