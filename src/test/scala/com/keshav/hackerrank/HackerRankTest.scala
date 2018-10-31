package com.keshav.hackerrank

import org.scalatest.{FunSuite, Matchers}
import HackerRank._

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
    val arr = Array(-4, 3, -9, 0, 4, 1)
    plusMinus(arr).foreach(v => println(v._2.toString.padTo(8, "0").mkString))
  }
}
