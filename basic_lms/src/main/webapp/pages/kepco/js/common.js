    // 쿠키 설정하기 expiredays = 1 = 하루 기준
    function setCookie(name, value, expiredays) {
        let todayDate = new Date();
        todayDate.setDate(todayDate.getDate() + expiredays);
        document.cookie = name + '=' + escape(value) + '; path=/; expires=' + todayDate.toGMTString() + ';';
    }
    
    // 쿠키 가져오기
    function getCookie(name) {
        let nameOfCookie = name + '=';
        let x = 0;
        while (x <= document.cookie.length) {
        let y = x + nameOfCookie.length;
        if (document.cookie.substring(x, y) == nameOfCookie) {
            if ((endOfCookie = document.cookie.indexOf(';', y)) == -1) endOfCookie = document.cookie.length;
            return unescape(document.cookie.substring(y, endOfCookie));
        }
        x = document.cookie.indexOf(' ', x) + 1;
        if (x == 0) break;
        }
        return '';
    }


	function getCookies(name) {
		let cookies = document.cookie.split(';');
		let result = [];

		for (let i = 0; i < cookies.length; i++) {
			let cookie = cookies[i].trim();
			if (cookie.indexOf(name) === 0) {
			result.push(cookie);
			}
		}

		return result;
	}
	
// AJAX 시작,종료시 로딩바 표시
$(document).ajaxStart(function() {
	gblLoadingBar(true);
});
$(document).ajaxStop(function() {
	gblLoadingBar(false);
});

function gblLoadingBar(isShow) {
	if(isShow) {
		$('#overlay, #loader_active').show();
	} else {
		$('#overlay, #loader_active').hide();
	}
}
	
//전역 ajax fail 설정
$(document).ready(function() {
    // 원래의 $.ajax 메서드를 저장합니다.
    var originalAjax = $.ajax;

    // $.ajax 메서드를 재정의합니다.
    $.ajax = function(options) {
        // 사용자 정의 done과 fail 핸들러를 저장합니다.
        var userDone = options.done;
        var userFail = options.fail;

        // jQuery Deferred 객체를 반환하는 AJAX 요청을 생성합니다.
        var deferred = originalAjax.apply(this, arguments);

        // 전역 done 핸들러를 추가합니다.
//        deferred.done(function(data, textStatus, xhr) {
//			
//        });
        // 전역 fail 핸들러를 추가합니다.
        deferred.fail(function(xhr, textStatus, errorThrown) {
            console.error("AJAX 요청 실패:", textStatus, errorThrown);
            if(xhr.responseJSON && typeof xhr.responseJSON.message != "undefined") {
					alert(xhr.responseJSON.message);
			}
			if(xhr.status != 200) {
				alert("HTTP "+xhr.status+ " 에러 입니다.");
			}
        });

        // 사용자 정의 done 핸들러를 추가합니다.
        if (userDone) {
            deferred.done(userDone);
        }

        // 사용자 정의 fail 핸들러를 추가합니다.
        if (userFail) {
            deferred.fail(userFail);
        }

        return deferred;
    };
});
