# Two-Phase Distributed Commit

## Overview

The Two-Phase Distributed Commit project is an implementation of the Two-Phase Commit protocol for distributed systems. This protocol ensures atomicity in distributed transactions, providing a reliable and fault-tolerant solution for coordinating transactions across multiple nodes.

## Features

- **Two-Phase Commit Protocol:** Implements the classic Two-Phase Commit protocol to ensure consistent and atomic distributed transactions.

- **Fault Tolerance:** Robust handling of failures and recoverability to maintain system integrity even in the presence of node failures.

- **Scalability:** Can be added more nodes easily to replicate a scenario with a lot of nodes

## Getting Started
- Need JRE to run the project

```bash
# Example commands to get started
git clone https://github.com/dj1095/two-phase-distributed-commit.git
cd two-phase-distributed-commit
```
## Handled Scenarios
### Node Failure
- In the event of a node failure during the transaction, the Two-Phase Distributed Commit project gracefully handles the situation:

- If a participant node fails during the prepare phase, the coordinator detects the failure and aborts the transaction.

- If a participant node fails during the commit phase, the coordinator detects the failure and initiates a rollback to maintain consistency.

### Transaction Coordinator Failure
- In the rare scenario where the transaction coordinator fails:

- The system is designed to recover from coordinator failures and resume normal operation.

- Participants receive a notification of the failure and take appropriate measures, such as rolling back the transaction.


