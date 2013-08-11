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

package com.vperi.groovy.lang.match

import com.vperi.groovy.lang.Cons
import com.vperi.groovy.lang.Nil
import com.vperi.groovy.lang.Variable
import com.vperi.groovy.lang._

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