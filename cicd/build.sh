#!/bin/bash

if [ "$#" -ne 1 ]; then
    echo "Usage: $0 <new_version>"
    exit 1
fi

set
echo $GITVERSION_FULLSEMVER

buildah build --env GITVERSION_FULLSEMVER --env BUILD_DEFINITIONNAME --env BUILD_BUILDID --build-context src=../src -t jfrog-platform-artifactory-nginx.jfrog-platform/kab-docker-local/whl/javawhlpub:$1 --platform linux/amd64 .

jf rt pp jfrog-platform-artifactory-nginx.jfrog-platform/kab-docker-local/whl/javawhlpub:$1 kab-docker-local --build-name $BUILD_DEFINITIONNAME --build-number $BUILD_BUILDID
jf rt bp $BUILD_DEFINITIONNAME $BUILD_BUILDID

####docker tag javawhlpub:$1 bcacr2023.azurecr.io/kab-whl/javawhlpub:$1
####docker push bcacr2023.azurecr.io/kab-whl/javawhlpub:$1
