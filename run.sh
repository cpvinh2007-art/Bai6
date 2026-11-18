#!/usr/bin/env bash
set -e

cd "$(dirname "$0")"

rm -rf build
mkdir -p build

# Copy file cấu hình logback.xml vào thư mục build
if [ -d "src/main/resources" ]; then
  cp -r src/main/resources/* build/ 2>/dev/null || true
fi

# Chọn dấu phân cách classpath và bật UTF-8 cho console trên Windows
SEP=":"
case "$(uname -s)" in
  MINGW*|MSYS*|CYGWIN*)
    SEP=";"
    chcp.com 65001 > /dev/null 2>&1 || true
    ;;
esac

# Biên dịch và chạy class Main
javac -encoding UTF-8 -cp "lib/*" -d build src/main/java/*.java
java -Dfile.encoding=UTF-8 -Dstdout.encoding=UTF-8 -cp "build${SEP}lib/*" Main