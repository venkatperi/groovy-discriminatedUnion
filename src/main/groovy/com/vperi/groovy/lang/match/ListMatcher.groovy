package com.vperi.groovy.lang.match

import com.vperi.groovy.lang.Cons
import com.vperi.groovy.lang.Nil

/**
 * Copyright (c) 2012-13 by Author(s). All Right Reserved.
 * Author(s): venkat
 */
@SuppressWarnings("GroovyUnusedDeclaration")
class ListMatcher extends AbstractMatcher {

  /**
   *  Instantiates a new List matcher.
   */
  ListMatcher( ) {
    matchers.add( [
        when: { x ->
          self instanceof List &&
              ( ( x == Cons && !self.empty ) || ( x == Nil && self.empty ) )
        },
        then: { r -> r instanceof Closure ? ( self.empty ? r() : r( self.head(), self.tail() ) ) : r }
    ] )

    matchers.add( [
        when: { x ->
          self instanceof List && x instanceof List && self.equals( x )
        },
        then: { r -> r instanceof Closure ? r() : r }
    ] )

  }
}