#!SHELLMARKER

# Usage: getwebpage.sh <url> [<bigr_username> <bigr_account> <bigr_password>]
#
# Retrieve the contents of the specified URL and print it to standard
# output.  Most of our Java code that is run from command-line scripts
# are run using mainrunner.sh, but this one doesn't.  It also uses a
# much-reduced classpath from what mainrunner.sh uses.  Both of these
# differences are due to problems using JDK 1.2.2 to request pages via
# HTTPS, a JDK bug that is fixed in JDK 1.3.1 and beyond.
# Our solution is to make sure that the WebPageGetter Java class used here
# uses only standard Java classes and not any IBM/WebSphere classes,
# and use a newer JDK to run just this script.

binDir=`dirname $0`
. $binDir/lib/setupCmdLine.sh

JAVA=${JAVA_HOME}/bin/java

CLASSPATH=${BIGR_INSTALLED_EAR_DIR}/BIGRCore.jar

RUNNER=com.ardais.bigr.api.WebPageGetter

$JAVA -Xmx8m -Djava.protocol.handler.pkgs=com.ibm.net.ssl.internal.www.protocol -classpath $CLASSPATH $RUNNER "$@"
