package com.rxjavalab;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

public class BaseClassesTest {

  @Test
  public void single() throws Exception {
    Single.just(1)
        .subscribe(System.out::println);
  }

  @Test
  public void maybe() throws Exception {

    Optional<String> optional = Optional.ofNullable("test");

    optional.map(Maybe::just)
        .orElse(Maybe.empty())
        .subscribe(System.out::println);
  }

  @Test
  public void completable() throws Exception {
    Completable.fromRunnable(() -> {
      System.out.println("Here");
    });
  }

  @Test
  public void flowable() throws Exception {
    Flowable.fromIterable(Arrays.asList(1, 2, 3, 4, 5));
  }

}
