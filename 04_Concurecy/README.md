### Concurrent

Write a program that reads 3 files `producer1.txt`, `producer2.txt` and `producer3.txt` using threads. The programs should use 3 threads to find prime numbers in each file and write them in an output file `prime.txt`;


```java
import java.util.*;
import java.io.*;

public  class Main {
    public static boolean prime(int n){
        if(n<=1) return false;
        int c = 0;
        for(int i =1; i<=n; i++){
            if(n%i == 0) c++;
            if(c >= 3) break;
        }
        return c == 2;
    }
    public static void main(String [] args) throws IOException {
        String basePath = "C://Users//crisp//Desktop//TUT//First Week//Hello there//src//";
        FileWriter writer = new FileWriter(basePath + "prime.txt");
        Thread th1 = new Thread(
                new Producer("producer1.txt", writer)
        );
        Thread th2 = new Thread(
                new Producer("producer2.txt", writer)
        );
        Thread th3 = new Thread(
                new Producer("producer3.txt", writer)
        );
        th1.start();
        th2.start();
        th3.start();

        try{
            th1.join();
            th2.join();
            th3.join();
        }catch(InterruptedException e){
            System.out.println("Something went wrong.");
        }
        writer.close();
    }
}


class Producer implements  Runnable {
    private final String file;
    private final FileWriter writer;
    public Producer(String file, FileWriter writer){
        this.file = file;
        this.writer = writer;
    }
    @Override
    public void run() {
        String basePath = "C://Users//crisp//Desktop//TUT//First Week//Hello there//src//";
        try {
            File file = new File(basePath + this.file);
            if(!file.exists()) throw new FileNotFoundException("The file was not found");
            Scanner reader = new Scanner(file);
            while(reader.hasNextLine()){
                int num = reader.nextInt();
                if(Main.prime(num)){
                    this.writePrimes(num);
                }
            }
            reader.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void writePrimes(int num) throws IOException{
        synchronized (this) {this.writer.write(num + "\n");}
    }
}
```
