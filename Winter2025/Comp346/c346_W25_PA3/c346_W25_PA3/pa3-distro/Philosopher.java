import common.BaseThread;

/**
 * Class Philosopher.
 * Outlines main subroutines of our virtual philosopher.
 *
 * @author Serguei
 */
public class Philosopher extends BaseThread {
    /**
     * Max time an action can take (in milliseconds)
     */
    public static final long TIME_TO_WASTE = 1000;

    /**
     * The act of eating.
     * - Print the fact that a given philosopher (their TID) has started eating.
     * - yield
     * - Then sleep() for a random interval.
     * - yield
     * - Then print that they are done eating.
     */
    public void eat() {
        try {
            System.out.println("Philosopher " + getTID() + " starts eating.");
            Thread.yield();
            sleep((long) (Math.random() * TIME_TO_WASTE));
            Thread.yield();
            System.out.println("Philosopher " + getTID() + " is done eating.");
        } catch (InterruptedException e) {
            System.err.println("Philosopher.eat():");
            DiningPhilosophers.reportException(e);
            System.exit(1);
        }
    }

    /**
     * The act of thinking.
     * - Print the fact that a given philosopher (their TID) has started thinking.
     * - yield
     * - Then sleep() for a random interval.
     * - yield
     * - Then print that they are done thinking.
     */
    public void think() {
        try {
            System.out.println("Philosopher " + getTID() + " starts thinking.");
            Thread.yield();
            sleep((long) (Math.random() * TIME_TO_WASTE));
            Thread.yield();
            System.out.println("Philosopher " + getTID() + " is done thinking.");
        } catch (InterruptedException e) {
            System.err.println("Philosopher.think():");
            DiningPhilosophers.reportException(e);
            System.exit(1);
        }
    }

    /**
     * The act of talking.
     * - Print the fact that a given philosopher (their TID) has started talking.
     * - yield
     * - Say something brilliant at random.
     * - yield
     * - Then print that they are done talking.
     */
    public void talk() {
        try {
            System.out.println("Philosopher " + getTID() + " starts talking.");
            Thread.yield();
            saySomething();
            Thread.yield();
            System.out.println("Philosopher " + getTID() + " is done talking.");
        } catch (Exception e) {
            System.err.println("Philosopher.talk():");
            DiningPhilosophers.reportException(e);
            System.exit(1);
        }
    }

    /**
     * No, this is not the act of running, just the overridden Thread.run()
     */
    public void run() {
        for (int i = 0; i < DiningPhilosophers.DINING_STEPS; i++) {
            // Pick up chopsticks
            DiningPhilosophers.soMonitor.pickUp(getTID());

            // Eat
            eat();

            // Put down chopsticks
            DiningPhilosophers.soMonitor.putDown(getTID());

            // Think
            think();

            // Randomly decide if the philosopher will talk
            if (Math.random() < 0.5) { // 50% chance to talk
                DiningPhilosophers.soMonitor.requestTalk();
                talk();
                DiningPhilosophers.soMonitor.endTalk();
            }

            Thread.yield();
        }
    }

    /**
     * Prints out a phrase from the array of phrases at random.
     * Feel free to add your own phrases.
     */
    public void saySomething() {
        String[] astrPhrases = {
            "Eh, it's not easy to be a philosopher: eat, think, talk, eat...",
            "You know, true is false and false is true if you think of it",
            "2 + 2 = 5 for extremely large values of 2...",
            "If thee cannot speak, thee must be silent",
            "My number is " + getTID() + ""
        };

        System.out.println(
            "Philosopher " + getTID() + " says: " +
            astrPhrases[(int) (Math.random() * astrPhrases.length)]
        );
    }
}
