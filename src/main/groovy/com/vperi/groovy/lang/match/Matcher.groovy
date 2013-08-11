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

@SuppressWarnings("GroovyUnusedDeclaration")
interface Matcher {

  /**
   * Provides a matching of a condition against an object and a
   * result, if the object matches.
   *
   * @param condition : the condition to which <code>self</code> object
   * will be matched against
   * @param result : the result to be executed if the condition is matched
   */
  void when( Object condition, Object result )

  /**
   * Provides a statically compilable DSL-like way to match an object in
   * the form:
   * <code>when condition then result</code>
   * @param condition : the condition to which <code>self</code> object
   * will be matched against
   * @return a Then object, which has a <code>then</code> method
   */
  Then when( Object condition )

  /**
   * When no option matches the caller object, this result
   * will be used
   *
   * @param
   */
  void otherwise( Object o )

  /**
   *  returns true if the matcher succeeded
   *
   * @return the boolean
   */
  boolean getMatched( );
}