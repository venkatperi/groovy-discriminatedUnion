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

import com.vperi.groovy.lang.Variable
import org.apache.log4j.Logger

@SuppressWarnings("GroovyUnusedDeclaration")
abstract class AbstractMatcher extends Resolver implements Matcher {
  static LOG = Logger.getLogger( AbstractMatcher )
  Map<String, Variable> variables = [ : ]
  boolean matched = false

  @SuppressWarnings("SpellCheckingInspection")
  def matchers = [
      [ when: { x -> x instanceof Closure && x.call( self ) },
          then: { result -> result instanceof Closure ? result.call( self ) : result } ],
  ]

  /**
   *  Sets result.
   *
   * @param val the val
   */
  void setResult( val ) {
    _result = val
  }

  def getResult( ) {
    if ( matched ) {
      return super._result
    } else {
      def other = otherwiseValue
      return ( other instanceof Closure ) ? ( (Closure) other )() : other
    }
  }

  void when( Object condition, Object result ) {
    if ( !matched ) {
      matched = matchers.any {
        def ret = false
        if ( it[ 'when' ]( condition ) ) {
          this.result = it[ 'then' ]( result )
          ret = true
        }
        ret
      }
    }
  }

  def propertyMissing( String name ) {
    if ( !doneWithWhen ) {
      if ( !variables.containsKey( name ) ) {
        variables[ name ] = new Variable( name: name )
      }
      return variables[ name ]
    }

    variables[ name ]?.value
  }
}