package com.keshav.hackerrank

object HackerRank {
  def maxScoreOfVincent(n: Int, s: String, t: String): Int = {
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

  def plusMinus(arr: Array[Int]): List[Double] = {
    val n = arr.length
    val base = List(1 -> 0.0, 2 -> 0.0, 3 -> 0.0)
    val gpd = arr.map(e => if (e > 0) 1 else if (e < 0) 2 else 3).toList.groupBy(identity).mapValues(v => roundAt(6)(v.size / n.toDouble))
    (gpd.toList ++ base).groupBy(_._1).mapValues(v => v.map(_._2).sum).toList.sortBy(_._1).map(_._2)
  }

  def epowx(n: Double) = {
    roundAt(4)(1 + (1 to 9 map { i => (math pow(n, i)) / (1 to i).product }).sum)
  }

  def arrayManipulationIterative(n: Int, queries: List[List[Int]]): Long = {
    val arr = Array.fill(n + 1)(0)

    queries.foreach { q =>
      val List(start, finish, summand) = q
      arr(start - 1) += summand
      arr(finish) -= summand
    }

    var tmp, max = 0L
    arr.foreach {
      v =>
        tmp += v
        max = if (max < tmp) tmp else max
    }
    max
  }

  def oddEvenGameScore(andrea: List[Int], maria: List[Int], s: String): String = {
    val result = s match {
      case "Even" =>
        val scores = andrea.zip(maria).zipWithIndex.map { case (e: (Int, Int), y: Int) => (e._1 - e._2, e._2 - e._1, y) }.filter(_._3 % 2 == 0)
        (scores.map(_._1).sum, scores.map(_._2).sum)
      case "Odd" =>
        val scores = andrea.tail.zip(maria.tail).zipWithIndex.map { case (e: (Int, Int), y: Int) => (e._1 - e._2, e._2 - e._1, y) }
        (scores.map(_._1).sum, scores.map(_._2).sum)
    }
    if (result._1 > result._2) "Andrea" else if (result._2 > result._1) "Maria" else "Tie"
  }

  def reverseListFold[A](a: List[A]): List[A] = {
    a.foldLeft(List.empty[A])((x: List[A], y: A) => y :: x)
  }

  def filterWithFold[A](f: A => Boolean)(arr: List[A]): List[A] = {
    arr.foldLeft(List.empty[A])((x: List[A], y: A) => if (f(y)) x :+ y else x)
  }

  private def roundAt(p: Int)(n: Double): Double = {
    val s = math pow(10, p); (math round n * s) / s
  }

  def rotateList(l: List[Int], rotations: Int): List[Int] = {
    (l.view.drop(rotations) ++ l.view.take(rotations)).toList
  }

  def tracesAreSimilarV2(s: Array[Int], t: Array[Int]): String = {
    if (s.length == t.length) {
      val g0: Map[Int, Int] = s.groupBy(identity).map{ case (k, vs) => (k, vs.length)}.withDefaultValue(0)
      val g1: Map[Int, Int] = s.groupBy(identity).map{ case (k, vs) => (k, vs.length)}.withDefaultValue(0)
      val maxDiff = g0.map {
        case (k, count) => math.abs(g1(k) - count)
      }.reduceOption(math.max)
      if (maxDiff.getOrElse(0) <= 3) "YES" else "NO"
    } else "NO"
  }

  def zipMap(a: Map[String, Int], b: Map[String, Int]): Iterable[(String, Int, Int)] = {
    val a0 = a.withDefaultValue(0)
    val b0 = b.withDefaultValue(0)
    for (key <- a0.keys ++ b0.keys) yield (key, a0(key), b0(key))
  }

  def counter[T](l: Array[T]) =
    l.foldLeft(Map[T, Int]() withDefaultValue 0) { (m, x) =>
      m + (x -> (1 + m(x)))
    }

  def compareTrades(d1: Array[Int], d2: Array[Int]) = {
    d2.map {
      s => d1.map(s1 => s - s1).count(_ >= 0)
    }
  }
}
