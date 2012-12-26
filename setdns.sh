#!/bin/bash

# Script is used to set the Nameserver Lookup under Max OS X 10.4 with the Console
# Script by Stephan Oeste <stephan@oeste.de>

if [ $# -lt 2 ] ; then
echo "Use: $0 <domain> <1.Nameserver> [2.Nameserver]"
echo "Example Use: $0 example.tld 1.2.3.4 1.2.3.5"
exit 1
fi

PSID=$( (scutil | grep PrimaryService | sed -e 's/.*PrimaryService : //')<< EOF
open
get State:/Network/Global/IPv4
d.show
quit
EOF
)

scutil << EOF
open
d.init
d.add ServerAddresses * $2 $3 
d.add DomainName $1
set State:/Network/Service/$PSID/DNS
quit
EOF
