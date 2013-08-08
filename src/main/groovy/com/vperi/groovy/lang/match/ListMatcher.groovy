package com.vperi.groovy.lang.match

import com.vperi.groovy.lang.Cons
import com.vperi.groovy.lang.Nil
import com.vperi.groovy.lang.Variable
import com.vperi.groovy.lang._

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
        when: { _x ->
          if ( !( self instanceof List && _x instanceof List ) ) return false
          def s = self as List
          def x = _x as List
          if ( s.size() != x.size() ) return false
          for ( int i = 0; i < s.size(); i++ ) {
            def xi = x.get( i )
            def si = self.get( i )

            if ( xi instanceof Variable ) {
              xi.value = si
              continue
            }

            if ( xi == _ ) continue

            if ( !xi.equals( si ) ) return false
          }
          true
        },
        then: { r -> r instanceof Closure ? r() : r }
    ] )

  }
}