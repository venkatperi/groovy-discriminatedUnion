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

import com.vperi.groovy.lang.tree.Tip
import com.vperi.groovy.lang.tree.Tree

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
