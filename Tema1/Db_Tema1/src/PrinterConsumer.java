import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class PrinterConsumer implements Runnable{

    private List<String> buffer ;
    private final Object lock;

    private int maxLimit;

    PrinterConsumer(List<String> buffer , Object lock , int maxLimit) {
        this.buffer = buffer;
        this.lock = lock;
        this.maxLimit = maxLimit;
    }

    public void lucreaza() throws InterruptedException {

        while (maxLimit > 0){
            synchronized (lock) {
                if (buffer.isEmpty()) {
                    System.out.println("Buffer is empty, consumer waiting");
                    lock.wait();
                } else {
                    String name = buffer.get(0);
                    buffer.remove(0);
                    System.out.println("Removing" + " " + name + " " + "from buffer");
                    lock.notifyAll();
                }
            }
        }
    }


    @Override
    public void run() {
        try {
            lucreaza();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
