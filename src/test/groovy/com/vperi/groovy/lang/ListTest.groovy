package com.vperi.groovy.lang
/**
 * Copyright (c) 2012-13 by Author(s). All Right Reserved.
 * Author(s): venkat
 */
class ListTest extends GroovyTestCase {

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

  void testMatchPattern( ) {
    assert [ true, false ].match {
      when( [ false, false ] ) then false
      when( [ false, true ] ) then false
      when( [ true, false ] ) then true
      when( [ true, true ] ) then false
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
