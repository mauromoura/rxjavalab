package com.rxjavalab;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.reactivex.Flowable;
import io.reactivex.Single;

public class ErrorHandlingTest {

  private static final Logger logger = LoggerFactory.getLogger(ErrorHandlingTest.class);

  public List<Integer> getData() {
    return Arrays.asList(1, 2, 0, 20, 50);
  }

  public Integer process(Integer n) {
    return 100 / n;
  }

  @Test
  public void example() throws Exception {
    Flowable.fromIterable(getData())
        .map(this::process)
        .subscribe(s -> logger.info("The data: {}", s));
  }

  @Test
  public void basicHandler() throws Exception {
    Flowable.fromIterable(getData())
        .map(this::process)
        .subscribe(s -> logger.info("The data: {}", s), e -> logger.error("The error", e));
  }

  @Test
  public void onErrorReturnItem() throws Exception {
    Flowable.fromIterable(getData())
        .map(this::process)
        .onErrorReturnItem(-1)
        .subscribe(s -> logger.info("The data: {}", s), e -> logger.error("The error", e));
  }

  @Test
  public void retry() throws Exception {
    Flowable.fromIterable(getData())
        .map(this::process)
        .retry(3)
        .subscribe(s -> logger.info("The data: {}", s), e -> logger.error("The error", e));
  }

  @Test
  public void resumeStreamExecutionWithTryCatch() throws Exception {
    Flowable.fromIterable(getData())
        .map(n -> {
          try {
            return this.process(n);
          } catch (Exception e) {
            return -1;
          }
        })
        .subscribe(s -> logger.info("The data: {}", s), e -> logger.error("The error", e));
  }

  @Test
  public void resumeStreamExecutionWithAnotherStream() throws Exception {
    Flowable.fromIterable(getData())
        .flatMapSingle(n -> Single.just(n)
            .map(this::process)
            .onErrorReturnItem(-1))
        .subscribe(s -> logger.info("The data: {}", s), e -> logger.error("The error", e));
  }

}
