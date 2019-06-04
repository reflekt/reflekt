# Example project using RefleKt


## Benchmark
I put together a benchmark to see if i could beat good old org.reflections. It seems org.reglections puts a lot of effort when initializing, anyone has asked it for anything. I opted fot a softer init flow, so i get worse max-times, and somewhat worse avg. But if i add org.reflections init-time to its first run the tables turn drastically.

Init time excluded from first run 
```
BUILD SUCCESSFUL in 10s
4 actionable tasks: 4 up-to-date
Running 3 iterations on same jvm with package limit to "null" [org.reflections, reflekt]!
[reflekt        ] Init=241ms Max=19ms Min=0ms, Avg=6.3ms
[org.reflections] Init=1003ms Max=2ms Min=0ms, Avg=1.0ms
Running 3 iterations on same jvm with package limit to "null" [org.reflections, reflekt]!
[org.reflections] Init=1194ms Max=4ms Min=0ms, Avg=1.3ms
[reflekt        ] Init=138ms Max=14ms Min=0ms, Avg=4.65ms
Running 3 iterations on same jvm with package limit to "com.example.annotations" [org.reflections, reflekt]!
[reflekt        ] Init=112ms Max=2ms Min=0ms, Avg=0.65ms
[org.reflections] Init=138ms Max=4ms Min=0ms, Avg=1.65ms
Running 3 iterations on same jvm with package limit to "com.example.annotations" [org.reflections, reflekt]!
[org.reflections] Init=152ms Max=5ms Min=0ms, Avg=2.0ms
[reflekt        ] Init=67ms Max=1ms Min=0ms, Avg=0.3ms
```

Init time included in first run
```
BUILD SUCCESSFUL in 12s
4 actionable tasks: 4 executed
Running 3 iterations on same jvm with package limit to "null" [org.reflections, reflekt]!
[reflekt        ] Init=250ms Max=269ms Min=0ms, Avg=89.65ms
[org.reflections] Init=1163ms Max=1166ms Min=0ms, Avg=388.65ms
Running 3 iterations on same jvm with package limit to "null" [org.reflections, reflekt]!
[org.reflections] Init=1205ms Max=1210ms Min=0ms, Avg=403.65ms
[reflekt        ] Init=134ms Max=149ms Min=0ms, Avg=49.65ms
Running 3 iterations on same jvm with package limit to "com.example.annotations" [org.reflections, reflekt]!
[reflekt        ] Init=119ms Max=121ms Min=0ms, Avg=40.3ms
[org.reflections] Init=137ms Max=142ms Min=0ms, Avg=47.65ms
Running 3 iterations on same jvm with package limit to "com.example.annotations" [org.reflections, reflekt]!
[org.reflections] Init=153ms Max=158ms Min=0ms, Avg=53.0ms
[reflekt        ] Init=68ms Max=69ms Min=0ms, Avg=23.0ms
```
