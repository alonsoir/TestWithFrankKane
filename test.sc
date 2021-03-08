val lines:String = "Hello world, Alonso!"

var helloThere:String = lines + " there!"

def cubeIt(x:Int) :Int = x*x*x

def applyFunction(x:Int, f :Int => Int):Int = f(x)

applyFunction(2,cubeIt)

val picardStuff = ("Piccard","Enterprise-D","three lights")

println(picardStuff._1)
println(picardStuff._2)
println(picardStuff._3)

val picardShip = "Picard" -> "Enterprise-D"

println(picardShip._1)

println(picardShip._2)

val listShip = List("Enterprise-D","Defiant", "WarPrey")

listShip.foreach(println)

for (ship <- listShip) println(ship)

val numberList = List(1,2,3,4,5,6)

val sum : Int = numberList.reduce( (x: Int,y: Int) => x + y)

println(sum)