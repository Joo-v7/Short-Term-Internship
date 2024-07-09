/**
 * @license Copyright (c) 2003-2020, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see https://ckeditor.com/legal/ckeditor-oss-license
 */

CKEDITOR.editorConfig = function( config ) {
    // Define changes to default configuration here. For example:
    // config.language = 'fr';
    // config.uiColor = '#AADC6E';
    config.toolbar = [
        { name: 'document', items: [ 'Source'] },
        { name: 'styles', items: [ 'Font', 'FontSize' ] },
        { name: 'colors', items: [ 'TextColor', 'BGColor' ] },
        { name: 'basicstyles', items: [ 'Bold', 'Italic', 'Underline', 'Strike', '-', 'RemoveFormat' ] },
        { name: 'paragraph', items: [ 'JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock'] },
        { name: 'links', items: [ 'Link', 'Unlink' ] },
        { name: 'insert', items: ['EasyImageUpload',  'Table', 'HorizontalRule', 'SpecialChar'] },
        { name: 'tools', items: [ 'Maximize', 'ImageMap'] }
    ];
    config.font_names = '맑은고딕;굴림;굴림체;돋움;돋움체;궁서;궁서체;Arial;Times New Roman;Verdana';
    config.language =  'ko';
    config.removePlugins = 'image';
    config.extraPlugins = 'lineutils';
    config.extraPlugins = 'filetools';
    config.extraPlugins =  'balloonpanel';
    config.enterMode = CKEDITOR.ENTER_BR;
    config.autoParagraph = false;
    config.extraPlugins =  'easyimage';
    config.allowedContent = true;
    //config.filebrowserImageUploadUrl = '/editor/Ckeditor2/upload?type=image';

    config.width = "100%"; //단위를 안쓰면 px. %, em도 쓸 수 있다.
    config.height = 500;

    //padding이 안되길래 봤더니 기본값은 거의 모든걸 필터링해버린다.
    config.allowedContent = {
        $1: {
            // Use the ability to specify elements as an object.
            elements: CKEDITOR.dtd,
            attributes: true,
            styles: true,
            classes: true,
            //autoParagraph: false,
        }
    };


    config.disallowedContent = 'script; *[on*]';




};
