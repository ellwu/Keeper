#!/usr/bin/env bash
KEEPER_PATH=`pwd`/..
java -DKEEPER_PATH=$KEEPER_PATH -Dconfig=../config/keeper.conf -jar ../lib/Keeper-0.0.1-SNAPSHOT.jar com.shnlng.keeper.Bootstrap &
