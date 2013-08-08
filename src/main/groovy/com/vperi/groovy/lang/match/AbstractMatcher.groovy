package com.vperi.groovy.lang.match

import com.vperi.groovy.lang.Variable
import org.apache.log4j.Logger

/**
 * Copyright (c) 2012-13 by Author(s). All Right Reserved.
 * Author(s): venkat
 */
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