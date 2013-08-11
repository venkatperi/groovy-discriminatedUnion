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

import com.vperi.groovy.lang.DiscriminatedUnion


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