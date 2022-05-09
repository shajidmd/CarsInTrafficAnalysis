package com.aips.traffic.signal

import scala.io.Source
import resource.managed

import java.io.File
import com.typesafe.scalalogging.LazyLogging

import java.time.{LocalDate, LocalDateTime}
import java.time.format.DateTimeFormatter

class CarsInTrafficAnalysis extends LazyLogging {

  val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

  def getNumberOfCarsInTotal(samples: List[TrafficSamples]): Int = samples.map(_.numberOfCars).sum

  def getNumberOfCarsByDay(samples: List[TrafficSamples]): Map[LocalDate, Int] =
    samples.groupBy(_.sampleTime.toLocalDate)
      .mapValues(_.map(_.numberOfCars).sum)

  def getTop3MostCarSeenInHalfHour(samples: List[TrafficSamples]): List[TrafficSamples] = {
    samples.sortBy(_.numberOfCars)(Ordering[Int].reverse).take(3)
  }

  def getLeastCarsSeenInOneAndHalfHour(samples: List[TrafficSamples]): (List[LocalDateTime], Int) = {
    samples.sliding(3)
      .map(samples => (samples.map(_.sampleTime), samples.map(_.numberOfCars).sum))
      .minBy(_._2)
  }

  def getTrafficSamples(filePath: String): List[TrafficSamples] = {
    getLinesFromFile(filePath).map(_.split(" ").map(_.trim)).map { sample =>
      TrafficSamples(parse(sample.head), sample.last.toInt)
    }
  }

  private def parse(time: String): LocalDateTime = LocalDateTime.from(formatter.parse(time))

  /** Read file content and returns list of strings by line.
   *
   * @param path : Path to input file
   * @return List[String]: Line contents in file
   */
  private[signal] def getLinesFromFile(path: String): List[String] = {
    logger.debug(s"Reading input file content from path: $path")
    require(new File(path).isFile, s"Invalid input file path: $path")
    managed(Source.fromFile(path)("UTF-8")).acquireAndGet(_.getLines().map(_.toLowerCase).toList)
  }

}

object CarsInTrafficAnalysis extends App with LazyLogging {

  val carsInTrafficAnalysis = new CarsInTrafficAnalysis()
  val samples = carsInTrafficAnalysis.getTrafficSamples("")

  carsInTrafficAnalysis.getNumberOfCarsInTotal(samples)
  carsInTrafficAnalysis.getNumberOfCarsByDay(samples)
  carsInTrafficAnalysis.getTop3MostCarSeenInHalfHour(samples)
  carsInTrafficAnalysis.getLeastCarsSeenInOneAndHalfHour(samples)

}
