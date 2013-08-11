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

package com.vperi.groovy.lang.tree

import com.vperi.groovy.lang.DiscriminatedUnion

@SuppressWarnings("GroovyUnusedDeclaration")
class Tree extends DiscriminatedUnion {
  /**
   * Types held by this DU
   */
  List types = [ Tip, Tree ]

  int val
  Tree leftChild
  Tree rightChild

  protected Tree( ) {
    this( 0, null, null )
  }

  /**
   *  Instantiates a new Tree with a node value
   *
   * @param v the v
   */
  Tree( int v ) {
    this( v, new Tip(), new Tip() )
  }

  /**
   *  Instantiates a new Tree with a node value and a left child
   *
   * @param v the v
   * @param l the l
   */
  Tree( int v, Tree l ) {
    this( v, l, new Tip() )
  }

  /**
   *  Instantiates a new Tree with a node value, and both children
   *
   * @param v the v
   * @param l the l
   * @param r the r
   */
  Tree( int v, Tree l, Tree r ) {
    val = v
    leftChild = l
    rightChild = r
    useSelfAsValue = true
  }

  String toString( ) {
    "Tree[$val, $leftChild, $rightChild]"
  }
}
