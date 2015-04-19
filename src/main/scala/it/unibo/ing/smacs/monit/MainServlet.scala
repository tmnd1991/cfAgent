package it.unibo.ing.smacs.monit

/**
 * @author Antonio Murgia
 *         Simple Servlet that launches the monit command and
 *         wraps it in a json object
 */

import java.io.{IOException, InputStreamReader, BufferedReader}
import java.util.Date
import it.unibo.ing.smacs.monit.gatherer.{RecentBuffer, DataGatherer}
import it.unibo.ing.smacs.monit.model.MonitInfo
import it.unibo.ing.utils.BufferedLineIterator

import scala.collection.JavaConversions._
import scala.collection.immutable.Stream.Empty

import org.scalatra._

import spray.json._

import it.unibo.ing.smacs.monit.parsers.MonitOutputParser
import it.unibo.ing.smacs.monit.model.JsonConversions._

class MainServlet extends MonitrestfulinterfaceStack {

  before() {
    contentType = "json"
  }

  get("/:start/:end") {
    val start = params("start")
    val end = params("end")
    val startDate = new Date(start.toLong)
    val endDate = new Date(end.toLong)
    val values = RecentBuffer.between(startDate,endDate).flatten.toList
    import DefaultJsonProtocol._
    if (values.isEmpty)
      InternalServerError(JsObject("error"->JsString("cannot retrieve monit data")).compactPrint)
    else
      values.toJson.compactPrint
  }
}