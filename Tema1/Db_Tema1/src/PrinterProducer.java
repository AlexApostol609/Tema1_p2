import java.util.List;
import java.util.Random;
import java.util.Set;

public class PrinterProducer implements Runnable {

    private List<String> buffer;

    private String s;

    private final Object lock;

    private int maxLimit;


    public PrinterProducer(List<String> buffer, String s , Object lock , int maxLimit) {
        this.buffer = buffer;
        this.lock = lock;
        this.s = s;
        this.maxLimit=maxLimit;
    }

    public void execute(List<String> buffer, String s) throws InterruptedException {

        while (maxLimit > 0){
            synchronized (lock) {
                if (buffer.size() >= 5) {
                    System.out.println("The buffer is already full");
                    lock.wait();
                } else {
                    String documentName = s + "-" + maxLimit;
                    buffer.add(documentName);
                    System.out.println("Added: " + documentName + " to buffer");
                    maxLimit--;
                    lock.notifyAll();
                }
            }
        }
    }

    @Override
    public void run() {
        try {
            execute(buffer, s);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}