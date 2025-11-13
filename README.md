# Minimum Spanning Tree (MST) - Edge Removal and Reconnection Algorithm

###  Project Objective:
This project demonstrates an algorithm that removes an edge from a Minimum Spanning Tree (MST) and substitutes it with another edge, ensuring the tree structure is preserved. The approach utilizes key properties of MSTs from graph theory.


## Initial MST:
<img width="500" height="185" alt="Screenshot 2025-11-13 at 19 43 40" src="https://github.com/user-attachments/assets/7315c738-97ac-40d6-bbdd-221dd55f04f3" />

## Algorthm stages:
### Stage 1: MST Construction Kruskal's Algorithm
1. Sort edges by weight: [2, 3, 5, 6, 7, 8, 9]
2. Check for cycles using Disjoint Set
3. Select 4 edges (V-1 = 4)

### Stage 2: Removing edge
Removed Edge: (1-2: 3)

#### MST After removing:
Broken MST:
(0-1: 2)
(1-4: 5) 
(0-3: 6)

### Stage 3: DFS
Component Separation:
Component 1: {0, 1, 3, }-
Component 2: {2}        This is isolated vertex

### Stage 5: New MST Construction
New MST Structure:
(0-1: 2)
(1-4: 5) 
(0-3: 6)
(2-4: 7) ← new edge

## Weight Comparison
Stage	Total Weight	Change	Description
Initial MST	16	-	Optimal solution
After Edge Removal	13	-3	Broken MST
After Replacement	20	+7	New optimal solution

## Class Responsibilities 

### DisjointSet.java 
	•	Utilizes the Union-Find data structure
	•	Detects cycles while building the MST
	•	Employs path compression and union-by-rank for efficiency

### Edge.java 
	•	Models graph edges with their vertices and weights
	•	Implements Comparable to allow sorting by weight
	•	Includes a string representation to aid debugging

### Graph.java 
	•	Manages the graph and MST-related operations
	•	Constructs the MST using Kruskal’s algorithm
	•	Supports edge removal and replacement within the MST
	•	Uses DFS to identify connected components

### Main.java 
	•	Shows the entire workflow of the algorithm
	•	Displays clear output at each stage
	•	Acts as a practical usage example

## Use cases: 
	•	Network Design: Optimizing communication networks in case of link failures
	•	Infrastructure Planning: Rerouting utilities when connections are disrupted
	•	Cluster Analysis: Preserving connectivity in data clustering tasks
	•	Robotics: Maintaining sensor networks and planning paths for robots

## Conclusion 
This implementation effectively showcases dynamic MST management by handling edge removal and reconnection. The algorithm guarantees efficient and correct tree reconstruction across different test cases. Its modular structure makes it easy to extend and integrate into larger graph processing systems.
