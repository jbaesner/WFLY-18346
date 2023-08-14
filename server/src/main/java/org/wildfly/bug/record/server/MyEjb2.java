package org.wildfly.bug.record.server;

import org.wildfly.bug.record.interfaces.MyRecord;
import org.wildfly.bug.record.interfaces.MyRemoteInterface1;
import org.wildfly.bug.record.interfaces.MyRemoteInterface2;
import jakarta.annotation.security.PermitAll;
import jakarta.ejb.EJB;
import jakarta.ejb.Remote;
import jakarta.ejb.Stateless;

@Remote(MyRemoteInterface2.class)
@Stateless(name = MyRemoteInterface2.NAME)
@PermitAll
public class MyEjb2 implements MyRemoteInterface2 {
  @EJB
  private MyRemoteInterface1 ejb2;

  @Override
  public String myStringMethod2(String myString) {
    System.out.println("MyEjb2.myStringMethod2(" + myString + ") enter");
    String result = ejb2.myStringMethod1(myString);
    System.out.println("MyEjb2.myStringMethod2(" + myString + ") exit");
    return result;
  }

  @Override
  public MyRecord myRecordMethod2(MyRecord myRecord) {
    System.out.println("MyEjb2.myRecordMethod2(" + myRecord + ") enter");
    MyRecord result = ejb2.myRecordMethod1(myRecord);
    System.out.println("MyEjb2.myRecordMethod2(" + myRecord + ") exit");
    return result;
  }
}
