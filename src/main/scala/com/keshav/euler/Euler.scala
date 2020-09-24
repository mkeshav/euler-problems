package com.keshav.euler

import scala.annotation.tailrec

object Euler {
  def isPrime(num: Long): Boolean = {
    num > 1 && !(2L to math.sqrt(num).toInt).exists(num % _ == 0)
  }

  def isPalindrome(s: String): Boolean = s.reverse == s

  //haskell: let fibs = 1:1:zipWith (+) (fibs) (tail fibs); take 1 $ drop 999 fibs
  val fibs: Stream[BigInt] = 0 #:: fibs.scan(BigInt(1))(_ + _)

  def fibPisano(n:Int) = {
    @tailrec
    def fibTailPisano(n: Int, a: Int, b: Int): Int = n match {
      case 0 => a
      case _ => fibTailPisano(n-1, b, (a+b)%1000000)
    }
    fibTailPisano(n%1500000, 0, 1)
  }
  def primeFactors(num: Long): List[Long] = {
    val exists = (2L to math.sqrt(num).toLong).find(num % _ == 0)
    exists.map(d => d :: primeFactors(num/d)).getOrElse(List(num))
  }

  def gcf(a: Long, b: Long): Long = b match {
    case 0 => a
    case _ => gcf(b, a % b)
  }

  def egcd(a: Long, b: Long): (Long, Long, Long) = a match {
    case 0 => (b, 0, 1)
    case _ =>
      val (g, x, y) = egcd(b % a, a)
      val q = b / a
      (g, y - q * x, x)
  }

  def mulinv(b: Long, n: Long): Long = {
    val (g, x, _) = egcd(b, n)
    //((dividend % divisor) + divisor) % divisor is the right way to do negative modulo
    if (g == 1) ((x % n) + n) % n else 0
  }

  def crt(n: List[Int], a: List[Int]): Long = {
    require(coprimes(n))
    val product = n.product
    val sum = n.zip(a).map {
      v =>
        val (ni, ai) = v
        val p = product / ni
        ai * mulinv(p, ni) * p
    }.sum

    sum % product
  }

  def coprimes(l: List[Int]): Boolean = {
    !l.combinations(2).toList.map {
      case Seq(x, y) => gcf(x, y)
    }.exists(_ != 1)
  }

  def lcm(a: Long, b: Long):Long = (a*b)/gcf(a, b)

  def nextPrime(seed: Int): Int = Iterator.from(seed + 1).find(isPrime(_)).get

  def isSpecialPythogoranTriplet(a:Int, b:Int, c:Int, sum:Int): Boolean =
    (a*a) + (b*b) == (c*c) && a + b + c == sum && a < b && b < c

  def collatzSeriesLength(start: Long): Long = {
    @tailrec
    def recurse(n: Long, length: Long): Long = n match {
      case 1 => length + 1
      case x if x % 2 == 0 => recurse(n/2, length + 1)
      case _ => recurse(3*n + 1, length + 1)
    }
    recurse(start, 0)
  }

  def maxValueInPyramid(row: Int, col: Int, pyramid: IndexedSeq[List[Int]], maxes: collection.mutable.Map[(Int, Int), Int]): Int = {
    if (row == pyramid.length) 0 else {
      maxes.getOrElseUpdate((row, col), pyramid(row)(col) + (maxValueInPyramid(row+1, col, pyramid, maxes) max maxValueInPyramid(row+1, col+1, pyramid, maxes)))
    }
  }

  def isLeapYear(year: Int): Boolean = {
    (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
  }

  val monthDays = List(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)

  def daysInMonth(month: Int, year: Int): Int = {
    monthDays(month) + (if (isLeapYear(year) && month == 1) 1 else 0)
  }

  @tailrec
  def countSundays(month: Int, year: Int, dayOfWeek: Int, count: Int, endYear: Int): Int = {
    if (year == endYear) {
      count
    } else {
      val newDay = (dayOfWeek + daysInMonth(month, year)) % 7
      val newCount = if (dayOfWeek == 0) count + 1 else count
      val newMonth = (month + 1) % 12
      val newYear = year + ((month + 1)/12)
      countSundays(newMonth, newYear, newDay, newCount, endYear)
    }
  }

  def divisorsSum(num: Long): Long = {
    val factors = primeFactors(num)
    factors.groupBy(identity).map { case (base, group) =>
      val count = group.size
      (1 - Math.pow(base, count + 1)).toInt / (1 - base)
    }.product - num
  }

  def lengthOfRecurringCycle(denom: Int): Int = {
    @tailrec
    def recurse(remainder: Int, seenBeforeRemainders: Seq[Int]): Int = {
      if (seenBeforeRemainders.contains(remainder)) {
        seenBeforeRemainders.size - seenBeforeRemainders.indexOf(remainder)
      } else {
        recurse((remainder * 10) % denom, seenBeforeRemainders :+ remainder)
      }
    }
    recurse(1, Seq.empty)
  }

  implicit class FactorialInt(val num: Int) {
    def ! : BigInt = {
      (BigInt(1) to num).product
    }
  }

  def speak(num: Int, printZero: Boolean = true): String = {
    if (num < 0) s"negative ${speak(-num)}"
    else if (num >= 1000000000) s"${speak(num / 1000000000)} billion ${speak(num % 1000000000, false)}"
    else if (num >= 1000000) s"${speak(num / 1000000)} million ${speak(num % 1000000, false)}"
    else if (num >= 1000) s"${speak(num / 1000)} thousand ${speak(num % 1000, false)}"
    else if (num >= 100) {
      if (num % 100 > 0) s"${speak(num / 100)} hundred and ${speak(num % 100, false)}" else s"${speak(num / 100)} hundred ${speak(num % 100, false)}"
    }
    else if (num >= 20) num / 10 match {
      case 2 => s"twenty ${speak(num % 10, false)}"
      case 3 => s"thirty ${speak(num % 10, false)}"
      case 4 => s"forty ${speak(num % 10, false)}"
      case 5 => s"fifty ${speak(num % 10, false)}"
      case n@_ => s"${speak(n).stripSuffix("t")}ty ${speak(num % 10, false)}"
    }
    else num match {
      case 0 => if (printZero) "zero" else ""
      case 1 => "one"
      case 2 => "two"
      case 3 => "three"
      case 4 => "four"
      case 5 => "five"
      case 6 => "six"
      case 7 => "seven"
      case 8 => "eight"
      case 9 => "nine"
      case 10 => "ten"
      case 11 => "eleven"
      case 12 => "twelve"
      case 13 => "thirteen"
      case 15 =>"fifteen";
      case n@_ => s"${speak(n-10).stripSuffix("t")}teen"
    }
  }
}
