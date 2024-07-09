package kepco.cms.board.post.file;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;

import kepco.util.StrUtil;

@Service
public class FileService {

	@Autowired
	private Validator validator;
	
	@Autowired
	private FileMapper fileMapper;
	
	public List<FileVo> selectList(EgovMap req) {
		
		int pageNum = StrUtil.toInt(req.get("pageNum"), 1);
		int pageSize = StrUtil.toInt(req.get("pageSize"), 10);
		PageHelper.startPage(pageNum, pageSize);
		
		return fileMapper.selectList(req);
	}

	public List<FileVo> selectAll(EgovMap req) {
		return fileMapper.selectList(req);
	}
	
	public FileVo select(EgovMap req) {
		return fileMapper.select(req);
	}

	@Transactional
	public int insert(FileVo vo) {
		// TODO: 컨트롤러에서 처리하는 경우는 검증 제외
		Set<ConstraintViolation<FileVo>> violations = validator.validate(vo);
		
		if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<FileVo> constraintViolation : violations) {
                sb.append(String.format("%s : %s", constraintViolation.getPropertyPath(), constraintViolation.getMessage()));
            }
            throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
        }
		
		return fileMapper.insert(vo);
	}
	
	@Transactional
	public int update(FileVo vo) {
		// TODO: 컨트롤러에서 처리하는 경우는 검증 제외
		Set<ConstraintViolation<FileVo>> violations = validator.validate(vo);
		
		if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<FileVo> constraintViolation : violations) {
                sb.append(String.format("%s : %s", constraintViolation.getPropertyPath(), constraintViolation.getMessage()));
            }
            throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
        }
		
		return fileMapper.update(vo);
	}
	
	@Transactional
	public int delete(EgovMap req) {
		return fileMapper.delete(req);
	}

	@Transactional
	public int download(FileVo req) {
		return fileMapper.download(req);
	}
	
	public List<FileVo> selectFile(EgovMap req) {
		return fileMapper.selectFile(req);
	}
	
	public FileVo selectFile2(EgovMap req) {
		return fileMapper.selectFile2(req);
	}
	
	@Transactional
	public int deleteFile(String req) {
		return fileMapper.deleteFile(req);
	}
	
	@Transactional
	public int deleteFile2(EgovMap req) {
		return fileMapper.deleteFile2(req);
	}
	
	@Transactional
	public int fileadd(EgovMap req) {
		return fileMapper.fileadd(req);
	}
	
	@Transactional
	public int filedelete(EgovMap req) {
		return fileMapper.filedelete(req);
	}
	
	/** 첨부파일 목록 */
	public List<FileVo> selectListByPostIdx(int postIdx) {
		return fileMapper.selectListByPostIdx(postIdx);
	}
	
	/** 첨부파일 조회 */
	public FileVo selectByFileIdx(int fileIdx) {
		return fileMapper.selectByFileIdx(fileIdx);
	}
	
	/** 게시물의 첨부파일 삭제 */
	public int deleteListByPostIdx(EgovMap req) {
		return fileMapper.deleteListByPostIdx(req);
	}
	
	
}
