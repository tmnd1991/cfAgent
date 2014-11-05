package it.unibo.ing.smacs.monit.model

/**
 * Created by tmnd on 03/11/14.
 */
case class CpuUsage(user : Double, system : Double, Wait : Double){
  override def toString = "us = " + user + "% sy = " + system + "% wa = " + Wait + "%"
}
