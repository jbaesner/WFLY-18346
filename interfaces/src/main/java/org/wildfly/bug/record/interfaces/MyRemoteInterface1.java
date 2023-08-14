package org.wildfly.bug.record.interfaces;

public interface MyRemoteInterface1 {
  String NAME = "MyRemoteInterface1";

  String myStringMethod1(String myString);

  MyRecord myRecordMethod1(MyRecord myRecord);
}
