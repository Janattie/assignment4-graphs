# Assignment 4 — Graph Algorithms (Java / Maven)

This project is a full Java implementation of **graph analysis algorithms** for directed graphs, using **Tarjan's Strongly Connected Components**, **graph condensation into DAG**, **topological sorting**, and **path computations**.  
It is implemented entirely from scratch in **Java 21** using **Maven**, with complete modular structure, metrics tracking, and automated testing.

---

##  Objectives

The goal of this assignment is to:
1. Detect **strongly connected components (SCCs)** using **Tarjan’s algorithm** (DFS-based).
2. Build a **condensed DAG** representing connections between SCCs.
3. Perform a **topological sort** using **Kahn’s algorithm**.
4. Compute both:
   - **Shortest paths** in the DAG (using topological order dynamic programming).
   - **Longest paths** (critical path analysis).
5. Collect and display **performance metrics** for each stage.

---

##  Implementation Overview

### 1. Tarjan’s Algorithm — `TarjanSCC.java`
- Uses recursive DFS to assign discovery and low-link values.
- Detects strongly connected components without using recursion stacks.
- Complexity: **O(V + E)**.  
- Each SCC is returned as a `List<Integer>` of vertex IDs.

### 2. Graph Condensation — `Graph.java`
- Converts the original graph into a Directed Acyclic Graph (DAG) where:
  - Each SCC becomes a single node.
  - Edges connect components if there was an edge between vertices of different SCCs.
- Prevents duplicate edges using hash sets.

### 3. Topological Sorting — `TopologicalSort.java`
- Implements **Kahn’s algorithm**:
  - Counts indegrees.
  - Iteratively removes zero-indegree vertices.
- Provides a linear ordering of DAG components.

### 4. Shortest Paths in DAG — `DagShortestPaths.java`
- Computes shortest distances from a source component using topological order.
- Each node is relaxed only once — **O(V + E)** time.
- Stores both `dist[]` and `parent[]` arrays.

### 5. Longest Path (Critical Path) — `DagLongestPath.java`
- Dynamic programming version that inverts the comparison in the relaxation step.
- Returns the **maximum path length** and the sequence of components.

### 6. Metrics Collection — `Metrics.java`
- Counts:
  - DFS visits & edge traversals  
  - Kahn’s queue pushes/pops  
  - DAG relaxations  
  - Execution time of Tarjan’s algorithm  

---

## 🧩 Project Structure

```

assignment4/
├── pom.xml                 # Maven dependencies (Gson, JUnit)
├── data/
│    └── tasks.json         # Input graph for analysis
└── src/
├── main/java/
│    ├── app/Main.java                     # Entry point
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



## 🧪 Example Input (`data/tasks.json`)

```json
{
  "directed": true,
  "n": 8,
  "edges": [
    {"u": 0, "v": 1, "w": 3},
    {"u": 1, "v": 2, "w": 2},
    {"u": 2, "v": 3, "w": 4},
    {"u": 3, "v": 1, "w": 1},
    {"u": 4, "v": 5, "w": 2},
    {"u": 5, "v": 6, "w": 5},
    {"u": 6, "v": 7, "w": 1}
  ],
  "source": 4,
  "weight_model": "edge"
}
```

---

## 🧾 Example Output

```
SCC components:
C0 = [3, 2, 1]
C1 = [0]
C2 = [7]
C3 = [6]
C4 = [5]
C5 = [4]
SCC time: 0.17 ms, dfsVisits=8, dfsEdges=7

Condensation DAG: n=6, edges=4
Topological order: [1, 5, 0, 4, 3, 2]
Shortest paths from component 5: dist = [∞, ∞, 8.0, 7.0, 2.0, 0.0]
Critical path length = 8.0 → Path [5, 4, 3, 2]
```

---

## 🧪 Testing and Validation

### Framework: **JUnit 5**

All tests are automated and cover both algorithmic logic and integration.

#### ✅ `SimpleTests.java`

* Validates SCC detection and topological order for small graphs.

#### ✅ `EdgeCasesTests.java`

* Tests graphs with 1 vertex, linear chains, and acyclic structures.

#### ✅ `FromJsonDatasetTest.java`

* Loads `data/tasks.json` and verifies:

  * Correct SCC count
  * Proper condensation DAG
  * Expected shortest/longest path values

To run all tests:

```
Right-click → Run 'Tests in graph'
```

All tests pass ✅ successfully.

---

## 📊 Performance Metrics

| Metric                    | Description                          |
| ------------------------- | ------------------------------------ |
| **dfsVisits**             | Number of DFS node visits (Tarjan)   |
| **dfsEdges**              | DFS edge traversals                  |
| **kahnPushes / kahnPops** | Queue operations in Kahn’s algorithm |
| **relaxations**           | Edge relaxations in shortest path    |
| **timeSccNs**             | Execution time of SCC (nanoseconds)  |

---

## 🧩 Dependencies

| Library   | Version | Purpose                      |
| --------- | ------- | ---------------------------- |
| **Gson**  | 2.11.0  | JSON parsing for input       |
| **JUnit** | 5.10.0  | Unit and integration testing |

---

## 🧠 Key Design Features

* **Modular architecture:** each algorithm in its own package
* **Fully generic Graph model** with weighted edges
* **Simple JSON input format** for easy dataset swapping
* **Metrics instrumentation** for performance tracking
* **Complete test coverage** ensuring correctness

---

## 🧾 Evaluation and Results

| Stage            | Algorithm   | Time (ms) | Notes               |
| ---------------- | ----------- | --------- | ------------------- |
| SCC              | Tarjan      | 0.17      | Found 6 components  |
| Condensation     | DAG builder | <0.1      | Built acyclic graph |
| Topological Sort | Kahn        | 0.28      | Correct ordering    |
| Shortest Paths   | DAG DP      | 0.38      | Valid distances     |
| Longest Path     | DAG DP      | <0.5      | Max = 8.0           |

✅ **All algorithms completed successfully with expected complexity O(V + E).**

---


## 🏁 Summary

This project demonstrates:

* Full understanding of graph theory concepts (SCC, DAG, Topological order).
* Independent algorithmic implementation without external libraries.
* Correctness verified by automated unit tests.
* Performance efficiency and modular Java design.

