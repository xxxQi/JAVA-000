import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author: Administrator
 * @description:
 */
public class Homework03 {
    public static void main(String[] args) throws Exception {

        long start = System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        //这是得到的返回值
        int result = executCallable();
//        int result = executeFutureTask();
//        int result = executeSemaphore();
//        int result = executeCountDownLatch();
//        int result = executeCyclicBarrier();
//        int result = executeCompletableFuture();
//        int result = executeThreadJoin();

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为：" + result);

        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");

        // 然后退出main线程
    }

    /**
     * 使用线程池 提交callable任务
     */
    public static int executCallable() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        try {
            Future<Integer> submit = executorService.submit(Homework03::sum);
            return submit.get();
        } finally {
            executorService.shutdown();
        }
    }

    /**
     * 通过FutureTask包装要执行的任务，将FutureTask作为任务传给单个线程、线程池
     * 然后调用FutureTask.get()获取执行结果
     */
    public static int executeFutureTask() throws ExecutionException, InterruptedException {
        FutureTask<Integer> futureTask = new FutureTask<>(Homework03::sum);
        Thread thread = new Thread(futureTask);
        thread.start();
        return futureTask.get();
    }

    /**
     * 根据信号量阻塞主线程等待子线程释放
     * @return
     */
    public static int executeSemaphore() throws InterruptedException {
        Semaphore semaphore = new Semaphore(0);
        AtomicReference<Object> result = new AtomicReference<>(new Object());
        new Thread(()->{
            result.set(sum());
            semaphore.release();
        }).start();
        semaphore.acquire();
        return (int) result.get();
    }

    public static int executeCountDownLatch() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        AtomicReference<Object> result = new AtomicReference<>(new Object());
        new Thread(()->{
            result.set(sum());
            countDownLatch.countDown();
        }).start();
        countDownLatch.await();
        return (int) result.get();
    }

    public static int executeCyclicBarrier() throws BrokenBarrierException, InterruptedException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        AtomicReference<Object> result = new AtomicReference<>(new Object());
        new Thread(()->{
            try {
                result.set(sum());
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }).start();
        cyclicBarrier.await();
        return (int) result.get();
    }

    public static int executeCompletableFuture(){
        return  CompletableFuture.supplyAsync(Homework03::sum).join();
    }

    private static int executeThreadJoin() throws InterruptedException {
        AtomicReference<Object> result = new AtomicReference<>(new Object());
        Thread thread = new Thread(() -> {
            result.set(sum());
        });
        thread.start();
        thread.join();
        return (int)result.get();
    }


    private static int sum() {
        return fibo(36);
    }

    private static int fibo(int a) {
        if (a < 2)
            return 1;
        return fibo(a - 1) + fibo(a - 2);
    }
}
