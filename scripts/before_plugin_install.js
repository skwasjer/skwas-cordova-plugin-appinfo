#!/usr/bin/env node

/**
 * Android specific before plugin install hook, that modifies our AppInfo.java for the Cordova Android platform.
 */
 
module.exports = function (context) {
  var path = require("path");
  var fs = require("fs");
  var DOMParser = require("@xmldom/xmldom").DOMParser;
  var domParser = new DOMParser();

  // Read app id from config.xml.
  var configXmlFile = path.join(context.opts.projectRoot, "config.xml");
  var configXml = fs.readFileSync(configXmlFile, "utf-8");
  var doc = domParser.parseFromString(configXml, "text/xml");
  var app_id = doc.documentElement.getAttribute("id"); 

  // Modify the AppInfo.java file.
  var javaFile = path.join(context.opts.plugin.dir, "src/android/AppInfo.java");
  var fileContents = fs.readFileSync(javaFile, "utf-8");
  var newFileContents = fileContents.replace(/\[app_id\]/, app_id);
  fs.writeFileSync(javaFile, newFileContents, "utf-8");
}
