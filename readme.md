# Profiling

In this practice session we will be looking at memory leaks, garbage collection logs and learn how to make applications faster.
The main tool we'll be using is VisualVM.
Install it now:
* download and unpack from https://visualvm.github.io/
* open etc/visualvm.conf and change the line `#visualvm_jdkhome="/path/to/jdk"` - remove the leading `#` and set the path to where the JDK is installed
* try to run bin/visualvm (.exe in windows)

We'll use the chess application in this repository for our experiments.
The application checks if a chess piece can be moved to a given location on the game board.
To do that, it will try all possible moves using the [breadth-first search](https://en.wikipedia.org/wiki/Breadth-first_search) algorithm.

## CPU usage sampling

The application as-is runs really slow.
It would be good to know which parts are slow so we can focus on these.
One option would be to read the code and guess which parts are slow.
You can try it, and your guess will likely be wrong (even if you have years of experience).
Another option is to add `System.currentTimeMillis()` calls all over the place, but that takes forever and you still might miss something.

Luckily VisualVM has got your back.
Start it up.
Next, start the sample chess application.
Some new entries should appear in VisualVM's application explorer.
There should be an entry for `chess.App`.
Double click on it to attach VisualVM.

To measure the time spent in different methods, select the "Sampler" tab.
Next, click the sample CPU button.
VisualVM is now recording the activity of each of the threads in your app.
Press enter in the chess application so it will start calculating its things (output will show "running board size ..."").

Let the application run for at least 15sec.
Different thread names should appear under the CPU samples tab, including the main thread.
Unfold the main thread and it should reveal methods that the main thread has run.
Keep unfolding the tree and see how much time is spent in each method.

Find the method where most of the time is spent.
Try to figure out where that method is called from.
Open the code in your IDE and see how the methods work.
Try to change the application so it will run faster.
Run the unit tests to make sure that the code is still correct.
Sample the application again to make sure it now works faster.

*What's the difference between sampling and profiling?
Profiling will measure each and every method call.
Sampling measures only some methods calls using periodic polling.
Profiling is more precise but much slower.*

## Monitoring memory usage

Commit your optimized code and restore the original version.
There are other bugs there that are easier to hunt if the code is slow.

Restart your application and attach VisualVM to it.
Open the "Monitor" tab.
You should see the memory usage graph on the top-right corner.
Try to figure out what it's showing.
What is it supposed to look like?
Why does the memory usage keep increasing?
What is using up all your memory?

The **Garbage Collector (GC)** is doing its thing and VisualVM can't help you much with understanding what's going on.
Fortunately the **Java Virtual Machine (JVM)** itself provides some additional logging.
Let's turn it on.

Open the run configuration of the sample app from IntelliJ and add the following to **VM options**: `-Xlog:gc`.
VM options are command line arguments to the JVM itself.
If you would start the application from command line, then it would look like `java -Xlog:gc chess.App`.

You can read more about the available options here:
https://docs.oracle.com/javase/9/tools/java.htm

Restart the application and you should see the following lines appearing:
```
[40.258s][info][gc] GC(6) Pause Young (G1 Evacuation Pause) 162M->109M(374M) 34.441ms
```
This means that the application was stopped for 34.4ms to free up some memory.
162MB of memory was in use initially and 109MB was in use after the garbage collection.
In total, the java process has reserved 374MB of memory.

Different lines may appear when the memory starts running really low:
```
[243.701s][info][gc] GC(69) Pause Initial Mark (G1 Evacuation Pause) 447M->433M(456M) 21.436ms
[243.701s][info][gc] GC(70) Concurrent Cycle
[244.445s][info][gc] GC(70) Pause Remark 440M->440M(456M) 11.372ms
[244.459s][info][gc] GC(70) Pause Cleanup 440M->440M(456M) 8.953ms
[244.463s][info][gc] GC(70) Concurrent Cycle 762.227ms
```
It is visible that the amount of used memory is close to the total reserved memory.
Running the regular garbage collection didn't free up enough memory so the JVM decided to use slower, but more effective algorithms.

https://plumbr.io/java-garbage-collection-handbook is a really good handbook on how the GC works and how to decipher its logs.

Next, let's set setting some limits to our application's memory usage.
Add the following VM option to the chess app: `-Xmx80M` (keep the GC logging).
This allows the application to use at most 80MB of memory.
Restart the application and check the GC logs.
How long does it take for the application to explode?

Notice how the garbage collection keeps running more often as the memory fills up.
Running low on memory is one common cause for performance issues in java applications.
All the time spent on collecting garbage is time not spent on running the application.
The fix is to change the application to use less memory or have the application reserve more memory by specifying `-Xmx`.

## Heap dumps and hunting memory leaks

For some reason, the application is using increasingly more memory as it runs.
This hints that there may be a memory leak - unused object keep piling up somewhere and are not properly garbage collected.
There are some common cases that can cause a memory leak:
* some object with a long lifetime is keeping references to too many objects
* some static field is keeping references to too many objects

Remove the `-Xmx` option from the VM options and restart the application.
Have it run for 30sec and then click "Heap Dump" from VisualVM monitoring tab (right above the GC graph).
Taking a heap dump will make a snapshot of the entire memory of your application.
This includes all the objects that have been created and haven't been garbage collected yet.

Clicking the button should pop open the heap dump view.
Open the "Objects" view from the drop-down menu.
The view shows how many instances have been created of each class and the amount of memory used.
Find the class that uses the most memory and unfold it.
Use the references in the tree view to figure out which field in which class is storing the objects.

Can you change the application so that is uses less memory (memory usage no longer climbs indefinitely)?
