$(function(){
    if($("#member_phone").length > 0){
        auto_hypen_phone_number('member_phone');
    }
    if($("#mb_tel").length > 0){
        auto_hypen_phone_number('mb_tel');
    }

    $("#agre_all").change(function(){
        var checked = false;
        if($(this).is(":checked")) {
            checked = true;
        }
        $("input[name='agreecheck[]']").each(function() {
            $(this).prop("checked", checked);
        });

    });
    $(".join_group_list > ul > li").click(function(){
        var idx = $(".join_group_list > ul > li").index(this);

        if(idx == 2){
            $(".param_r2").val("5");

        }else if(idx == 1){
            $(".param_r2").val("3");
        }else{
            $(".param_r2").val("1");
        }
        $(".box_item").hide();
        $(".box_item").eq(1).show();
        return false;
    });



    //저장
    $("#frm").submit(function() {
        if( $("#agre_all").length > 0){
            var cb = ['agreecheck1', 'agreecheck2', 'agreecheck3'];
            var labels = ['이용약관','회원가입약관','개인정보 수집 및 이용'];
            var isValid = true;
            $.each(cb, function(index, item) {
                if($("input:checkbox[id="+item+"]").is(":checked") == false ){
                    alert(labels[index] + " 동의 후 진행 가능합니다.");
                    $('#'+item).focus();
                    isValid = false;
                    return false;
                }
            });
            if(!isValid) return false;
        }
        return true;
    });
})

var check_id = false; //아이디 사용가능 여부
var check_pw = false; // 패스워드 사용가능 여부
var check_pw_r = false;//패스워드 사용가능 여부
var birth_y = true; // 생년월일 사용가능 여부
var birth_m = true; // 생년월일 사용가능 여부
var birth_d = true;// 생년월일 사용가능 여부

var tel = true;// 일반전화 사용가능 여부

var hp = false;// 휴대전화 사용가능 여부

var check_email = false; //이메일 사용가능 여부

//숫자 형식 체크
function fnIsNum(num){
    return (/^[0-9]+$/).test(num) ? true : false;
}

//공백 검사
function checkNull(str) {
    return (str == null || str.replace(/\s/gi,"") == "");
}

//회원아이디 검사
//아이디 대문자 불가
function fnIsuerid(str) {
	if(checkNull(str)) return false;
    var pattern = /^[a-z]{1}[0-9a-z]{3,11}$/;
    return pattern.test(str);
}

//영문자,숫자,_만 가능, 첫글자는 영문자,숫자만..
function checkMemberID(str) {
    if(checkNull(str)) return false;
    var pattern = /(^([a-zA-Z0-9]+)([a-zA-Z0-9_]+$))/;
    return pattern.test(str);
}

//특수문자 입력제한
function checkValidText(str) {
    var chars = "'&@%*_[]#!$^";
    for(i=0; i<str.length; i++) {
        for(j=0; j<chars.length; j++)
            if(str.charAt(i) == chars.charAt(j)) return false;
    }
    return true;
}

//패스워드 검사
function fnIsPwd(pw) {
    return (/^[0-9a-zA-Z]+$/).test(pw) ? true : false;
}

//이메일 형식검사
function fnIsEmail(em){
    return (/\w+([-+.]\w+)*@\w+([-.]\w+)*\.[a-zA-Z]{2,4}$/).test(em);
}

/*
function fnIsEmailNew(em){
	return (/^[a-z0-9_+.-]+@([a-z0-9-]+\.)+[a-z0-9]{2,4}$/i).test(em);
}


//일반전화 형식 체크
function fnIsphone(hp){
	var arg = "";
	return eval("(/01[016789]" + arg + "[1-9]{1}[0-9]{2,3}" + arg + "[0-9]{4}$/).test(hp)");

//휴대전화 형식 체크
function fnIsMobile(hp){
	var arg = "";
	return eval("(/01[016789]" + arg + "[1-9]{1}[0-9]{2,3}" + arg + "[0-9]{4}$/).test(hp)");
}
*/
//아이디 형식 체크
function id_check() {
    var member_id = $("input[name='member_id']","#frm").val();

//    if ($.trim(member_id).length < 4 || fnIsuerid(member_id) == false ){
//        check_id = false;
//        $(".id_ok").text("4~12자리 영문(소문자)+숫자로 설정하여주세요.");
//        $("input[name='check_id']","#frm").val("2");
//        return false;
//    }

    if (checkValidText(member_id) == false ){
        check_id = false;
        $(".id_ok").text("특수문자 사용은 불가능합니다.");
        $("input[name='check_id']","#frm").val("2");
        return false;
    }
    
    if (fnIsuerid(member_id) == false ){
        check_id = false;
        $(".id_ok").text("4~12자리 영문(소문자)+숫자로 설정하여주세요.");
        $("input[name='check_id']","#frm").val("2");
        return false;
    }

    //아이디 중복체크
    var query = "";
    var site = $("input[name='site']").val();
    var mn_idx = $("input[name='mn']",$("#frm")).val();

    $.ajax({
        url: "/cms/member/checkId?site="+site+"&mn="+mn_idx+"&member_id="+member_id,
        type: "get",
        success: function(data) {
            $(".id_ok").text(data.message);
            if(data.code == 1){
                check_id = true;
            }else{
                check_id = false;
            }
            $("input[name='check_id']","#frm").val(data.code);
        },
        error: function(xhr, status, error) {
            alert("시스템 에러\n\n" + xhr.responseText);
        },
        dataType: "json",
        timeout:60,
        async: false
    });
}

//비밀번호 형식 체크
function paswd_check() {
    var member_id = $("input[name='member_id']","#frm").val();
    var member_pw = $("input[name='member_pw']","#frm").val();
    var member_pw_r = $("input[name='member_pw_r']","#frm").val();

    if(fnChkPwContinuity(member_pw) == false){
        check_pw = false;
        $(".passwd_1").hide();
        $(".passwd_1").text("");
        $(".passwd_error1").show();
        $(".passwd_error1").text("연속된 4자리 이상 숫자나 영문자, 동일한 4자리 이상 숫자나 영문자는 비밀번호로 설정할 수 없습니다.");
        $("input[name='check_pw']","#frm").val("2");
        return false;
    }else if (fnChkPwSameIDCheck(member_id, member_pw) == false) {
        check_pw = false;
        $(".passwd_1").hide();
        $(".passwd_1").text("");
        $(".passwd_error1").show();
        $(".passwd_error1").text("ID와 동일한 문자가 연속 4자리 이상 포함될 경우 비밀번호로 설정 할 수 없습니다.");
        $("input[name='check_pw']","#frm").val("2");
        return false;
    }else if (member_pw.length < 6 || fnIsPwdNumberAndChar(member_pw) == false){
        check_pw = false;
        $(".passwd_1").hide();
        $(".passwd_1").text("");
        $(".passwd_error1").show();
        $(".passwd_error1").text("6~20자리의 영문 대/소문자, 숫자, 특수문자를 사용하세요");
        $("input[name='check_pw']","#frm").val("2");
        return false;
    }else{
        check_pw = true;
        $(".passwd_1").show();
        $(".passwd_1").text("사용가능합니다.");
        $(".passwd_error1").hide();
        $(".passwd_error1").text("");
        $("input[name='check_pw']","#frm").val("1");
        return true;
    }

    if(member_pw_r != "") {
        paswd_check2();
        return false;
    }
    return false;
}

//비밀번호 일치 확인
function paswd_check2() {
    var member_pw = $("input[name='member_pw']","#frm").val();
    var member_pw_r = $("input[name='member_pw_r']","#frm").val();

    if (member_pw_r == "") {
        check_pw_r = false;
        $(".passwd_2").hide();
        $(".passwd_2").text("");
        $(".passwd_error2").show();
        $(".passwd_error2").text("입력하신 비밀번호 확인을 위해 다시 한번 입력해 주세요.");
        $("input[name='check_pw']","#frm").val("2");
        return false;
    } else if (member_pw != member_pw_r) {
        check_pw_r = false;
        $(".passwd_2").hide();
        $(".passwd_2").text("");
        $(".passwd_error2").show();
        $(".passwd_error2").text("비밀번호가 일치하지 않습니다.");
        $("input[name='check_pw']","#frm").val("2");
        return false;
    } else {
        check_pw_r = true;
        $(".passwd_2").show();
        $(".passwd_2").text("비밀번호가 일치합니다.");
        $(".passwd_error2").hide();
        $(".passwd_error2").text("");
        $("input[name='check_pw']","#frm").val("1");
        return true;
    }
    return false;
}


//비밀번호 형식 체크
function fnIsPwdNumberAndChar(pw) {
    if ((/^[0-9a-zA-Z\!\"\#\$\%\&\'\(\)\*\+\,\-\.\/\:\;\<\=\>\?\@\[\\\]\^\_\`\{\|\}\~]+$/).test(pw) == true)
    {
        if (/[a-zA-Z]/.test(pw) && (/[0-9]/.test(pw)|| /[\!\"\#\$\%\&\'\(\)\*\+\,\-\.\/\:\;\<\=\>\?\@\[\\\]\^\_\`\{\|\}\~]/.test(pw))){
            return true;
        }else{
            return false;
        }
    }else{
        return false;
    }
}
//비밀번호 연속성 검사
function fnChkPwContinuity(pw){
    var nowChar = "";
    var nextChar = "";
    var len = pw.length;
    var equalscount = 0;
    var continuePlusCount = 0;
    var continueMinusCount = 0;
    for(var i = 0 ; i < len ; i++){
        if(i+1<len){
            nowChar = pw.charCodeAt(i);
            nextChar = pw.charCodeAt(i+1);
            if(nowChar==nextChar){
                equalscount++;
            }else if(nowChar!=nextChar&&equalscount==2){
                equalscount=0;
            }
            if((nowChar+1)==nextChar){
                continuePlusCount++;
            }else if((nowChar+1)!=nextChar&&continuePlusCount==2){
                continuePlusCount = 0;
            }
            if ((nowChar-1)==nextChar){
                continueMinusCount++;
            }else if((nowChar-1)!=nextChar&&continuePlusCount==2){
                continueMinusCount = 0;
            }
        }
        if(continuePlusCount >= 3 || continueMinusCount >= 3||equalscount >= 3){
            return false;
        }
    }
}
//아이디/비밀번호 일치 검사
function fnChkPwSameIDCheck(id,pw){
    var cnt = 0;
    var temp = "";
    var temp_id,temp_pass;

    for(var i = 0; i < id.length; i++){
        temp_id = id.charAt(i);

        for(var j=0;j < pw.length;j++){
            if (cnt >0){
                j = tmp_pass_no + 1;
            }
            if (temp == "r"){
                j=0;
                temp="";
            }
            temp_pass = pw.charAt(j);
            if (temp_id == temp_pass){
                cnt = cnt + 1;
                tmp_pass_no = j;
                break;
            }else if(cnt > 0 && j > 0){
                temp="r";
                cnt = 0;
            }else{
                cnt = 0;
            }
        }
        if (cnt > 3) break;
    }
    if (cnt > 3){
        return false;
    }
}


//이메일 형식체크
function emaile_check() {
    var member_email = $("input[name='member_email']","#frm").val();

    if (member_email.length < 5){
        check_email = false;
        $(".email_error").text("올바른 이메일 주소가 아닙니다.");
        $("input[name='check_email']","#frm").val("2");
        return false;
    } else if (fnIsEmail(member_email) == false){
        check_email = false;
        $(".email_error").text("올바른 이메일 주소가 아닙니다.");
        $("input[name='check_email']","#frm").val("2");
        return false;
    } else {
        check_email = true;
        $(".email_error").text(" ");
        $("input[name='check_email']","#frm").val("1");
        return true;
    }
    return false;
}


//생년월일 체크 (년)
function year_check() {
    var member_birth_y = $("input[name='member_birth_y']","#frm").val();

    var today = new Date(); // 날자 변수 선언
    var yearNow = today.getFullYear();
    var adultYear = yearNow-100;


    if(fnIsNum(member_birth_y) == false){
        birth_y = false;
        $("input[name='member_birth_y']","#frm").val("");
        $(".birth_error").text("숫자만 입력할 수 있습니다.");
        return false;
    }else if(member_birth_y < 1900  || member_birth_y.length < 4){
        birth_y = false;
        $("input[name='member_birth_y']","#frm").val("");
        //$(".birth_error").text("태어난 년을 잘못입력했습니다.");
        return false;
    }else{
        birth_y = true;
        $(".birth_error").text("");
        return true;
    }
}

//생년월일 체크 (월)
function month_check() {
    var member_birth_m = $("input[name='member_birth_m']","#frm").val();

    var today = new Date(); // 날자 변수 선언
    var yearNow = today.getFullYear();
    var adultYear = yearNow-20;


    if(fnIsNum(member_birth_m) == false){
        birth_m = false;
        $("input[name='member_birth_m']","#frm").val("");
        $(".birth_error").text("숫자만 입력할 수 있습니다.");
        return false;
    }else if(member_birth_m < 1 || member_birth_m > 12){
        birth_m = false;
        $("input[name='member_birth_m']","#frm").val("");
        //$(".birth_error").text("태어난 월을 잘못입력했습니다.");
        return false;
    }else{
        birth_m = true;
        $(".birth_error").text("");
        return true;
    }
}

//생년월일 체크 (일)
function day_check() {
    var member_birth_y = $("input[name='member_birth_y']","#frm").val();
    var member_birth_m = $("input[name='member_birth_m']","#frm").val();
    var member_birth_d = $("input[name='member_birth_d']","#frm").val();

    var today = new Date(); // 날자 변수 선언
    var yearNow = today.getFullYear();
    var adultYear = yearNow-20;


    if(fnIsNum(member_birth_d) == false){
        birth_d = false;
        $("input[name='member_birth_d']","#frm").val("");
        $(".birth_error").text("숫자만 입력할 수 있습니다.");
        return false;
    }else if(member_birth_d < 1 || member_birth_d > 31){
        birth_d = false;
        $("input[name='member_birth_d']","#frm").val("");
        //$(".birth_error").text("태어난 일을 잘못입력했습니다.");
        return false;
    }else if ((member_birth_m==4 || member_birth_m==6 || member_birth_m==9 || member_birth_m==11) && member_birth_d==31) {
        birth_d = false;
        $("input[name='member_birth_d']","#frm").val("");
        //$(".birth_error").text("태어난 일을 잘못입력했습니다.");
        return false;
    }else if (member_birth_m == 2) { // 윤년체크
        var isleap = (member_birth_y % 4 == 0 && (member_birth_y % 100 != 0 || member_birth_y % 400 == 0));
        if (member_birth_d>29 || (member_birth_d==29 && !isleap)) {
            birth_d = false;
            $("input[name='member_birth_d']","#frm").val("");
            //$(".birth_error").text("태어난 일을 잘못입력했습니다.");
            return false;
        }
    }else{
        birth_d = true;
        $(".birth_error").text("");
        return true;
    }
}


//일반전화 형식 검사
function tel_check() {
    var mb_tel = $("input[name='mb_tel']","#frm").val();
    if(mb_tel ==""){
        tel = true;
        $(".tel_error").text("");
        return false;
    }

    mb_tel = mb_tel.replace(/(^02.{0}|^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})/,"$1-$2-$3");
    pattern1 = /^(0(2|3[1-3]|4[1-4]|5[1-5]|6[1-4]|7[0-0]))-(\d{3,4})-(\d{4})$/;
    if(pattern1.test(mb_tel) == false){
        tel = false;
        $(".tel_error").text("올바른 전화번호가 아닙니다.");
        return false;
    }else{
        tel = true;
        $("input[name='mb_tel']","#frm").val(mb_tel);
        $(".tel_error").text("");
    }

    return true;
}


//휴대전화 형식 검사
function hp_check() {
    var member_phone = $("input[name='member_phone']","#frm").val();
    if(checkNull(member_phone)) return false;
    member_phone = member_phone.replace(/(^02.{0}|^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})/,"$1-$2-$3");
    pattern1 = /^(01[01346-9])-?([0-9]{4})-?([0-9]{4})$/;

    if(pattern1.test(member_phone) == false){
        hp = false;
        $(".hp_error").text("올바른 전화번호가 아닙니다.");
        return false;
    }else{
        hp = true;
        $("input[name='member_phone']","#frm").val(member_phone);
        $(".hp_error").text("");
    }
    return true;
}

//휴대전화 형식 검사
function bizid_check() {
    var bizid = $("input[name='mb_etc2']","#frm").val();

    if(checkNull(bizid)) return false;
    if(checkBizID(bizid) == false){
        bizid = false;
        $(".bizid_error").text("올바른 번호가 아닙니다.");
        return false;
    }else{
        bizid = true;
        $(".bizid_error").text("");
    }

    return true;
}


//기업정보 체크
function checkBizID(bizID){
    // bizID는 숫자만 10자리로 해서 문자열로 넘긴다.
    var checkID = new Array(1, 3, 7, 1, 3, 7, 1, 3, 5, 1);
    var tmpBizID, i, chkSum=0, c2, remander;
    bizID = bizID.replace(/-/gi,'');

    for (i=0; i<=7; i++) chkSum += checkID[i] * bizID.charAt(i);
    c2 = "0" + (checkID[8] * bizID.charAt(8));
    c2 = c2.substring(c2.length - 2, c2.length);
    chkSum += Math.floor(c2.charAt(0)) + Math.floor(c2.charAt(1));
    remander = (10 - (chkSum % 10)) % 10 ;

    if (Math.floor(bizID.charAt(9)) == remander) return true ; // OK!
    return false;
}



function formsubmit(){
	var member_idx = $('#memberIdx').val();
	if(member_idx){
		check_id = true;
		check_pw = true;
		check_pw_r = true;
		hp = true;
		check_email = true;
	}
    member_nm = $("input[name='member_nm']","#frm");
    if( member_nm.length ) {
        if( member_nm.val().length == 0 ) {
            alert("성명을 확인해주십시요.");
            $("input[name='member_nm']","#frm").focus();
            return false;
        }
    }


    if(check_id == false){
        alert("아이디를 확인해주십시요.");
        $("input[name='member_id']","#frm").focus();
        return false;
    }

    if(check_pw == false){
        alert("비밀번호를 확인해주십시요.");
        $("input[name='member_pw']","#frm").focus();
        return false;
    }


    if(check_pw_r == false){
        alert("비밀번호를 확인해주십시요.");
        $("input[name='member_pw_r']","#frm").focus();
        return false;
    }

    if(hp == false){
        alert("휴대전화를 확인해주십시요.");
        $("input[name='member_phone']","#frm").focus();
        return false;
    }

    if(birth_y == false || birth_m == false || birth_d == false){
        alert("생년월일을 확인해주십시요.");
        $("input[name='member_email']","#frm").focus();
        return false;
    }
    
    if(check_email == false){
        alert("이메일을 확인해주십시요.");
        return false;
    }
    
	var $frm = $("#frm");
	
	setTimeout(function (){
		$.ajax({
			type: "post",
			url: $frm.attr("action"),
			data: $frm.serialize(),
			contentType: "application/x-www-form-urlencoded",
			dataType: "json",
			beforeSend: function(xhr) {
				xhr.setRequestHeader("X-CSRF-TOKEN", $("input[name='_csrf']", $frm).val());
			},
		}).done(function(data) {
			if(data.data.resultCnt == 1) {
				if(member_idx){
					alert("수정되었습니다.");
				} else{
					alert("가입되었습니다.\n관리자 승인 후 로그인할 수 있습니다.");
				}
				location.href="/";
			}
			else {
				alert(data.message);
			}
		}).fail(function(xhr) {
			if(typeof xhr.responseJSON.message != "undefined") {
				alert(xhr.responseJSON.message);
			}
		});
	 },500);
    //$("#frm").submit();
    return false;
}

/*
$(document).ready(function() {
	//저장
	$("#frm").submit(function() {

		var $input = null;
		$input = $("input[name='check_id']",this);
		if($input.val() == 0 ) {
			alert("사용불가능한 아이디입니다. 확인해주십시요");
			return false;
		}else if($input.val() == 2){
			alert("아이디 중복확인을 해주십시요");
			return false;
		}else{

		}
	if( !confirm("저장하시겠습니까?") ) {
			return false;
		}

		return true;
	});
});
*/
