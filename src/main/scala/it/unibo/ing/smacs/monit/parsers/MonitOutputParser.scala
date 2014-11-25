package it.unibo.ing.smacs.monit.parsers

import it.unibo.ing.smacs.monit.model.MonitInfo

/**
 * @author Antonio Murgia
 *         parses the output of the monit status command into a Seq[MonitInfo]
 */
object MonitOutputParser extends Parser[Seq[MonitInfo],String]{
  val myProcessParser = ProcessParser
  val mySystemParser = SystemParser
  override def parse(x: String) = {
    var lines = x.linesWithSeparators.toList.map(_.replaceAll("\n","").trim).filterNot(_ == "")
    lines = lines.tail
    var toRet = List[MonitInfo]()
    while(lines.nonEmpty){
      var lista = ""
      if (lines.head.startsWith("Process")) {
        lista += lines.head + "\n"
        lines = lines.tail
        while((lines nonEmpty) && !(lines.head.startsWith("Process")) && !(lines.head.startsWith("System"))) {
          lista += lines.head + "\n"
          lines = lines.tail
        }
        toRet :+= myProcessParser.parse(lista)
      }
      else if (lines.head.startsWith("System")) {
        lista += lines.head + "\n"
        lines = lines.tail
        while((lines nonEmpty) && !(lines.head.startsWith("Process")) && !(lines.head.startsWith("System"))) {
          lista += lines.head + "\n"
          lines = lines.tail
        }
        toRet :+= mySystemParser.parse(lista)
      }
    }
    toRet
  }
}
