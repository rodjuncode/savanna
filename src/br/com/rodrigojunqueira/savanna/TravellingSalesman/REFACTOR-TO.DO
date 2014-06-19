[06/18/2014 - 20h50 - GMT -3]
I've been running into several bumps and walls during the 
coding of the Travelling Salesman for the Savanna GA Stack. 
The modeling of the Dna for this particular problem is not
even close to be considered usual.
The main problem resides in the way the possible routes are
mapped into the Dna and the consequent complications on the
mutation and mainly the crossover operation.
The first challenge is how to create valid routes.
Each route must contain only one occurrence of each city, but
also needs to contain all the possible cities. So (Todo 1) We 
might want to have a route validator method.
The second important thing to have is a efficient way of storing
the map for the problem. It's important to notice that the
path from A to B must have the same cost of the path from B to A.
Inconsistencies of such nature will cause flaws in the process, 
hence is vital to (Todo 2) have a map validator method.
I believe that the calculator of cost for a given route is fine,
like the methods to compare two possible routes. We should be 
okay on those.
Also, the mutation concept is fine, but it must be generalized
for any possible number of nodes (right now it works only for
4 cities). So (Todo 3) we need to refactor the mutation method.
The Factory is not implemented yet. (Todo 4) The generate method
needs to be coded following the simple idea of starting from 
city A, and then picking randomly each next city, respecting the
basic rule of never repeating the city and making sure it passes
through all the cities.
The greatest challenge so far is the concept for crossover. I've
drafted a concept for it, although I am not sure if it covers the
needs. The basic idea is poorly represented on the following notes:

Cities & Distances
  A B C D E F G
A - 2 2 4 5 8 5 
B 2 - 8 3 8 1 5
C 2 8 - 7 2 2 1 
D 4 3 7 - 1 4 4
E 5 8 2 1 - 5 2
F 8 1 2 4 5 - 3
G 5 5 1 4 2 3 -

King - A C G B F D E A -> 2 + 1 + 5 + 1 + 4 + 1 + 5 = 19 cost
Lion - A B E C G D F A -> 2 + 8 + 2 + 1 + 4 + 4 + 8 = 29 cost

1) Finds the "move" from King with lower cost, that isn't present on Lion
2) Replaces that move in Lion, making the changes to keep the route valid

In this example, we would have this solution as a cub:
Cub - A B F C G D E A -> 2 + 1 + 2 + 1 + 4 + 1 + 5 = 16

(Todo 5) Yeah, this needs to be implemented



 


