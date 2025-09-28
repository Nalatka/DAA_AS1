# Divide-and-Conquer Algorithms Project

This project implements classic divide-and-conquer algorithms with comprehensive performance analysis and testing.

## Algorithms Implemented

1. **MergeSort** - O(n log n) with linear merge, reusable buffer, and small-n cutoff
2. **QuickSort** - O(n log n) average case with randomized pivot and bounded recursion
3. **Deterministic Select** - O(n) median-of-medians algorithm for k-th smallest element
4. **Closest Pair of Points** - O(n log n) 2D closest pair algorithm

## Project Structure

```
src/main/java/org/example/
├── algorithms/          # Algorithm implementations
├── metrics/            # Performance measurement utilities
├── util/               # Common utilities (partition, swap, etc.)
├── benchmark/          # JMH benchmark harness
└── Main.java           # CLI interface

src/test/java/org/example/
├── algorithms/          # Algorithm tests
├── metrics/            # Metrics tests
└── benchmark/          # Benchmark tests
```

## Building and Running

```bash
# Compile and test
mvn clean compile test

# Run benchmarks
mvn exec:java -Dexec.mainClass="org.example.Main" -Dexec.args="--help"

# Package
mvn clean package
```

## Performance Analysis

The project includes comprehensive performance analysis with:
- Master Theorem recurrence analysis
- Akra-Bazzi method for complex recurrences
- Time complexity measurements
- Recursion depth tracking
- Memory allocation monitoring
- CSV output for data analysis

## Git Workflow

- `main` - Only working releases (tagged v0.1, v1.0)
- Feature branches: `feature/mergesort`, `feature/quicksort`, `feature/select`, `feature/closest`, `feature/metrics`

## Commit History

- `init`: Maven setup, JUnit5, README
- `feat(metrics)`: Counters, depth tracker, CSV writer
- `feat(mergesort)`: Baseline + reuse buffer + cutoff + tests
- `feat(quicksort)`: Smaller-first recursion, randomized pivot + tests
- `refactor(util)`: Partition, swap, shuffle, guards
- `feat(select)`: Deterministic select (MoM5) + tests
- `feat(closest)`: Divide-and-conquer implementation + tests
- `feat(cli)`: Parse args, run algos, emit CSV
- `bench(jmh)`: Harness for select vs sort
- `docs(report)`: Master cases & AB intuition, initial plots
- `fix`: Edge cases (duplicates, tiny arrays)
- `release`: v1.0
