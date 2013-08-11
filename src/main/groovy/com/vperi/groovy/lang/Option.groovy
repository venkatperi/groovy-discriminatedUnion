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

import com.vperi.groovy.lang.match.OptionMatcher


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
