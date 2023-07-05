# two-phase-distributed-commit
#Two phase distributed commit protocol .

Implement a 2-phase distributed commit(2PC) protocol and use controlled and randomly injected failures to study how the 2PC protocol handles node crashes. Assume one coordinator and at least two participants in the 2PC protocol. Similar to the previous projects, we use multiple processes to emulate multiple nodes. Each node (both the coordinator and the participants) devises a time-out mechanism when no response is received and transits to either the abort or commit state. For simplicity, you can assume that only one node fails in the controlled test. Evaluate different possibilities of failures(e.g., coordinator fails before or after sending commit message). To emulate a failure, you can impose a much longer delay at a failed node than the time-out period used by other active nodes. Node prints their states(commit/abort) before termination. 

[Disclaimer] : Using this code towards grades might result in plagiarism . Use at your own risk.
