package networking.hw3;


import java.util.concurrent.*;

/**
 * This class returns the result number from the youtube analytics for each month
 */
public class Analytics {

    private static final ExecutorService threadpool = Executors.newFixedThreadPool(3);

    static Future future;

    public long analyze (int index) throws ExecutionException, InterruptedException {
        YoutubeTask task = new YoutubeTask(index);
        future = threadpool.submit(task);
        long viewCount;
        try {
            viewCount = (long) future.get();
        } catch (final InterruptedException ex) {
            return -1;
        } catch (final ExecutionException ex) {
            return -1;
        } catch(CancellationException ce){
            return -1;
        }
        return viewCount;
    }


    private class YoutubeTask implements Callable {

        int index = 0;
        Query query = new Query();

        public YoutubeTask(int index) {
            this.index = index;
        }

        @Override
        public Object call() throws Exception {
            return query.countViews(index);
        }
    }
}