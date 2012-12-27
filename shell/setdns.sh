#!/bin/bash

# Script is used to set the Nameserver Lookup under Max OS X 10.4 with the Console
# Script by Stephan Oeste <stephan@oeste.de>

if [ $# -lt 2 ] ; then
echo "Use: $0 PSID <1.Nameserver> [2.Nameserver]"
echo "Example Use: $0 XXXXX-XXX-XXX-XXX 1.2.3.4 1.2.3.5"
exit 1
fi

PSID=$1

scutil << EOF
open
d.init
d.add ServerAddresses * $2 $3 
set State:/Network/Service/$PSID/DNS
quit
EOF
