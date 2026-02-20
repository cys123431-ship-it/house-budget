#!/usr/bin/env sh
##############################################################################
##
##  Gradle start up script for POSIX-like environments.
##
##############################################################################

APP_NAME="Gradle"
APP_BASE_NAME=`basename "$0"`
APP_HOME=`dirname "$0"`

# Resolve links: $0 may be a symlink
while [ -h "$APP_HOME" ] ; do
  TARGET_FILE=`readlink "$APP_HOME"`
  if echo "$TARGET_FILE" | grep -q '^/' ; then
    APP_HOME=`dirname "$TARGET_FILE"`
  else
    APP_HOME=`dirname "$APP_HOME/$TARGET_FILE"`
  fi
done

APP_HOME=`cd "$APP_HOME" && pwd`

# Use the maximum available, or set MAX_FD
MAX_FD=maximum

case "`uname`" in
  Darwin* )
    MAX_FD=2048
    ;;
esac

if [ "$MAX_FD" = "maximum" ]; then
  MAX_FD=`ulimit -Hn`
fi

if [ "$MAX_FD" != "unlimited" -a -n "$MAX_FD" ] ; then
  ulimit -n $MAX_FD
fi

CLASSPATH=$APP_HOME/gradle/wrapper/gradle-wrapper.jar

DEFAULT_JVM_OPTS='"-Xmx64m" "-Xms64m"'

if [ -n "$JAVA_HOME" ] ; then
  JAVACMD="$JAVA_HOME/bin/java"
else
  JAVACMD=`which java`
fi

if [ ! -x "$JAVACMD" ] ; then
  if [ -z "$JAVA_HOME" ] ; then
    echo "Could not find java. Set JAVA_HOME or update your PATH."
  else
    echo "JAVA_HOME is set to an invalid directory: $JAVA_HOME"
  fi
  echo "Please visit https://adoptopenjdk.net/ for a JDK."
  exit 1
fi

exec "$JAVACMD" $DEFAULT_JVM_OPTS \
  -Dorg.gradle.appname=$APP_BASE_NAME \
  -classpath "$CLASSPATH" \
  org.gradle.wrapper.GradleWrapperMain "$@"
