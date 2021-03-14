package com.keshav.hackerrank

import com.keshav.hackerrank.HackerRank._
import org.scalatest.{FunSuite, Matchers}

import scala.compat.Platform
import scala.io.Source
import scala.util.Random
class HackerRankTest extends FunSuite with Matchers {
  test("triplet compare") {
    val a = Array(1, 2, 3)
    val b = Array(3, 2, 1)
    val result = compareTriplets(a, b)
    result should be((1, 1))
    val z = result.productIterator.toArray.map(_.toString.toInt)
    z should be(Array(1, 1))
  }

  test("big sum") {
    val ar = "1000000001 1000000002 1000000003 1000000004 1000000005".split(" ").map(_.trim.toLong)
    println(ar.sum)
  }

  test("diagonal diff") {
    val mat = Array(
      Array(11, 2, 4),
      Array(4, 5, 6),
      Array(10, 8, -12))

    diagonalDiff(mat) should be(15)
  }

  test("plus minus") {
    plusMinus(Array(-4, 3, -9, 0, 4, 1)) should be(Array(0.5, 0.333333, 0.166667))

    plusMinus(Array(0, 1, 29, 0, 4, 1)).foreach(v => println(v.toString.padTo(8, "0").mkString))
  }

  test("epowx") {
    epowx(20.0000) should be(2423600.1887)
    epowx(5.0000) should be(143.6895)
    epowx(-0.5000) should be(0.6065)
  }

  test("winner") {
    val andrea = List(1, 2, 3)
    val maria = List(2, 1, 3)

    oddEvenGameScore(andrea, maria, "Odd")
  }

  test("match Array") {
    val a = List(123, 543)
    val m = List(321, 279)
    val result = a.zip(m).flatMap {
      e =>
        e._1.toString.zip(e._2.toString).map {
          x =>
            Math.abs(x._1 - x._2)
        }
    }.sum
    result should be(16)
  }

  test("print longest even word") {
    "It is a pleasant day today"
      .split(" ").map(w => (w, w.length)).filter(_._2 % 2 == 0).minBy(-_._2)._1 should be("pleasant")
    "write code for great time"
      .split(" ").map(w => (w, w.length)).filter(_._2 % 2 == 0).head._1 should be("code")
  }

  test("array manipulation - print") {
    val queries = List(List(1, 2, 100), List(2, 5, 100), List(3, 4, 100))
    arrayManipulationIterative(5, queries) should be(200)
  }

  test("array manipulation") {
    val source = Source.fromResource("array_manipulation_input_tc7.txt").getLines()
    val n = source.next().split(" ").toList.head
    val queries = source.toList.map(_.split(" ").toList.map(_.toInt))
    arrayManipulationIterative(n.toInt, queries) should be(2497169732L)
  }

  test("reverse") {
    reverseListFold(List(1, 2, 3)) should be(List(3, 2, 1))
    reverseListFold(List("hello", "world")) should be(List("world", "hello"))
  }

  test("hour glass") {
    /*
      generate hour glass
      16 hour glasses in 6X6
      1 1 1  1 1 0
        1      0
      1 1 1  1 1 0
    */

    val mat = Array(
      Array(1, 1, 1, 0, 0, 0),
      Array(0, 1, 0, 0, 0, 0),
      Array(1, 1, 1, 0, 0, 0),
      Array(0, 0, 2, 4, 4, 0),
      Array(0, 0, 0, 2, 0, 0),
      Array(0, 0, 1, 2, 4, 0)
    )

    val mat1 = List(
      List(-9, -9, -9, 1, 1, 1),
      List(0, -9, 0, 4, 3, 2),
      List(-9, -9, -9, 1, 2, 3),
      List(0, 0, 8, 6, 6, 0),
      List(0, 0, 0, -2, 0, 0),
      List(0, 0, 1, 2, 4, 0)
    )

    val result = for (i <- 0 until 4) yield {
      for (j <- 0 until 4) yield {
        (mat(i).slice(j, j+3) +: Array(mat(i+1)(j+1)) +: Array(mat(i+2).slice(j, j+3))).flatten
      }.sum
    }

    println(result)
    println(result.map(_.max).max)
  }

  test("list rotation") {
    rotateList(List(1, 2, 3, 4, 5), 4) should be(List(5, 1, 2 ,3 ,4))
  }

  test("sparse arrays") {
    val ss = List("ab", "ab", "abc")
    val qs = List("ab", "abc", "bc")
    println(qs.map(q => ss.count(s => s == q)))
  }

  test("Perf characteristics of sequences over arrays") {
    val t1 = Platform.currentTime
    Array.fill(10000000)(0)
    val t2 = Platform.currentTime
    println(s"Took ${t2 - t1} ms")

    val t3 = Platform.currentTime
    List.fill(10000000)(0)
    val t4 = Platform.currentTime
    println(s"Took ${t4 - t3} ms")
  }

  test("Anz - p2") {
    compareTrades(Array(2, 7, 4, 8), Array(2, 6, 10)).foreach(println)
    compareTrades(Array(2, 4, 6), Array(4, 7)).foreach(println)
  }

}