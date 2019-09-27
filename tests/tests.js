/* eslint-env jasmine */

exports.defineAutoTests = function () {
    describe('App Information (window.appInfo)', function () {
        it('should exist', function () {
            expect(window.appInfo).toBeDefined();
        });

        it('should contain an application name that is a string', function () {
            expect(window.appInfo.name).toBeDefined();
            expect(typeof window.appInfo.name).toBe("string");
            expect(window.appInfo.name.length > 0).toBe(true);
        });

        it('should contain an application identifier that is a string', function () {
            expect(window.appInfo.identifier).toBeDefined();
            expect(typeof window.appInfo.identifier).toBe("string");
            expect(window.appInfo.identifier.length > 0).toBe(true);
        });

        it('should contain a version specification that is a string', function () {
            expect(window.appInfo.version).toBeDefined();
            expect((String(window.appInfo.version)).length > 0).toBe(true);
        });

        it('should contain a build specification that is a number', function () {
            expect(window.appInfo.build).toBeDefined();
            expect(typeof window.appInfo.build).toBe("number");
            expect(window.appInfo.build > 0).toBe(true);
        });

        it('should contain a compile date specification that is a date string', function () {
            expect(window.appInfo.compileDate).toBeDefined();
            var date = window.appInfo.compileDate;
            expect(date instanceof Date && !isNaN(date.valueOf())).toBe(true);
        });

        it('should contain an isHardwareAccelerated identifier that is a boolean', function () {
            expect(window.appInfo.isHardwareAccelerated).toBeDefined();
            expect(typeof window.appInfo.isHardwareAccelerated).toBe("boolean");
        });

        it('should contain an isDebuggable identifier that is a boolean', function () {
            expect(window.appInfo.isDebuggable).toBeDefined();
            expect(typeof window.appInfo.isDebuggable).toBe("boolean");
        });
    });
};

exports.defineManualTests = function (contentEl, createActionButton) {
    var logMessage = function (message, color) {
        var log = document.getElementById('info');
        var logLine = document.createElement('div');
        if (color) {
            logLine.style.color = color;
        }
        logLine.innerHTML = message;
        log.appendChild(logLine);
    };

    var clearLog = function () {
        var log = document.getElementById('info');
        log.innerHTML = '';
    };

    var appInfo_tests = '<h3>Press Dump App Info button to get application information</h3>' +
        '<div id="dump_appInfo"></div>' +
        'Expected result: Status box will get updated with application info. (i.e. application name, version, uuid, etc)';

    contentEl.innerHTML = '<div id="info"></div>' + appInfo_tests;

    createActionButton('Dump App Info', function () {
        clearLog();
        logMessage(JSON.stringify(window.appInfo, null, '\t'));
    }, 'dump_appInfo');
};
