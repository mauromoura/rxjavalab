package com.rxjavalab;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import io.reactivex.Flowable;

public class OperatorsTest {

  @Test
  public void map() {
    Flowable.fromArray(4, 9, 16, 25)
        .map(Math::sqrt)
        .subscribe(System.out::println);
  }

  @Test
  public void filter() {
    Flowable.fromArray(1, 2, 3, 4, 5, 6, 7, 8)
        .filter(num -> num > 4)
        .subscribe(System.out::println);
  }

  @Test
  public void filterWithFirst() {
    Flowable.fromArray(1, 2, 3, 4, 5, 6, 7, 8)
        .filter(num -> num > 4)
        .firstElement()
        .subscribe(System.out::println);
  }

  @Test
  public void filterWithDefault() {
    Flowable.fromArray("John", "Sarah", "Joe")
        .filter(name -> name.equals("Mauro"))
        .defaultIfEmpty("Not Found")
        .subscribe(System.out::println);
  }

  @Test
  public void all() {
    Flowable.fromArray(1, 2, 3, 4, 5, 6, 7, 8)
        .all(num -> num < 10)
        .subscribe(System.out::println);
  }

  @Test
  public void flatMap() throws Exception {

    List<List<Integer>> data = createData();

    Flowable.fromIterable(data)
        .flatMap(Flowable::fromIterable)
        .subscribe(System.out::println);

  }

  @Test
  public void toList() throws Exception {

    List<List<Integer>> data = createData();

    Flowable.fromIterable(data)
        .flatMap(Flowable::fromIterable)
        .toList()
        .subscribe(System.out::println);
  }

  @Test
  public void reduce() throws Exception {
    List<List<Integer>> data = createData();

    Flowable.fromIterable(data)
        .flatMap(Flowable::fromIterable)
        .reduce((a, b) -> a + b)
        .subscribe(System.out::println);
  }

  @Test
  public void skipTake() throws Exception {
    Flowable.fromArray(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        .skipWhile(n -> n < 3)
        .takeWhile(n -> n < 8)
        .subscribe(System.out::println);
  }

  @Test
  public void merge() throws Exception {
    Flowable.mergeArray(Flowable.fromArray(1, 3, 5), Flowable.fromArray(2, 4, 6))
        .subscribe(System.out::println);
  }

  @Test
  public void mergeWith() throws Exception {
    Flowable.fromArray(1, 3, 5)
        .mergeWith(Flowable.fromArray(2, 4, 6))
        .subscribe(System.out::println);
  }

  @Test
  public void zip() throws Exception {
    Flowable.fromArray(1, 3, 5)
        .zipWith(Flowable.fromArray(2, 4, 6), (a, b) -> a + b)
        .subscribe(System.out::println);
  }

  private List<List<Integer>> createData() {
    List<List<Integer>> data = new ArrayList<>();
    data.add(Arrays.asList(1, 4, 9));
    data.add(Arrays.asList(12, 56, 35));
    data.add(Arrays.asList(3, 54, 99));
    return data;
  }



}
