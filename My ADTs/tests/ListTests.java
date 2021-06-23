import java.util.Arrays;
import java.util.stream.IntStream;
import listImplementations.Hohsinglylinkedlist;
import listImplementations.List;
import listImplementations.SinglyLinkedList;
import listImplementations.hohFineList;
import org.junit.Assert;
import org.junit.Test;

public class ListTests {

  @Test
  public void testAdd(){
    List<Integer> testList = new SinglyLinkedList<>();

    testList.add(0);
    testList.add(1);
    testList.add(2);
    testList.add(17);

    Assert.assertEquals(0, (int) testList.get(0));
    Assert.assertEquals(1, (int) testList.get(1));
    Assert.assertEquals(2, (int) testList.get(2));
    Assert.assertEquals(17, (int) testList.get(3));

    testList.remove(1);

    Assert.assertEquals(0, (int) testList.get(0));
    Assert.assertEquals(2, (int) testList.get(1));
    Assert.assertEquals(17, (int) testList.get(2));

    Assert.assertEquals(testList.size(), 3);

    System.out.println(testList.toString());

    testList.insert(48, 3);


    System.out.println(testList.toString());

  }

  @Test
  public void anothertest(){
    List<Character> testList = new Hohsinglylinkedlist<>();
    testList.add('a');
    System.out.println(testList.toString());
    testList.insert('b', 1);
    System.out.println(testList.toString());
  }

  @Test
  public void stressTest(){
    final int THREADS = 2;

    List<Character> testlist = new hohFineList<>();


    Thread[] threads = new Thread[THREADS];


    Arrays.setAll(threads, (a) ->  new Thread(() -> {
      Character data = (char) (a + 65);

      for (int i = 0; i < 9; i++){
        testlist.add(data);
      }
    }));

    Arrays.stream(threads).forEach(Thread::start);
    Arrays.stream(threads).forEach((a) -> {
      try {
        a.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });

    System.out.println(testlist.toString());
    Assert.assertTrue(testlist.size() == THREADS * 9);
  }
}
