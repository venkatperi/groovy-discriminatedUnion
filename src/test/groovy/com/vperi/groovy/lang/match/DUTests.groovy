package com.vperi.groovy.lang.match

import com.vperi.groovy.lang.*

/**
 * Copyright (c) 2012-13 by Author(s). All Right Reserved.
 * Author(s): venkat
 */
class DUTests extends GroovyTestCase {

  void tearDown( ) {
    System.out.flush()
  }

  void testSome( ) {

    assert Option.Some( 100 ).match {
      when Some then true
      when None then false
      otherwise false
    }
  }

  void testList1( ) {
    assert [ 1, 2, 3 ].match {
      when Cons then { h, t -> h == 1 && t == [ 2, 3 ] }
      when Nil then false
      otherwise false
    }
  }

  void testList2( ) {

    assert [ true, false ].match {
      when( [ false, false ] ) then false
      when( [ false, true ] ) then false
      when( [ true, false ] ) then true
      when( [ true, true ] ) then false
      otherwise false
    }

  }

  void testList3( ) {

    [ [ 0, 1 ], [ 0, 1 ] ].combinations().eachWithIndex { List c, idx ->
      assert c.reverse().match {
        when( [ 0, 0 ] ) then { idx == 0 }
        when( [ 0, 1 ] ) then { idx == 1 }
        when( [ 1, 0 ] ) then { idx == 2 }
        when( [ 1, 1 ] ) then { idx == 3 }
        otherwise false
      }
    }
  }

  void testTree( ) {

    def tree = new Tree( 100 )

    assert tree.match {
      when Tip then false
      when Tree then true
      otherwise false
    }

    assert new Tip().match {
      when Tip then true
      when Tree then false
      otherwise false
    }
  }

  void testTreeWalk( ) {
    def tree = new Tree( 0, new Tree( 1, new Tree( 2 ) ), new Tree( 3, new Tree( 4 ) ) )

    assertEquals 10, sumTree( tree )
  }

  static def sumTree( Tree t ) {
    t?.match {
      when Tip then 0
      when Tree then { Tree n -> n.val + sumTree( n.leftChild ) + sumTree( n.rightChild ) }
    }
  }
}
