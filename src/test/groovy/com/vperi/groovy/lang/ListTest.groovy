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

import org.apache.log4j.BasicConfigurator

class ListTest extends GroovyTestCase {

  void setUp( ) {
    BasicConfigurator.configure()
  }


  void tearDown( ) {
    System.out.flush()
  }

  void testHeadTail( ) {
    assert [ 1, 2, 3 ].match {
      when Cons then { h, t -> h == 1 && t == [ 2, 3 ] }
      when Nil then false
      otherwise false
    }
  }

  def sumList( List l ) {
    l.match {
      when Cons then { h, List t -> h + sumList( t ) }
      when Nil then 0
    }
  }

  void testListTailRecursion( ) {
    assertEquals 15, sumList( [ 1, 2, 3, 4, 5 ] )
  }

  void testMatchPattern( ) {
    assert [ true, false ].match {
      when( [ false, false ] ) then false
      when( [ false, true ] ) then false
      when( [ true, false ] ) then true
      when( [ true, true ] ) then false
      otherwise false
    }
  }


  void testVariable( ) {
    assertEquals 1, [ 1, 2, 3, "a" ].match {
      when( [ x, 2, 3, _ ] ) then { x }
      otherwise 100
    }
  }

  void testWildcard( ) {

    assert [ 1, 2, 3, "a" ].match {
      when( [ 1, _, 3, _ ] ) then true
      otherwise false
    }
    assert [ 1, 2, 3, "a" ].match {
      when( [ _, _, 3, _ ] ) then true
      otherwise false
    }
    assert [ 1, 2, 3, "a" ].match {
      when( [ _, _, _, _ ] ) then true
      otherwise false
    }
  }

  void testMatchPattern2( ) {
    [ [ 0, 1 ], [ 0, 1 ] ].combinations().eachWithIndex { List c, idx ->
      // reverse output of combinations to match up with generated indices
      assert c.reverse().match {
        when( [ 0, 0 ] ) then { idx == 0 }
        when( [ 0, 1 ] ) then { idx == 1 }
        when( [ 1, 0 ] ) then { idx == 2 }
        when( [ 1, 1 ] ) then { idx == 3 }
        otherwise false
      }
    }
  }
}
