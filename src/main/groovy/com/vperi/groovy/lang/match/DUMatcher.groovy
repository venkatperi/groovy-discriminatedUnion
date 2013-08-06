package com.vperi.groovy.lang.match

import com.vperi.groovy.lang.DiscriminatedUnion


/**
 * Copyright (c) 2012-13 by Author(s). All Right Reserved.
 * Author(s): venkat
 */
@SuppressWarnings("GroovyUnusedDeclaration")
class DUMatcher extends AbstractMatcher {

  DUMatcher( ) {
    matchers.add( [
        when: { x ->
          def clazz = self?.value?.class
          ( self instanceof DiscriminatedUnion ) &&
              ( x instanceof Class ) &&
              ( clazz == x ||
                  self.searchClassHierarchy && x.isAssignableFrom( clazz ) )
        },
        then: { result ->
          result instanceof Closure ? result( ( self as DiscriminatedUnion ).value ) : result
        }
    ] )
  }
}