package com.keshav.hackerrank

object HackerRank {
  def maxScoreOfVincent(n: Int, s: String, t: String): Int =  {
    s.toList.zip(t.toList).count(e => e._1 != '.' && e._1 != e._2)
  }

  def compareTriplets(a: Array[Int], b: Array[Int]): (Int, Int) = {
    val scores = a.zip(b).filter(e => e._1 != e._2).map {
      e =>
        if (e._1 > e._2) (1, 0) else (0, 1)
    }

    scores.fold((0, 0))((x, y) => (x._1 + y._1, x._2 + y._2))
  }

  def bigSum(ar: Array[Long]): Long = {
    ar.sum
  }

  def diagonalDiff(mat: Array[Array[Int]]): Int = {
    val n = mat.length
    val diagonalElements = mat.indices map { i => (mat(i)(i), mat(n - i - 1)(i)) }
    val sums = diagonalElements.fold((0, 0))((x, y) => (x._1 + y._1, x._2 + y._2))
    Math.abs(sums._2 - sums._1)
  }

  def plusMinus(arr: Array[Int]): List[(String, Double)] = {
    val n = arr.length
    val gpd = arr.map(e => if (e == 0) "c" else if (e > 0) "a" else "b").toList.groupBy(identity).mapValues(v => roundAt(6)(v.size / n.toDouble))
    gpd.toList.sortBy(_._1)
  }

  private def roundAt(p: Int)(n: Double): Double = { val s = math pow (10, p); (math round n * s) / s }
}
