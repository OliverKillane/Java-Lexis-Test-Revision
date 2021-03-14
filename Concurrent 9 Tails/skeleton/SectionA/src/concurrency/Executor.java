package concurrency;

import java.util.LinkedList;
import java.util.List;

import concurrency.schedulers.Scheduler;

public class Executor {

	private ConcurrentProgram program;
	private Scheduler scheduler;

	public Executor(ConcurrentProgram program, Scheduler scheduler) {
		this.program = program;
		this.scheduler = scheduler;
	}

	/**
	 * Executes program with respect to scheduler
	 *
	 * @return the final state and history of execution
	 */
	public String execute() {
		List<Integer> history = new LinkedList<>();
		boolean deadlockOccurred = false;

		// TODO: Add code here to complete Question 3

		int currentThread;

		try {
			while (!program.isTerminated()){
				currentThread = scheduler.chooseThread(program);
				history.add(currentThread);
				program.step(currentThread);
			}
		} catch(DeadlockException e){
			deadlockOccurred = true;
		}


		StringBuilder result = new StringBuilder();
		result.append("Final state: " + program + "\n");
		result.append("History: " + history + "\n");
		result.append("Termination status: "
				+ (deadlockOccurred ? "deadlock" : "graceful") + "\n");
		return result.toString();

	}

	// An incorrect attempt at overriding the equals method
	// of Object
	public boolean equals(Executor that) {
		return program.toString().equals(that.program.toString());
	}

}
