# To reproduce

## Attempt 5 - JDK21
Contains some optimizations
```sh
$ git clone git@github.com:apache/ignite.git
$ cd ignite
$ sdk install java 11.0.20.1-librca
$ sdk use java 11.0.20.1-librca
$ mvn -N wrapper:wrapper -Dmaven=3.8.3
$ ./mvnw clean install -DskipTests
$ cd -
$ sdk install java 21-graal
$ sdk use java 21-graal
$ ./gradlew clean bootRun
...
$ time build/native/nativeCompile/graalvm-ignite
...
2023-10-08T11:22:47.269-07:00  INFO 95681 --- [           main] com.example.demo.GraalVmApplication      : Started GraalVmApplication in 0.083 seconds (process running for 0.099)
...
>>> +-------------------------------------------------------------------------------------------+
>>> Ignite ver. 2.16.0-SNAPSHOT#20230615-sha1:fc51f0e43275953ab6a77c7f4d10ba32d1a640b6 stopped OK
>>> +-------------------------------------------------------------------------------------------+
>>> Ignite instance name: graalvm
>>> Grid uptime: 00:00:00.021



real	0m0.256s
user	0m0.095s
sys	0m0.060s
```

## Attempt 4 - JDK21
```sh
$ git clone git@github.com:apache/ignite.git
$ cd ignite
$ sdk install java 11.0.20.1-librca
$ sdk use java 11.0.20.1-librca
$ mvn -N wrapper:wrapper -Dmaven=3.8.3
$ ./mvnw clean install -DskipTests
$ cd -
$ sdk install java 21-graal
$ sdk use java 21-graal
$ ./gradlew clean bootRun
...
$ time build/native/nativeCompile/graalvm-ignite
...
...
2023-10-06T14:33:39.983-07:00  INFO 92649 --- [ionShutdownHook] o.a.i.internal.IgniteKernal%graalvm      :

>>> +-------------------------------------------------------------------------------------------+
>>> Ignite ver. 2.16.0-SNAPSHOT#20231006-sha1:ad0b96b248b3ee56e240bdb2b614c145b5657c32 stopped OK
>>> +-------------------------------------------------------------------------------------------+
>>> Ignite instance name: graalvm
>>> Grid uptime: 00:00:00.023

real	0m1.959s
user	0m0.103s
sys	0m0.174s
```

## Attempt 3 - JDK17
```sh
$ sdk install java 17.0.8-graal
$ sdk use java 17.0.8-graal
$ ./gradlew clean bootRun
99
<===========--> 90% EXECUTING [7s]
> :bootRun
^C$ ./gradlew nativeCompile
$ build/native/nativeCompile/graalvm-ignite
Fatal error: unhandled exception in isolate 0x107800000: com.oracle.svm.core.jdk.UnsupportedFeatureError: Code that was considered unreachable by closed-world analysis was reached.
    at com.oracle.svm.core.util.VMError.unsupportedFeature(VMError.java:92)
    at com.oracle.svm.core.snippets.SnippetRuntime.unsupportedFeature(SnippetRuntime.java:173)
    at java.util.concurrent.locks.AbstractQueuedSynchronizer.release(AbstractQueuedSynchronizer.java:1007)
    at java.util.concurrent.locks.ReentrantLock.unlock(ReentrantLock.java:494)
    at com.oracle.svm.core.jdk.NativeLibrarySupport.addLibrary(NativeLibrarySupport.java:215)
    at com.oracle.svm.core.jdk.NativeLibrarySupport.loadLibrary0(NativeLibrarySupport.java:160)
    at com.oracle.svm.core.jdk.NativeLibrarySupport.loadLibraryRelative(NativeLibrarySupport.java:105)
    at java.lang.ClassLoader.loadLibrary(ClassLoader.java:50)
    at java.lang.Runtime.loadLibrary0(Runtime.java:818)
    at java.lang.System.loadLibrary(System.java:1989)
    at com.oracle.svm.core.jdk.JNIPlatformNativeLibrarySupport.loadJavaLibrary(JNIPlatformNativeLibrarySupport.java:44)
    at com.oracle.svm.core.posix.PosixNativeLibrarySupport.loadJavaLibrary(PosixNativeLibraryFeature.java:117)
    at com.oracle.svm.core.posix.PosixNativeLibrarySupport.initializeBuiltinLibraries(PosixNativeLibraryFeature.java:98)
    at com.oracle.svm.core.graal.snippets.CEntryPointSnippets.initializeIsolate(CEntryPointSnippets.java:346)
    at com.oracle.svm.core.JavaMainWrapper$EnterCreateIsolateWithCArgumentsPrologue.enter(JavaMainWrapper.java:387)
```

## Attempt 2
```sh
$ ./gradlew nativeCompile
...
BUILD SUCCESSFUL in 2m 27s
9 actionable tasks: 1 executed, 8 up-to-date
$ build/native/nativeCompile/graalvm-ignite

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.1.3)

2023-09-21T21:41:30.332-07:00  INFO 15543 --- [           main] com.example.demo.GraalVmApplication      : Starting AOT-processed GraalVmApplication using Java 21 with PID 15543 (/Users/sfeldstein/workspace/graalvm-ignite/build/native/nativeCompile/graalvm-ignite started by sfeldstein in /Users/sfeldstein/workspace/graalvm-ignite)
2023-09-21T21:41:30.332-07:00  INFO 15543 --- [           main] com.example.demo.GraalVmApplication      : No active profile set, falling back to 1 default profile: "default"
2023-09-21T21:41:30.338-07:00  INFO 15543 --- [           main] com.example.demo.GraalVmApplication      : Started GraalVmApplication in 0.019 seconds (process running for 0.034)
[21:41:30] (wrn) Default Spring XML file not found (is IGNITE_HOME set?): config/default-config.xml

2023-09-21T21:41:30.343-07:00  WARN 15543 --- [           main]                                          : Failed to resolve default logging config file: config/java.util.logging.properties
Console logging handler is not configured.
2023-09-21T21:41:30.343-07:00  WARN 15543 --- [           main] o.apache.ignite.internal.util.typedef.G  : Ignite work directory is not provided, automatically resolved to: /Users/sfeldstein/workspace/graalvm-ignite/ignite/work
java.lang.RuntimeException: jdk.internal.misc.JavaNioAccess class is unavailable.
Please add the following parameters to JVM startup settings and restart the application: {parameters: --add-exports=java.base/jdk.internal.misc=ALL-UNNAMED
--add-exports=java.base/sun.nio.ch=ALL-UNNAMED
--add-exports=java.management/com.sun.jmx.mbeanserver=ALL-UNNAMED
--add-exports=jdk.internal.jvmstat/sun.jvmstat.monitor=ALL-UNNAMED
--add-exports=java.base/sun.reflect.generics.reflectiveObjects=ALL-UNNAMED
--add-opens=jdk.management/com.sun.management.internal=ALL-UNNAMED
--illegal-access=permit
}
See https://apacheignite.readme.io/docs/getting-started#section-running-ignite-with-java-9-10-11 for more information.
	at org.apache.ignite.internal.util.GridUnsafe.javaNioAccessObject(GridUnsafe.java:1627)
	at org.apache.ignite.internal.util.GridUnsafe.<clinit>(GridUnsafe.java:175)
	at org.apache.ignite.internal.util.GridSpinReadWriteLock.<clinit>(GridSpinReadWriteLock.java:40)
	at org.apache.ignite.spi.communication.tcp.internal.ConnectGateway.<init>(ConnectGateway.java:34)
	at org.apache.ignite.spi.communication.tcp.TcpCommunicationSpi.<init>(TcpCommunicationSpi.java:339)
	at org.apache.ignite.internal.IgnitionEx$IgniteNamedInstance.initializeDefaultSpi(IgnitionEx.java:2035)
	at org.apache.ignite.internal.IgnitionEx$IgniteNamedInstance.initializeConfiguration(IgnitionEx.java:1941)
	at org.apache.ignite.internal.IgnitionEx$IgniteNamedInstance.start(IgnitionEx.java:1641)
	at org.apache.ignite.internal.IgnitionEx.start0(IgnitionEx.java:1089)
	at org.apache.ignite.internal.IgnitionEx.start(IgnitionEx.java:533)
	at org.apache.ignite.internal.IgnitionEx.start(IgnitionEx.java:509)
	at org.apache.ignite.Ignition.start(Ignition.java:282)
	at com.example.demo.GraalVmApplication.main(GraalVmApplication.java:18)
	at java.base@21/java.lang.invoke.LambdaForm$DMH/sa346b79c.invokeStaticInit(LambdaForm$DMH)
Caused by: java.lang.ClassNotFoundException: jdk.internal.misc.SharedSecrets
	at org.graalvm.nativeimage.builder/com.oracle.svm.core.hub.ClassForNameSupport.forName(ClassForNameSupport.java:122)
	at org.graalvm.nativeimage.builder/com.oracle.svm.core.hub.ClassForNameSupport.forName(ClassForNameSupport.java:86)
	at java.base@21/java.lang.Class.forName(DynamicHub.java:1346)
	at java.base@21/java.lang.Class.forName(DynamicHub.java:1309)
	at java.base@21/java.lang.Class.forName(DynamicHub.java:1302)
	at org.apache.ignite.internal.util.GridUnsafe.javaNioAccessObject(GridUnsafe.java:1620)
	... 13 more
Exception in thread "main" java.lang.ExceptionInInitializerError
	at org.apache.ignite.internal.util.GridSpinReadWriteLock.<clinit>(GridSpinReadWriteLock.java:40)
	at org.apache.ignite.spi.communication.tcp.internal.ConnectGateway.<init>(ConnectGateway.java:34)
	at org.apache.ignite.spi.communication.tcp.TcpCommunicationSpi.<init>(TcpCommunicationSpi.java:339)
	at org.apache.ignite.internal.IgnitionEx$IgniteNamedInstance.initializeDefaultSpi(IgnitionEx.java:2035)
	at org.apache.ignite.internal.IgnitionEx$IgniteNamedInstance.initializeConfiguration(IgnitionEx.java:1941)
	at org.apache.ignite.internal.IgnitionEx$IgniteNamedInstance.start(IgnitionEx.java:1641)
	at org.apache.ignite.internal.IgnitionEx.start0(IgnitionEx.java:1089)
	at org.apache.ignite.internal.IgnitionEx.start(IgnitionEx.java:533)
	at org.apache.ignite.internal.IgnitionEx.start(IgnitionEx.java:509)
	at org.apache.ignite.Ignition.start(Ignition.java:282)
	at com.example.demo.GraalVmApplication.main(GraalVmApplication.java:18)
	at java.base@21/java.lang.invoke.LambdaForm$DMH/sa346b79c.invokeStaticInit(LambdaForm$DMH)
Caused by: java.lang.RuntimeException: Unable to set up byte buffer creation using reflections :java.nio.DirectByteBuffer.<init>(long, int)
	at org.apache.ignite.internal.util.GridUnsafe.createNewDirectBufferCtor(GridUnsafe.java:1706)
	at org.apache.ignite.internal.util.GridUnsafe.createAndTestNewDirectBufferCtor(GridUnsafe.java:1671)
	at org.apache.ignite.internal.util.GridUnsafe.<clinit>(GridUnsafe.java:171)
	... 12 more
	Suppressed: java.lang.RuntimeException: jdk.internal.misc.JavaNioAccess class is unavailable.
Please add the following parameters to JVM startup settings and restart the application: {parameters: --add-exports=java.base/jdk.internal.misc=ALL-UNNAMED
--add-exports=java.base/sun.nio.ch=ALL-UNNAMED
--add-exports=java.management/com.sun.jmx.mbeanserver=ALL-UNNAMED
--add-exports=jdk.internal.jvmstat/sun.jvmstat.monitor=ALL-UNNAMED
--add-exports=java.base/sun.reflect.generics.reflectiveObjects=ALL-UNNAMED
--add-opens=jdk.management/com.sun.management.internal=ALL-UNNAMED
--illegal-access=permit
}
See https://apacheignite.readme.io/docs/getting-started#section-running-ignite-with-java-9-10-11 for more information.
		at org.apache.ignite.internal.util.GridUnsafe.javaNioAccessObject(GridUnsafe.java:1627)
		at org.apache.ignite.internal.util.GridUnsafe.<clinit>(GridUnsafe.java:175)
		... 12 more
	Caused by: java.lang.ClassNotFoundException: jdk.internal.misc.SharedSecrets
		at org.graalvm.nativeimage.builder/com.oracle.svm.core.hub.ClassForNameSupport.forName(ClassForNameSupport.java:122)
		at org.graalvm.nativeimage.builder/com.oracle.svm.core.hub.ClassForNameSupport.forName(ClassForNameSupport.java:86)
		at java.base@21/java.lang.Class.forName(DynamicHub.java:1346)
		at java.base@21/java.lang.Class.forName(DynamicHub.java:1309)
		at java.base@21/java.lang.Class.forName(DynamicHub.java:1302)
		at org.apache.ignite.internal.util.GridUnsafe.javaNioAccessObject(GridUnsafe.java:1620)
		... 13 more
Caused by: java.lang.NoSuchMethodException: java.nio.DirectByteBuffer.<init>(long, int)
	at java.base@21/java.lang.Class.checkMethod(DynamicHub.java:1065)
	at java.base@21/java.lang.Class.getConstructor0(DynamicHub.java:1228)
	at java.base@21/java.lang.Class.getDeclaredConstructor(DynamicHub.java:2930)
	at org.apache.ignite.internal.util.GridUnsafe.createNewDirectBufferCtor(GridUnsafe.java:1699)
	... 14 more
```

## Attempt 1

```sh
$ ./gradlew bootBuildImage
...
BUILD SUCCESSFUL in 12s
9 actionable tasks: 1 executed, 8 up-to-date
$ docker run --rm docker.io/library/graalvm-ignite:0.0.1-SNAPSHOT
...

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.1.3)

2023-09-20T18:50:02.838Z  INFO 1 --- [           main] com.example.demo.GraalVmApplication      : Starting AOT-processed GraalVmApplication using Java 17.0.7 with PID 1 (/workspace/com.example.demo.GraalVmApplication started by cnb in /workspace)
2023-09-20T18:50:02.838Z  INFO 1 --- [           main] com.example.demo.GraalVmApplication      : No active profile set, falling back to 1 default profile: "default"
2023-09-20T18:50:02.844Z  INFO 1 --- [           main] com.example.demo.GraalVmApplication      : Started GraalVmApplication in 0.016 seconds (process running for 0.02)
[18:50:02] (wrn) Default Spring XML file not found (is IGNITE_HOME set?): config/default-config.xml

Console logging handler is not configured.
2023-09-20T18:50:02.847Z  WARN 1 --- [           main]                                          : Failed to resolve default logging config file: config/java.util.logging.properties
2023-09-20T18:50:02.847Z  WARN 1 --- [           main] o.apache.ignite.internal.util.typedef.G  : Ignite work directory is not provided, automatically resolved to: /workspace/ignite/work
java.lang.RuntimeException: jdk.internal.misc.JavaNioAccess class is unavailable.
Please add the following parameters to JVM startup settings and restart the application: {parameters: --add-exports=java.base/jdk.internal.misc=ALL-UNNAMED
--add-exports=java.base/sun.nio.ch=ALL-UNNAMED
--add-exports=java.management/com.sun.jmx.mbeanserver=ALL-UNNAMED
--add-exports=jdk.internal.jvmstat/sun.jvmstat.monitor=ALL-UNNAMED
--add-exports=java.base/sun.reflect.generics.reflectiveObjects=ALL-UNNAMED
--add-opens=jdk.management/com.sun.management.internal=ALL-UNNAMED
--illegal-access=permit
}
See https://apacheignite.readme.io/docs/getting-started#section-running-ignite-with-java-9-10-11 for more information.
	at org.apache.ignite.internal.util.GridUnsafe.javaNioAccessObject(GridUnsafe.java:1627)
	at org.apache.ignite.internal.util.GridUnsafe.<clinit>(GridUnsafe.java:175)
	at org.apache.ignite.internal.util.GridSpinReadWriteLock.<clinit>(GridSpinReadWriteLock.java:40)
	at org.apache.ignite.spi.communication.tcp.internal.ConnectGateway.<init>(ConnectGateway.java:34)
	at org.apache.ignite.spi.communication.tcp.TcpCommunicationSpi.<init>(TcpCommunicationSpi.java:339)
	at org.apache.ignite.internal.IgnitionEx$IgniteNamedInstance.initializeDefaultSpi(IgnitionEx.java:2035)
	at org.apache.ignite.internal.IgnitionEx$IgniteNamedInstance.initializeConfiguration(IgnitionEx.java:1941)
	at org.apache.ignite.internal.IgnitionEx$IgniteNamedInstance.start(IgnitionEx.java:1641)
	at org.apache.ignite.internal.IgnitionEx.start0(IgnitionEx.java:1089)
	at org.apache.ignite.internal.IgnitionEx.start(IgnitionEx.java:533)
	at org.apache.ignite.internal.IgnitionEx.start(IgnitionEx.java:509)
	at org.apache.ignite.Ignition.start(Ignition.java:282)
	at com.example.demo.GraalVmApplication.main(GraalVmApplication.java:16)
Caused by: java.lang.ClassNotFoundException: jdk.internal.misc.SharedSecrets
	at com.oracle.svm.core.hub.ClassForNameSupport.forName(ClassForNameSupport.java:123)
	at com.oracle.svm.core.hub.ClassForNameSupport.forName(ClassForNameSupport.java:87)
	at java.base@17.0.7/java.lang.Class.forName(DynamicHub.java:1324)
	at java.base@17.0.7/java.lang.Class.forName(DynamicHub.java:1287)
	at java.base@17.0.7/java.lang.Class.forName(DynamicHub.java:1280)
	at org.apache.ignite.internal.util.GridUnsafe.javaNioAccessObject(GridUnsafe.java:1620)
	... 12 more
Exception in thread "main" java.lang.ExceptionInInitializerError
	at org.apache.ignite.internal.util.GridSpinReadWriteLock.<clinit>(GridSpinReadWriteLock.java:40)
	at org.apache.ignite.spi.communication.tcp.internal.ConnectGateway.<init>(ConnectGateway.java:34)
	at org.apache.ignite.spi.communication.tcp.TcpCommunicationSpi.<init>(TcpCommunicationSpi.java:339)
	at org.apache.ignite.internal.IgnitionEx$IgniteNamedInstance.initializeDefaultSpi(IgnitionEx.java:2035)
	at org.apache.ignite.internal.IgnitionEx$IgniteNamedInstance.initializeConfiguration(IgnitionEx.java:1941)
	at org.apache.ignite.internal.IgnitionEx$IgniteNamedInstance.start(IgnitionEx.java:1641)
	at org.apache.ignite.internal.IgnitionEx.start0(IgnitionEx.java:1089)
	at org.apache.ignite.internal.IgnitionEx.start(IgnitionEx.java:533)
	at org.apache.ignite.internal.IgnitionEx.start(IgnitionEx.java:509)
	at org.apache.ignite.Ignition.start(Ignition.java:282)
	at com.example.demo.GraalVmApplication.main(GraalVmApplication.java:16)
Caused by: java.lang.RuntimeException: Unable to set up byte buffer creation using reflections :java.nio.DirectByteBuffer.<init>(long, int)
	at org.apache.ignite.internal.util.GridUnsafe.createNewDirectBufferCtor(GridUnsafe.java:1706)
	at org.apache.ignite.internal.util.GridUnsafe.createAndTestNewDirectBufferCtor(GridUnsafe.java:1671)
	at org.apache.ignite.internal.util.GridUnsafe.<clinit>(GridUnsafe.java:171)
	... 11 more
	Suppressed: java.lang.RuntimeException: jdk.internal.misc.JavaNioAccess class is unavailable.
Please add the following parameters to JVM startup settings and restart the application: {parameters: --add-exports=java.base/jdk.internal.misc=ALL-UNNAMED
--add-exports=java.base/sun.nio.ch=ALL-UNNAMED
--add-exports=java.management/com.sun.jmx.mbeanserver=ALL-UNNAMED
--add-exports=jdk.internal.jvmstat/sun.jvmstat.monitor=ALL-UNNAMED
--add-exports=java.base/sun.reflect.generics.reflectiveObjects=ALL-UNNAMED
--add-opens=jdk.management/com.sun.management.internal=ALL-UNNAMED
--illegal-access=permit
}
See https://apacheignite.readme.io/docs/getting-started#section-running-ignite-with-java-9-10-11 for more information.
		at org.apache.ignite.internal.util.GridUnsafe.javaNioAccessObject(GridUnsafe.java:1627)
		at org.apache.ignite.internal.util.GridUnsafe.<clinit>(GridUnsafe.java:175)
		... 11 more
	Caused by: java.lang.ClassNotFoundException: jdk.internal.misc.SharedSecrets
		at com.oracle.svm.core.hub.ClassForNameSupport.forName(ClassForNameSupport.java:123)
		at com.oracle.svm.core.hub.ClassForNameSupport.forName(ClassForNameSupport.java:87)
		at java.base@17.0.7/java.lang.Class.forName(DynamicHub.java:1324)
		at java.base@17.0.7/java.lang.Class.forName(DynamicHub.java:1287)
		at java.base@17.0.7/java.lang.Class.forName(DynamicHub.java:1280)
		at org.apache.ignite.internal.util.GridUnsafe.javaNioAccessObject(GridUnsafe.java:1620)
		... 12 more
Caused by: java.lang.NoSuchMethodException: java.nio.DirectByteBuffer.<init>(long, int)
	at java.base@17.0.7/java.lang.Class.checkMethod(DynamicHub.java:1040)
	at java.base@17.0.7/java.lang.Class.getConstructor0(DynamicHub.java:1206)
	at java.base@17.0.7/java.lang.Class.getDeclaredConstructor(DynamicHub.java:2754)
	at org.apache.ignite.internal.util.GridUnsafe.createNewDirectBufferCtor(GridUnsafe.java:1699)
	... 13 more
...
...
```
