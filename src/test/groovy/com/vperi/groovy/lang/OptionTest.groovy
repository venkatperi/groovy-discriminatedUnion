/*
 * Copyright (c) 2013 Venkat Peri
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.vperi.groovy.lang

class OptionTest extends GroovyTestCase {

  void tearDown( ) {
    System.out.flush()
  }

  /**
   *  Test some.
   */
  void testSome( ) {
    assert Option.Some( 100 ).match {
      when Some then true
      when None then null
      otherwise null
    }, "Expected Some"
  }

  /**
   *  Test none.
   */
  void testNone( ) {
    assert Option.None().match {
      when Some then null
      when None then true
      otherwise null
    }, "Expected None"
  }

  /**
   *  Return value held by some
   *
   */
  void testValue( ) {
    assertEquals 100, Option.Some( 100 ).match {
      when Some then { it }
      when None then null
      otherwise null
    }
  }

  /**
   *  No condition matches -> return value is otherwise
   */
  void testOtherwise( ) {
    assertEquals 200, Option.Some( 100 ).match {
      when None then null
      otherwise 200
    }
  }
}
