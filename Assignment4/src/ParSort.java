import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * This code has been fleshed out by Ziyao Qiao. Thanks very much.
 * TODO tidy it up a bit.
 */
class ParSort {

    public static int cutoff = 1000;

    public static void sort(int[] array, int from, int to) {
        if (to - from < cutoff) Arrays.sort(array, from, to);
        else {
            CompletableFuture<int[]> parsort1 = parsort(array, from, from + (to - from) / 2); // TO IMPLEMENT
            CompletableFuture<int[]> parsort2 = parsort(array, from + (to - from) / 2, to); // TO IMPLEMENT
            CompletableFuture<int[]> parsort = parsort1.thenCombine(parsort2, (xs1, xs2) -> {
                        int[] result = new int[xs1.length + xs2.length];
                        // TO BE IMPLEMENTED ...
                        int i = 0; int j =0;
                        for(int k = 0; k < result.length; k++){
                            if(i > xs1.length - 1){
                                result[k] = xs2[j++];
                            } else if (j > xs2.length - 1){
                                result[k] = xs1[i++];
                            } else if (xs2[j] < xs1[i]){
                                result[k] = xs2[j++];
                            } else {
                                result[k] = xs2[i++];
                            }
                        }
                        // ... END IMPLEMENTATION
                        return result;
                    });

            parsort.whenComplete((result, throwable) -> System.arraycopy(result, 0, array, from, result.length));
//            System.out.println("# threads: "+ ForkJoinPool.commonPool().getRunningThreadCount());
            parsort.join();
        }
    }

    private static CompletableFuture<int[]> parsort(int[] array, int from, int to) {
       Executor executor = Executors.newFixedThreadPool(16);
        return CompletableFuture.supplyAsync(
                () -> {
                    int[] result = new int[to - from];
                    // TO BE IMPLEMENTED ...
//                    int j = from;
//                    sort(array, from, to);
//                    for(int i = 0; i< result.length; i++ ){
//                        result[i] = array[j];
//                        j++;
//                    }
                     System.arraycopy(array, from, result, 0, result.length);
                     sort(result, 0, to - from);
                     // ... END IMPLEMENTATION
                    return result;
                }, executor
        );
    }

    private static boolean less(Comparable  i, Comparable j){
        return i.compareTo(j) < 0;
    }
}