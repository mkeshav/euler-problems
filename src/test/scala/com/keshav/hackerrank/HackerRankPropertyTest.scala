package com.keshav.hackerrank

import com.keshav.hackerrank.HackerRank._
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.scalatest.prop.TableDrivenPropertyChecks

class HackerRankPropertyTest extends AnyFunSuite with Matchers with TableDrivenPropertyChecks {
  test("Max score of vincent") {
    val scores = Table(
      ("vincent", "catherine", "maxScore"),
      ("ACCEDED", "DECADE.", 4),
      ("BEE..ADDED.", "CAB.DAD.DEE", 6),
      ("CCACCBAEBAAAAAAAA.......", "CCACCBAEBAAAAAAAA.......", 0)
    )

    forAll(scores) { (v:String, c:String, expectedScore: Int) =>
      maxScoreOfVincent(v.length, v, c) should be(expectedScore)
    }
  }

  test("Array Manipulation") {
    val scores = Table(
      ("n", "queries", "max"),
      (5, List(List(1, 2, 100), List(2, 5, 100), List(3, 4, 100)), 200),
      (10, List(List(1, 5, 3), List(4, 8, 7), List(6, 9, 1)), 10),
    )

    forAll(scores) { (n:Int, q:List[List[Int]], max: Int) =>
      arrayManipulationIterative(n, q) should be(max)
    }
  }
}
