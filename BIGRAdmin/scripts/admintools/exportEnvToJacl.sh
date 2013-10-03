#!SHELLMARKER
#
# Force a regeneration of the file that contains JACL-accessible versions
# of various shell environment variables that the administrative
# scripts depend on.
#

if [ $# != 0 ]; then
  echo "Usage: ${0}" 1>&2
  exit 1
fi

# setupCmdLine regenerates the JACL environment file if it doesn't exist,
# so all we need to do is delete it and run setupCmdLine.

binDir=`dirname $0`
rm -f $binDir/local/wsadmin_profile_generated.jacl
. $binDir/lib/setupCmdLine.sh

