##!SHELLMARKER
#
# Wrap up a log file as HTML and put it in a logs directory that is accessible
# via the web server.
#

if [ $# != 2 ]; then
  echo "Usage: ${0} <log-file-name> <title>" 1>&2
  exit 1
fi

binDir=`dirname $0`
. $binDir/lib/setupCmdLine.sh

LOG_FILE=${1}
TITLE_PART=${2}

HOST_NAME=`hostname`
TITLE="${HOST_NAME}: ${TITLE_PART}"
LOG_FILE_BASENAME=`basename "${LOG_FILE}"`
LOG_FILE_HTML_BASENAME=`date "+${HOST_NAME}_%Y%m%d%H%M%S_${LOG_FILE_BASENAME}.html"`
LOG_FILE_HTML=${POSTLOG_PUBLICATION_DIR}/${LOG_FILE_HTML_BASENAME}

mkdir -p "${POSTLOG_PUBLICATION_DIR}"

cat > ${LOG_FILE_HTML} <<EOF
<html>
<head>
<meta http-equiv="Pragma" CONTENT="no-cache">
<meta http-equiv="Cache-Control" CONTENT="no-cache, must-revalidate">
<meta http-equiv="max-age" CONTENT="0">
<meta http-equiv="Expires" CONTENT="0">
<title>${TITLE}</title>
</head>
<body>
<h3>${TITLE}</h3>
<pre>
EOF

if [ -f ${LOG_FILE} ]; then
  # Append the file into the HTML file, escaping special HTML characters.
  #
  sed -e 's/\&/\&amp\;/g' < "${LOG_FILE}" | sed -e 's/>/\&gt\;/g' -e 's/</\&lt\;/g' >> "${LOG_FILE_HTML}"
else
  echo "${LOG_FILE} does not exist." >> ${LOG_FILE_HTML}
fi

cat >> ${LOG_FILE_HTML} <<EOF
</pre>
</body>
</html>
EOF

cat >> ${BIGR_LOG_DIR}/newlogs.txt <<EOF
${HOST_URL}/logs/${LOG_FILE_HTML_BASENAME}
EOF
