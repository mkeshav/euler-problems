package com.keshav.hackerrank

import org.scalatest.{FunSuite, Matchers}
import HackerRank._

import scala.io.Source

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

  test("array manipulation") {
    val source = Source.fromResource("array_manipulation_input.txt").getLines()
    val n = source.next().split(" ").toList.head
    val queries = source.toList.tail.map(_.split(" ").toList.map(_.toInt))
    arrayManipulationIterative(n.toInt, queries) should be(7542539201L)
  }
}
