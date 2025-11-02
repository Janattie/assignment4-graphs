
# Assignment 4 — Graph Algorithms (Java / Maven)

This project implements a complete graph analysis framework in **Java 21**, using **Maven** as the build system.  
It covers all core algorithms required in the assignment — **SCC detection**, **graph condensation (DAG)**, **topological sorting**, **shortest paths**, **critical path**, and **performance metrics**.

---

##  Objectives

The goal of this assignment is to:

1. Detect **Strongly Connected Components (SCCs)** using **Tarjan’s algorithm**.  
2. Build a **Condensed Graph (DAG)** representing SCC relationships.  
3. Perform **Topological Sorting** using **Kahn’s algorithm**.  
4. Compute:
   - **Shortest Paths** in the DAG (based on topological order).
   - **Longest Path (Critical Path)** for project planning analysis.  
5. Measure **algorithmic performance metrics** (DFS traversals, relaxations, timing).

All algorithms are implemented **from scratch**, following modular and object-oriented design.

---

##  Algorithm Implementation Details

###  Tarjan’s SCC — `graph/scc/TarjanSCC.java`
- Based on depth-first search (DFS).
- Uses discovery time and low-link values to find SCCs.
- Runs in **O(V + E)** time.

###  Condensation Graph — `graph/model/Graph.java`
- Converts SCCs into single DAG nodes.
- Removes duplicate edges between components.
- Builds an acyclic graph suitable for further analysis.

###  Topological Sort — `graph/topo/TopologicalSort.java`
- Implements **Kahn’s Algorithm** using indegree counting and queue operations.
- Produces a valid topological order for DAG nodes.

###  DAG Shortest Paths — `graph/dagsp/DagShortestPaths.java`
- Dynamic programming approach using topological order.
- Computes shortest distances from a given source.
- Each vertex is relaxed once — **O(V + E)**.

###  DAG Longest Path — `graph/dagsp/DagLongestPath.java`
- Similar DP logic but with reversed comparison (`max` instead of `min`).
- Finds the **critical path length** and reconstructs the path sequence.

###  Metrics — `util/Metrics.java`
- Tracks:
  - DFS visits / DFS edges  
  - Kahn’s queue pushes and pops  
  - DAG relaxations  
  - Execution time for Tarjan SCC  
- Used for performance and timing analysis.

---

##  Project Structure
```

assignment4/
├── pom.xml
├── data/
│    ├── tasks.json
│    ├── small_1.json
│    ├── small_2.json
│    ├── small_3.json
│    ├── medium_1.json
│    ├── medium_2.json
│    ├── medium_3.json
│    ├── large_1.json
│    ├── large_2.json
│    └── large_3.json
└── src/
├── main/java/
│    ├── app/Main.java
│    ├── graph/
│    │     ├── model/Graph.java, Edge.java
│    │     ├── io/GraphIO.java
│    │     ├── scc/TarjanSCC.java
│    │     ├── topo/TopologicalSort.java
│    │     └── dagsp/DagShortestPaths.java, DagLongestPath.java
│    └── util/Metrics.java
└── test/java/graph/
├── SimpleTests.java
├── EdgeCasesTests.java
└── FromJsonDatasetTest.java

```

---



##  Example Output

```
SCC components:
C0 = [3, 2, 1]
C1 = [0]
C2 = [7]
C3 = [6]
C4 = [5]
C5 = [4]

Condensation DAG: n=6, edges=4
Topological order: [1, 5, 0, 4, 3, 2]

Shortest paths from component 5:
dist = [∞, ∞, 8.0, 7.0, 2.0, 0.0]

Critical path length = 8.0
Path (component ids): [5, 4, 3, 2]
```

---

##  Testing

### Framework: **JUnit 5**

All tests are implemented and pass successfully.

| Test                         | Purpose                                                  |
| ---------------------------- | -------------------------------------------------------- |
| **SimpleTests.java**         | Checks SCC and Topological order correctness             |
| **EdgeCasesTests.java**      | Validates edge cases (1 node, chain, disconnected graph) |
| **FromJsonDatasetTest.java** | Full pipeline test using `tasks.json` input              |

To run tests:
Right-click `test/java/graph` → **Run Tests in 'graph'**

✅ All tests pass with expected outputs.

---

##  Performance Metrics

| Metric                | Description                                    |
| --------------------- | ---------------------------------------------- |
| dfsVisits             | Number of DFS node visits (Tarjan)             |
| dfsEdges              | Number of edges traversed in DFS               |
| kahnPushes / kahnPops | Queue operations in Topological Sort           |
| relaxations           | Edge relaxations in DAG shortest paths         |
| timeSccNs             | Execution time for SCC algorithm (nanoseconds) |

All collected and printed in formatted output.

---

##  Dataset Description

The `/data/` directory contains 10 datasets:

| Category   | Nodes (n) | Description                          | Files                                             |
| ---------- | --------- | ------------------------------------ | ------------------------------------------------- |
| **Demo**   | 8         | Main example used in `Main.java`     | `tasks.json`                                      |
| **Small**  | 6–10      | Simple cases, 1–2 cycles or pure DAG | `small_1.json`, `small_2.json`, `small_3.json`    |
| **Medium** | 10–20     | Mixed structures with several SCCs   | `medium_1.json`, `medium_2.json`, `medium_3.json` |
| **Large**  | 20–50     | Performance and timing analysis      | `large_1.json`, `large_2.json`, `large_3.json`    |

---

##  Dependencies

| Library   | Version | Purpose                |
| --------- | ------- | ---------------------- |
| **Gson**  | 2.11.0  | JSON parsing           |
| **JUnit** | 5.10.0  | Unit testing framework |

---

##  Key Design Features

* Modular architecture with clear package separation
* Generic graph model supporting weights
* Easy dataset integration via JSON
* Independent algorithmic implementation
* Metrics collection for runtime and complexity analysis

---

##  Results Summary

| Stage            | Algorithm   | Complexity | Status   |
| ---------------- | ----------- | ---------- | -------- |
| SCC Detection    | Tarjan      | O(V + E)   |  Passed |
| Condensation     | DAG Builder | O(V + E)   |  Passed |
| Topological Sort | Kahn        | O(V + E)   |  Passed |
| Shortest Path    | DAG DP      | O(V + E)   |  Passed |
| Longest Path     | DAG DP      | O(V + E)   |  Passed |

✅ **All algorithms work as expected.**



---

## 🏁 Conclusion

This project demonstrates:

* Mastery of graph theory and algorithms.
* Correct implementation of SCC, DAG, Topological Sort, and Path computations.
* Full modular design and test coverage.
* Performance analysis with multiple dataset scales.


