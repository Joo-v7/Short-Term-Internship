window.loadingEnd = false;


window.waitFunction = [];

function createWaitFunction(name, parameters) {
    window[name] = function (...args) {
        const data = parameters.map(param => args[param]);
        window.waitFunction.push({ functionType: name, data });
    }
}

const functionDataList = [
    { name: 'call3DModelEvent', parameters: [0] },
    { name: 'enabled_muscle', parameters: [0, 1, 2] },
    { name: 'enabled_muscle_texture', parameters: [0, 1, 2] },
    { name: 'enabled_center_point', parameters: [0, 1] },
    { name: 'enabled_bucket', parameters: [0] },
    { name: 'enabled_stick', parameters: [0] },
    { name: 'set_point_size', parameters: [0, 1] },
    { name: 'add_point_position', parameters: [0, 1, 2, 3] },
    { name: 'character_color_setting', parameters: [0, 1] },
    { name: 'playcanvas_bloom_set', parameters: [0] }
];

functionDataList.forEach(func => createWaitFunction(func.name, func.parameters));
pc.script.createLoadingScreen(function (app) {
    var showSplash = function () {
        // splash wrapper
        var wrapper = document.createElement('div');
        wrapper.id = 'application-splash-wrapper';
        document.querySelector('#test_viewbox').appendChild(wrapper);
        // wrapper.style.width = "100%";
        // wrapper.style.height = "100%";

        // splash
        var splash = document.createElement('div');
        splash.id = 'application-splash';
        wrapper.appendChild(splash);
        // splash.style.display = 'none';

        var logo = document.createElement('img');
        //임시 이미지 주소
        logo.src = '/assets/playcanvas/loading_logo_04.png';
        // logo.src = './image/logo_3.png';

        splash.appendChild(logo);
        logo.onload = function () {
            splash.style.display = 'block';
        };

        var container = document.createElement('div');
        container.id = 'progress-bar-container';
        splash.appendChild(container);

        var bar = document.createElement('div');
        bar.id = 'progress-bar';
        container.appendChild(bar);

    };

    var hideSplash = function () {
        var splash = document.getElementById('application-splash-wrapper');
        splash.parentElement.removeChild(splash);
        window.loadingEnd = true;
    };

    var setProgress = function (value) {
        var bar = document.getElementById('progress-bar');
        if (bar) {
            value = Math.min(1, Math.max(0, value));
            bar.style.width = value * 100 + '%';
        }
    };

    var createCss = function () {
        var css = [
            'body {',
            '    background-color: #171714;',
            '}',
            '',
            '#application-splash-wrapper {',
            '    position: absolute;',
            '    top: 0;',
            '    left: 0;',
            '    height: 100%;',
            '    width: 100%;',
            '    background-color: #171714;',
            '        display: flex;',
            '        align-items: center;',
            '        justify-content: center;',
            '}',
            '',
            '#application-splash {',
            '    position: initial;',
            '    top: calc(50% - 28px);',
            '    width: 264px;',
            '    left: calc(50% - 132px);',
            '}',
            '',
            '#application-splash img {',
            '    width: 100%;',
            '}',
            '',
            '#progress-bar-container {',
            '    margin: 20px auto 0 auto;',
            '    height: 2px;',
            '    width: 100%;',
            '    background-color: #1d292c;',
            '}',
            '',
            '#progress-bar {',
            '    width: 0%;',
            '    height: 100%;',
            '    background-color: #fff;',
            '}',
            '',
            '@media (max-width: 480px) {',
            '    #application-splash {',
            '        width: 170px;',
            '        left: calc(50% - 85px);',
            '    }',
            '}'
        ].join('\n');

        var style = document.createElement('style');
        style.type = 'text/css';
        if (style.styleSheet) {
            style.styleSheet.cssText = css;
        } else {
            style.appendChild(document.createTextNode(css));
        }

        document.head.appendChild(style);
    };

    createCss();
    showSplash();

    app.on('preload:end', function () {
        const switch_load_on = true;
        app.off('preload:progress');
    });
    app.on('preload:progress', setProgress);
    app.on('start', hideSplash);
});