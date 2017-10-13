package com.rxjavalab;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class SchedulersTest {

  private static final Logger logger = LoggerFactory.getLogger(SchedulersTest.class);

  public List<String> getData() {
    return Arrays.asList("test1", "test2", "test3", "test4");
  }

  private String convertToUpperCase(String s) {
    logger.info("begin {}", s);
    try {
      Thread.sleep(500);
      s = s.toUpperCase();
    } catch (InterruptedException e) {
      logger.error("error processing {}", s, e);
    }
    logger.info("end {}", s);
    return s;
  }

  public void show(String s) {
    logger.info("final String: {}", s);
  }

  @Test
  public void normal() throws Exception {
    Flowable.fromIterable(getData())
        .map(this::convertToUpperCase)
        .subscribe(this::show);
  }

  @Test
  public void changingThreads() throws Exception {
    Flowable.fromIterable(getData())
        .observeOn(Schedulers.computation())
        .map(this::convertToUpperCase)
        .subscribe(this::show);

    Thread.sleep(10000);
  }

  @Test
  public void changingThreadsGoingBackToMainThread() throws Exception {
    Flowable.fromIterable(getData())
        .observeOn(Schedulers.computation())
        .map(this::convertToUpperCase)
        .blockingSubscribe(this::show);
  }

  @Test
  public void multiThreading() throws Exception {
    Flowable.fromIterable(getData())
        .flatMap(s -> Flowable.just(s)
            .observeOn(Schedulers.computation())
            .map(this::convertToUpperCase))
        .blockingSubscribe(this::show);
  }
  
  @Test
  public void multiThreadingInOrder() throws Exception {
    Flowable.fromIterable(getData())
        .flatMap(s -> Flowable.just(s)
            .observeOn(Schedulers.computation())
            .map(this::convertToUpperCase))
        .sorted()
        .blockingSubscribe(this::show);
  }
  
  @Test
  public void customScheduler() throws Exception {
    
    Scheduler myScheduler = Schedulers.from(Executors.newFixedThreadPool(2));
    
    Flowable.fromIterable(getData())
        .flatMap(s -> Flowable.just(s)
            .observeOn(myScheduler)
            .map(this::convertToUpperCase))
        .sorted()
        .blockingSubscribe(this::show);
  }



}
