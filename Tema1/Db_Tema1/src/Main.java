import java.io.IOException;
import java.util.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

    private static volatile int maxLimit = 90;
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        List<String> buffer = new ArrayList<>();
        Thread[] producerThreads = new Thread[3];

        Object lock = new Object();



        Thread consumer = new Thread(new PrinterConsumer(buffer , lock , maxLimit));


        consumer.start();
        for (int i = 0; i < producerThreads.length; i++) {
            String documentName = "Document-" + i;
            producerThreads[i] = new Thread(new PrinterProducer(buffer, documentName , lock , maxLimit));
            producerThreads[i].start();
        }


        try {
            for (Thread producerThread : producerThreads) {
                producerThread.join();
            }
            consumer.join();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}