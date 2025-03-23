$(document).ready(function(){

	// 상단 메뉴
	$(".hd_menu_box > li").hover(function(event) {
		event.preventDefault();
	})
	$(".hd_menu_box > li").on("mouseover focus", function(event) {
		event.preventDefault();
		$(".hd_menu_box .hd_depth_box").removeClass('active');
		$(this).find(".hd_depth_box").addClass('active');
	});
	$(".hd_menu_box > li").mouseout(function(event) {
		event.preventDefault();
	})


	// 드롭다운 일반 - 클래스 토글형
	if($('.dropdown_box_disc')){
		$('.dropdown_box_disc').click(function(){
			$(this).toggleClass('on');
		});
	};

	$('ul.con-tabs li').click(function(){
		var tab_id = $(this).attr('data-tab');

		$('ul.con-tabs li').removeClass('current');
		$('.tab-content').removeClass('current');

		$(this).addClass('current');
		$("#"+tab_id).addClass('current');
	});
	
	$('ul.con-tabs2 li').click(function(){
		var tab_id = $(this).attr('data-tab');

		$('ul.con-tabs2 li').removeClass('current2');
		$('.tab-content2').removeClass('current2');

		$(this).addClass('current2');
		$("#"+tab_id).addClass('current2');
	});

	

	// 차트 퍼포먼스
	if($('#chartdiv')){
		// 퍼포먼스 요소 생성
		var pp_box = document.createElement('div');
		pp_box.classList.add('pp_box'); // 제이쿼리 클래스 추가 함수로 불가능(바닐라 사용)
		$('#chartdiv').append(pp_box);
		// 정원 형태를 만듦 (비율 맞추기 위한 width or height 값을 이용)
		let width_wh = $('#chartdiv').outerWidth() < $('#chartdiv').outerHeight() ? $('#chartdiv').outerWidth() : $('#chartdiv').outerHeight();
		$('#chartdiv .pp_box').css('width', width_wh * 0.8);
		$('#chartdiv .pp_box').css('height', width_wh * 0.8);
	};
	
	// 수행결과 표 - 드롭다운
	if($('.cursor_line')){
		$('.cursor_line').click(function(){
			$(this).find('.toggle_icon').toggleClass('on');
			$(this).next().toggleClass('on');
		});
	};


	// 세부 수행결과 표(영역)
	let t_result_wrap = $('.trainging_result_info_wrap');



})
