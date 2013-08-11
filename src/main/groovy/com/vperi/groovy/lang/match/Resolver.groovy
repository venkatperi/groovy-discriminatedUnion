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
import org.codehaus.groovy.runtime.NullObject

@SuppressWarnings("GroovyUnusedDeclaration")
@CompileStatic
abstract class Resolver {

  /*
   * The object which a closure is being matched against, i.e:
   * object.case {}
   *   ^ this guy
   */
  def self

  /*
   * The "otherwise" value which will be used when no match was found
   */
  Object otherwiseValue

  /*
   * The result of the process
   */
  def _result

  /**
   * Express a condition. It tries to matches a value against the
   * condition using Groovy's switch statement
   * @param condition an object to be tested against 'self'
   * @param result a closure to be executed
   */
  protected boolean matches( Object condition ) {
    boolean matched = false

    switch ( self ) {case condition: matched = true}

    if ( self instanceof NullObject && condition == null ) { // Weird. Bug?
      matched = true
    }

    matched
  }

  /*
   * Sets the 'otherwise' condition
   */
  void otherwise( Object c ) {
    this.otherwiseValue = c
  }

  /*
   * Called by the <code>Then</code> object when the user
   */
  abstract void when( Object condition, Object result )

  /*
   * Checks whether we are waiting for a <code>then</code> input
   * from the user
   */
  boolean waitingForThen = false
  boolean doneWithWhen = false

  /*
   * Will be called by the <code>Then</code> object when the
   * user passes a result
   */
  def doneWithThen( ) {
    waitingForThen = false
  }

  /*
   * For a DSL-ish case/when. Returns a <code>Then</code> object
   * where the user can call a <code>then(result)</code> method.
   * Provides the following syntax:
   * <code>when String then new Date()</code>
   * <code>when {it < 90} then { throw new RuntimeException() }</code>
   */
  Then when( Object condition ) {
    doneWithWhen = true
    waitingForThen = true
    new ThenImpl( this, condition )
  }

  /**
   * Needs to be manually called when this object finishes matching
   * the user input.
   * If any state is invalid, this method will throw an exception
   */
  def done( ) {
    if ( waitingForThen ) {
      throw new IllegalStateException(
          "You didn't provided a then() result for the when() method" )
    }
    result
  }

  /**
   * Returns the <code>otherwiseValue</code>. If it is a closure, it
   * will be curried with the <code>self</code> object
   */
  Object getOtherwiseValue( ) {
    if ( otherwiseValue instanceof Closure ) {
      return ( (Closure) otherwiseValue ).curry( self )
    } else {
      return otherwiseValue
    }
  }

  abstract def getResult( )
}
