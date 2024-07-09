$.extend($.fn.dataTable.defaults, {
	columnDefs: [
		{
			targets: '_all',
			defaultContent: '-'
		}
	],
	autoWidth : true,
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
	language : {
		decimal : ".",
		emptyTable : "데이터가 없습니다.",
		info : "전체: _TOTAL_, 페이지: _PAGE_/_PAGES_",
		infoEmpty : "전체: 0, 페이지: 0/0",
		infoFiltered : "최대 _MAX_건의 데이터만 조회",
		infoPostFix : "",
		thousands : ",",
		lengthMenu : "페이지당 표시 : _MENU_건",
		loadingRecords : "데이터 로딩 중...",
		processing : "데이터 검색 중...",
		search : "결과내 검색:",
		zeroRecords : "데이터가 없습니다.",
		paginate : {
			first : "처음",
			last : "마지막",
			next : "다음",
			previous : "이전"
		},
		aria : {
			sortAscending : "오름차순 정렬",
			sortDescending : "내림차순 정렬"
		}
	}
});

$.fn.dataTable.render.date = function(fmt) {
	return function(data, type, row) {
		if (type != 'display' || typeof data != 'string') {
			return data;
		}
		
		// yyyy-MM-dd HH:mm:ss
		if (!fmt) {
			return data.substring(0, 10);
		}
	};
}

$.fn.dataTable.render.datetime = function(fmt) {
	return function(data, type, row) {
		if (type != 'display' || typeof data != 'string') {
			return data;
		}
		
		// yyyy-MM-dd HH:mm:ss
		if (!fmt) {
			return data.substring(0, 16);
		}
	};
}

$.fn.dataTable.render.yn = function(fmt) {
	return function(data, type, row) {
		if (type != 'display' || typeof data != 'string') {
			return data;
		}
		
		if(data == 'y' || data == 'Y') {
			return '<i class="material-icons green-text">check_circle</i>';
		}
		if(data == 'n' || data == 'N') {
			return '<i class="material-icons red-text">remove_circle</i>';
		}
		return '';
	};
}

/** 역할명 */
$.fn.dataTable.render.workRoleNm = function() {
	return function(data, type, row) {
		if (type != 'display' || typeof data != 'string') {
			return data;
		}
		
		if (data == '1') {
			return "주작업자";
		} else if (data == '2') {
			return "보조작업자";
		} else if (data == '3') {
			return "지상작업자";
		} else if (data == '4') {
			return "작업책임자";
		}
		return '알수없음';
	};
}
