package com.rxjavalab;
import org.junit.Test;

import io.reactivex.Observable;

public class HelloWorldTest {

  @Test
  public void HelloWorld() {
    Observable.just("Hello world")
        .subscribe(System.out::println);
  }

}


