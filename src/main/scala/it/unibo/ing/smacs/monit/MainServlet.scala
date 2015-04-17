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

  get("/:time") {
    val tick = params("time")
    val realDate = new Date(tick.toLong)
    if (RecentBuffer isLatest realDate){
      val newData = DataGatherer.getData()
      import DefaultJsonProtocol._
      newData match{
        case Some(data: Seq[MonitInfo]) => {
          RecentBuffer(realDate) = data
          data.toJson.compactPrint
        }
        case _ => JsObject("error"->JsString("cannot retrieve monit data")).compactPrint
      }
    }
    else{
      import DefaultJsonProtocol._
      if (RecentBuffer contains realDate){
        RecentBuffer(realDate).toJson.compactPrint
      }
      else
        JsObject("error"->JsString("cannot retrieve monit data")).compactPrint
    }
  }
}