# Introduction

The ForkJoin Framework is standard in Java 8 and Java 7. But the `ForkJoinPool` class doesn't have the `commonPool` method.

# Classes

* `ForkJoinPool`: An instance of this class is used to run fork-join tasks.
* `RecursiveTask<V>`: A subclass of this runs in a pool and return a result.
* `RecursiveAction`: Like `RecursiveTask<V>` except it doesn't return a result.
* `ForkJoinTask<V>`: Superclass of `RecursiveTask<V>` and `RecursiveAction`.

# CompletableFuture , Lambda

The `CompletableFuture` by default uses `ForkJoinPool.commonPool()`, thread pool shared between all `CompletableFuture`S,
all parallel streams.

