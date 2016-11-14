package com.tapatron.testsupport

import java.util.{HashMap => JHashMap, List => JList}

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import io.gatling.jsonpath.{JPError, JsonPath}
import org.scalatest.matchers.{MatchResult, Matcher}

import scala.collection.JavaConversions.seqAsJavaList

trait JsonPathMatchers {

  val mapper = new ObjectMapper() with ScalaObjectMapper

  def bool(b: Boolean) = b

  def int(i: Int) = i

  def double(f: Double) = f

  def text(s: String) = s

  def nullNode: Any = null

  def array(elements: Any*): JList[Any] = elements

  def obj(elements: (String, Any)*) = elements.foldLeft(new JHashMap[String, Any]())((o: JHashMap[String, Any], e) => {
    o.put(e._1, e._2)
    o
  })

  def jsonPath(query: String, underTest: String): Either[JPError, Iterator[Any]] =
    JsonPath.query(query, mapper.readValue(underTest, classOf[Object]))

  class OrderedElementsMatcher(expected: Traversable[Any]) extends Matcher[Either[JPError, Iterator[Any]]] {
    override def apply(input: Either[JPError, Iterator[Any]]): MatchResult =
      input match {
        case Right(it) =>
          val seq = it.toVector
          MatchResult(
            seq == expected,
            s"$seq does not contain the same elements as $expected",
            s"$seq is equal to $expected but it shouldn't"
          )
        case Left(e) => MatchResult(
          matches = false,
          s"parsing issue, $e",
          s"parsing issue, $e"
        )
      }
  }

  def containItemsInOrder(expected: Any*) = new OrderedElementsMatcher(expected)

  class ElementsMatcher(expected: Traversable[Any]) extends Matcher[Either[JPError, Iterator[Any]]] {
    override def apply(input: Either[JPError, Iterator[Any]]): MatchResult =
      input match {
        case Right(it) =>
          val actualSeq = it.toVector
          val expectedSeq = expected.toVector

          val missing = expectedSeq.diff(actualSeq)
          val added = actualSeq.diff(expectedSeq)
          MatchResult(
            missing.isEmpty && added.isEmpty,
            s"$actualSeq is missing $missing and should not contain $added",
            s"$actualSeq is equal to $expectedSeq but it shouldn't"
          )
        case Left(e) => MatchResult(
          matches = false,
          s"parsing issue, $e",
          s"parsing issue, $e"
        )
      }
  }

  def containItems(expected: Any*) = new ElementsMatcher(expected)
}