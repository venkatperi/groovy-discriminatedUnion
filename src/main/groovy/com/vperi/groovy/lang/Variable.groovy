package com.vperi.groovy.lang

import org.apache.log4j.Logger

/**
 * Copyright (c) 2012-13 by Author(s). All Right Reserved.
 * Author(s): venkat
 */
@SuppressWarnings("GroovyUnusedDeclaration")
class Variable {
  static def LOG = Logger.getLogger( Variable )
  String name
  private Object _value
  boolean valueSet = false

  def getValue( ) { _value }

  void setValue( Object val ) {
    valueSet = true
    _value = val
  }

  String toString( ) {
    valueSet ? "$name($_value)" : "$name()"
  }

  boolean equals( Object other ) {
    value = other
    true
  }
}
