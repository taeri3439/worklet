# 👨‍👩‍👧‍👦 WORKLET: 당신의 취업을 돕는 플랫폼 이야기

**WORKLET**은 고용노동부가 제공하는 공개 채용 공공 데이터를 활용한 웹 기반 취업 알선 플랫폼입니다.  
다양한 편의 기능을 제공하여 사용자의 체계적인 취업 활동을 지원합니다.

---

## ✨ 주요 기능

- ✅ 회원가입 / 로그인  
- 📝 커뮤니티 작성 / 수정 / 삭제 / 목록 조회  
- 🪪 이력서 관리 기능  
- 📅 풀 캘린더를 활용한 찜 기능  
- 🤖 사용자의 이력서를 ChatGPT로 첨삭  
- 📂 완성된 이력서 JasperReport 출력  

---

## 🛠 사용 기술

### 🔸 Backend
- Spring Boot
- Ajax
- ChatGPT API
- JasperReport

---

## 📁 폴더 구조

```bash
worklet2/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/project2/worklet/
│   │   │       ├── board.service/         # 게시판 관련 서비스 로직
│   │   │       ├── calendarService/       # 캘린더 관련 서비스
│   │   │       ├── component/             # 공통 컴포넌트
│   │   │       ├── config/                # 스프링 설정 클래스
│   │   │       ├── controller/            # MVC 컨트롤러
│   │   │       ├── handlerInterceptor/    # 인터셉터 처리
│   │   │       ├── jobcategory/           # 직무 카테고리 관련 로직
│   │   │       ├── jobPostingService/     # 채용 공고 관련 서비스
│   │   │       ├── myCalendarService/     # 사용자 캘린더
│   │   │       ├── qna.service/           # QnA 서비스
│   │   │       ├── resume.service/        # 이력서 관련 서비스
│   │   │       ├── user.service/          # 유저 관련 서비스
│   │   │       ├── util_interceptor/      # 기타 유틸/인터셉터
│   │   │       └── WorkletApplication.java # Spring Boot 진입점
│   │   └── resources/
│   │       ├── mapper/                   
│   │       │   ├── CalendarMapper.xml
│   │       │   ├── JobCategoryMapper.xml
│   │       │   ├── JobPostingMapper.xml
│   │       │   ├── MyCalendarMapper.xml
│   │       │   ├── Qnamapper.xml
│   │       │   ├── ResumeMapper.xml
│   │       │   └── UserMapper.xml
│   │       ├── static/                    # 정적 자원 (CSS, JS, 이미지 등)
│   │       │   ├── bootstrap-3.4.1-dist/ 
│   │       │   ├── css/
│   │       │   │   ├── board/
│   │       │   │   ├── calendar/
│   │       │   │   ├── js/
│   │       │   │   ├── user/
│   │       │   │   ├── JobPosting.css
│   │       │   │   ├── OpenJobPosting.css
│   │       │   │   ├── reset.css
│   │       │   │   └── resume.css
│   │       │   ├── image/                 
│   │       │   └── uploads/               # 업로드된 파일 저장 경로
│   │       ├── templates/                 # Thymeleaf HTML 템플릿
│   │       │   ├── Board/
│   │       │   ├── calendar/
│   │       │   ├── include/               # 공통 include fragment
│   │       │   └── User/
│   │       │       ├── airesume.html
│   │       │       ├── airesumeresult.html
│   │       │       ├── jobposting.html
│   │       │       ├── openjobposting.html
│   │       │       └── resume.html
│   │       ├── application.properties     
│   │       ├── application-production.properties
│   │       ├── jasperreports_extension.properties # Jasper 보고서 설정
│   │       ├── log4jdbc.log4j2.properties 
│   │       ├── logback.xml                
│   │       ├── resume_ex_01.jrxml         # JasperReport 템플릿
│   │       └── test.jrxml                 # 테스트용 JasperReport 템플릿
│   └── test/                              # 테스트 코드 디렉터리
└── .gitignore                             # Git 예외 설정
