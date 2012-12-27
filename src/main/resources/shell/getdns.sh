#!/bin/bash

if [ $# -lt 1 ] ; then
echo "Use: $0 PSID"
echo "Example Use: $0 XXXXX-XXX-XXX-XXX"
exit 1
fi

PSID=$1

scutil << EOF
open
get State:/Network/Service/$PSID/DNS
d.show
quit
EOF
