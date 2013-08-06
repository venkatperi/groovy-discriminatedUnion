package com.vperi.groovy.lang.match

import com.vperi.groovy.lang.None
import com.vperi.groovy.lang.Option
import com.vperi.groovy.lang.Some

/**
 * Copyright (c) 2012-13 by Author(s). All Right Reserved.
 * Author(s): venkat
 */
@SuppressWarnings("GroovyUnusedDeclaration")
class OptionMatcher extends AbstractMatcher {

  OptionMatcher( ) {
    matchers.add( [
        when: { x ->
          ( self instanceof Option ) &&
              ( ( x == Some && self.hasValue ) ||
                  ( x == None && !self.hasValue ) )
        },
        then: { result ->
          result instanceof Closure ? result( ( self as Option ).value ) : result
        }
    ] )

    }
}