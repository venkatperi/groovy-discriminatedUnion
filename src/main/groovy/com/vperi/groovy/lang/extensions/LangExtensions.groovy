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

package com.vperi.groovy.lang.extensions

import com.vperi.groovy.lang.match.ListMatcher
import com.vperi.groovy.lang.match.Resolver

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