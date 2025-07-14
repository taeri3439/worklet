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

## 🧑‍💻 나의 역할

- **회원가입 / 로그인 기능 구현**
  - 아이디 중복 확인, 비밀번호 유효성 검사 등 기본 유효성 로직 구현
  - 로그인 성공 시 세션 생성 및 실패 시 에러 메시지 처리
  - 로그인이 필요한 페이지 접근 시 로그인 페이지로 리다이렉트 처리 (Interceptor 사용)

- **이력서 작성 페이지 비동기 처리**
  - 이력서의 학력, 경력, 자격증을 각각 비동기로 추가/수정/삭제할 수 있도록 AJAX 적용
  - 사용자의 입력 흐름을 방해하지 않도록 동적으로 입력 필드가 추가되거나 제거되도록 구현
  - 작성한 이력서를 DB에 저장하고, MyPage에서 바로 확인 가능하도록 연동

- **전체적인 UI/UX 개선 및 화면 설계**
  - 사용자 흐름에 맞게 메뉴, 폼, 버튼 등의 배치 조정
  - 페이지 간 이동 시 시각적 통일성과 사용성 고려
  - 메인페이지, 이력서 작성 등 주요 기능 화면 퍼블리싱


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
```
---

## 🫠 프로젝트를 통해 느낀 점

이번 프로젝트에서는 로그인과 회원가입 같은 필수 기능부터  
사용자가 직접 이력서를 작성하고 수정하는 복잡한 화면까지 맡아보며,  
**단순한 기능 구현을 넘어 사용자 흐름과 편의성을 함께 고려하는 개발**이 무엇인지 배울 수 있었습니다.

특히 이력서 작성 페이지에서는 입력 항목을 **비동기로 처리**하며  
기존 폼 구조와 충돌하지 않도록 조심스럽게 구현해야 했고,  
프론트엔드와 백엔드 간의 데이터 처리 흐름에 대해 한층 깊이 이해할 수 있었습니다.

또한 전체 UI/UX를 직접 구성해보며,  
그냥 보이게 만드는 것보다 **사용자가 편하게 쓸 수 있도록 만드는 게 더 중요하다는 것을** 느꼈습니다.  
버튼 하나의 위치나 폼 구성도 실제로 써보는 입장에서 계속 수정하게 되었고, 그 과정에서 화면을 어떻게 설계해야 할지에 대한 감도 조금씩 생겼습니다.


