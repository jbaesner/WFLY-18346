package org.wildfly.bug.record.server;

import org.wildfly.bug.record.interfaces.MyRecord;
import org.wildfly.bug.record.interfaces.MyRemoteInterface1;
import jakarta.annotation.security.PermitAll;
import jakarta.ejb.Remote;
import jakarta.ejb.Stateless;

@Remote(MyRemoteInterface1.class)
@Stateless(name = MyRemoteInterface1.NAME)
@PermitAll
public class MyEjb1 implements MyRemoteInterface1 {
  @Override
  public String myStringMethod1(String myString) {
    System.out.println("MyEjb1.myStringMethod1(" + myString + ") enter");
    String result = "MyEjb1: Pong";
    System.out.println("MyEjb1.myStringMethod1(" + myString + ") exit");
    return result;
  }

  @Override
  public MyRecord myRecordMethod1(MyRecord myRecord) {
    System.out.println("MyEjb1.myRecordMethod1(" + myRecord + ") enter");
    MyRecord result = new MyRecord("MyEjb1", "Pong");
    System.out.println("MyEjb1.myRecordMethod1(" + myRecord + ") exit");
    return result;
  }
}
