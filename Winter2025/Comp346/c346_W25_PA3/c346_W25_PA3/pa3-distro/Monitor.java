/**
 * Class Monitor
 * To synchronize dining philosophers.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class Monitor {
    /*
     * ------------
     * Data members
     * ------------
     */
    private final int numberOfPhilosophers;
    private final State[] philosopherStates; // States of philosophers: THINKING, HUNGRY, or EATING
    private final boolean[] chopsticks; // Availability of chopsticks
    private boolean isSomeoneTalking; // To track if a philosopher is talking

    private enum State {
        THINKING, HUNGRY, EATING
    }

    /**
     * Constructor
     */
    public Monitor(int piNumberOfPhilosophers) {
        this.numberOfPhilosophers = piNumberOfPhilosophers;
        this.philosopherStates = new State[piNumberOfPhilosophers];
        this.chopsticks = new boolean[piNumberOfPhilosophers];
        this.isSomeoneTalking = false;

        // Initialize all philosophers to THINKING and all chopsticks to available
        for (int i = 0; i < piNumberOfPhilosophers; i++) {
            philosopherStates[i] = State.THINKING;
            chopsticks[i] = true;
        }
    }

    /*
     * -------------------------------
     * User-defined monitor procedures
     * -------------------------------
     */

    /**
     * Grants request (returns) to eat when both chopsticks/forks are available.
     * Else forces the philosopher to wait()
     */
    public synchronized void pickUp(final int piTID) {
        int leftChopstick = piTID;
        int rightChopstick = (piTID + 1) % numberOfPhilosophers;

        philosopherStates[piTID] = State.HUNGRY;

        // Wait until both chopsticks are available
        while (!chopsticks[leftChopstick] || !chopsticks[rightChopstick]) {
            try {
                wait(); 
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("pickUp(): InterruptedException");
            }
        }

        // Pick up both chopsticks
        chopsticks[leftChopstick] = false;
        chopsticks[rightChopstick] = false;
        philosopherStates[piTID] = State.EATING;

        System.out.println("Philosopher " + piTID + " picks up chopsticks " + leftChopstick + " and " + rightChopstick);
    }

    /**
     * When a given philosopher's done eating, they put the chopsticks/forks down
     * and let others know they are available.
     */
    public synchronized void putDown(final int piTID) {
        int leftChopstick = piTID;
        int rightChopstick = (piTID + 1) % numberOfPhilosophers;

        // Put down both chopsticks
        chopsticks[leftChopstick] = true;
        chopsticks[rightChopstick] = true;
        philosopherStates[piTID] = State.THINKING;

        System.out.println("Philosopher " + piTID + " puts down chopsticks " + leftChopstick + " and " + rightChopstick);
        
        // Notify all waiting philosophers
        notifyAll(); 
    }

    /**
     * Only one philosopher at a time is allowed to talk
     * (while they are not eating).
     */
    public synchronized void requestTalk() {
        while (isSomeoneTalking) {
            try {
                wait(); // Wait until no one is talking
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("requestTalk(): InterruptedException");
            }
        }

        isSomeoneTalking = true;
        System.out.println("A philosopher starts talking.");
    }

    /**
     * When one philosopher is done talking, others
     * can feel free to start talking.
     */
    public synchronized void endTalk() {
        isSomeoneTalking = false;
        System.out.println("A philosopher stops talking.");
        notifyAll(); // Notify all waiting philosophers
    }
}

// EOF
