package com.vperi.groovy.lang

import com.vperi.groovy.lang.tree.Tip
import com.vperi.groovy.lang.tree.Tree

/**
 * Copyright (c) 2012-13 by Author(s). All Right Reserved.
 * Author(s): venkat
 */
/**
 *  The type Tree test.
 */
class TreeTest extends GroovyTestCase {

  void tearDown( ) {
    System.out.flush()
  }

  /**
   *  Test node.
   */
  void testNode( ) {
    assert new Tree( 0 ).match {
      when Tip then false
      when Tree then true
      otherwise false
    }
  }

  /**
   *  Test tip.
   */
  void testTip( ) {
    assert new Tip().match {
      when Tip then true
      when Tree then false
      otherwise false
    }
  }

  /**
   *  return node value
   */
  void testNodeValue( ) {
    assert 100, new Tree( 100 ).match {
      when Tip then null
      when Tree then { it }
      otherwise null
    }
  }

  /**
   *  Walk the tree
   */
  void testTreeWalk( ) {
    def tree = new Tree( 0, new Tree( 1, new Tree( 2 ) ), new Tree( 3, new Tree( 4 ) ) )
    assertEquals 10, sumTree( tree )
  }

  /**
   *  Sum tree recursively
   *
   * @param t the t
   */
  static def sumTree( Tree t ) {
    t?.match {
      when Tip then 0
      when Tree then { Tree n -> n.val + sumTree( n.leftChild ) + sumTree( n.rightChild ) }
    }
  }
}
