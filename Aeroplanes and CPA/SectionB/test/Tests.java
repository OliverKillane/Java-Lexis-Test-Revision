import org.junit.Assert;
import org.junit.Test;

public class Tests {
  @Test
  public void checkQueue(){
    Queue<Integer> test = new Queue<>();
    test.enqueue(1);
    test.enqueue(2);
    test.enqueue(3);
    Assert.assertTrue(test.dequeue() == 1);
    Assert.assertTrue(test.dequeue() == 2);
    Assert.assertTrue(test.dequeue() == 3);
  }

  @Test
  public void ProjectTest(){
    Scheduler a = new Scheduler();

  }
}
