package Generic_Utility;

import java.util.concurrent.atomic.AtomicInteger;

public class JavaUtility {

    // Thread-safe counter with a random seed — unique across parallel threads and across JVM runs
    private static final AtomicInteger COUNTER =
            new AtomicInteger(new java.util.Random().nextInt(8900000) + 100000);

    public int getRandomNumber() {
        return COUNTER.getAndIncrement();
    }

}
