package com.keshav.hackerrank

import com.keshav.hackerrank.HackerRank._
import org.scalatest.prop.PropertyChecks
import org.scalatest.{Matchers, PropSpec}

class HackerRankPropertyTest extends PropSpec with PropertyChecks with Matchers {
  property("Max score of vincent") {
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
}
