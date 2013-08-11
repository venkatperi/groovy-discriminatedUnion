Discriminated Unions
====================
Discriminated unions provide support for values that can be one of a number of named cases, possibly each with different values and types. UsesDiscriminated unions are useful for heterogeneous data: 

* Data that can have special cases, including valid and error cases
* Data that varies in type from one instance to another
* As an alternative for small object hierarchies. 

See: [MSDN][DU-msdn],[Wikipedia][DU-wikipedia]. 


##A Groovy Discriminated Union
### `Option` Type
Let's say we want a variable `option` to hold some value (`Some`) or no value (`None`). Let's start with `Some`:

```groovy
def option = Option.Some( 100 )
```
We can test for `Some` vs. `None` like this:

```groovy
option.match {
  when Some then { println "We have 'some' value" }
  when None then { println "It's None!" }
}
```

We can get the value of an `Option` like this:

```groovy
def value = option.match {
  when Some then { it }
  otherwise null
}
assert 100 == value
```

where `otherwise` is a *catch-all* matcher. When an Option is matched with Some, the value held by Some is injected into the `then` closure (if a closure is provided).

It follows that:

```groovy
assert Option.None().match{
  when Some then false
  when None then true
  otherwise false
}
```

##About the `match` method
* A `match` method is defined for `DiscriminatedUnion` and is available to its descendants (e.g. Option). 
* We also inject a `match` method into `List` for list based pattern matching.
* `match` matches one and only one `when` pattern or `othewise` if no matching `when` was found.
* `match` returns the result of the matched pattern, i.e. the argument to `then`. If a closure is supplied, arguments are injected into the closure depending on the matched pattern, the closure is evaluated and its result returned. In the case of a non-closure argument, the argument is returned.  *Note* that any non-closure expression in the entire `match` block is evaluated even if it is part of a pattern that has not matched. When in doubt, enclose `then` arguments in a closure, or expect interesting side-effects.
* `match` delegates the actual matching to handlers based primarily on its context (e.g. Option vs List). So in the case of `Option.match`, the underlying matcher matches `Class`. Other matchers match against value, vector size, etc. A special case is if a closure is supplied as the match pattern, e.g.:
 
```groovy
something.match {
  when { it == 'abc' } then { ... }
}
```
In this case, the closure is evaluated with a single argument such that `it == something`

##List Patterns and `match`
### `Cons` and `Nil`
The `Cons` pattern matches lists and provides `then` with the head element and tail list. Likewise, `Nil` matches against an empty list.

```groovy
assert [ 1, 2, 3 ].match {
  when Cons then { h, t -> h == 1 && t == [ 2, 3 ] }
  when Nil then false
  otherwise false
}
```
As in F#, this pattern lends itself to tail recursion:

```groovy
def sumList( List l ) {
  l.match {
    when Cons then { h, List t -> h + sumList( t ) }
    when Nil then 0
  }
}

assert 15 == sumList( [ 1, 2, 3, 4, 5 ] )
```

###List Equality
List equality is straightforward:

```groovy
assert [ true, false ].match {
  when( [ false, false ] ) then false
  when( [ false, true ] ) then false
  when( [ true, false ] ) then true
  when( [ true, true ] ) then false
  otherwise false
}
```

Obviously the contents of the list isn't limited to booleans as in the example above. We note that truth-table style matching is a compact alternative to if-then-else/switch statements. 

###Matching with wildcards
When matching lists, we can replace a list element with the wildcard operator `_` (underscore) to imply that we'll accept anything at that position in the list.

```groovy
assert [ 1, 2, 3, "a" ].match {
  when( [ 1, 2, 3, 4 ] ) then false
  when( [ 1, 2, 3, _ ] ) then true
  otherwise false
}
```

###Matching with Variables
We can replace the wildcard operator with a variable identifier (underfined, standard scoping rules apply) to imply that we'll matching anything, but want to capture the matched value.

```groovy
assertEquals 1, [ 1, 2, 3, "a" ].match {
  when( [ x, 2, 3, _ ] ) then { x }
  otherwise 100
}
```


##Custom DU Types - Trees
We can define custom DU types, e.g. a Tree node with a value and two child nodes each of which can be a Tree node or a Tip (terminal node, no value) like this (see sources - tests):

```groovy
class Tree extends DiscriminatedUnion {
  /**
   * Types held by this DU
   */
  List types = [ Tip, Tree ]

  int val
  Tree leftChild
  Tree rightChild

  protected Tree( ) {
    this( 0, null, null )
  }
  
  Tree( int v ) {
    this( v, new Tip(), new Tip() )
  }

  Tree( int v, Tree l ) {
    this( v, l, new Tip() )
  }

  Tree( int v, Tree l, Tree r ) {
    val = v
    leftChild = l
    rightChild = r
    useSelfAsValue = true
  }

  String toString( ) {
    "Tree[$val, $leftChild, $rightChild]"
  }
}

```

and a Tip node:

```groovy
class Tip extends Tree {
  Tip( ) {}

  String toString( ) {
    "Tip"
  }
}
```

We can do a recursive walk on the tree:

```groovy
def sumTree( Tree t ) {
  t?.match {
    when Tip then 0
    when Tree then { Tree n -> n.val + sumTree( n.leftChild ) + sumTree( n.rightChild ) }
  }
}

def tree = new Tree( 0, new Tree( 1, new Tree( 2 ) ), new Tree( 3, new Tree( 4 ) ) )
assertEquals 10, sumTree( tree )
```


##Credits
The core matcher functionality comes from [graphology][graphology].

[graphology]: https://github.com/will-lp/graphology-case-match
[DU-msdn]:http://msdn.microsoft.com/en-us/library/dd233226.aspx
[DU-wikipedia]:https://en.wikipedia.org/wiki/Tagged_union


