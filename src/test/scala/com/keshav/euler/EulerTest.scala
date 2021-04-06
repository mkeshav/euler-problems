package com.keshav.euler

import com.keshav.euler.Euler._
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import java.lang.Math.{ceil, log, sqrt}
import scala.collection.mutable
import scala.io.Source

class EulerTest extends AnyFunSuite with Matchers {
  test("euler - 1") {
    (1 to 999).filter(x => x % 3 == 0 || x % 5 == 0).sum should be(233168)
  }

  test("euler - 2") {
    fibs.takeWhile(_ <= 4000000).filter(_ % 2 == 0).sum should be(4613732)
  }

  test("euler - 3") {
    primeFactors(600851475143L).last should be(6857)
  }

  test("euler - 4") {
    val pals = for (i <- 100 to 999; j <- 100 to 999 if isPalindrome((i * j).toString)) yield i * j
    pals.max should be(906609)
  }

  test("euler - 5") {
    (1 to 20).foldLeft(1L)((a, b) => lcm(a, b)) should be(232792560)
  }

  test("euler - 6") {
    val sumOfSquares = (1 to 100).map(x => x * x).sum
    val sum = (1 to 100).sum
    sum * sum - sumOfSquares should be(25164150)
  }

  test("euler - 7") {
    val primes = Iterator.iterate(2)(nextPrime)
    primes.drop(10000).next should be(104743)
  }

  test("euler - 8") {
    def productOfDigits = (sn: String) => sn.trim.map(d => d.asDigit.toLong).product

    val products = (n: Int, s: String) => s.sliding(n).map(productOfDigits)
    val num =
      """
        73167176531330624919225119674426574742355349194934
       |96983520312774506326239578318016984801869478851843
       |85861560789112949495459501737958331952853208805511
       |12540698747158523863050715693290963295227443043557
       |66896648950445244523161731856403098711121722383113
       |62229893423380308135336276614282806444486645238749
       |30358907296290491560440772390713810515859307960866
       |70172427121883998797908792274921901699720888093776
       |65727333001053367881220235421809751254540594752243
       |52584907711670556013604839586446706324415722155397
       |53697817977846174064955149290862569321978468622482
       |83972241375657056057490261407972968652414535100474
       |82166370484403199890008895243450658541227588666881
       |16427171479924442928230863465674813919123162824586
       |17866458359124566529476545682848912883142607690042
       |24219022671055626321111109370544217506941658960408
       |07198403850962455444362981230987879927244284909188
       |84580156166097919133875499200524063689912560717606
       |05886116467109405077541002256983155200055935729725
       |71636269561882670428252483600823257530420752963450
      """.stripMargin
    products(13, num.trim.filter(_.isDigit)).max should be(23514624000L)
  }

  test("euler - 9") {
    val sum = 1000
    val triplets = for (a <- (1 to sum).view; b <- 1 to sum - a; c <- 1 to sum - a - b if isSpecialPythogoranTriplet(a, b, c, sum)) yield (a, b, c)
    val at = triplets.head
    at._1 * at._2 * at._3 should be(31875000)
  }

  test("euler - 10") {
    2 + (3L to 2000000 by 2).filter(isPrime).sum should be(142913828922L)
  }

  test("euler - 11") {
    def getValue(x: Int, y: Int, grid: List[List[Int]]): Int = {
      if (grid.isDefinedAt(y) && grid(y).isDefinedAt(x)) grid(y)(x)
      else 0
    }

    def products(x: Int, y: Int, grid: List[List[Int]]): List[Int] = {
      val v = for (shift <- 0 to 3) yield getValue(x, y + shift, grid)
      val h = for (shift <- 0 to 3) yield getValue(x + shift, y, grid)
      val dd = for (shift <- 0 to 3) yield getValue(x + shift, y + shift, grid)
      val du = for (shift <- 0 to 3) yield getValue(x + shift, y - shift, grid)
      List(v.product, h.product, dd.product, du.product)
    }

    val input =
      """
        |08 02 22 97 38 15 00 40 00 75 04 05 07 78 52 12 50 77 91 08
        |49 49 99 40 17 81 18 57 60 87 17 40 98 43 69 48 04 56 62 00
        |81 49 31 73 55 79 14 29 93 71 40 67 53 88 30 03 49 13 36 65
        |52 70 95 23 04 60 11 42 69 24 68 56 01 32 56 71 37 02 36 91
        |22 31 16 71 51 67 63 89 41 92 36 54 22 40 40 28 66 33 13 80
        |24 47 32 60 99 03 45 02 44 75 33 53 78 36 84 20 35 17 12 50
        |32 98 81 28 64 23 67 10 26 38 40 67 59 54 70 66 18 38 64 70
        |67 26 20 68 02 62 12 20 95 63 94 39 63 08 40 91 66 49 94 21
        |24 55 58 05 66 73 99 26 97 17 78 78 96 83 14 88 34 89 63 72
        |21 36 23 09 75 00 76 44 20 45 35 14 00 61 33 97 34 31 33 95
        |78 17 53 28 22 75 31 67 15 94 03 80 04 62 16 14 09 53 56 92
        |16 39 05 42 96 35 31 47 55 58 88 24 00 17 54 24 36 29 85 57
        |86 56 00 48 35 71 89 07 05 44 44 37 44 60 21 58 51 54 17 58
        |19 80 81 68 05 94 47 69 28 73 92 13 86 52 17 77 04 89 55 40
        |04 52 08 83 97 35 99 16 07 97 57 32 16 26 26 79 33 27 98 66
        |88 36 68 87 57 62 20 72 03 46 33 67 46 55 12 32 63 93 53 69
        |04 42 16 73 38 25 39 11 24 94 72 18 08 46 29 32 40 62 76 36
        |20 69 36 41 72 30 23 88 34 62 99 69 82 67 59 85 74 04 36 16
        |20 73 35 29 78 31 90 01 74 31 49 71 48 86 81 16 23 57 05 54
        |01 70 54 71 83 51 54 69 16 92 33 48 61 43 52 01 89 19 67 48
      """.stripMargin

    val grid = input.trim.split("\n").map(_.split(" ").map(_.toInt).toList).toList
    val prods = for (
      x <- 0 until grid.head.length;
      y <- 0 until grid.length) yield products(x, y, grid)

    println(prods.flatten.max)
  }
  test("euler - 12") {
    val triangleNumbersFn = (n: Int) => (1 to n).sum
    val tns = Iterator.from(1).map(triangleNumbersFn)
    val nFactors = (num: Long) => primeFactors(num).groupBy(identity).map(_._2.length + 1).product
    tns.find(nFactors(_) > 500).get should be(76576500)
  }

  test("euler - 13") {
    val sns =
      """|37107287533902102798797998220837590246510135740250
         |46376937677490009712648124896970078050417018260538
         |74324986199524741059474233309513058123726617309629
         |91942213363574161572522430563301811072406154908250
         |23067588207539346171171980310421047513778063246676
         |89261670696623633820136378418383684178734361726757
         |28112879812849979408065481931592621691275889832738
         |44274228917432520321923589422876796487670272189318
         |47451445736001306439091167216856844588711603153276
         |70386486105843025439939619828917593665686757934951
         |62176457141856560629502157223196586755079324193331
         |64906352462741904929101432445813822663347944758178
         |92575867718337217661963751590579239728245598838407
         |58203565325359399008402633568948830189458628227828
         |80181199384826282014278194139940567587151170094390
         |35398664372827112653829987240784473053190104293586
         |86515506006295864861532075273371959191420517255829
         |71693888707715466499115593487603532921714970056938
         |54370070576826684624621495650076471787294438377604
         |53282654108756828443191190634694037855217779295145
         |36123272525000296071075082563815656710885258350721
         |45876576172410976447339110607218265236877223636045
         |17423706905851860660448207621209813287860733969412
         |81142660418086830619328460811191061556940512689692
         |51934325451728388641918047049293215058642563049483
         |62467221648435076201727918039944693004732956340691
         |15732444386908125794514089057706229429197107928209
         |55037687525678773091862540744969844508330393682126
         |18336384825330154686196124348767681297534375946515
         |80386287592878490201521685554828717201219257766954
         |78182833757993103614740356856449095527097864797581
         |16726320100436897842553539920931837441497806860984
         |48403098129077791799088218795327364475675590848030
         |87086987551392711854517078544161852424320693150332
         |59959406895756536782107074926966537676326235447210
         |69793950679652694742597709739166693763042633987085
         |41052684708299085211399427365734116182760315001271
         |65378607361501080857009149939512557028198746004375
         |35829035317434717326932123578154982629742552737307
         |94953759765105305946966067683156574377167401875275
         |88902802571733229619176668713819931811048770190271
         |25267680276078003013678680992525463401061632866526
         |36270218540497705585629946580636237993140746255962
         |24074486908231174977792365466257246923322810917141
         |91430288197103288597806669760892938638285025333403
         |34413065578016127815921815005561868836468420090470
         |23053081172816430487623791969842487255036638784583
         |11487696932154902810424020138335124462181441773470
         |63783299490636259666498587618221225225512486764533
         |67720186971698544312419572409913959008952310058822
         |95548255300263520781532296796249481641953868218774
         |76085327132285723110424803456124867697064507995236
         |37774242535411291684276865538926205024910326572967
         |23701913275725675285653248258265463092207058596522
         |29798860272258331913126375147341994889534765745501
         |18495701454879288984856827726077713721403798879715
         |38298203783031473527721580348144513491373226651381
         |34829543829199918180278916522431027392251122869539
         |40957953066405232632538044100059654939159879593635
         |29746152185502371307642255121183693803580388584903
         |41698116222072977186158236678424689157993532961922
         |62467957194401269043877107275048102390895523597457
         |23189706772547915061505504953922979530901129967519
         |86188088225875314529584099251203829009407770775672
         |11306739708304724483816533873502340845647058077308
         |82959174767140363198008187129011875491310547126581
         |97623331044818386269515456334926366572897563400500
         |42846280183517070527831839425882145521227251250327
         |55121603546981200581762165212827652751691296897789
         |32238195734329339946437501907836945765883352399886
         |75506164965184775180738168837861091527357929701337
         |62177842752192623401942399639168044983993173312731
         |32924185707147349566916674687634660915035914677504
         |99518671430235219628894890102423325116913619626622
         |73267460800591547471830798392868535206946944540724
         |76841822524674417161514036427982273348055556214818
         |97142617910342598647204516893989422179826088076852
         |87783646182799346313767754307809363333018982642090
         |10848802521674670883215120185883543223812876952786
         |71329612474782464538636993009049310363619763878039
         |62184073572399794223406235393808339651327408011116
         |66627891981488087797941876876144230030984490851411
         |60661826293682836764744779239180335110989069790714
         |85786944089552990653640447425576083659976645795096
         |66024396409905389607120198219976047599490197230297
         |64913982680032973156037120041377903785566085089252
         |16730939319872750275468906903707539413042652315011
         |94809377245048795150954100921645863754710598436791
         |78639167021187492431995700641917969777599028300699
         |15368713711936614952811305876380278410754449733078
         |40789923115535562561142322423255033685442488917353
         |44889911501440648020369068063960672322193204149535
         |41503128880339536053299340368006977710650566631954
         |81234880673210146739058568557934581403627822703280
         |82616570773948327592232845941706525094512325230608
         |22918802058777319719839450180888072429661980811197
         |77158542502016545090413245809786882778948721859617
         |72107838435069186155435662884062257473692284509516
         |20849603980134001723930671666823555245252804609722
         |53503534226472524250874054075591789781264330331690
      """.stripMargin
    sns.trim.split("\n").map(BigInt(_)).sum.toString.take(10) should be("5537376230")
  }

  test("euler - 14") {
    println(((1L to 1000000).toList) .maxBy(collatzSeriesLength))
  }

  test("euler - 15") {
    val fac = (n: Int) => (BigInt(1) to n).product

    /*
      routes available for 2x2 grid
      (2+2)!/2!*2!
      for 20x20
      40!/20! * 20!
     */
    fac(40) / (fac(20) * fac(20)) should be(137846528820L)
  }

  test("euler - 16") {
    BigInt(2).pow(1000).toString.map(_.asDigit).sum should be(1366)
  }

  test("euler - 18&67") {
    val triangle =
      """
        |75
        |95 64
        |17 47 82
        |18 35 87 10
        |20 04 82 47 65
        |19 01 23 75 03 34
        |88 02 77 73 07 63 67
        |99 65 04 28 06 16 70 92
        |41 41 26 56 83 40 80 70 33
        |41 48 72 33 47 32 37 16 94 29
        |53 71 44 65 25 43 91 52 97 51 14
        |70 11 33 28 77 73 17 78 39 68 17 57
        |91 71 52 38 17 14 91 43 58 50 27 29 48
        |63 66 04 68 89 53 67 30 73 16 69 87 40 31
        |04 62 98 27 23 09 70 98 73 93 38 53 60 04 23
      """.stripMargin
    val pyramid = triangle.trim.split("\n").map(_.split(" ").toList.map(_.toInt)).toIndexedSeq
    maxValueInPyramid(0, 0, pyramid, collection.mutable.Map[(Int, Int), Int]()) should be(1074)
  }

  test("euler - 19") {
    //dayofweek is 2 because 1901 jan 1st was tuesday
    countSundays(0, 1901, 2, 0, 2001) should be(171)
  }

  test("euler - 20") {
    (1 to 10).product.toString.map(_.asDigit).sum should be(27)
    (BigInt(1) to 100).product.toString.map(_.asDigit).sum should be(648)
  }

  test("euler - 21") {
    val d = (num: Long) => divisorsSum(num)
    val isAmicable = (a: Long) => {
      val b = d(a)
      b > 1 && a == d(b) && a != b
    }
    println((2L until 10000).filter(isAmicable).sum)
  }

  test("euler - 22") {
    val nameScore = (name: String) => name.map(c => (c.toInt - 'A'.toInt) + 1).sum
    val source = Source.fromResource("p022_names.txt").getLines().next().split(',').map(_.tail.init).sorted
    source.zipWithIndex.map {
      case (name, idx) =>
        nameScore(name) * (idx + 1)
    }.sum should be(871198282L)
  }

  test("euler - 23") {
    val isAbundant = (num: Long) => {
      divisorsSum(num) > num
    }
    val abundantNumbers = (2 to 28123).filter(n => isAbundant(n.toLong))

    val canBeWritten = new Array[Boolean](28123)
    for {
      a <- abundantNumbers.indices
      b <- a until abundantNumbers.length
      sum = abundantNumbers(a) + abundantNumbers(b) if sum <= 28123
    } {
      canBeWritten(sum - 1) = true
    }

    (1 to 28123).filterNot(n => canBeWritten(n - 1)).sum should be(4179871)
  }

  /*
    φ = pow(φ, n) => 1.6180...
    we need to find pow(φ, n)/Math.sqrt(5) > pow(10, 999)
    http://mathworld.wolfram.com/FibonacciNumber.html
    http://www.mathblog.dk/project-euler-25-fibonacci-sequence-1000-digits/
   */
  test("euler - 25") {
    val φ = (1 + sqrt(5)) / 2
    //log 10 is 1
    val n = ceil(((log(10) * 999) + (log(5)/2))/log(φ))
    n.toLong should be(4782)
  }

  test("euler - 26") {
    (1 until 1000).maxBy(lengthOfRecurringCycle) should be(983)
  }

  test("euler - 28") {
    /*
    Starting with the number 1 and moving to the right in a clockwise direction a 5 by 5 spiral is formed as follows:

      21 22 23 24 25
      20  7  8  9 10
      19  6  1  2 11
      18  5  4  3 12
      17 16 15 14 13
      It can be verified that the sum of both diagonals is 101.

      What is the sum of both diagonals in a 1001 by 1001 spiral formed in the same way?
      top-right: n^2 top-left: n^2 - n + 1 bottom-left: n^2 - 2n + 2 bottom-right: n^2 - 3n + 3
      which will be solved to: 4n^2 -6n + 6
      http://www.mathblog.dk/project-euler-28-sum-diagonals-spiral/
      end equation: 16/3x^3 + 10x^2 + 26/3x + 1
     */
    val spiralSum = (n: BigInt) => (n.pow(3)*16 + 26*n)/3 + 10*n.pow(2)
    spiralSum(500)+1 should be(669171001) //+1 is for base case
  }

  test("euler - 29") {
    val powers = (num: Int) => for (x <- BigInt(2) to num; y <- 2 to num) yield x.pow(y)
    powers(100).distinct.size should be(9183)
  }

  test("euler - 30") {
    def digitPowers(power: Int) = {
      val maxDigits = Iterator.from(1).find(n =>
        Math.pow(10, n - 1).toInt > n * Math.pow(9, power)).get

      val powers = (0 to 9).map(n => Math.pow(n, power))
      (10 until Math.pow(10, maxDigits - 1).toInt).filter { n =>
        val fifthSum = n.toString.map(c => powers(c.asDigit)).sum
        fifthSum == n
      }.sum
    }

    digitPowers(5) should be(443839)
  }

  test("euler - 31") {
    val coins = List(1, 2, 5, 10, 20, 50, 100, 200).reverse
    val memo = mutable.Map[(Int, Int), Int]()
    def combinations(v: Int, maxCoin: Int): Int = v match {
      case _ if v < 0 => 0
      case 0 => 1
      case _ =>
        memo.getOrElseUpdate((v, maxCoin), {
          coins.filter(_ <= maxCoin).map(c => combinations(v - c, c)).sum
        })
    }

    combinations(200, 200) should be(73682)
  }

  test("euler - 32") {
    /*
      9! different ways of arranging numbers 1 to 9
      take sliding combination from the vector
     */
    val cases = (1 to 9).permutations flatMap {
      digs =>
        Seq(
          (digs.take(1), digs.slice(1, 1+4), digs.drop(5)),
          (digs.take(2), digs.slice(2, 2+3), digs.drop(5))
        )
    }
    cases
      .filter(e => e._1.mkString.toInt * e._2.mkString.toInt == e._3.mkString.toInt)
      .map(_._3.mkString.toInt)
      .toList.distinct.sum should be(45228)
  }

  test("euler - 33") {
    def ~=(x: Double, y: Double, precision: Double) = {
      if ((x - y).abs < precision) true else false
    }

    def digitCancelDivision(tup: (Int, Int)): (Float, Int, Int) = {
      val numDigs = tup._1.toString.map(_.asDigit).toSet
      val denomDigs = tup._2.toString.map(_.asDigit).toSet
      val num = if (numDigs.diff(denomDigs).nonEmpty) numDigs.diff(denomDigs).mkString.toInt else 0
      val denom = if (denomDigs.diff(numDigs).nonEmpty) denomDigs.diff(numDigs).mkString.toInt else 1
      (num.toFloat/denom, num, denom)
    }

    val l1 = 10 to 99
    val zipped = l1.flatMap {
      e =>
        val l2 = e + 1 to 99
        List.fill(l2.size)(e).zip(l2)
    }
    val toWork = zipped.filter {
      e =>
        val numDigs = e._1.toString.map(_.asDigit)
        val denomDigs = e._2.toString.map(_.asDigit)
        numDigs.intersect(denomDigs).nonEmpty && e._2 % 10 > 0
    }

    /*
      Do actual division
      Do digit cancel division
      if they are equal
     */

    val res = toWork.map(e => (digitCancelDivision(e), e._1.toFloat / e._2))
      .filter(x => x._1._1 == x._2)
      .map(y => y._2)
    ~=(res.product, 0.01, 0.000001) should be(true)
  }

  test("euler - 34"){
    /*
      We quickly find that n < 8
      since the largest sum of factorials we can produce from an 8-digit number
      is itself a 7-digit number (set all digits equal to 9 and look at 8x9!,
      which only has 7 digits). So, we know the numbers that we're looking
      at are at most 7-digit numbers. Since  9999999 would give 7 x 9! = 2540161
     */
    val result = (3 to 2540161)
      .map(e => (e, e.toString.map(_.asDigit).map(_!).sum))
      .filter(x => x._1 == x._2)
      .map(_._2).sum

    result should be(40730)

  }

  test("euler - 17") {
    val sum1To9 = List("one", "two", "three", "four", "five", "six",
      "seven", "eight", "nine").map(_.length).sum
    val sum10to19 = List("ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen",
      "seventeen", "eighteen", "nineteen").map(_.length).sum

    val sum20To90 = List("twenty", "thirty", "forty", "fifty",
      "sixty", "seventy", "eighty", "ninety").map(_.length).sum

    val sum20to99 = 10*sum20To90 + 8*sum1To9
    val sum1to99 = sum1To9 + sum10to19 + sum20to99

    val oneTo9occurs100times = 100*sum1To9
    val oneTo99occurs9times = 9*sum1to99
    val hundredOccurs9times = "hundred".length * 9
    val hundredAndOccurs891times = "hundredand".length * 891

    val sum100to999 = oneTo9occurs100times + oneTo99occurs9times + hundredOccurs9times + hundredAndOccurs891times

    sum1to99 + sum100to999 + "onethousand".length should be(21124)
    (1 to 1000).map(speak(_)).map(_.trim.split(" ").mkString.length).sum should be(21124)
  }

  test("Modulo 10 check digit") {
    val x = "139".map(_.toString).reverse.zipWithIndex.map {
      e: (String, Int) => (e._1.toInt, e._2+1)
    }.map {
      v => if (v._2 % 2 != 0) v._1 * 2 else v._1
    }.flatMap(_.toString.toList).map(_.toInt - 48).sum % 10 // Subtract 48 to get actual value as char to int will be ascii value

    10 - x should be(6)
  }

  test("Chinese Remainder Theorem(CRT") {
    crt(List(3, 5, 7), List(2, 3, 2)) should be(23)
    crt(List(3, 4, 5), List(2, 2, 1 )) should be(26)
  }
}

