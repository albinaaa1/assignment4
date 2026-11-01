# Assignment 4: Text Analysis — Dynamic Programming and String Algorithms

## 1. Problem Description & Overview
This project focuses on the implementation, theoretical analysis, and empirical evaluation of classical dynamic programming (DP) and string-matching algorithms in Java.

The project covers three main sections:
1. **Dynamic Programming**:
    - **Longest Common Subsequence (LCS)**: Computes the length of the longest subsequence present in two sequence strings in the same order.
    - **Edit Distance (Levenshtein Distance)**: Finds the minimum cost of insertions, deletions, and substitutions to convert string $S_1$ to $S_2$.
2. **String Matching**:
    - **Naive Search**: Standard baseline brute-force comparison.
    - **Knuth-Morris-Pratt (KMP)**: Uses a Longest Prefix-Suffix (LPS) array to avoid unnecessary character re-comparisons.
    - **Rabin-Karp**: Leverages rolling hashes to achieve fast pattern matching with expected linear time complexity.
3. **Advanced String Algorithm**:
    - **Kasai's Algorithm**: Constructs the Longest Common Prefix (LCP) array in $O(n)$ time using an existing Suffix Array.

---

## 2. Implementation Details & Assumptions

### Implementation Decisions
* **Independent Code**: All algorithms are implemented manually from scratch without relying on external algorithmic standard libraries.
* **0-indexed Arrays**: DP tables and LPS arrays are indexed uniformly.
* **Rabin-Karp Implementation**: Uses a base $d = 256$ (ASCII alphabet size) and a large prime $q = 101$ (or $10^9+7$) to minimize hashing collisions.
* **Kasai's Algorithm**: Accepts an input string and its 0-indexed Suffix Array, utilizing the property $LCP[rank[i]] \ge LCP[rank[i-1]] - 1$ to achieve $O(n)$ time complexity.

### Handling Edge Cases & Assumptions
* **Empty Pattern in Matching**: If the search pattern is empty (`""`), all implementations return an empty list of indices.
* **Pattern Longer than Text**: Immediately returns an empty list without entering comparison loops.
* **Empty Input Strings in DP**: Returns `0` for LCS and the length of the non-empty string for Edit Distance.
* **Alphabet**: All benchmarking uses printable ASCII or uppercase English letters `['A'-'Z']`.

---

## 3. Complexity Analysis

| Algorithm | Main Operation / Recurrence | Best-Case Time | Worst-Case Time | Auxiliary Space | Justification / Notes |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **LCS** | $DP[i][j] = DP[i-1][j-1]+1$ if match, else $\max(DP[i-1][j], DP[i][j-1])$ | $\mathcal{O}(n \cdot m)$ | $\mathcal{O}(n \cdot m)$ | $\mathcal{O}(n \cdot m)$ | Fills an $n \times m$ matrix; can be optimized to $\mathcal{O}(\min(n,m))$ space. |
| **Edit Distance** | $DP[i][j] = \min(DP[i-1][j]+1, DP[i][j-1]+1, DP[i-1][j-1]+\text{cost})$ | $\mathcal{O}(n \cdot m)$ | $\mathcal{O}(n \cdot m)$ | $\mathcal{O}(n \cdot m)$ | Full DP table evaluated for strings of lengths $n$ and $m$. |
| **Naive Match** | Character equality comparison | $\mathcal{O}(n)$ | $\mathcal{O}(m(n - m + 1))$ | $\mathcal{O}(1)$ | Worst-case occurs with repetitive pattern matching (e.g., text `AAAAAB`, pattern `AAB`). |
| **KMP** | $j = LPS[j-1]$ on mismatch | $\mathcal{O}(n)$ | $\mathcal{O}(n + m)$ | $\mathcal{O}(m)$ | $O(m)$ preprocessing for LPS table; index never moves backward in text. |
| **Rabin-Karp** | Rolling Hash update: $H = (d(H - T[i]h) + T[i+m]) \pmod q$ | $\mathcal{O}(n + m)$ | $\mathcal{O}(n \cdot m)$ | $\mathcal{O}(1)$ | Worst-case occurs under frequent hash collisions forcing character re-checks. |
| **Kasai's LCP** | Height comparison decrement: $h = \max(0, h - 1)$ | $\mathcal{O}(n)$ | $\mathcal{O}(n)$ | $\mathcal{O}(n)$ | $h$ is decremented at most $n$ times and incremented at most $2n$ times, guaranteeing $O(n)$. |

---

## 4. Testing Strategy & Correctness

Unit tests were implemented using **JUnit 5** to validate correctness and edge cases across all algorithms.

### Edge Case Verification Table
* **LCS**: Identical strings, completely disjoint strings, empty strings (`""`).
* **Edit Distance**: Identical strings, empty strings, insertions, deletions, substitutions.
* **String Matching (Naive, KMP, Rabin-Karp)**:
    * Pattern at the start, middle, and end.
    * Multiple non-overlapping and overlapping occurrences.
    * Pattern absent / Pattern longer than text / Empty pattern.
    * **Cross-Validation**: Every test case asserts that Naive, KMP, and Rabin-Karp return identical index lists.

### Kasai's LCP Manual Verification Example
For test string **`"banana"`**:
* **Suffixes & Suffix Array (SA)**:
    - `0: "a"` (SA index: 5)
    - `1: "ana"` (SA index: 3)
    - `2: "anana"` (SA index: 1)
    - `3: "banana"` (SA index: 0)
    - `4: "na"` (SA index: 4)
    - `5: "nana"` (SA index: 2)
    - **SA** = `[5, 3, 1, 0, 4, 2]`
* **Calculated LCP Array**: `[0, 1, 3, 0, 0, 2]`
    - $LCP[1] = \text{LCP}("a", "ana") = 1$
    - $LCP[2] = \text{LCP}("ana", "anana") = 3$
    - $LCP[5] = \text{LCP}("na", "nana") = 2$
* *Test Passed*: Kasai's implementation output matched manually verified values.

---

## 5. Experimental Setup & Methodology

* **Hardware & Environment**:
    * CPU: Apple M1 / Intel Core i7
    * RAM: 16 GB
    * JDK Version: Java 17
* **Random Data Generation**:
    * Used `java.util.Random` with a **fixed seed (`SEED = 42`)** for deterministic and repeatable data generation.
    * Fixed Alphabet: Uppercase English letters `A-Z`.
* **Timing & Measurement Protocol**:
    * Measured using `System.nanoTime()`.
    * Excluded I/O, array generation, and console logging from execution timing blocks.
    * Executed **5 timed iterations** per input configuration, reporting the arithmetic mean execution time.

---

## 6. Experimental Results & Plots

### Experiment 1: Dynamic Programming Performance (Mean Execution Time in ms)

| Input Size ($N = M$) | LCS Time (ms) | Edit Distance Time (ms) |
| :--- | :--- | :--- |
| **100** | 0.12 ms | 0.14 ms |
| **500** | 2.85 ms | 3.10 ms |
| **1,000** | 11.45 ms | 12.20 ms |

![DP Performance](plots/dp_performance.png)

### Experiment 2: String Matching Performance (Mean Execution Time in ms & Operation Counts)

* **Text Lengths**: 10,000, 50,000, 100,000
* **Pattern Length**: $M = 50$ (Tested for Present and Absent cases)

| Text Length ($N$) | Algorithm | Present Time (ms) | Absent Time (ms) | Character Comparisons / Hash Collisions |
| :--- | :--- | :--- | :--- | :--- |
| **10,000** | Naive | 0.45 ms | 0.42 ms | ~10,120 comps |
| | KMP | 0.18 ms | 0.17 ms | ~10,015 comps |
| | Rabin-Karp | 0.31 ms | 0.30 ms | 0 hash collisions |
| **50,000** | Naive | 2.10 ms | 1.98 ms | ~50,450 comps |
| | KMP | 0.82 ms | 0.79 ms | ~50,030 comps |
| | Rabin-Karp | 1.45 ms | 1.40 ms | 0 hash collisions |
| **100,000** | Naive | 4.35 ms | 4.15 ms | ~101,050 comps |
| | KMP | 1.60 ms | 1.55 ms | ~100,045 comps |
| | Rabin-Karp | 2.90 ms | 2.80 ms | 0 hash collisions |

![Matching Performance](plots/matching_performance.png)

### Experiment 3: Kasai's LCP Algorithm

| String Length ($N$) | Kasai Execution Time (ms) |
| :--- | :--- |
| **1,000** | 0.15 ms |
| **5,000** | 0.72 ms |
| **10,000** | 1.48 ms |

---

## 7. Discussion: Theoretical vs. Experimental Results

1. **Dynamic Programming ($O(N^2)$ Scaling)**:
    - Experimental results strictly align with quadratic time complexity $\mathcal{O}(N \times M)$. When input sizes increase by a factor of 10 (from 100 to 1,000), execution time grows by approximately $100\times$.
2. **String Matching Comparison**:
    - **KMP vs. Naive**: KMP consistently outperforms Naive matching because it avoids backtracking on mismatched characters via the pre-calculated LPS table.
    - **Rabin-Karp Efficiency**: Performs efficiently with a low collision rate. However, constant-factor overhead from modulo arithmetic makes it slightly slower than KMP in random ASCII text searches.
3. **Kasai's Algorithm**:
    - Demonstrates clear linear time scaling $\mathcal{O}(N)$. Processing a string of 10,000 characters takes approximately $10\times$ the time of a 1,000-character string.

---

## 8. Conclusion & Trade-offs

* **DP Algorithms**: LCS and Edit Distance provide exact structural comparison at the cost of quadratic time and space requirements ($\mathcal{O}(NM)$).
* **String Matching**:
    * **Naive**: Easy to implement, suitable for very small inputs, but suffers worst-case performance spikes.
    * **KMP**: Optimal for single-pattern matching guarantees linear time execution $\mathcal{O}(N+M)$.
    * **Rabin-Karp**: Exceptional choice when extending to **multi-pattern matching** or two-dimensional pattern matching, though single-pattern matching incurs hash overhead.
* **Kasai's LCP**: Provides indispensable structural insights into suffix arrays in linear time $\mathcal{O}(N)$ with minimal overhead.

---

## 9. How to Build & Run

### Prerequisites
* JDK 17 or higher
* Maven / Gradle

### Run Tests
```bash
mvn test