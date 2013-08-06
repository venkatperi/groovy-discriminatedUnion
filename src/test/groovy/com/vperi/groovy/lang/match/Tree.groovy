package com.vperi.groovy.lang.match

import com.vperi.groovy.lang.DiscriminatedUnion


/**
 * Copyright (c) 2012-13 by Author(s). All Right Reserved.
 * Author(s): venkat
 */
@SuppressWarnings("GroovyUnusedDeclaration")
class Tree extends DiscriminatedUnion {
  List types = [ Tip, Tree ]

  int val
  Tree leftChild
  Tree rightChild

  protected Tree( ) {
    this( 0, null, null )
  }

  Tree( int v ) {
    this( v, new Tip(), new Tip() )
  }

  Tree( int v, Tree l ) {
    this( v, l, new Tip() )
  }

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
