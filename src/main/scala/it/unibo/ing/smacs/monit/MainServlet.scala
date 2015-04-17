package it.unibo.ing.smacs.monit

/**
 * @author Antonio Murgia
 *         Simple Servlet that launches the monit command and
 *         wraps it in a json object
 */

import java.io.{IOException, InputStreamReader, BufferedReader}
import it.unibo.ing.smacs.monit.gatherer.DataGatherer
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

  get("/") {
    import DefaultJsonProtocol._
    DataGatherer.getData() match{
      case Some(data: Seq[MonitInfo]) => data.toJson.compactPrint
      case _ => JsObject("error"->JsString("cannot retrieve monit data"))
    }
  }
}