object po {

  /**
   * Defines a function potum
   * @param r - Input radius value
   * @return  - Length
   */

  def potumCalc(r: Double): Double = {

    // Number of terms to iterate over PI formulae to get exact value of pi constant
    val PI_ITER = 12

    // Number of terms to iterate over sine/cosine functions
    val TRIG_ITER = 12

    /**
     * Calculates the value of the first argument raised to the power of the second argument.
     * @param base - Base number
     * @param exp - exponent number
     * @return - base&#94;exp
     */

    def pow(base: Double, exp: Int): Double = {
      var ret: Double = 1
      var b = base
      var e = exp
      while (e > 0) {
        // optimize around b ^ (2 * n) === (b * b) ^ n
        if (e % 2 == 0) {
          e /= 2
          b *= b
        } else {
          e -= 1
          ret *= b
        }
      }
      return ret
    }


    /**
     * Calculates factorial of given number
     * @param x - Number
     * @return - factorial value of x
     */

    def fact(x: BigInt): BigInt = {
      var ret: BigInt = 1
      var xx: BigInt = x
      while (xx > 0) {
        ret *= xx
        xx -= 1
      }
      return ret
    }

    /**
     * Calculates the value of constant PI
     * @return - pi value
     */

    def calcpi(): Double = {
      var ret: Double = 0
      var i = PI_ITER
      while (i != 0) {
        i -= 1
        // terms are from the Bailey-Borwein-Plouffe forumla for PI
        ret += (1.0 / pow(16, i)) * (
          4.0 / (8 * i + 1) - 2.0 / (8 * i + 4) -
            1.0 / (8 * i + 5) - 1.0 / (8 * i + 6))
      }
      return ret
    }

    /**
     * Calculates value using modified sine function
     * @param x - the input number to which sine has to be calculated
     * @return - modified sine value of x
     */

    def lsin(x: Double): Double = {
      var ret: Double = 0
      var i = TRIG_ITER
      //loop in reverse to reduce error
      while (i > 1) {
        i -= 1
        ret += pow(-1, i) * pow(x, 2 * i + 1) / fact(2 * i + 1).toDouble
      }
      return ret
    }

    /**
     * Calculates value using modified cosine function
     * @param x - the input number to which cosine has to be calculated
     * @return - modified cosine value of x
     */

    def lcos(x: Double): Double = {
      var ret:Double = 0
      var i = TRIG_ITER
      //loop in reverse to reduce errors
      while (i > 1) {
        i -= 1
        ret += pow(-1, i) * pow(x, 2 * i) / fact(2 * i).toDouble
      }
      return ret
    }

    // calculate pi
    val pi = calcpi

    /**
     * function used to solve alpha (a) (simplified to reduce truncation errors)
     * @param a - Alpha angle
     * @return value
     */
    def f(a: Double): Double = lsin(a) + pi / 2

    /**
     * Bisection method implementation to solve the zero for function f in the range of [l, h]
     * @param low - Lower bound value
     * @param high - Higher bound value
     * @return - Alpha angle value
     */

    def solve(low: Double, high: Double): Double = {
      var l = low
      var h = high
      var m: Double = 0
      while (true) {
        // find the mid point
        m = (l + h) / 2

        // if the rounding error causes m to match either l or h
        // this will not work with infinite precision
        if (m == l || m == h)
          return m

        // solve f at two points
        var a = f(l)
        var b = f(m)

        // if the signs match, then the zero is not in that region
        // otherwise it is.
        if ((a < 0) == (b < 0))
          l = m
        else
          h = m
      }
      m
    }

    // call solve to get a
    val a: Double = solve(0, 2 * pi);
    println("Angle a : " + a)

    // calculate l based on a
    val len: Double = -2 * r * lcos(a / 2)

    return len
  }
}

object potum {

  /**
   * Main method
   * @param args - no command line arguments
   */
  def main (args : Array[String]) {

    println("Enter exit/quit to stop")
    var input : String = " "

    while(true) {
      println("\nEnter Radius : ")
      input = Console.readLine()

      if (input.equals("exit") || input.equals("quit"))
        sys.exit()

      println("Length : " + po.potumCalc(input.toDouble))
    }
  }
}

