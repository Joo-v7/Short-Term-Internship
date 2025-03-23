package kepco.cms.board;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import kepco.cms.board.post.PostVo;
import kepco.cms.member.auth.MemberDetailVo;
import kepco.util.CamelMap;
import kepco.util.StrUtil;

@Service
public class BoardPermissionService {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 디비에 설정된 롤별 권한 가져오기
	 * @param memberDetailVo
	 * @param boardVo
	 * @return
	 */
	public CamelMap getBoardPermission(MemberDetailVo memberDetailVo, BoardVo boardVo) {
		CamelMap boardPermission = new CamelMap();
		
		// 모든 권한이 없는 것으로 가정하고 시작
		
		// 게시판 관리자 여부
		boardPermission.put("isBoardAdmin", false);
		
		// 디비에 설정된 롤별 권한
		List<String> authList = Arrays.asList("list", "read", "write", "reply", "upload", "download", "comment");
		for(String auth : authList) {
			boardPermission.put(auth, false);
		}
		
		boardPermission.put("reply", false);
		boardPermission.put("modify", false);
		boardPermission.put("delete", false);
		
		// 메인 ROLE을 통한 게시판 권한 가져오기
		String memberRole = memberDetailVo.getMemberRole();
		
		// ROLE_USER, ROLE_ADMIN_SUPER 를 컬럼명(getter)에 맞추기
		String myRoleName = "";
		if(memberDetailVo.getMemberIdx() == 0) {
			myRoleName = "NON_MEMBER";
		}
		else if(!memberDetailVo.getIsAdmin()) {
			myRoleName = "USER";
		}
		else if("ROLE_ADMIN_SUPER".equals(memberDetailVo.getMemberRole())) {
			// ROLE_ADMIN_SUPER는 모든 권한 적용
			boardPermission.put("isBoardAdmin", true);
			
			for(String auth : authList) {
				boardPermission.put(auth, true);
			}
		}
		else {
			// 그 외는, CMS, LMS, RAS 관리자만 존재
			myRoleName = memberRole.substring(5);
		}
		
		// 수퍼관리자를 제외한 게시판 권한 가져오기
		if(!"ROLE_ADMIN_SUPER".equals(memberDetailVo.getMemberRole())) {
			// "LMS_ADMIN" 을 "LmsAdmin" 으로..
			String myRoleNamePascal = WordUtils.capitalizeFully(myRoleName, '_').replaceAll("_", "");
			
			// memberRole에 따른 기본권한 조회 getter 메서드
			Method myMethod;
			// 메서드 리턴값. 권한이 있다면 "y"
			String myValue;
			try {
				for(String auth : authList) {
					myMethod = boardVo.getClass().getMethod("getBoardAuth"+StringUtils.capitalize(auth)+myRoleNamePascal);
					myValue = StrUtil.toStr(myMethod.invoke(boardVo), "n");
					boardPermission.put(auth, "y".equals(myValue));
				}
			} catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		
		// ROLE_ADMIN_SUPER에 대한 예외 처리. CMS, LMS, RAS의 권한을 OR로 적용.
		/*
		if("ROLE_ADMIN_SUPER".equals(memberDetailVo.getMemberRole())) {
			List<String> myAdminRoleList = Arrays.asList("LmsAdmin", "LmsAdmin", "RasAdmin");
			for(String myAdminRole : myAdminRoleList) {
				myRoleNamePascal = myAdminRole;
				try {
					for(String auth : authList) {
						myMethod = boardVo.getClass().getMethod("getBoardAuth"+StringUtils.capitalize(auth)+myRoleNamePascal);
						myValue = StrUtil.toStr(myMethod.invoke(boardVo), "n");
						
						// 권한이 없는 경우에만 덮어쓰기. OR 처리 목적
						if(!boardPermission.getBool(auth)) {
							boardPermission.put(auth, "y".equals(myValue));
						}
					}
				} catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		*/
		
		// 게시판의 업로드 사용여부
		if(!"y".equals(boardVo.getBoardUseUpload())) {
			boardPermission.put("upload", false);
		}
		
		// 게시판의 댓글 사용여부
		if(!"y".equals(boardVo.getBoardUseComment())) {
			boardPermission.put("comment", false);
		}
		
		// 게시판의 답변 사용여부
		if(!"y".equals(boardVo.getBoardUseReply())) {
			boardPermission.put("reply", false);
		}
		
		return boardPermission;
	}
	
	public CamelMap getBoardPermissionByCmd(String cmd, MemberDetailVo memberDetailVo, BoardVo boardVo, PostVo postVo) {
		
		CamelMap boardPermission = getBoardPermission(memberDetailVo, boardVo);
		
		if("list".equals(cmd)) {
			return boardPermission;
		}
		
		// NOTE: 게시물 조회는 "view"가 아니라 read" 로 되어있다.
		if("read".equals(cmd)) {
			// 작성자 본인 여부
			boolean isOwner = memberDetailVo.getMemberIdx() > 0 && postVo.getInsertIdx() == memberDetailVo.getMemberIdx();
			
			if("y".equals(postVo.getPostSecret())) {
				boardPermission.put("read", isOwner);
				boardPermission.put("download", isOwner);
			}
			
			return boardPermission;
		}
		
		return boardPermission;
	}

	/**
	 * 목록에서 권한 가져오기
	 * @param memberDetailVo
	 * @param boardVo
	 * @return
	 */
	public CamelMap getBoardPermissionList(MemberDetailVo memberDetailVo, BoardVo boardVo) {
		
		CamelMap boardPermission = getBoardPermission(memberDetailVo, boardVo);
		
		// 목록에서는 기본권한만 체크하고, 각 게시물별 권한은 VIEW에서 체크
		
		return boardPermission;
	}
	
	/**
	 * 조회에서 권한 가져오기
	 * @param memberDetailVo
	 * @param boardVo
	 * @param postVo
	 * @return
	 */
	public CamelMap getBoardPermissionView(MemberDetailVo memberDetailVo, BoardVo boardVo, PostVo postVo) {
		
		CamelMap boardPermission = getBoardPermission(memberDetailVo, boardVo);
		
		// 읽기 권한이 없다면 추가 체크할 이유가 없다.
		if(!boardPermission.getBool("read")) {
			return boardPermission;
		}
			
		// 작성자 본인 여부
		boolean isOwner = memberDetailVo.getMemberIdx() > 0 && postVo.getInsertIdx() == memberDetailVo.getMemberIdx();
		
		boolean isBoardAdmin = boardPermission.getBool("isBoardAdmin");
		
		// 비밀글
		if("y".equals(postVo.getPostSecret())) {
			boardPermission.put("read", isOwner|isBoardAdmin);
			boardPermission.put("download", isOwner|isBoardAdmin);
		}
		
		boardPermission.put("modify", isOwner|isBoardAdmin);
		boardPermission.put("delete", isOwner|isBoardAdmin);
		
		return boardPermission;
		
	}
	
	/**
	 * 폼(추가,수정)에서 권한 가져오기
	 * @param memberDetailVo
	 * @param boardVo
	 * @param postVo
	 * @return
	 */
	public CamelMap getBoardPermissionForm(MemberDetailVo memberDetailVo, BoardVo boardVo, PostVo postVo) {
		
		CamelMap boardPermission = getBoardPermission(memberDetailVo, boardVo);
		
		// 읽기 권한이 없다면 추가 체크할 이유가 없다.
		if(!boardPermission.getBool("read")) {
			return boardPermission;
		}
		
		// 수정, 답변인 경우 postVo는 null이 될 수 없다.
		if(postVo == null) {
			return boardPermission;
		}
		
		// 작성자 본인 여부
		boolean isOwner = memberDetailVo.getMemberIdx() > 0 && postVo.getInsertIdx() == memberDetailVo.getMemberIdx();
		
		boolean isBoardAdmin = boardPermission.getBool("isBoardAdmin");
		
		// 비밀글
		if("y".equals(postVo.getPostSecret())) {
			boardPermission.put("read", isOwner|isBoardAdmin);
			boardPermission.put("download", isOwner|isBoardAdmin);
		}
		
		boardPermission.put("modify", isOwner|isBoardAdmin);
		boardPermission.put("delete", isOwner|isBoardAdmin);
		
		return boardPermission;
	}
	
}
