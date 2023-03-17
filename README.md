# TSP-KSA-Map
A Desktop app that solves Traveling Salesman Problem (TSP) using local search algorithms.

Traveling salesman problem (TSP):
Given a set of cities and the distance between every pair of cities, the problem is to find the shortest 
possible route that visits every city exactly once and returns to the starting point. This problem is easy to 
state and difficult to solve.

Hill Climbing algorithm:
It is a local search algorithm that starts with an initial state, then attempts to find a better solution by 
looking into adjacent states. It moves in an increasing direction using a value that evaluates how good the 
solution is. It moves to find the peak of the mountain. Usually, it is stuck on a local maximum but if we are 
lucky, we may stop on the global maxima.

Simulated Annealing:
Like the Hill-Climbing algorithm. But instead of always taking a better value. It takes some bad moves 
with probability to escape the local maxima.

Genetic algorithm:
A genetic algorithm starts with a set of solutions called population. A solution is presented by a 
chromosome. At each generation, the fitness of each chromosome is evaluated, and then chromosomes for 
the next generation are selected using crossover and mutation randomly. Chromosomes with high fitness 
values have a high probability of being selected, and chromosomes of the new generation may have a 
higher average fitness value than those of the old generation.


![image](https://user-images.githubusercontent.com/121561626/225916126-390a0f9a-1443-4fd3-9e3e-a636b4046af8.png)
