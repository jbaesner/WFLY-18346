package org.wildfly.bug.record.interfaces;

public interface MyRemoteInterface2 {
  String NAME = "MyRemoteInterface2";

  String myStringMethod2(String myString);

  MyRecord myRecordMethod2(MyRecord myRecord);
}
