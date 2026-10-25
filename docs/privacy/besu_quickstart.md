!!! warning "Historical private-network guide"
    This page describes the older Besu/Orion privacy integration. It is not a setup guide for current public-network clients. Verify the exact client versions and supported privacy features before using these recipes. For current Ethereum setup, see [Run a node locally](../getting_started/run_node_locally.md).

## Besu Quickstart
In order to get off the group quickly with Web3j and privacy on Besu, Besu Quickstart should be used. Besu quickstart is a collection of dockerfiles and associated configuration files which allows you to start up a local private-transaction-enabled Ethereum network. In order to get started, ensure you have Docker and Docker Compose installed on your system. Then clone the Besu Quickstart repository likeso:
```bash
git clone https://github.com/PegaSysEng/besu-quickstart.git
```
  
In order to start an Ethereum network with privacy enabled, invoke the `run-privacy.sh` shell script. This should result in a number of containers being started, including 3 Besu nodes which will communicate using the ports `localhost:20000-20004`. It should be noted that starting the full network generally uses 12GB+ of RAM.

Once the network has been started, the output of the `netstat` command should indicate that ports 20000-20004 are in use.
