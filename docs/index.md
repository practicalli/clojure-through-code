---
icon: lucide/rocket
---

# Get started

Practicalli recommends opening files from this project in a [Clojure aware editor]() with a connected REPL session.

This project assumes Clojure CLI aliases from [Practicalli Clojure CLI Config](https://practical.li/clojure/clojure-cli/practicalli-config/){target=_blank} are available.

Start an interactive REPL for use with a connected editor

=== "Makefile tasks"

    ```shell
    make repl
    ```

=== "Clojure CLI"

    ```shell
    clojure -M:dev/env:test/env:repl/rebel
    ```


A [Rebel Readline terminal UI](https://practical.li/clojure/clojure-cli/repl/#rebel-terminal-repl-ui){target=_blank} provides an interactive REPL session.

An nRepl (network repl) server starts, allowing a Clojure aware editor to connect to the REPL session.
