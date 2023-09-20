# To reproduce

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
