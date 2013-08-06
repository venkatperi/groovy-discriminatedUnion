package com.vperi.groovy.lang

import com.vperi.groovy.lang.match.OptionMatcher


/**
 * Copyright (c) 2012-13 by Author(s). All Right Reserved.
 * Author(s): venkat
 */
@SuppressWarnings("GroovyUnusedDeclaration")
class Option extends DiscriminatedUnion {
  List types = [ Some, None ]

  /**
   *  Create a Some option
   *
   * @param val the val
   * @return the option
   */
  static Option Some( val ) {
    new Option( val )
  }

  /**
   *  Create a "None" option.
   *
   * @return the option
   */
  static Option None( ) {
    new Option()
  }

  /**
   *  Instantiates a new Option.
   *
   * @param val the val
   */
  protected Option( val ) {
    types.add val.class
    value = val
    readOnly = true
  }

  /**
   *  Instantiates a new Option.
   */
  protected Option( ) {
    readOnly = true
  }

  protected def createMatcher( ) {
    new OptionMatcher( self: this )
  }
}
