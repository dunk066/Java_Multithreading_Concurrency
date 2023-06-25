package Multithreading.SumCalculator;
/*
We want to calculate the sum of numbers in a large array using multiple threads. 

We can divide the array into smaller chunks and assign each chunk to a separate thread for parallel processing. 
Finally, we can combine the results from each thread to obtain the overall sum.

Here's a step-by-step guide to creating the multithreaded application:

Step 1: Define the Task:

Create a class, let's call it SumCalculator, that implements the Runnable interface.
Inside the SumCalculator class, define instance variables to hold the input array, the starting and ending indices of the chunk, 
and a variable to store the partial sum.

Step 2: Implement the Task:

Implement the run() method of the Runnable interface in the SumCalculator class.
In the run() method, iterate over the chunk of the array assigned to the thread and calculate the partial sum.
Store the partial sum in the instance variable.

Step 3: Create and Start Threads:

In the main method or any other entry point of your application, create an array of SumCalculator objects, with each object representing a separate thread.
Divide the input array into chunks and assign each chunk to a SumCalculator object.
Create and start the threads using the Thread class, passing the SumCalculator objects as arguments to the Thread constructor.
Start all the threads.

Step 4: Wait for Threads to Complete:

After starting the threads, you can use the join() method on each thread to wait for its completion.
This ensures that the main thread waits until all the worker threads finish their calculations.

Step 5: Combine Partial Results:

Once all the threads have completed their calculations, obtain the partial sums from each SumCalculator object.
Combine the partial sums to obtain the overall sum of the array.

Step 6: Analyze Performance:

Measure the execution time of the multithreaded approach and compare it with a single-threaded approach for the same task.
Experiment with different array sizes and the number of threads to observe the impact on performance.
 */

class SumCalculator implements Runnable {
    private final int[] numbers;
    private final int start;
    private final int end;
    private int partialSum;

    public SumCalculator(int[] numbers, int start, int end) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
        this.partialSum = 0;
    }

    @Override
    public void run() {
        for (int i = start; i <= end; i++) {
            partialSum += numbers[i];
        }
    }

    public int getPartialSum() {
        return partialSum;
    }
}

public class MultithreadedSum {
    public static void main(String[] args) throws InterruptedException {
        int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}; // Sample array

        int numThreads = 4; // Number of threads to use
        int chunkSize = numbers.length / numThreads; // Divide the array into chunks

        SumCalculator[] calculators = new SumCalculator[numThreads];
        Thread[] threads = new Thread[numThreads];

        // Create and start threads
        for (int i = 0; i < numThreads; i++) {
            int start = i * chunkSize;
            int end = (i == numThreads - 1) ? numbers.length - 1 : (start + chunkSize - 1);

            calculators[i] = new SumCalculator(numbers, start, end);
            threads[i] = new Thread(calculators[i]);
            threads[i].start();
        }

        // Wait for threads to complete
        for (Thread thread : threads) {
            thread.join();
        }

        // Combine partial results
        int totalSum = 0;
        for (SumCalculator calculator : calculators) {
            totalSum += calculator.getPartialSum();
        }

        System.out.println("Sum of numbers: " + totalSum);
    }
}
