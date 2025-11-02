# Assignment 4 — Graph Algorithms (Java / Maven)

This project implements advanced **graph algorithms** using **Java 21** and **Maven**.  
It includes full implementations of:
- **Tarjan's Algorithm** for Strongly Connected Components (SCC)
- **Graph Condensation** into a Directed Acyclic Graph (DAG)
- **Kahn's Topological Sorting**
- **Shortest Paths in DAG**
- **Longest Path (Critical Path)** computation
- **Performance metrics** for DFS, relaxations, and execution time

---

## 🧠 Project Overview

The goal is to analyze directed graphs by:
1. Detecting strongly connected components (SCCs)
2. Building a condensation graph (DAG)
3. Performing a topological sort
4. Computing shortest and longest paths
5. Measuring algorithm performance

All algorithms are implemented **from scratch** (no external graph libraries).

---

## 🧩 Project Structure
```

assignment4/
├── pom.xml                 # Maven configuration (Gson, JUnit)
├── data/
│    └── tasks.json         # input graph data
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



## 🧾 Example Output

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
Shortest paths from component 5: dist = [∞, ∞, 8.0, 7.0, 2.0, 0.0]
Critical path length = 8.0 → Path [5, 4, 3, 2]
```

---

## 🧪 Testing

All tests use **JUnit 5**:

* `SimpleTests` – verifies SCC and topological sorting
* `EdgeCasesTests` – checks edge cases (single node, chain, etc.)
* `FromJsonDatasetTest` – full integration test using `tasks.json`

To run all tests:

```
Right click on `test` folder → Run 'Tests in graph'
```

All tests pass ✅ with **no console output**.

---

## 📊 Performance Metrics

The `Metrics` class collects:

* DFS node visits
* DFS edge traversals
* Kahn queue pushes/pops
* Relaxation count in DAG shortest paths
* Execution time in nanoseconds

---

## 🧩 Dependencies

| Library | Version | Purpose           |
| ------- | ------- | ----------------- |
| Gson    | 2.11.0  | JSON parsing      |
| JUnit   | 5.10.0  | Testing framework |

---

