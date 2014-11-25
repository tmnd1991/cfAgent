package myUtils

/**
 * Created by tmnd on 25/11/14.
 */
import scala.collection.Iterator
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.InputStream

class BufferedLineIterator(inputStream: InputStream) extends Iterator[String] {

  /** Buffered reader for reading lines from the file */
  private val bufferedReader: BufferedReader = {
    val inputStreamReader = new InputStreamReader(inputStream)
    new BufferedReader(inputStreamReader)
  }

  /** Stores the next line waiting to be returned from the next() method */
  private var nextLine: String = null

  /** Identifies if the file has another line to read
    *
    * @return boolean identifying if the file has another line to read
    */

  override def hasNext(): Boolean = {
    if( nextLine == null ) {
      nextLine = bufferedReader.readLine
    }
    nextLine != null
  }

  /** Reads the next line from the file
    *
    * @return the line
    */
  override def next(): String = {
    try {
      if(hasNext) {
        nextLine
      } else {
        Iterator.empty.next
      }
    } finally {
      nextLine = null
    }
  }

}