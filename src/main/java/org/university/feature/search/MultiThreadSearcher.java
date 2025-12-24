package org.university.feature.search;

import org.university.common.collection.CustomArrayList;
import org.university.common.collection.CustomList;
import org.university.common.model.Student;

import java.util.Objects;
import java.util.concurrent.*;

public class MultiThreadSearcher implements StudentSearcher {

    private static final int DEFAULT_QUANTITY_THREADS = 3;

    public MultiThreadSearcher() {}

    @Override
    public int countOccurrences(CustomList<Student> collection, String findGroupNumber) {

        if (collection == null || collection.isEmpty()) {
            return 0;
        }

        int totalCount = 0;
        int threadCount = Math.min(DEFAULT_QUANTITY_THREADS, collection.size());

        try (ExecutorService executorService = Executors.newFixedThreadPool(threadCount)) {
            CustomList<Searcher> tasks = createTasks(collection, findGroupNumber, threadCount);
            CustomList<Future<Integer>> futures = new CustomArrayList<>();

            for (Searcher task : tasks) {
                futures.add(executorService.submit(task));
            }

            for (Future<Integer> future : futures) {
                totalCount += getResultExecuteThread(future);
            }
        }

        return totalCount;
    }

    private int getResultExecuteThread(Future<Integer> executed)  {
        try {
            return executed.get();
        } catch (InterruptedException e) {
            executed.cancel(true);
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted", e);
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof RuntimeException) {
                throw (RuntimeException) cause;
            } else {
                throw new RuntimeException("Error in task", cause);
            }
        }
    }

    private CustomList<Searcher> createTasks(CustomList<Student> collection, String toFind, int threadCount) {

        CustomList<Searcher> tasks = new CustomArrayList<>();
        int sizeStudents = collection.size();
        int chunkSize = (int) (Math.ceil((double) sizeStudents / threadCount));

        for (int i = 0; i < threadCount ; i++) {
            int start = i * chunkSize;
            int end = Math.min(start + chunkSize, sizeStudents);

            if (start >= sizeStudents) {
                break;
            }

            tasks.add(new Searcher(collection, start, end, toFind));
        }

        return tasks;
    }

    private record Searcher(CustomList<Student> collection, int startPosition,
                            int endPosition, String findGroupNumber) implements Callable<Integer> {

        @Override
        public Integer call() {
            int countOccurrences = 0;
            for (int i = startPosition; i < endPosition; i++) {
                if (Objects.equals(collection.get(i).getGroupNumber(), findGroupNumber)) {
                    countOccurrences++;
                }
            }

            return countOccurrences;
        }
    }
}
