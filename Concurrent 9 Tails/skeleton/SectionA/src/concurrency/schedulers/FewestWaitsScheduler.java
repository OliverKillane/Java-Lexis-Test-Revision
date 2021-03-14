package concurrency.schedulers;

import concurrency.ConcurrentProgram;
import concurrency.DeadlockException;
import concurrency.statements.WaitStmt;
import java.util.Collections;
import java.util.Set;

public class FewestWaitsScheduler implements Scheduler{

  @Override
  public int chooseThread(ConcurrentProgram program) throws DeadlockException {
    return
        program
            .getEnabledThreadIds()
            .stream()
            .min((id1,id2) -> getWaits(id1,program) <= getWaits(id2, program) ? -1 : 1)
            .orElseThrow(DeadlockException::new);
  }

  private int getWaits(int thread, ConcurrentProgram program){
    return (int)
        program
            .remainingStatements(thread)
            .stream()
            .filter(stmt -> stmt instanceof WaitStmt)
            .count();
  }
}
