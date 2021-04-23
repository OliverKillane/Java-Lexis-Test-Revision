import java.util.Arrays;
import listImplementations.Hohsinglylinkedlist;
import listImplementations.List;
import org.junit.Assert;
import org.junit.Test;
import treeImplementations.BinarySearchTree;
import treeImplementations.BinaryTree;
import treeImplementations.ParallelBinarySearchTree;

public class BSTTests {

  @Test
  public void addtest(){
    BinaryTree<Integer> testTree = new ParallelBinarySearchTree<>();

    testTree.add(3);
    testTree.add(1);

    System.out.println(testTree.toString());

    testTree.remove(1);

    Assert.assertTrue(testTree.contains(3));
    testTree.remove(3);
    Assert.assertTrue(!testTree.contains(3));
    Assert.assertTrue(!testTree.contains(1));
    testTree.add(3);
    Assert.assertTrue(testTree.contains(3));

    System.out.println(testTree.toString());
  }

  @Test
  public void stresstest() {
    final int THREADS = 2;

    BinaryTree<Character> testTree = new ParallelBinarySearchTree<>();

    Thread[] threads = new Thread[THREADS];

    Arrays.setAll(threads, (a) ->  new Thread(() -> {
      Character data = (char) (a + 65);

      for (int i = 0; i < a; i++){
        testTree.add(data);
      }

      for (int i = 0; i < a; i++){
        testTree.remove(data);
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


    // expected to be nothinbg
    System.out.println(testTree.toString());

    //test size (should be zero):

    System.out.println(testTree.size());


    Assert.assertTrue(testTree.isEmpty());
  }
}
