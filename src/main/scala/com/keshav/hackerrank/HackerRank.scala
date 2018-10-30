package com.keshav.hackerrank

object HackerRank {
  def maxScoreOfVincent(n: Int, s: String, t: String): Int =  {
    s.toList.zip(t.toList).count(e => e._1 != '.' && e._1 != e._2)
  }
}
