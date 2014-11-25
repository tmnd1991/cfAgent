package it.unibo.ing.smacs.monit

/**
 * @author Antonio Murgia
 * Library that enriches the other libraries as shown in :
 * http://www.artima.com/weblogs/viewpost.jsp?thread=179766
 * also known as Pimp My Library pattern (Martin Odersky)
 */
object PimpMyLib{
  implicit def StringToRichString(s : String) = new RichString(s)

  /**
   *
   * @param s : the String to be enriched
   * this makes possible to pass a negative endIndex in substring method
   * to start counting from the last element of the string backwards
   */
  class RichString(s : String) {
    def mySubstring(beginIndex : Int, endIndex : Int) : String = {
      val rightEndIndex = if (endIndex < 0) s.length+endIndex
                          else endIndex
      s.substring(beginIndex, rightEndIndex)
    }
  }
}