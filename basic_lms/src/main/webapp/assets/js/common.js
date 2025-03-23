

// TODO: JS 함수 추가
// trim, pad, 숫자3자리 콤마, 한글변환?, toInt, toStr, noNull(undefined 포함), Date 비교, 마스크(플러그인)


if(typeof(COMMON_JS) === 'undefined') { // 한 번만 실행
    var COMMON_JS = true;
    function showToast(message, timer, type) {
        if(timer === undefined){
            timer = 1000;
        }
        if(type === undefined){
            //error,info,success,warning
            type = 'success';
        }
        var css='toast-top-right';
        var tapToDismiss=true,closeButton=false;
        toastr.options = {
            "closeButton": closeButton,
            "debug": false,
            "newestOnTop": false,
            "progressBar": false,
            "preventDuplicates": closeButton,
            "onclick": null,
            "showDuration": "300",
            "hideDuration": timer,
            "timeOut": "5000",
            "extendedTimeOut": "1000",
            "showEasing": "swing",
            "hideEasing": "linear",
            "showMethod": "fadeIn",
            "hideMethod": "fadeOut",
            "positionClass": css,
            "tapToDismiss" : tapToDismiss
        };
        $("#toast-container").attr('class',css);
        toastr[type](message);
    }

    /*====입력값 체크 관련====*/
    function input_regexp(_this,pattern){

        var text = '';
        var data = $(_this).val();
        var position = $(_this).position();
        for( var i=0; i<data.length; i++){
            if(data.charAt(i) !== " " && pattern.test(data.charAt(i)) === false ){
                var html = '<div class="material-tooltip" style="top: '+(position.top+45)+'px; left: '+(position.left)+'px; visibility: visible; opacity: 1; transform: translateX(0px) translateY(0px);width: 100%;    background-color: #ee6e73;">' +
                    '<div class="tooltip-content">입력 불가능한 문자입니다</div>' +
                    '</div>';
                if($(_this).next().find('.material-tooltip').length === 0){
                    $(_this).after(html);
                }
                break;
            }
            else{
                text += data.charAt(i);
            }
        }
        setTimeout(function () {
            $('.material-tooltip').remove();
        },2000);
        return text;
    }
    function onlyNumber(data) {
        if (data) {
            return parseFloat(data.toString().replace(/[^0-9\+\-\.]/gi, ''));
        }
        return 0;
    }

    /**
     * 일반전화 및 휴대전화번호 하이픈 넣기
     * @param str
     * @returns {string}
     */
    function auto_hypen_phone_number_build(str){

        str = str.replace(/[^0-9]/g, '');
        var tmp = '';

        // 서울 지역번호(02)가 들어가는 경우 1을 삽입
        if (str.substring(0, 2).indexOf('02') == 0) {
            var seoul = 1;
        } else {
            var seoul = 0;
        }

        // 문자열을 자르는 기준에서 서울의 값을 뺄쌤(-)한다.
        // 값이 1일경우 -1이 될것이고, 값이 0일경우 변화가 없다.
        if(str.length < (4 - seoul)) {
            return str;
        } else if(str.length < (7 - seoul)) {
            tmp += str.substr(0, (3 - seoul));
            tmp += "-";
            tmp += str.substr(3 - seoul);
            return tmp;
        } else if(str.length < (11 - seoul)) {
            tmp += str.substr(0, (3 - seoul));
            tmp += "-";
            tmp += str.substr((3 - seoul), 3);
            tmp += "-";
            tmp += str.substr(6 - seoul);
            return tmp;
        } else {
            tmp += str.substr(0, (3 - seoul));
            tmp += "-";
            tmp += str.substr((3 - seoul), 4);
            tmp += "-";
            tmp += str.substr(7 - seoul);
            return tmp;
        }
        return  str;
    }

    function auto_hypen_phone_number(id) {
        var id = document.getElementById(id);
        id.onkeyup = function(event) {
            event = event || window.event;
            var _val = this.value.trim();
            this.value = auto_hypen_phone_number_build(_val);
        };
    }

    /**
     * 숫자형식 데이터 3자리 콤마 처리
     * @param  {object} data         데이터
     * @param  {string} empty_output 0일 경우 출력될 문자열
     * @return {string}              포맷팅된 숫자형식 문자열
     */
    function number_format(data, empty_output) {

        var tmp = '';
        var number = '';
        var cutlen = 3;
        var comma = ',';
        var i;

        if (data === null || data === undefined || data === '' || data == 0) {
            return 0;
        } else {

            var sign = data.toString().match(/^[\+\-]/);
            if (sign) {
                data = data.toString().replace(/^[\+\-]/, '');
            }

            data = data.toString().replace(/[^0-9]/g, '');
        }

        len = data.length;
        mod = (len % cutlen);
        k = cutlen - mod;
        for (i = 0; i < data.length; i++) {
            number = number + data.charAt(i);

            if (i < data.length - 1) {
                k++;
                if ((k % cutlen) == 0) {
                    number = number + comma;
                    k = 0;
                }
            }
        }

        if (sign != null) number = sign + number;

        return number;
    }

    function only_number(data) {
        if (data) {
            return parseFloat(data.toString().replace(/[^0-9\+\-\.]/gi, ''));
        }
        return 0;
    }

    function to_string(data) {
        if (data === null || data === undefined || data === '') {
            return '';
        }
        return data;
    }

    function reset_input(elem) {
        $(elem).find('input[type=checkbox]').prop('checked', false);
        $(elem).find('input[type=radio]').prop('checked', false);
        $(elem).find('select').val('');
        $(elem).find('input').val('');
    }

    //일자 검사
    function isValidDate(dateString) {
        if(typeof dateString !== 'string') {
            return false;
        }
        if(dateString.replace(/^\s+|\s+$/g,"").length != 10) {
            return false;
        }
        var regEx = /^\d{4}-\d{2}-\d{2}$/;
        if(!dateString.match(regEx)) return false;  // Invalid format

        var d = new Date(dateString);
        var dNum = d.getTime();
        if(!dNum && dNum !== 0) return false; // NaN value, Invalid date
        return d.toISOString().slice(0,10) === dateString;
    }

    //시작시각 종료시각에따른 시간계산
    function start_end_time(form_select, start_time, end_time, tl_re_time, tl_time_input, tl_min_input){
        $(start_time).timepicker({
            default: 'now', // Set default time: 'now', '1:30AM', '16:30'
            autoClose : true,
            fromnow: 0,       // set default time to * milliseconds from now (using with default = 'now')
            twelveHour: false, // Use AM/PM or 24-hour format
            donetext: 'OK', // text for done-button
            cleartext: 'Clear', // text for clear-button
            canceltext: 'Cancel', // Text for cancel-button,
            container: 'body', // ex. 'body' will append picker to body,
            autoclose: true, // automatic close timepicker
            ampmclickable: true, // make AM PM clickable
            aftershow: function(){} //Function for after opening timepicker
        });

        $(end_time).timepicker({
            default: 'now', // Set default time: 'now', '1:30AM', '16:30'
            autoClose : true,
            fromnow: 0,       // set default time to * milliseconds from now (using with default = 'now')
            twelveHour: false, // Use AM/PM or 24-hour format
            donetext: 'OK', // text for done-button
            cleartext: 'Clear', // text for clear-button
            canceltext: 'Cancel', // Text for cancel-button,
            container: 'body', // ex. 'body' will append picker to body,
            autoclose: true, // automatic close timepicker
            ampmclickable: true, // make AM PM clickable
            aftershow: function(){} //Function for after opening timepicker
        });

        var tl_st_time_val = $(start_time).val(); //시작시간
        var tl_ed_time_val = $(end_time).val(); //종료시간
        //처음 값이 있으면 기본계산
        var today = new Date();
        var dd = today.getDate();
        var mm = today.getMonth()+1; //January is 0!
        var yyyy = today.getFullYear();
        var start_date_val = yyyy+'-'+mm+'-'+dd; //오늘날짜
        var end_date_val =  yyyy+'-'+mm+'-'+dd; //오늘날짜

        if(tl_st_time_val){
            if(tl_ed_time_val){
                var tl_st_time_val = $(start_time).val(); //시작시간
                var tl_ed_time_val = $(end_time).val(); //종료시간

                var start_date_array = start_date_val.split('-'); //시작일자 배열
                var end_date_array = end_date_val.split('-'); //종료일자 배열
                var tl_st_time_array = tl_st_time_val.split(':'); //시작시간 배열
                var tl_ed_time_array = tl_ed_time_val.split(':'); //종료시간 배열
                var start_times = start_date_array.concat(tl_st_time_array); //시작일자시간 합치기
                var end_times = end_date_array.concat(tl_ed_time_array); //종료일자시간 합치기

                var startDate = new Date(start_times[0],start_times[1],start_times[2],start_times[3],start_times[4],0);
                var endDate  = new Date(end_times[0],end_times[1],end_times[2],end_times[3],end_times[4],0);
                var tmp = (endDate.getTime() - startDate.getTime()) / 60000;
                var realmin = tmp % 60;
                var hours = Math.floor(tmp / 60);
                if(!tl_st_time_val){
                    $(tl_re_time).text('0시간 0분');
                } else if(!tl_ed_time_val){
                    $(tl_re_time).text('0시간 0분');
                } else {
                    $(tl_time_input).val(hours);
                    $(tl_min_input).val(realmin);
                    $(tl_re_time).text(hours+'시간 '+realmin+'분');
                }
            }
        }

        $(start_time).change( function() {
            var tl_st_time_val = $(start_time).val(); //시작시간
            var tl_ed_time_val = $(end_time).val(); //종료시간

            var start_date_array = start_date_val.split('-'); //시작일자 배열
            var end_date_array = end_date_val.split('-'); //종료일자 배열
            var tl_st_time_array = tl_st_time_val.split(':'); //시작시간 배열
            var tl_ed_time_array = tl_ed_time_val.split(':'); //종료시간 배열
            var start_times = start_date_array.concat(tl_st_time_array); //시작일자시간 합치기
            var end_times = end_date_array.concat(tl_ed_time_array); //종료일자시간 합치기

            var startDate = new Date(start_times[0],start_times[1],start_times[2],start_times[3],start_times[4],0);
            var endDate  = new Date(end_times[0],end_times[1],end_times[2],end_times[3],end_times[4],0);
            var tmp = (endDate.getTime() - startDate.getTime()) / 60000;
            var realmin = tmp % 60;
            var hours = Math.floor(tmp / 60);
            if(tmp < 0){
                toast('종료시간이 잘못되었습니다.');
                $(end_time).val('');
            } else {
                if(!tl_st_time_val){
                    $(tl_re_time).text('0시간 0분');
                } else if(!tl_ed_time_val){
                    $(tl_re_time).text('0시간 0분');
                } else {
                    $(tl_time_input).val(hours);
                    $(tl_min_input).val(realmin);
                    $(tl_re_time).text(hours+'시간 '+realmin+'분');
                }
            }
        });

        $(end_time).change( function() {
            var tl_st_time_val = $(start_time).val(); //시작시간
            var tl_ed_time_val = $(end_time).val(); //종료시간

            var start_date_array = start_date_val.split('-'); //시작일자 배열
            var end_date_array = end_date_val.split('-'); //종료일자 배열
            var tl_st_time_array = tl_st_time_val.split(':'); //시작시간 배열
            var tl_ed_time_array = tl_ed_time_val.split(':'); //종료시간 배열
            var start_times = start_date_array.concat(tl_st_time_array); //시작일자시간 합치기
            var end_times = end_date_array.concat(tl_ed_time_array); //종료일자시간 합치기

            var startDate = new Date(start_times[0],start_times[1],start_times[2],start_times[3],start_times[4],0);
            var endDate  = new Date(end_times[0],end_times[1],end_times[2],end_times[3],end_times[4],0);
            var tmp = (endDate.getTime() - startDate.getTime()) / 60000;
            var realmin = tmp % 60;
            var hours = Math.floor(tmp / 60);
            if(tmp < 0){
                toast('종료시간이 잘못되었습니다.');
                $(end_time).val('');
            } else {
                if(!tl_st_time_val){
                    $(tl_re_time).text('0시간 0분');
                } else if(!tl_ed_time_val){
                    $(tl_re_time).text('0시간 0분');
                } else {
                    $(tl_time_input).val(hours);
                    $(tl_min_input).val(realmin);
                    $(tl_re_time).text(hours+'시간 '+realmin+'분');
                }
            }
        });

    }

//시작시간 종료시간
    function start_end_date(form_select, start_date, end_date,container) {
        if(container === undefined){
            container = 'body'
        }
        maxdt = new Date($(end_date).val());
        mindt = new Date($(start_date).val());
        $(start_date).datepicker({
            autoClose: true,
            maxDate: maxdt,
            min: mindt,
            today: 'Today',
            showClearBtn: true,
            closeOnSelect: true, // Close upon selecting a date,
            container: container, // ex. 'body' will append picker to body,
        });

        $(end_date).datepicker({
            autoClose: true,
            max: maxdt,
            minDate: mindt,
            today: 'Today',
            showClearBtn: true,
            closeOnSelect: true, // Close upon selecting a date,
            container: container, // ex. 'body' will append picker to body,
        });
        //시작일 값이 바뀔때 min값 변경
        $(start_date).change(function(){
            start_end_date(form_select, start_date, end_date);
        });

        //종료일 값이 바뀔때 max값 변경
        $(end_date).change(function(){
            start_end_date(form_select, start_date, end_date);
        });
    }

    function max_length_check(object) {
        if (object.value.length > object.maxLength) {
            object.value = object.value.slice(0, object.maxLength);
        }
    }

// 생년월일 가져오기
    function get_age(dateString, date) {

        var today = new Date();
        if (date) today = new Date(date);
        var birthDate = new Date(dateString);
        var age = today.getFullYear() - birthDate.getFullYear();
        var m = today.getMonth() - birthDate.getMonth();
        if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
            age--;
        }
        if (age == NaN) age = '';
        return age;
    }

    /**
     * Input Select 엔터키로 다음 포커스 이동
     * @param findId
     */
    function onenter_focus(findId, lastId) {

        var selector = 'select, input, textarea';
        var items = findId.find(selector);

        items.keydown(function(e) {
            if (e.keyCode == 9 || e.keyCode == 13) {
                e.preventDefault();
                return;
            }
        });

        items.on('keyup', function(e) {
            e.preventDefault();

            var key = e.keyCode || e.which,
                thisIndex = $(this).index(selector),
                thisPrev = $(selector).eq(thisIndex - 1),
                thisNext = $(selector).eq(thisIndex + 1);

            if (key === 13 && e.shiftKey) {
                thisPrev.focus();
                return false;
            } else if (key == 13 || key == 9) {

                // 비활성화시 건너뛰기
                if (thisNext.prop('disabled') || thisNext.prop('readonly')) {
                    var e = jQuery.Event('keyup', {keyCode: 13});
                    thisNext.trigger(e);
                    return;
                }

                // 다음값이 없을경우
                if (thisNext.length == 0) {
                    if (lastId) findId.find(lastId).click();
                    return false;
                }

                thisNext.focus();
                return false;
            }
        });
    }

    /*====클립보드 관련====*/
    function copy_clipboard_user(url){
        var temp = $("<input>");
        $("body").append(temp);
        temp.val(url).select();
        document.execCommand("copy");
        temp.remove();
        toastr['success']('URL이 클립보드에 복사 되었습니다.');
    }
    //관리자페이지에서 사용
    function copy_clipboard(elem){
        var url_input = document.getElementById(elem);
        url_input.select();
        document.execCommand("copy");
        url_input.blur();
        toast('URL이 클립보드에 복사 되었습니다.');
    }

    /*====사이트,모달닫기====*/
    /**
     * @param close_modal
     * @param callBack - 콜백함수
     */
    function side_close(close_modal, callBack = function(){}){
        $(close_modal).sidenav('close');
        callBack();
    }
    function modal_close(close_modal){
        $(close_modal).modal('close');
    }

    /*====쿠기 입력,삭제====*/
    function setCookie(cname, cvalue, exdays) {
        var d = new Date();
        d.setTime(d.getTime() + (exdays*24*60*60*1000));
        var expires = "expires="+ d.toUTCString();
        document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
    }

    function getCookie(cname) {
        var name = cname + "=";
        var decodedCookie = decodeURIComponent(document.cookie);
        var ca = decodedCookie.split(';');
        for(var i = 0; i <ca.length; i++) {
            var c = ca[i];
            while (c.charAt(0) == ' ') {
                c = c.substring(1);
            }
            if (c.indexOf(name) == 0) {
                return c.substring(name.length, c.length);
            }
        }
        return "";
    }

//    /**
//     * 로딩바 기본 1초
//     * @param timer
//     */
//    function loading_bar(parrent_html,timer) {
//        var loading_html = $("#loader_active");
//        $('#overlay').show();
//        loading_html.show();
//        setTimeout(function() {
//            $('#overlay').hide();
//            loading_html.hide();
//            loading_html.empty();
//        }, timer || 1000);
//    }

    /**
     *
     * @param input
     * @param ex (<a><b><c>)
     * @returns {XML|string}
     */
    function strip_tags(input, allowed) {
        allowed = (((allowed || '') + '').toLowerCase().match(/<[a-z][a-z0-9]*>/g) || []).join('');
        var tags = /<\/?([a-z][a-z0-9]*)\b[^>]*>/gi, commentsAndPhpTags = /<!--[\s\S]*?-->|<\?(?:php)?[\s\S]*?\?>/gi;
        return input.replace(commentsAndPhpTags, '').replace(tags, function($0, $1) {
            return allowed.indexOf('<' + $1.toLowerCase() + '>') > -1 ? $0 : '';
        });
    }

    function toast(message, timer) {
        M.toast({html: message});
    }

    function toast_error(timer) {
        M.toast({html: '오류가 발생되었습니다. 다시 시도하여주세요.'});
    }


// 숫자 2개 표시
    function pad(n) {
        n = n + '';
        return n.length >= 2 ? n : new Array(2 - n.length + 1).join('0') + n;
    }

// 오늘 날짜 가져오기
    function today_date() {
        var today = new Date();
        var y = today.getFullYear();
        var M = today.getMonth() + 1;
        var d = today.getDate();
        return y + pad(M) + pad(d);
    }

    function month_one_date() {
        var today = new Date();
        var y = today.getFullYear();
        var M = 1;
        var d = 1;
        return y + pad(M) + pad(d);
    }

//그달의 시작일
    function month_start_date() {
        var today = new Date();
        var y = today.getFullYear();
        var M = today.getMonth() + 1;
        return y + pad(M) + '01';
    }

////그달의 마지막일
    function month_last_date() {
        var today = new Date();
        var y = today.getFullYear();
        var M = today.getMonth() + 1;
        var d = ( new Date(y, M, 0) ).getDate();

        return y + pad(M) + pad(d);
    }

    function string_date_format(date) {
        if (!date) return '';
        return date.slice(0, 4) + '-' + date.slice(4, 6) + '-' + date.slice(6, 8);
    }

    function date_sub_str(date) {
        if (!date) return '';
        return date.substr(0, 10);
    }

    function month_add(date, month) {
        var temp = date;
        temp = new Date(date.getFullYear(), date.getMonth(), 1);
        temp.setMonth(temp.getMonth() + (month + 1));
        temp.setDate(temp.getDate() - 1);

        if (date.getDate() < temp.getDate()) {
            temp.setDate(date.getDate());
        }
        return temp;
    }

    function get_string_to_date(date, type) {
        var y = date.getFullYear();
        var M = date.getMonth() + 1;
        var d = date.getDate();

        if (type) {
            return y + type + this.pad(M) + type + this.pad(d);
        } else {
            return y + '년 ' + this.pad(M) + '월 ' + this.pad(d) + '일';
        }
    }

	//새창 띄우기
    function new_win(url, name, w, h, left, top, scroll) {
        var win_left, win_top;
        if (left == 'random') {
            win_left = (screen.width)
                ? Math.floor(Math.random() * (screen.width - w))
                : 100;
        } else if (left == 'center') {
            win_left = (screen.width) ? (screen.width - w) / 2 : 100;
        } else if (left) {
            win_left = left;
        } else {
            win_left = 5;
        }
        if (top == 'random') {
            win_top = (screen.height) ? Math.floor(Math.random() *
                ((screen.height - h) - 75)) : 100;
        } else if (top == 'center') {
            win_top = (screen.height) ? (screen.height - h) / 2 : 100;
        } else if (top) {
            win_top = top;
        } else {
            win_top = 5;
        }
        var settings = 'width=' + w + ',height=' + h + ',top=' + win_top + ',left=' +
            win_left + ',scrollbars=' + scroll +
            ',location=no,directories=no,status=no,menubar=no,toolbar=no,resizable=no';
        var myWindow = window.open(url, name, settings);
        if (myWindow) myWindow.focus();
    }

    var get_url_parameter = function get_url_parameter(sParam) {
        var sPageURL = decodeURIComponent(window.location.search.substring(1)),
            sURLVariables = sPageURL.split('&'),
            sParameterName,
            i;

        for (i = 0; i < sURLVariables.length; i++) {
            sParameterName = sURLVariables[i].split('=');

            if (sParameterName[0] === sParam) {
                return sParameterName[1] === undefined ? true : sParameterName[1];
            }
        }
    };
    function goPage(url) {
        window.location.href = url;
    }
    function isFullScreen(cm) {
        return /\bCodeMirror-fullscreen\b/.test(cm.getWrapperElement().className);
    }
    function winHeight() {
        return window.innerHeight || (document.documentElement || document.body).clientHeight;
    }
    function setFullScreen(cm, full) {
        var wrap = cm.getWrapperElement(), scroll = cm.getScrollerElement();

        if (full) {
            wrap.className += " CodeMirror-fullscreen";
            scroll.style.height = (winHeight()-100)+ "px";
            document.documentElement.style.overflow = "hidden";
        } else {
            wrap.className = wrap.className.replace(" CodeMirror-fullscreen", "");
            scroll.style.height = "";
            document.documentElement.style.overflow = "";
        }
        cm.refresh();
    }
//
//	var is_lodding = null;
//	function lodding(){
//		$('#lodding').css("display","block");
//		
//	}
//	function lodding_close(){
//		setTimeout(function() { 
//			$('#lodding').css("display","none");
//		}, 500);
//	}
//

    function check_mobile(){
        var isMobile = false; //initiate as false
        if(/(android|bb\d+|meego).+mobile|avantgo|bada\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|ipad|iris|kindle|Android|Silk|lge |maemo|midp|mmp|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\.(browser|link)|vodafone|wap|windows (ce|phone)|xda|xiino/i.test(navigator.userAgent)
            || /1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\-(n|u)|c55\/|capi|ccwa|cdm\-|cell|chtm|cldc|cmd\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\-s|devi|dica|dmob|do(c|p)o|ds(12|\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\-|_)|g1 u|g560|gene|gf\-5|g\-mo|go(\.w|od)|gr(ad|un)|haie|hcit|hd\-(m|p|t)|hei\-|hi(pt|ta)|hp( i|ip)|hs\-c|ht(c(\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\-(20|go|ma)|i230|iac( |\-|\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\/)|klon|kpt |kwc\-|kyo(c|k)|le(no|xi)|lg( g|\/(k|l|u)|50|54|\-[a-w])|libw|lynx|m1\-w|m3ga|m50\/|ma(te|ui|xo)|mc(01|21|ca)|m\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\-2|po(ck|rt|se)|prox|psio|pt\-g|qa\-a|qc(07|12|21|32|60|\-[2-7]|i\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\-|oo|p\-)|sdk\/|se(c(\-|0|1)|47|mc|nd|ri)|sgh\-|shar|sie(\-|m)|sk\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\-|v\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\-|tdg\-|tel(i|m)|tim\-|t\-mo|to(pl|sh)|ts(70|m\-|m3|m5)|tx\-9|up(\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\-|your|zeto|zte\-/i.test(navigator.userAgent.substr(0,4))) {
            isMobile = true;
        }

        return isMobile;
    }

    function set_imag_map(){
        $.each($('img[usemap]'),function(){
            var temp = new Image();
            temp.src = $(this).attr('src');
            var originWidth = temp.width;
            var originHeight = temp.height;
            var this_width = $(this).width();
            var this_height = $(this).height();
            var wpercent = this_width/100,
                hpercent = this_height/100,
                map = $(this).attr('usemap').replace('#', ''),
                c = 'coords';
            $('map[name="' + map + '"]').find('area').each(function() {
                if (!$(this).data(c))
                    $(this).data(c, $(this).attr(c));
                var coords = $(this).data(c).split(','),
                    coordsPercent = new Array(coords.length);
                for (var i = 0; i < coordsPercent.length; ++i) {
                    if(isNaN(parseInt(((coords[i]/originWidth)*100)*wpercent)) || isNaN(parseInt(((coords[i]/originHeight)*100)*hpercent))){

                    }
                    else{
                        if (i % 2 === 0)
                            coordsPercent[i] = parseInt(((coords[i]/originWidth)*100)*wpercent);
                        else
                            coordsPercent[i] = parseInt(((coords[i]/originHeight)*100)*hpercent);
                    }

                }
                if(coordsPercent.toString().length > 3){
                    $(this).attr(c, coordsPercent.toString());
                }

            });
        });
    }
    $(function(){
        //이미지맵 적응형 적용
        setTimeout(function(){
            set_imag_map();
        },500);
        $(window).resize(function(){
            setTimeout(function(){
                set_imag_map();
            },800);
        });
    });
}

  function newWin(url, name, w, h, left, top, scroll) {
    var win_left, win_top;
    if(left == "random") {
      win_left = (screen.width)  ? Math.floor(Math.random()*(screen.width-w)) : 100;
    } else if(left == "center") {
      win_left = (screen.width)  ? (screen.width-w)/2 : 100;
    } else if(left) {
      win_left = left;
    } else {
      win_left = 5;
    }
    if(top == "random") {
      win_top = (screen.height) ? Math.floor(Math.random()*((screen.height-h)-75)) : 100;
    } else if (top == "center") {
      win_top = (screen.height) ? (screen.height-h)/2 : 100;
    } else if(top) {
      win_top = top;
    } else {
      win_top = 5;
    }
    var settings = "width="+ w +",height="+ h +",top="+ win_top +",left="+ win_left +",scrollbars="+ scroll +",location=no,directories=no,status=no,menubar=no,toolbar=no,resizable=no";
    var myWindow = window.open(url,name,settings);
    if(myWindow) myWindow.focus();
  }
