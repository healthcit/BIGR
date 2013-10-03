#!SHELLMARKER
#
# update_branding.sh
#
# This script is used to define the branding image for a BIGR brand.
# It performs 2 operations:
#
#  1. Moves the image file specified (which must be on the unix machine)
#     to the deployment directory
#  2. Updates the database row that reflects the branded image

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
  echo "   [-file <IMAGE-FILENAME>]" 
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
    x-file) shift; IMAGE_FILENAME="${1}" ;;
    x-help) usage ;;
    x-*) echo "Unknown option $1" 1>&2; usage ;;
    *) break ;;
  esac
  shift
done 

echo "begin update_branding.sh"

# copy the image file
DESTDIR=${BIGR_INSTALLED_EAR_DIR}/BIGRWeb.war/images
IMAGE=`basename ${IMAGE_FILENAME}`
cp $IMAGE_FILENAME $DESTDIR
cp $IMAGE_FILENAME ${BIGR_LOCAL_LIB}/web_images/

# update the branding row       

export IMAGE
export BRAND_NAME

$ORACLE_HOME/bin/sqlplus "${BIGR_DATASOURCE_OWNER_USERNAME}/${BIGR_DATASOURCE_OWNER_PASSWORD}@${BIGR_DATASOURCE_DB}" << EOF
     update ARD_BRANDING set BRAND_LOGO = '$IMAGE' where BRAND_NAME = '$BRAND_NAME';
     commit;
EOF

echo "completed update_branding.sh"
