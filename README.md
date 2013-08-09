Discriminated Unions
====================
Discriminated unions provide support for values that can be one of a number of named cases, possibly each with different values and types. UsesDiscriminated unions are useful for heterogeneous data: 

* Data that can have special cases, including valid and error cases
* Data that varies in type from one instance to another
* As an alternative for small object hierarchies. 

See: [MSDN][DU-msdn],[Wikipedia][DU-wikipedia].


#A Groovy Discriminated Union
The following sections discuss the an implmentation of discriminated unions in `Groovy` including various design decisions, examples, a usage guide and areas for future work.

This Groovy DU implementation defines a method `match` (the core draws heavily from the [graphology][graphology] 
###Option Type

### Some Option
The `Some` case of the Option type has a value whose type is represented by the type of the value held by the option.

    def option = Option.Some( 100 )
    def value = option.match {
        when Some then { it }
        when None then null
        otherwise null
    }
    assert 100 == value

### None Option
The `None` case has no associated value. Note that the result of the matched statement is returned by `match{}`

    def option = Option.None()
    def value = option.match {
        when Some then 100
        when None then null
        otherwise 100
    }
    assert null == value

###Return Value


##Trees
    
    assert new Tree(100).match {
        when Tip then false
        when Tree then true
        otherwise false
    }

    assert new Tip().match {
        when Tip then true
        when Tree then false
        otherwise false
    }


[graphology]: https://github.com/will-lp/graphology-case-match
[DU-msdn]:http://msdn.microsoft.com/en-us/library/dd233226.aspx
[DU-wikipedia]:https://en.wikipedia.org/wiki/Tagged_union


