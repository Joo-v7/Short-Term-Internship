[[_TOC_]]
## 개요
### 환경
##### 개발환경, 라이브러리
- JAVA 17
- spring-boot 2.7.8
- spring 5.3.25
- mybatis 3.5.11
- thymeleaf 3.0.15
- pagehelper 5.3.2
##### 참고
- [구글시트 - 기능명세서](https://docs.google.com/spreadsheets/d/1bnkb-vtHNLi_IdtLRnwIQcjUBBro9lmrLhOhXyDxijk)
- [구글시트 - API명세서](https://docs.google.com/spreadsheets/d/1Wv6BY2xNmVQ4peyeY76Bq2aGsYvnkO-mvXLEQYnzzIQ)
### 서버
##### 서버구성
접근제한(베이직인증) : `kepco` / `kepco12#$`
- 플레이캔버스
  - http://i-lms.uokdc.net:12080
- 디자인
  - http://i-lms2.uokdc.net:12080/pages/kepco/main.html
- 개발서버(스프링)
  - http://i-lms.uokdc.net:18080
##### 개발서버
- http://i-lms.uokdc.net:18080/admin/cms/member/auth
  - `s_admin` / `dbdhzpdl^^**`
- SSH (2278)
  - 121.254.175.163
  - `kepco_lms` / `kepco_lms12#$`
  - /home/kepco_lms/html
- MySQL
  - 121.254.175.163
  - `kepco_lms` / `kepco_lms12#$`
### 백엔드
##### 패키지 레이아웃

| package | file | 
|----|----|
|uok.cms.member|MemberVo.java|
|uok.cms.member|AdminMemberController.java|
|uok.cms.member|MemberController.java|
|uok.cms.member|MemberService.java|
|uok.cms.member|MemberMapper.java|
|uok.cms.member|MemberMapper.xml|
- 단순 CRUD는 `XxxMapper.xml` 없이 처리하는 것도 무관
- `XxxVo`없이 `Map`, `EgovMap`, `CamelMap` 으로 처리하는 것도 무관

| package | file | 
|----|----|
|uok.cms.board|BoardVo.java|
|uok.cms.board|PostVo.java|
|uok.cms.common.code|CodeGroupVo.java|
|uok.cms.common.code|CodeVo.java|
- 가급적 패키지에는 1개 테이블에 대한 처리만 있지만 성격이 유사하거나 간단한 로직은 합쳐도 무관

##### 컨트롤러, 뷰 레이아웃

| Controller | URL | VIEW |
|----|----|----|
|uok.cms.member.MemberController.list()|cms/member/list|webapp/cms/member/list.html|
|uok.cms.member.MemberController.listData()|cms/member/listData|webapp/cms/member/listData.html|
|uok.cms.member.MemberController.listJson()|cms/member/listJson|JSON|
|uok.cms.member.MemberController.view()|cms/member/view|webapp/cms/member/view.html|
|uok.cms.member.MemberController.form()|cms/member/form|webapp/cms/member/form.html|
|uok.cms.member.MemberController.save()|cms/member/save|JSON|
|uok.cms.member.MemberController.delete()|cms/member/delete|JSON|

- 가급적 처리(INSERT, UPDATE, DELETE)는 JSON으로 처리하고 뷰에서 리디렉션 등

### 프론트
##### jquery

##### datatables
- https://datatables.net/
##### materialize-ui
- https://materializecss.com/
