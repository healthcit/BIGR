#!SHELLMARKER
#
# update_branding_acct.sh
#
# This script is used to associate a BIGR brand with a single account.  To associate
# a brand with multiple accounts, run this script once per account.
# 
# It performs a single operation:
#
#  1. Updates the database account row to be associated with the brand

# helper functions

# Terminate the script
#   $1 = Message to print
#   $2 = Exit code
die () {
  ex=0
  if [ $# -eq 2 ] ; then
    echo 
    echo "Message: $1; exit code: $2"
    echo
    ex=$2
  fi
  exit $2
}

# Print out help text for this script
#   No Arguments
usage () {
  echo "Usage: ${0}" 
  echo "   [-brand <BRAND-NAME>]" 
  echo "   [-acct <ACCT-KEY>]" 
  echo ""
  exit 1
}

###############################
# begin main program
###############################

#  setup shell arguments that are useful
binDir=`dirname $0`
. $binDir/lib/setupCmdLine.sh

# check for correct number of arguments
if [ $# -ne 4  ] ; then
   usage
   echo
   die "follow usage conventions" "2"
fi

# extract the arguments into variables
while [ ! -z "$1" ];
  do case x"$1" in
    x-brand) shift; BRAND_NAME="${1}" ;;
    x-acct) shift; ACCT_KEY="${1}" ;;
    x-help) usage ;;
    x-*) echo "Unknown option $1" 1>&2; usage ;;
    *) break ;;
  esac
  shift
done 

echo "begin update_branding_acct.sh"

# update the account row       

export BRAND_NAME
export ACCT_KEY

$ORACLE_HOME/bin/sqlplus "${BIGR_DATASOURCE_OWNER_USERNAME}/${BIGR_DATASOURCE_OWNER_PASSWORD}@${BIGR_DATASOURCE_DB}" << EOF
     update ES_ARDAIS_ACCOUNT set BRAND_ID = (select BRAND_ID from ARD_BRANDING where BRAND_NAME = '$BRAND_NAME') where ARDAIS_ACCT_KEY = '$ACCT_KEY';
     commit;
EOF

echo "completed update_branding_acct.sh"
