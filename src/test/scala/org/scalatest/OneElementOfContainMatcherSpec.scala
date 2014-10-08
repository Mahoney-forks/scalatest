/*
 * Copyright 2001-2014 Artima, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.scalatest

import collection.GenTraversable
import SharedHelpers._
import Matchers._
import FailureMessages.decorateToStringValue
import org.scalactic.Entry

class OneElementOfContainMatcherSpec extends Spec {

  object `oneElementOf ` {

    def checkStackDepth(e: exceptions.StackDepthException, left: Any, right: GenTraversable[Any], lineNumber: Int) {
      e.message should be (Some(FailureMessages("didNotContainOneElementOf", left, right)))
      e.failedCodeFileName should be (Some("OneElementOfContainMatcherSpec.scala"))
      e.failedCodeLineNumber should be (Some(lineNumber))
    }

    def `should succeeded when left List contains same elements in same order as right List` {
      List(1, 3, 5, 8) should contain oneElementOf Seq(7, 8, 9)
      Array(1, 3, 5, 8) should contain oneElementOf Seq(7, 8, 9)
      javaList(1, 3, 5, 8) should contain oneElementOf Seq(7, 8, 9)

      Set(1, 3, 5, 8) should contain oneElementOf Seq(7, 8, 9)
      javaSet(1, 3, 5, 8) should contain oneElementOf Seq(7, 8, 9)

      Map(1 -> "one", 3 -> "three", 5 -> "five", 8 -> "eight") should contain oneElementOf Seq(7 -> "seven", 8 -> "eight", 9 -> "nine")
      javaMap(Entry(1, "one"), Entry(3, "three"), Entry(5, "five"), Entry(8, "eight")) should contain oneElementOf Seq(Entry(7, "seven"), Entry(8, "eight"), Entry(9, "nine"))
    }

    def `should succeeded when right List contains at least one element in right List` {
      List(1, 2, 3) should contain oneElementOf Seq(5, 3, 8)
      Array(1, 2, 3) should contain oneElementOf Seq(5, 3, 8)
      javaList(1, 2, 3) should contain oneElementOf Seq(5, 3, 8)

      Set(1, 2, 3) should contain oneElementOf Seq(5, 3, 8)
      javaSet(1, 2, 3) should contain oneElementOf Seq(5, 3, 8)

      Map(1 -> "one", 2 -> "two", 3 -> "three") should contain oneElementOf Seq(5 -> "five", 3 -> "three", 8 -> "eight")
      javaMap(Entry(1, "one"), Entry(2, "two"), Entry(3, "three")) should contain oneElementOf Seq(Entry(5, "five"), Entry(3, "three"), Entry(8, "eight"))
    }

    def `should throw NotAllowedException when oneElementOf contains duplicate element` {
      val e1 = intercept[exceptions.NotAllowedException] {
        List(1, 2, 3) should contain oneElementOf Seq(6, 7, 6)
      }
      e1.getMessage() should be (FailureMessages("oneElementOfDuplicate"))

      val e2 = intercept[exceptions.NotAllowedException] {
        Set(1, 2, 3) should contain oneElementOf Seq(6, 7, 6)
      }
      e2.getMessage() should be (FailureMessages("oneElementOfDuplicate"))

      val e3 = intercept[exceptions.NotAllowedException] {
        Array(1, 2, 3) should contain oneElementOf Seq(6, 7, 6)
      }
      e3.getMessage() should be (FailureMessages("oneElementOfDuplicate"))
    }

    def `should throw TestFailedException with correct stack depth and message when left and right List are same size but does not contain any same element` {
      val left1 = List(1, 2, 3)
      val e1 = intercept[exceptions.TestFailedException] {
        left1 should contain oneElementOf Seq(7, 8, 9)
      }
      checkStackDepth(e1, left1, Seq(7, 8, 9), thisLineNumber - 2)

      val left2 = javaList(1, 2, 3)
      val e2 = intercept[exceptions.TestFailedException] {
        left2 should contain oneElementOf Seq(7, 8, 9)
      }
      checkStackDepth(e2, left2, Seq(7, 8, 9), thisLineNumber - 2)

      val left3 = Map(1 -> "one", 2 -> "two", 3 -> "three")
      val e3 = intercept[exceptions.TestFailedException] {
        left3 should contain oneElementOf Seq(7 -> "seven", 8 -> "eight", 9 -> "nine")
      }
      checkStackDepth(e3, left3, Seq(7 -> "seven", 8 -> "eight", 9 -> "nine"), thisLineNumber - 2)

      val left4 = javaMap(Entry(1, "one"), Entry(2, "two"), Entry(3, "three"))
      val e4 = intercept[exceptions.TestFailedException] {
        left4 should contain oneElementOf Seq(Entry(7, "seven"), Entry(8, "eight"), Entry(9, "nine"))
      }
      checkStackDepth(e4, left4, Seq(Entry(7, "seven"), Entry(8, "eight"), Entry(9, "nine")), thisLineNumber - 2)

      val left5 = Array(1, 2, 3)
      val e5 = intercept[exceptions.TestFailedException] {
        left5 should contain oneElementOf Seq(7, 8, 9)
      }
      checkStackDepth(e5, left5, Seq(7, 8, 9), thisLineNumber - 2)
    }

    def `should throw TestFailedException with correct stack depth and message when left List is shorter than right List and does not contain any same element` {
      val left1 = List(1, 2, 3)
      val e1 = intercept[exceptions.TestFailedException] {
        left1 should contain oneElementOf Seq(6, 7, 8, 9)
      }
      checkStackDepth(e1, left1, Seq(6, 7, 8, 9), thisLineNumber - 2)

      val left2 = javaList(1, 2, 3)
      val e2 = intercept[exceptions.TestFailedException] {
        left2 should contain oneElementOf Seq(6, 7, 8, 9)
      }
      checkStackDepth(e2, left2, Seq(6, 7, 8, 9), thisLineNumber - 2)

      val left3 = Map(1 -> "one", 2 -> "two", 3 -> "three")
      val e3 = intercept[exceptions.TestFailedException] {
        left3 should contain oneElementOf Seq(6 -> "six", 7 -> "seven", 8 -> "eight", 9 -> "nine")
      }
      checkStackDepth(e3, left3, Seq(6 -> "six", 7 -> "seven", 8 -> "eight", 9 -> "nine"), thisLineNumber - 2)

      val left4 = javaMap(Entry(1, "one"), Entry(2, "two"), Entry(3, "three"))
      val e4 = intercept[exceptions.TestFailedException] {
        left4 should contain oneElementOf Seq(Entry(6, "six"), Entry(7, "seven"), Entry(8, "eight"), Entry(9, "nine"))
      }
      checkStackDepth(e4, left4, Seq(Entry(6, "six"), Entry(7, "seven"), Entry(8, "eight"), Entry(9, "nine")), thisLineNumber - 2)

      val left5 = Array(1, 2, 3)
      val e5 = intercept[exceptions.TestFailedException] {
        left5 should contain oneElementOf Seq(6, 7, 8, 9)
      }
      checkStackDepth(e5, left5, Seq(6, 7, 8, 9), thisLineNumber - 2)
    }

    def `should throw TestFailedException with correct stack depth and message when left List is longer than right List and does not contain any same element` {
      val left1 = List(1, 2, 3)
      val e1 = intercept[exceptions.TestFailedException] {
        left1 should contain oneElementOf Seq(8, 5)
      }
      checkStackDepth(e1, left1, Seq(8, 5), thisLineNumber - 2)

      val left2 = javaList(1, 2, 3)
      val e2 = intercept[exceptions.TestFailedException] {
        left2 should contain oneElementOf Seq(8, 5)
      }
      checkStackDepth(e2, left2, Seq(8, 5), thisLineNumber - 2)

      val left3 = Map(1 -> "one", 2 -> "two", 3 -> "three")
      val e3 = intercept[exceptions.TestFailedException] {
        left3 should contain oneElementOf Seq(8 -> "eight", 5 -> "five")
      }
      checkStackDepth(e3, left3, Seq(8 -> "eight", 5 -> "five"), thisLineNumber - 2)

      val left4 = javaMap(Entry(1, "one"), Entry(2, "two"), Entry(3, "three"))
      val e4 = intercept[exceptions.TestFailedException] {
        left4 should contain oneElementOf Seq(Entry(8, "eight"), Entry(5, "five"))
      }
      checkStackDepth(e4, left4, Seq(Entry(8, "eight"), Entry(5, "five")), thisLineNumber - 2)

      val left5 = Array(1, 2, 3)
      val e5 = intercept[exceptions.TestFailedException] {
        left5 should contain oneElementOf Seq(8, 5)
      }
      checkStackDepth(e5, left5, Seq(8, 5), thisLineNumber - 2)
    }

    def `should throw TestFailedException with correct stack depth and message when left and right List contain all same element in different order` {
      val left1 = List(1, 2, 3)
      val e1 = intercept[exceptions.TestFailedException] {
        left1 should contain oneElementOf Seq(3, 2, 1)
      }
      checkStackDepth(e1, left1, Seq(3, 2, 1), thisLineNumber - 2)

      val left2 = javaList(1, 2, 3)
      val e2 = intercept[exceptions.TestFailedException] {
        left2 should contain oneElementOf Seq(3, 2, 1)
      }
      checkStackDepth(e2, left2, Seq(3, 2, 1), thisLineNumber - 2)

      val left3 = Map(1 -> "one", 2 -> "two", 3 -> "three")
      val e3 = intercept[exceptions.TestFailedException] {
        left3 should contain oneElementOf Seq(3 -> "three", 2 -> "two", 1 -> "one")
      }
      checkStackDepth(e3, left3, Seq(3 -> "three", 2 -> "two", 1 -> "one"), thisLineNumber - 2)

      val left4 = javaMap(Entry(1, "one"), Entry(2, "two"), Entry(3, "three"))
      val e4 = intercept[exceptions.TestFailedException] {
        left4 should contain oneElementOf Seq(Entry(3, "three"), Entry(2, "two"), Entry(1, "one"))
      }
      checkStackDepth(e4, left4, Seq(Entry(3, "three"), Entry(2, "two"), Entry(1, "one")), thisLineNumber - 2)

      val left5 = Array(1, 2, 3)
      val e5 = intercept[exceptions.TestFailedException] {
        left5 should contain oneElementOf Seq(3, 2, 1)
      }
      checkStackDepth(e5, left5, Seq(3, 2, 1), thisLineNumber - 2)
    }

    def `should throw TestFailedException with correct stack depth and message when left and right List contain all same element in same order` {
      val left1 = List(1, 2, 3)
      val e1 = intercept[exceptions.TestFailedException] {
        left1 should contain oneElementOf Seq(1, 2, 3)
      }
      checkStackDepth(e1, left1, Seq(1, 2, 3), thisLineNumber - 2)

      val left2 = javaList(1, 2, 3)
      val e2 = intercept[exceptions.TestFailedException] {
        left2 should contain oneElementOf Seq(1, 2, 3)
      }
      checkStackDepth(e2, left2, Seq(1, 2, 3), thisLineNumber - 2)

      val left3 = Map(1 -> "one", 2 -> "two", 3 -> "three")
      val e3 = intercept[exceptions.TestFailedException] {
        left3 should contain oneElementOf Seq(1 -> "one", 2 -> "two", 3 -> "three")
      }
      checkStackDepth(e3, left3, Seq(1 -> "one", 2 -> "two", 3 -> "three"), thisLineNumber - 2)

      val left4 = javaMap(Entry(1, "one"), Entry(2, "two"), Entry(3, "three"))
      val e4 = intercept[exceptions.TestFailedException] {
        left4 should contain oneElementOf Seq(Entry(1, "one"), Entry(2, "two"), Entry(3, "three"))
      }
      checkStackDepth(e4, left4, Seq(Entry(1, "one"), Entry(2, "two"), Entry(3, "three")), thisLineNumber - 2)

      val left5 = Array(1, 2, 3)
      val e5 = intercept[exceptions.TestFailedException] {
        left5 should contain oneElementOf Seq(1, 2, 3)
      }
      checkStackDepth(e5, left5, Seq(1, 2, 3), thisLineNumber - 2)
    }

    def `should throw TestFailedException with correct stack depth and message when left and right List contain more than one same element` {
      val left1 = List(1, 2, 3)
      val e1 = intercept[exceptions.TestFailedException] {
        left1 should contain oneElementOf Seq(5, 1, 2)
      }
      checkStackDepth(e1, left1, Seq(5, 1, 2), thisLineNumber - 2)

      val left2 = javaList(1, 2, 3)
      val e2 = intercept[exceptions.TestFailedException] {
        left2 should contain oneElementOf Seq(5, 1, 2)
      }
      checkStackDepth(e2, left2, Seq(5, 1, 2), thisLineNumber - 2)

      val left3 = Map(1 -> "one", 2 -> "two", 3 -> "three")
      val e3 = intercept[exceptions.TestFailedException] {
        left3 should contain oneElementOf Seq(5 -> "five", 1 -> "one", 2 -> "two")
      }
      checkStackDepth(e3, left3, Seq(5 -> "five", 1 -> "one", 2 -> "two"), thisLineNumber - 2)

      val left4 = javaMap(Entry(1, "one"), Entry(2, "two"), Entry(3, "three"))
      val e4 = intercept[exceptions.TestFailedException] {
        left4 should contain oneElementOf Seq(Entry(5, "five"), Entry(1, "one"), Entry(2, "two"))
      }
      checkStackDepth(e4, left4, Seq(Entry(5, "five"), Entry(1, "one"), Entry(2, "two")), thisLineNumber - 2)

      val left5 = Array(1, 2, 3)
      val e5 = intercept[exceptions.TestFailedException] {
        left5 should contain oneElementOf Seq(5, 1, 2)
      }
      checkStackDepth(e5, left5, Seq(5, 1, 2), thisLineNumber - 2)
    }
  }

  object `not oneElementOf ` {

    def checkStackDepth(e: exceptions.StackDepthException, left: Any, right: GenTraversable[Any], lineNumber: Int) {
      val leftText = FailureMessages.decorateToStringValue(left)
      e.message should be (Some(FailureMessages("containedOneElementOf", left, right)))
      e.failedCodeFileName should be (Some("OneElementOfContainMatcherSpec.scala"))
      e.failedCodeLineNumber should be (Some(lineNumber))
    }

    def `should succeed when left List contains different elements as right List` {
      List(1, 2, 3) should not contain oneElementOf (Seq(7, 8, 9))
      Array(1, 2, 3) should not contain oneElementOf (Seq(7, 8, 9))
      javaList(1, 2, 3) should not contain oneElementOf (Seq(7, 8, 9))

      Set(1, 2, 3) should not contain oneElementOf (Seq(7, 8, 9))
      javaSet(1, 2, 3) should not contain oneElementOf (Seq(7, 8, 9))

      Map(1 -> "one", 2 -> "two", 3 -> "three") should not contain oneElementOf (Seq(7 -> "seven", 8 -> "eight", 9 -> "nine"))
      javaMap(Entry(1, "one"), Entry(2, "two"), Entry(3, "three")) should not contain oneElementOf (Seq(Entry(7, "seven"), Entry(8, "eight"), Entry(9, "nine")))
    }

    def `should throw TestFailedException with correct stack depth and message when left and right List contain at least one same element` {
      val left1 = List(1, 2, 3)
      val e1 = intercept[exceptions.TestFailedException] {
        left1 should not contain oneElementOf (Seq(5, 1, 7))
      }
      checkStackDepth(e1, left1, Seq(5, 1, 7), thisLineNumber - 2)

      val left2 = javaList(1, 2, 3)
      val e2 = intercept[exceptions.TestFailedException] {
        left2 should not contain oneElementOf (Seq(5, 1, 7))
      }
      checkStackDepth(e2, left2, Seq(5, 1, 7), thisLineNumber - 2)

      val left3 = Map(1 -> "one", 2 -> "two", 3 -> "three")
      val e3 = intercept[exceptions.TestFailedException] {
        left3 should not contain oneElementOf (Seq(5 -> "five", 1 -> "one", 7 -> "seven"))
      }
      checkStackDepth(e3, left3, Seq(5 -> "five", 1 -> "one", 7 -> "seven"), thisLineNumber - 2)

      val left4 = javaMap(Entry(1, "one"), Entry(2, "two"), Entry(3, "three"))
      val e4 = intercept[exceptions.TestFailedException] {
        left4 should not contain oneElementOf (Seq(Entry(5, "five"), Entry(1, "one"), Entry(7, "seven")))
      }
      checkStackDepth(e4, left4, Seq(Entry(5, "five"), Entry(1, "one"), Entry(7, "seven")), thisLineNumber - 2)

      val left5 = Array(1, 2, 3)
      val e5 = intercept[exceptions.TestFailedException] {
        left5 should not contain oneElementOf (Seq(5, 1, 7))
      }
      checkStackDepth(e5, left5, Seq(5, 1, 7), thisLineNumber - 2)
    }

    def `should succeeded when right List contains all elements in left List in different order` {
      List(1, 2, 3) should not contain oneElementOf (Seq(1, 3, 2))
      Array(1, 2, 3) should not contain oneElementOf (Seq(1, 3, 2))
      javaList(1, 2, 3) should not contain oneElementOf (Seq(1, 3, 2))

      Set(1, 2, 3) should not contain oneElementOf (Seq(1, 3, 2))
      javaSet(1, 2, 3) should not contain oneElementOf (Seq(1, 3, 2))

      Map(1 -> "one", 2 -> "two", 3 -> "three") should not contain oneElementOf (Seq(1 -> "one", 3 -> "three", 2 -> "twp"))
      javaMap(Entry(1, "one"), Entry(2, "two"), Entry(3, "three")) should not contain oneElementOf (Seq(Entry(1, "one"), Entry(3, "three"), Entry(2, "twp")))
    }

    def `should succeeded when right List contains all elements in left List in same order` {
      List(1, 2, 3) should not contain oneElementOf (Seq(1, 2, 3))
      Array(1, 2, 3) should not contain oneElementOf (Seq(1, 2, 3))
      javaList(1, 2, 3) should not contain oneElementOf (Seq(1, 2, 3))

      Set(1, 2, 3) should not contain oneElementOf (Seq(1, 2, 3))
      javaSet(1, 2, 3) should not contain oneElementOf (Seq(1, 2, 3))

      Map(1 -> "one", 2 -> "two", 3 -> "three") should not contain oneElementOf (Seq(1 -> "one", 2 -> "two", 3 -> "three"))
      javaMap(Entry(1, "one"), Entry(2, "two"), Entry(3, "three")) should not contain oneElementOf (Seq(Entry(1, "one"), Entry(2, "two"), Entry(3, "three")))
    }

    def `should succeeded when right List contains more than one element in right List` {
      List(1, 2, 3) should not contain oneElementOf (Seq(5, 3, 2))
      Array(1, 2, 3) should not contain oneElementOf (Seq(5, 3, 2))
      javaList(1, 2, 3) should not contain oneElementOf (Seq(5, 3, 2))

      Set(1, 2, 3) should not contain oneElementOf (Seq(5, 3, 2))
      javaSet(1, 2, 3) should not contain oneElementOf (Seq(5, 3, 2))

      Map(1 -> "one", 2 -> "two", 3 -> "three") should not contain oneElementOf (Seq(5 -> "five", 3 -> "three", 2 -> "two"))
      javaMap(Entry(1, "one"), Entry(2, "two"), Entry(3, "three")) should not contain oneElementOf (Seq(Entry(5, "five"), Entry(3, "three"), Entry(2, "two")))
    }
  }
}