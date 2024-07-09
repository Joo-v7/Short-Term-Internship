window.loadingEnd = false;

pc.script.createLoadingScreen(function (app) {
    var showSplash = function () {
        // splash wrapper
        var wrapper = document.createElement('div');
        wrapper.id = 'application-splash-wrapper';
        document.querySelector('#vsimg').appendChild(wrapper);

        // splash
        var splash = document.createElement('div');
        splash.id = 'application-splash';
        wrapper.appendChild(splash);
        // splash.style.display = 'none';

        var logo = document.createElement('div');
        logo.classList.add('background');


        //임시 이미지 주소
        // logo.src = 'https://collabo.uokdc.com/assets/img/testimage.png';
        // logo.src = './image/logo_3.png';

        splash.appendChild(logo);

        logoSplash = document.createElement('img');
        logoSplash.src = "http://i-lms.uokdc.net:18080/pages/kepco/img/canvas_logoSplash.png";
        // logoSplash.src = "./logoSplash.png";

        logo.appendChild(logoSplash);

        logoSplash.onload = function () {
            splash.style.display = 'block';
            // console.log('완료');
        };

        var container = document.createElement('div');
        container.id = 'progress-bar-container';
        container.style.display = 'none';
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
            '#application-splash-wrapper {',
            '    position: absolute;',
            '    top: 0;',
            '    left: 0;',
            '    height: 100%;',
            '    width: 100%;',
            '    background-color: #000000;',
            '        display: flex;',
            '        align-items: center;',
            '        justify-content: center;',
            '}',
            '',
            '#application-splash {',
            '    position: initial;',
            '    top: calc(50% - 28px);',
            '    width: 100%;',
            '    left: calc(50% - 132px);',
            '}',
            '',
            '#application-splash .background {',
            '    width: 100%;',
            '    height: 100%;',
            '    position: absolute;',
            '    top: 0;',
            '    display: flex;',
            '    justify-content: center;',
            '    align-items: center;',
            '}',
            '#application-splash .background img {',
            '    height: 100%;',
            '}',
            '',
            '#progress-bar-container {',
            '    margin: 20px auto 0 auto;',
            '    height: 2px;',
            '    width: 100%;',
            '    background-color: #000000;',
            '}',
            '',
            '#progress-bar {',
            '    width: 0%;',
            '    height: 100%;',
            '    background-color: #fff;',
            '}',
            '',
            // '@media (max-width: 480px) {',
            // '    #application-splash {',
            // '        width: 170px;',
            // '        left: calc(50% - 85px);',
            // '    }',
            // '}'
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
        app.off('preload:progress');
    });
    app.on('preload:progress', setProgress);
    app.on('start', hideSplash);
});