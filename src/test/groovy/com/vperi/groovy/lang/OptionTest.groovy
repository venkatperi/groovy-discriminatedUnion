package com.vperi.groovy.lang
/**
 * Copyright (c) 2012-13 by Author(s). All Right Reserved.
 * Author(s): venkat
 */
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
