package com.vperi.groovy.lang.match

import groovy.transform.CompileStatic

/**
 * Copyright (c) 2012-13 by Author(s). All Right Reserved.
 * Author(s): venkat
 */
@CompileStatic
class ThenImpl implements Then {
  Resolver resolver
  Object condition

  /**
   * This object MUST have a reference to the resolver object
   * to which it was asked to receive the input from the user,
   * to which it will pass again in a callback fashion.
   *
   * @param resolver : a resolver object
   * @param condition : the condition to which the <code>self</code>
   * object needs to match.
   */
  ThenImpl( Resolver resolver, Object condition ) {
    this.resolver = resolver
    this.condition = condition
  }

  void then( Object result ) {
    assert resolver, "No Resolver was passed to this object"
    resolver.doneWithThen()
    resolver.when condition, result
  }
}