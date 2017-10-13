package com.rxjavalab;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import io.reactivex.Flowable;

public class RealTest {

  @Test
  public void assertsTest() {
    List<String> testData = Arrays.asList("test1", "test22", "test333", "test4444");
    Flowable.fromIterable(testData)
    .map(String::length)
    .test()
    .assertNoErrors()
    .assertValueCount(4)
    .assertResult(5,6,7,8)
    .assertComplete();
  }

}
