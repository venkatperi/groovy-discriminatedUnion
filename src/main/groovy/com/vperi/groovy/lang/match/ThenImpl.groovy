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

import groovy.transform.CompileStatic

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