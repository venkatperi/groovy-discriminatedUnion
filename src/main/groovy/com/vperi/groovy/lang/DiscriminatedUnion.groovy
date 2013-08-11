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

package com.vperi.groovy.lang

import com.vperi.groovy.lang.match.DUMatcher
import com.vperi.groovy.lang.match.Resolver

@SuppressWarnings("GroovyUnusedDeclaration")
abstract class DiscriminatedUnion {
  boolean searchClassHierarchy = true
  protected boolean useSelfAsValue
  protected boolean explicitValueSet
  protected Object theValue
  protected boolean readOnly

  /**
   *  Get types.
   *
   * @return the list
   */
  abstract List<Class> getTypes( );

  /**
   *  Gets value.
   *
   * @return the value
   */
  Object getValue( ) {
    explicitValueSet ? theValue : useSelfAsValue && ensureTypeInUnion( this.class ) ? this : null
  }

  /**
   *  Gets has value.
   *
   * @return the has value
   */
  boolean getHasValue( ) {
    explicitValueSet || useSelfAsValue
  }

  /**
   *  Sets value.
   *
   * @param v the v
   */
  void setValue( Object v ) {
    assert !readOnly, "Cannot set readonly property 'value'"

    ensureTypeInUnion v.class
    theValue = v
    explicitValueSet = true
  }

  /**
   *  Match with closure
   *
   * @param closure the closure
   */
  def match( @DelegatesTo(value = Resolver, strategy = Closure.DELEGATE_FIRST) Closure closure ) {
    def matcher = createMatcher()
    closure.delegate = matcher
    closure( this )
    matcher.done()
  }

  /**
   *  Create matcher.
   */
  protected def createMatcher( ) {
    new DUMatcher( self: this )
  }

  /**
   *  Ensure type in union.
   *
   * @param clazz the clazz
   */
  protected def ensureTypeInUnion( Class clazz ) {

    if ( !types?.contains( clazz ) && searchClassHierarchy && !types.any { it.isAssignableFrom( clazz ) } ) {
      throw new RuntimeException( "${clazz.name} not part of this union ($types)" )
    }
    true
  }

  /**
   *  Test type is class.
   *
   * @param x the x
   */
  static def testTypeIsClass( Object x ) {
    assert x instanceof Class, "Object must be of Class type"
    true
  }
}
