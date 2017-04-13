# Profiling

In this practice session we will be looking at memory leaks, garbage collection logs and learn how to make applications faster.
The main tool we'll be using is JVisualVM which is a diagnostic tool bundled with Java.

JVisualVM is usually installed at $JDK_HOME/bin, but some linux distributions do not include it by default (search for package visualvm).

Before we continue, quickly read through the template application.
Don't make any changes (yet)!

## CPU usage sampling

The application as-is runs really slow.
It would be good to know which parts are slow so we can focus on these.
One option would be to read the code and guess which parts are slow.
You can try it, and your guess will likely be wrong (even if you have years of experience).
Another option is to add System.currentTimeMillis() calls all over the place, but that takes forever and you still might miss something.

Luckily JVisualVM has got your back.
Start it up.
Next, start the sample chess application.
Some new entries should appear in JVisualVM's application explorer.
If you started the chess app from IntelliJ, then the right entry is for `com.intellij.rt.execution.application.AppMain`.
Double click on it to hook JVisualVM into it.

To measure the time spent in different methods, select the "Sampler" tab.
Next, choose to sample CPU.
JVisualVM is now recording the activity of each of the threads in your app.
Enter a newline in the chess application so it would start calculating its things.

You can press the "Snapshot" button any time during your application runtime.
This will open a detailed view of the sampling results so far.
Let the application run for at least 15sec and open a snapshot.
There are different buttons at the bottom of the window - the most useful one is usually the combined view.

Browse around in the call trees and identify which method is the most likely one to make the application slow.
Open the code in your IDE and see if you can figure out a way to make it faster.
Try to fix the application and sample it again to make sure you did something useful.
Repeat.

*What's the difference between sampling and profiling?
Profiling will measure each and every method call.
Sampling measures only some methods calls using periodic polling.
Profiling is more precise but much slower.*

## Monitoring memory usage

Restart your application and attach JVisualVM to it.
Open the "Monitor" tab.
You should see the memory usage graph on the top-right corner.
Try to figure out what it's showing.
What is it supposed to look like?
Why does the memory usage keep increasing?
What is using up all your memory?

The garbage collector is doing its thing and JVisualVM can't help you much with understanding what's going on.
Fortunately the JVM itself provides some additional logging.
Let's turn it on.

Open the run configuration of the sample app from IntelliJ and add the following (space separated) **VM options**
 * -XX:+PrintGCDetails
 * -XX:+PrintGCTimeStamps

You can read more about the available options here:
http://www.oracle.com/technetwork/java/javase/tech/vmoptions-jsp-140102.html

Restart the application and you should see the following lines appearing (Full GC will take a while):
```
80.766: [GC (Allocation Failure) [PSYoungGen: 77616K->13600K(99840K)] 213364K->176924K(270848K), 0.0091759 secs] [Times: user=0.03 sys=0.00, real=0.01 secs]`
80.775: [Full GC (Ergonomics) [PSYoungGen: 13600K->0K(99840K)] [ParOldGen: 163324K->170597K(263680K)] 176924K->170597K(363520K), [Metaspace: 3002K->3002K(1056768K)], 0.0910023 secs] [Times: user=0.35 sys=0.01, real=0.09 secs]
```

Try to decipher the GC logs using this tutorial (Parallel GC).
Read the rest of the handbook at home, it's really good!
https://plumbr.eu/handbook/garbage-collection-algorithms-implementations#parallel-gc

Next, let's set setting some limits to our application's memory usage.
Add the following VM option to the chess app: "-Xmx80M".
This allows the application to use at most 80MB of memory.
Restart the application and check the GC logs.
How long does it take for the application to explode?

## Heap dumps and hunting memory leaks

Remove the -Xmx option from the run configuration and restart the application.
Have it run for 30sec and then click "Heap Dump" from JVisualVM monitoring tab (right above the GC graph).
Taking a heap dump will make a snapshot of the entire heap memory of your application.
This includes all the objects that have been created and haven't been garbage collected yet.

Clicking the button should pop open a heap dump view.
Read the summary.
Next, open the "Classes" tab from the above the summary.
The view shows how many instances have been created of each class and their sizes.
Find the class that uses the most memory and double click on it.
Use the views to figure out which field in which class is storing the objects.
Finally, try to fix the memory leak.
Check the GC logs to make sure you fixed it.
