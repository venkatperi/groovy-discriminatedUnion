package com.vperi.groovy.lang.match
/**
 * Copyright (c) 2012-13 by Author(s). All Right Reserved.
 * Author(s): venkat
 */
@SuppressWarnings("GroovyUnusedDeclaration")
abstract class AbstractMatcher extends Resolver implements Matcher {

  boolean matched = false

  @SuppressWarnings("SpellCheckingInspection")
  def matchers = [
      [ when: { x -> x instanceof Closure && x.call( self ) },
          then: { result -> result instanceof Closure ? result.call( self ) : result } ],
//      [ when: { x -> x instanceof Class && x.equals( self.class ) },
//          then: { result -> result instanceof Closure ? result.call( self ) : result } ]
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
}