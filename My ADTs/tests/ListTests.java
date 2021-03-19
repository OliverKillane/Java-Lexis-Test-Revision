import Lists.List;
import Lists.SinglyLinkedList;
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
}
