package com.vperi.groovy.lang.match

/**
 * Copyright (c) 2012-13 by Author(s). All Right Reserved.
 * Author(s): venkat
 */
@SuppressWarnings("GroovyUnusedDeclaration")
interface Matcher {

  /**
   * Provides a matching of a condition against an object and a
   * result, if the object matches.
   *
   * @param condition : the condition to which <code>self</code> object
   * will be matched against
   * @param result : the result to be executed if the condition is matched
   */
  void when( Object condition, Object result )

  /**
   * Provides a statically compilable DSL-like way to match an object in
   * the form:
   * <code>when condition then result</code>
   * @param condition : the condition to which <code>self</code> object
   * will be matched against
   * @return a Then object, which has a <code>then</code> method
   */
  Then when( Object condition )

  /**
   * When no option matches the caller object, this result
   * will be used
   *
   * @param
   */
  void otherwise( Object o )

  /**
   *  returns true if the matcher succeeded
   *
   * @return the boolean
   */
  boolean getMatched( );
}