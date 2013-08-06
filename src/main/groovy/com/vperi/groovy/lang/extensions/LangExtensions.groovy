package com.vperi.groovy.lang.extensions

import com.vperi.groovy.lang.match.ListMatcher
import com.vperi.groovy.lang.match.OptionMatcher
import com.vperi.groovy.lang.match.Resolver

/**
 * Copyright (c) 2012-13 by Author(s). All Right Reserved.
 * Author(s): venkat
 */
@SuppressWarnings("GroovyUnusedDeclaration")
class LangExtensions {

  public static Object match( List self, @DelegatesTo(value = Resolver, strategy = Closure.DELEGATE_FIRST) Closure
  matchClosure ) {
    def matcher = new ListMatcher( self: self );
    matchClosure.delegate = matcher
    matchClosure( self )
    matcher.done()
  }


}