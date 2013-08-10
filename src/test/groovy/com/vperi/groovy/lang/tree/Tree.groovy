package com.vperi.groovy.lang.tree

import com.vperi.groovy.lang.DiscriminatedUnion

/**
 * A simple binary tree node that holds a value (val) and can have two children
 *
 * Copyright (c) 2012-13 by Author(s). All Right Reserved.
 * Author(s): venkat
 */
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
