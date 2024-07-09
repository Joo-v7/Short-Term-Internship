function ini_set(container) {
	// 결과적으로 큰 의미 없는 코드
//	if(container === undefined){
//		container = 'body';
//	}
	
	// input-text의 value가 이미 입력된 경우 필요. label 위치 재조정.
//	M.updateTextFields();
	
	
//	$('.tooltipped').tooltip({
//		container:container,
//	});
	
	// select 에 대해 materialize-ui 적용
//	$('select').formSelect();
	
	// Lightbox? 필요한 곳에서 사용하는 걸로...
//	$('.materialboxed').materialbox();
	
	// 쓰기 너무 불편하다 브라우저 제공으로 사용
//	$('.datepicker').datepicker({
//		autoClose : true,
//		selectMonths: true, // Creates a dropdown to control monthz
//		selectYears: 50, // Creates a dropdown of 15 years to control year,
//		today: 'Today',
//		setDefaultDate: true,
//		showClearBtn: true,
//		closeOnSelect: true, // Close upon selecting a date,
//		container: container // ex. 'body' will append picker to body,
//	});	

//	$('.timepicker').timepicker({
//		default: 'now', // Set default time: 'now', '1:30AM', '16:30'
//		autoClose : true,
//		fromnow: 0,       // set default time to * milliseconds from now (using with default = 'now')
//		twelveHour: false, // Use AM/PM or 24-hour format
//		donetext: 'OK', // text for done-button
//		cleartext: 'Clear', // text for clear-button
//		canceltext: 'Cancel', // Text for cancel-button,
//		container:container, // ex. 'body' will append picker to body,
//		autoclose: true, // automatic close timepicker
//		ampmclickable: true, // make AM PM clickable
//		aftershow: function(){} //Function for after opening timepicker
//	});
}

$(function() {
	// 이상한 패턴 삭제
//  /*데이타테이블 Lang*/
//  lang_kor = {
//    "decimal": "",
//    "emptyTable": "데이터가 없습니다.",
//    "info": "Page _START_ - _END_ (Total _TOTAL_)",
//    "infoEmpty": "0명",
//    "infoFiltered": "(전체 _TOTAL_ 명 중 검색결과)",
//    "infoPostFix": "",
//    "thousands": ",",
//    "lengthMenu": "_MENU_ ",
//    "loadingRecords": "로딩중...",
//    "processing": "검색중...",
//    "search": "결과내 검색 : ",
//    "zeroRecords": "검색된 데이터가 없습니다.",
//    "paginate": {
//      "first": "First",
//      "last": "End",
//      "next": ">",
//      "previous": "<"
//    },
//    "aria": {
//      "sortAscending": " :  오름차순 정렬",
//      "sortDescending": " :  내림차순 정렬"
//    }
//  };
	
	// materialize-ui 적용
	M.updateTextFields();
	$('select').formSelect();
	
	// 데이터테이블 기본값
	$.extend(true, $.fn.dataTable.defaults, {
		autoWidth : false,
		lengthChange : false,
		searching : false,
		bDestroy : true,
		bInfo : false,
		responsive : true,
		processing : true,
		ordering : true,
		select : false,
		paging : true,
		pageLength : 10,
		fixedHeader : {
			header : false,
			footer : false,
		},
		language: {
			"decimal": "",
			"emptyTable": "데이터가 없습니다.",
			"info": "Page _START_ - _END_ (Total _TOTAL_)",
			"infoEmpty": "0명",
			"infoFiltered": "(전체 _TOTAL_ 명 중 검색결과)",
			"infoPostFix": "",
			"thousands": ",",
			"lengthMenu": "_MENU_ ",
			"loadingRecords": "로딩중...",
			"processing": "검색중...",
			"search": "결과내 검색 : ",
			"zeroRecords": "검색된 데이터가 없습니다.",
			"paginate": {
				"first": "<<",
				"last": ">>",
				"next": ">",
				"previous": "<"
			},
			"aria": {
				"sortAscending": " :  오름차순 정렬",
				"sortDescending": " :  내림차순 정렬"
			}
		}
	});
	
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

});