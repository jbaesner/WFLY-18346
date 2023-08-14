package org.wildfly.bug.record.client;

import static javax.naming.Context.INITIAL_CONTEXT_FACTORY;
import static javax.naming.Context.PROVIDER_URL;
import javax.naming.InitialContext;
import org.wildfly.bug.record.interfaces.MyRecord;
import org.wildfly.bug.record.interfaces.MyRemoteInterface1;
import org.wildfly.bug.record.interfaces.MyRemoteInterface2;
import org.wildfly.security.auth.client.AuthenticationConfiguration;
import org.wildfly.security.auth.client.AuthenticationContext;
import org.wildfly.security.auth.client.MatchRule;

public class MyClient {
  public static void main(String[] args) throws Throwable {
    System.setProperty(INITIAL_CONTEXT_FACTORY, org.wildfly.naming.client.WildFlyInitialContextFactory.class.getName());
    System.setProperty(PROVIDER_URL, "remote+http://localhost:8080");
    AuthenticationContext ctx = AuthenticationContext.empty().with(MatchRule.ALL, AuthenticationConfiguration.empty() //
        .useAuthorizationName("username") //
        .usePassword("password") //
    );
    ctx.runCallable(() -> {
      MyRemoteInterface1 session1 =
          InitialContext.doLookup("server/" + MyRemoteInterface1.NAME + "!" + MyRemoteInterface1.class.getName());
      MyRemoteInterface2 session2 =
          InitialContext.doLookup("server/" + MyRemoteInterface2.NAME + "!" + MyRemoteInterface2.class.getName());

      System.out.println(session1.myStringMethod1("Session1: Ping"));
      System.out.println(session2.myStringMethod2("Session2: Ping"));
      System.out.println(session1.myRecordMethod1(new MyRecord("Session1", "Ping")));
      System.out.println(session2.myRecordMethod2(new MyRecord("Session2", "Ping")));

      return null;
    });
  }
}
