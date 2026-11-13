#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")"
mkdir -p build
javac --release 17 -encoding UTF-8 -d build \
  src/main/java/com/lab/*.java
java -cp build com.lab.Main
