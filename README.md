Discriminated Unions
====================
Discriminated unions provide support for values that can be one of a number of named cases, possibly each with different values and types. UsesDiscriminated unions are useful for heterogeneous data: 

* Data that can have special cases, including valid and error cases
* Data that varies in type from one instance to another
* As an alternative for small object hierarchies. 

See: [MSDN][DU-msdn],[Wikipedia][DU-wikipedia].

##F# Discriminated Union Primer
(from [MSDN][DU-msdn]) Discriminated unions are similar to union types in other languages, but there are differences. As with a union type in C++ or a variant type in Visual Basic, the data stored in the value is not fixed; it can be one of several distinct options. Unlike unions in these other languages, however, each of the possible options is given a case identifier. The case identifiers are names for the various possible types of values that objects of this type could be; the values are optional. If values are not present, the case is equivalent to an enumeration case. If values are present, each value can either be a single value of a specified type, or a tuple that aggregates multiple values of the same or different types.

### Option Type
The option type is a simple discriminated union in `F#` and is declared as:

`F#`

    type Option<'a> 
        | Some of 'a
        | None

The case identifiers (`Some` and `None`) can be used as constructors for the discriminated union type. For example, the following code is used to create values of the `Option` type.
    `F#`
    
    let myOption1 = Some(10.0)
    let myOption2 = Some("string")
    let myOption3 = None

The case identifiers are also used in pattern matching expressions. In a pattern matching expression, identifiers are provided for the values associated with the individual cases. For example, in the following code, x is the identifier given the value that is associated with the `Some` case of the option type.

`F#`

    let printValue opt =
        match opt with
        | Some x -> printfn "%A" x
        | None -> printfn "No value."
    
###Using Discriminated Unions Instead of Object Hierarchies
The following discriminated union could be used instead of a `Shape` base class that has derived types for circle, square, and so on.

`F#`

    type Shape =
            // The value here is the radius.
        | Circle of float    
            // The value here is the side length.
        | EquilateralTriangle of double
            // The value here is the side length.
        | Square of double
            // The values here are the height and width.
        | Rectangle of double * double
        
Instead of a virtual method to compute an area or perimeter, as you would use in an object-oriented implementation, you can use pattern matching to branch to appropriate formulas to compute these quantities. In the following example, different formulas are used to compute the area, depending on the shape.

`F#`
    
    let pi = 3.141592654

    let area myShape =
        match myShape with
        | Circle radius -> pi * radius * radius
        | EquilateralTriangle s -> (sqrt 3.0) / 4.0 * s * s
        | Square s -> s * s
        | Rectangle (h, w) -> h * w

    let radius = 15.0
    let myCircle = Circle(radius)
    printfn "Area of circle that has radius %f: %f" radius (area myCircle)
    
    let squareSide = 10.0
    let mySquare = Square(squareSide)
    printfn "Area of square that has side %f: %f" squareSide (area mySquare)
    
    let height, width = 5.0, 10.0
    let myRectangle = Rectangle(height, width)
    printfn "Area of rectangle that has height %f and width %f is %f" height width (area myRectangle)

The output is as follows:
    
    Area of circle that has radius 15.000000: 706.858347
    Area of square that has side 10.000000: 100.000000
    Area of rectangle that has height 5.000000 and width 10.000000 is 50.000000

###Using Discriminated Unions for Tree Data Structures
Discriminated unions can be recursive, meaning that the union itself can be included in the type of one or more cases. Recursive discriminated unions can be used to create tree structures, which are used to model expressions in programming languages. In the following code, a recursive discriminated union is used to create a binary tree data structure. The union consists of two cases, `Node`, which is a node with an integer value and left and right subtrees, and `Tip`, which terminates the tree.

`F#`

    type Tree =
        | Tip
        | Node of int * Tree * Tree
    
    let rec sumTree tree =
        match tree with
        | Tip -> 0
        | Node(value, left, right) ->
            value + sumTree(left) + sumTree(right)
    let myTree = Node(0, Node(1, Node(2, Tip, Tip), Node(3, Tip, Tip)), Node(4, Tip, Tip))
    let resultSumTree = sumTree myTree
In the previous code, `resultSumTree` has the value `10`.

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


