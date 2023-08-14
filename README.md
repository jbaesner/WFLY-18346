Reproducer for <https://issues.redhat.com/browse/WFLY-18346>

On Windows:
* `gradlew startServer`
* Wait for WildFly to start
* `gradlew deployServer`
* `gradlew run`
* You get the following exception in the client:
  ```
  Aug. 14, 2023 3:18:11 PM org.wildfly.security.Version <clinit>
  INFO: ELY00001: WildFly Elytron version 2.2.1.Final
  Aug. 14, 2023 3:18:11 PM org.wildfly.naming.client.Version <clinit>
  INFO: WildFly Naming version 2.0.1.Final
  Aug. 14, 2023 3:18:11 PM org.xnio.Xnio <clinit>
  INFO: XNIO version 3.8.9.Final
  Aug. 14, 2023 3:18:11 PM org.xnio.nio.NioXnio <clinit>
  INFO: XNIO NIO Implementation Version 3.8.9.Final
  Aug. 14, 2023 3:18:11 PM org.jboss.threads.Version <clinit>
  INFO: JBoss Threads version 2.4.0.Final
  Aug. 14, 2023 3:18:11 PM org.jboss.remoting3.EndpointImpl <clinit>
  INFO: JBoss Remoting version 5.0.27.Final
  MyEjb1: Pong
  MyEjb1: Pong
  MyRecord[title=MyEjb1, message=Pong]
  Exception in thread "main" jakarta.ejb.EJBException: IO channel timed out or closed. Check server endpoint read or write timeout settings
          at org.jboss.ejb.protocol.remote.EJBClientChannel$MethodInvocation.handleClosed(EJBClientChannel.java:1288)
          at org.jboss.remoting3.util.InvocationTracker.connectionClosed(InvocationTracker.java:222)
          at org.jboss.remoting3.util.InvocationTracker.lambda$new$0(InvocationTracker.java:70)
          at org.jboss.remoting3.spi.SpiUtils.safeHandleClose(SpiUtils.java:50)
          at org.jboss.remoting3.spi.AbstractHandleableCloseable$CloseHandlerTask.run(AbstractHandleableCloseable.java:520)
          at org.jboss.remoting3.spi.AbstractHandleableCloseable.runCloseTask(AbstractHandleableCloseable.java:425)
          at org.jboss.remoting3.spi.AbstractHandleableCloseable.closeComplete(AbstractHandleableCloseable.java:286)
          at org.jboss.remoting3.remote.RemoteConnectionChannel.closeAction(RemoteConnectionChannel.java:510)
          at org.jboss.remoting3.spi.AbstractHandleableCloseable.closeAsync(AbstractHandleableCloseable.java:368)
          at org.jboss.remoting3.remote.RemoteConnectionHandler.closeAllChannels(RemoteConnectionHandler.java:623)
          at org.jboss.remoting3.remote.RemoteConnectionHandler.receiveCloseRequest(RemoteConnectionHandler.java:226)
          at org.jboss.remoting3.remote.RemoteConnectionHandler.handleConnectionClose(RemoteConnectionHandler.java:123)
          at org.jboss.remoting3.remote.RemoteReadListener.lambda$null$0(RemoteReadListener.java:67)
          at org.jboss.remoting3.EndpointImpl$TrackingExecutor.lambda$execute$0(EndpointImpl.java:993)
          at org.jboss.threads.ContextClassLoaderSavingRunnable.run(ContextClassLoaderSavingRunnable.java:35)
          at org.jboss.threads.EnhancedQueueExecutor.safeRun(EnhancedQueueExecutor.java:1990)
          at org.jboss.threads.EnhancedQueueExecutor$ThreadBody.doRunTask(EnhancedQueueExecutor.java:1486)
          at org.jboss.threads.EnhancedQueueExecutor$ThreadBody.run(EnhancedQueueExecutor.java:1377)
          at org.xnio.XnioWorker$WorkerThreadFactory$1$1.run(XnioWorker.java:1282)
          at java.base/java.lang.Thread.run(Thread.java:833)
  Caused by: java.nio.channels.ClosedChannelException
          ... 20 more
  ```
* You get the following exception in the server:
  ```
  15:18:12,135 INFO  [stdout] (default task-2) MyEjb1.myStringMethod1(Session1: Ping) enter

  15:18:12,135 INFO  [stdout] (default task-2) MyEjb1.myStringMethod1(Session1: Ping) exit

  15:18:12,141 INFO  [stdout] (default task-2) MyEjb2.myStringMethod2(Session2: Ping) enter

  15:18:12,168 INFO  [stdout] (default task-2) MyEjb1.myStringMethod1(Session2: Ping) enter

  15:18:12,168 INFO  [stdout] (default task-2) MyEjb1.myStringMethod1(Session2: Ping) exit

  15:18:12,169 INFO  [stdout] (default task-2) MyEjb2.myStringMethod2(Session2: Ping) exit

  15:18:12,186 INFO  [stdout] (default task-2) MyEjb1.myRecordMethod1(MyRecord[title=Session1, message=Ping]) enter

  15:18:12,187 INFO  [stdout] (default task-2) MyEjb1.myRecordMethod1(MyRecord[title=Session1, message=Ping]) exit

  15:18:12,199 INFO  [stdout] (default task-2) MyEjb2.myRecordMethod2(MyRecord[title=Session2, message=Ping]) enter

  #
  # A fatal error has been detected by the Java Runtime Environment:
  #
  #  EXCEPTION_ACCESS_VIOLATION (0xc0000005) at pc=0x00000226bc5dcf4f, pid=6376, tid=15200
  #
  # JRE version: OpenJDK Runtime Environment Temurin-17.0.8+7 (17.0.8+7) (build 17.0.8+7)
  # Java VM: OpenJDK 64-Bit Server VM Temurin-17.0.8+7 (17.0.8+7, mixed mode, sharing, tiered, compressed oops, compressed class ptrs, g1 gc, windows-amd64)
  # Problematic frame:
  # J 4674 c2 java.util.IdentityHashMap.get(Ljava/lang/Object;)Ljava/lang/Object; java.base@17.0.8 (60 bytes) @ 0x00000226bc5dcf4f [0x00000226bc5dcf20+0x000000000000002f]
  #
  # No core dump will be written. Minidumps are not enabled by default on client versions of Windows
  #
  # An error report file with more information is saved as:
  # C:\wildfly-record-bug2\server\build\wildfly\wildfly-29.0.0.Final\bin\hs_err_pid6376.log
  Compiled method (c2)   16061 4674       4       java.util.IdentityHashMap::get (60 bytes)
   total in heap  [0x00000226bc5dcd90,0x00000226bc5dd288] = 1272
   relocation     [0x00000226bc5dcee8,0x00000226bc5dcf18] = 48
   main code      [0x00000226bc5dcf20,0x00000226bc5dd0e0] = 448
   stub code      [0x00000226bc5dd0e0,0x00000226bc5dd108] = 40
   metadata       [0x00000226bc5dd108,0x00000226bc5dd120] = 24
   scopes data    [0x00000226bc5dd120,0x00000226bc5dd1c0] = 160
   scopes pcs     [0x00000226bc5dd1c0,0x00000226bc5dd250] = 144
   dependencies   [0x00000226bc5dd250,0x00000226bc5dd258] = 8
   handler table  [0x00000226bc5dd258,0x00000226bc5dd270] = 24
   nul chk table  [0x00000226bc5dd270,0x00000226bc5dd288] = 24
  Compiled method (c2)   16069 4674       4       java.util.IdentityHashMap::get (60 bytes)
   total in heap  [0x00000226bc5dcd90,0x00000226bc5dd288] = 1272
   relocation     [0x00000226bc5dcee8,0x00000226bc5dcf18] = 48
   main code      [0x00000226bc5dcf20,0x00000226bc5dd0e0] = 448
   stub code      [0x00000226bc5dd0e0,0x00000226bc5dd108] = 40
   metadata       [0x00000226bc5dd108,0x00000226bc5dd120] = 24
   scopes data    [0x00000226bc5dd120,0x00000226bc5dd1c0] = 160
   scopes pcs     [0x00000226bc5dd1c0,0x00000226bc5dd250] = 144
   dependencies   [0x00000226bc5dd250,0x00000226bc5dd258] = 8
   handler table  [0x00000226bc5dd258,0x00000226bc5dd270] = 24
   nul chk table  [0x00000226bc5dd270,0x00000226bc5dd288] = 24
  #
  # If you would like to submit a bug report, please visit:
  #   https://github.com/adoptium/adoptium-support/issues
  #
  Dr�cken Sie eine beliebige Taste . . .
  ```

Cleanup:
* `gradlew stopServer`
