/*
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
*/

var argscheck = require('cordova/argscheck'),
    channel = require('cordova/channel'),
    utils = require('cordova/utils'),
    exec = require('cordova/exec'),
    cordova = require('cordova');

channel.createSticky('onCordovaInfoReady');
// Tell cordova channel to wait on the CordovaInfoReady event
channel.waitForInitialization('onCordovaInfoReady');

/**
 * This represents the app, and provides properties for inspecting the app info.
 * phone, etc.
 * @constructor
 */
function AppInfo() {
    this.available = false;
    this.name = null;
    this.version = null;
    this.compileDate = null;
    this.isHardwareAccelerated = null;
    this.isDebuggable = null;

    var me = this;

    channel.onCordovaReady.subscribe(function() {
        me.getInfo(function(info) {
            me.available = true;
            me.name = info.name;
            me.version = info.version;
            me.compileDate = info.compileDate;
            me.isHardwareAccelerated = info.isHardwareAccelerated;
            me.isDebuggable = info.isDebuggable;
            channel.onCordovaInfoReady.fire();
        },function(e) {
            me.available = false;
            utils.alert("[ERROR] Error initializing Cordova: " + e);
        });
    });
}

/**
 * Get app info
 *
 * @param {Function} successCallback The function to call when the heading data is available
 * @param {Function} errorCallback The function to call when there is an error getting the heading data. (OPTIONAL)
 */
AppInfo.prototype.getInfo = function(successCallback, errorCallback) {
    argscheck.checkArgs('fF', 'AppInfo.getInfo', arguments);
    exec(successCallback, errorCallback, "AppInfo", "getAppInfo", []);
};

module.exports = new AppInfo();