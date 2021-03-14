package concurrency.schedulers;

import concurrency.ConcurrentProgram;
import concurrency.DeadlockException;
import java.util.Collections;
import java.util.Set;

public class RoundRobinScheduler implements Scheduler{
  private int currentId = -1;

  @Override
  public int chooseThread(ConcurrentProgram program) throws DeadlockException {
    Set<Integer> enabledThreads = program.getEnabledThreadIds();

    if (enabledThreads.isEmpty()){
      throw new DeadlockException();
    } else {
      currentId = enabledThreads.stream().sorted().filter(id -> id > currentId).findFirst().orElse(
          Collections.min(enabledThreads));
      return currentId;
    }
  }
}
