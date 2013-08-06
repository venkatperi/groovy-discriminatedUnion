package com.vperi.groovy.lang.match

/**
 * Copyright (c) 2012-13 by Author(s). All Right Reserved.
 * Author(s): venkat
 */
@SuppressWarnings("GroovyUnusedDeclaration")
interface Then {

  /**
   * Provides a <code>then</code> method to enable the following syntax:
   * <code>when condition then result</code>
   *
   * @param result : the resulting object, if the match succeeds
   */
  void then( Object result )
}
