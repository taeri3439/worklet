
const plusBtn = document.querySelectorAll('.plus figure');
const addEduForm = document.querySelectorAll('.add-edu-form');
const cancelBtn = document.querySelectorAll('.btn-del');
const sec03BtnSave = document.querySelector('.sec03 .btn-save');
const sec04BtnSave = document.querySelector('.sec04 .btn-save');
const education = document.querySelectorAll('.education');
const schoolList = document.querySelector(".school-list");
const comList = document.querySelector(".com-list");
const pencil = document.querySelectorAll(".pencil");

// 추가 버튼 클릭 시 해당 섹션의 폼만 열기
plusBtn.forEach((btn) => {
    btn.addEventListener('click', () => {
        const section = btn.closest('section'); // 해당 버튼이 속한 섹션
        const form = section.querySelector('.add-edu-form');
        form.classList.toggle('add');
        //form.style.display = 'block'; // 또는 'flex', 상황에 따라
    });
});

// pencil.forEach((btn) => {
//     btn.addEventListener('click', () => {
//         const section = btn.closest('section'); // 해당 버튼이 속한 섹션
//         const form = section.querySelector('.edit-edu-form');
//         form.classList.add('add');
//
//         const eduDiv = btn.closest('.education');
//
//         if (section.classList.contains('sec03')) {
//             // 학력 수정
//             const schoolName = eduDiv.querySelector('.school h4')?.innerText || '';
//             const major = eduDiv.querySelector('.school p')?.innerText || '';
//             const part = eduDiv.querySelector('.edu-info span:nth-child(1)')?.innerText || '';
//             const gradType = eduDiv.querySelector('.edu-info span:nth-child(2)')?.innerText || '';
//             const grad = eduDiv.querySelector('.edu-info span:nth-child(3)')?.innerText || '';
//             const gradDate = eduDiv.querySelector('.graduration')?.innerText.replace(' 졸업', '') || '';
//
//             form.querySelector('.input-school').value = schoolName;
//             form.querySelector('.input-major').value = major;
//             form.querySelector('.completion').value = part;
//             form.querySelector('.level').value = gradType;
//             form.querySelector('.grad').value = grad; // 디테일하게 조정 가능
//             form.querySelector('input[name="graduationDate"]').value = gradDate;
//
//         } else if (section.classList.contains('sec04')) {
//             // 경력 수정
//             const companyName = eduDiv.querySelector('.school h4')?.innerText || '';
//             const department = eduDiv.querySelector('.school p')?.innerText || '';
//             const dates = eduDiv.querySelector('.edu-info span:nth-child(1)')?.innerText || '';
//             const position = eduDiv.querySelector('.edu-info span:nth-child(2)')?.innerText || '';
//             const jobDescription = eduDiv.querySelector('.job_description')?.innerText || '';
//
//             const [joinDate, quitDate] = dates.split(' ~ '); // 입사일과 퇴사일 나누기
//
//             form.querySelector('.company-name').value = companyName;
//             form.querySelector('.department').value = department;
//             form.querySelector('input[name="joinDate"]').value = joinDate?.trim() || '';
//             form.querySelector('input[name="quitDate"]').value = quitDate?.trim() || '';
//             form.querySelector('input[name="position"]').value = position;
//             form.querySelector('textarea[name="jobDescription"]').value = jobDescription;
//
//
//         }
//         // else if (section.classList.contains('sec05')) {
//         //     // 자격증 수정
//         //     const licenseName = eduDiv.querySelector('.sec05 .name')?.innerText || '';
//         //     const licenseOrg = eduDiv.querySelector('.sec05 .org')?.innerText || '';
//         //     const dateRange = eduDiv.querySelector('.sec05 .date')?.innerText || '';
//         //     const [acquisition, expiration] = dateRange.split(' ~ ');
//         //
//         //     form.querySelector('input[name="licenseName"]').value = licenseName;
//         //     form.querySelector('input[name="licenseOrg"]').value = licenseOrg;
//         //     form.querySelector('input[name="acquisition"]').value = acquisition?.trim() || '';
//         //     form.querySelector('input[name="expiration"]').value = expiration?.trim() || '';
//         // }
//     });
// });



// 취소 버튼 클릭 시 해당 섹션의 폼만 닫기
cancelBtn.forEach((btn) => {
    btn.addEventListener('click', () => {
        // 어떤 버튼인지에 따라 careerEdit 또는 eduEdit을 닫기
        const careerForm = document.getElementById('careerEdit');
        const eduForm = document.getElementById('eduEdit');
const careerAdd=document.getElementById("careerAdd");
const addEduForm=document.getElementById("addEduForm");

        // 버튼이 careerForm 안에 있으면
        if (careerForm.contains(btn)) {
            careerForm.classList.remove('add');
            // careerForm.style.display = 'none';
        }

        // 버튼이 eduForm 안에 있으면
        if (eduForm.contains(btn)) {
            eduForm.classList.remove('add');
            // eduForm.style.display = 'none';
        }
        // 버튼이 careerForm 안에 있으면
        if (careerAdd.contains(btn)) {
            careerAdd.classList.remove('add');
            // careerAdd.style.display = 'none';
        }

        // 버튼이 eduForm 안에 있으면
        if (addEduForm.contains(btn)) {
            addEduForm.classList.remove('add');
            // addEduForm      .style.display = 'none';
        }
    });
});



sec03BtnSave.addEventListener('click', function () {
    let schoolName = document.querySelector(".add-edu-form input[name='schoolName']").value;
    let major = document.querySelector(".add-edu-form input[name='major']").value;
    let part = document.querySelector(".add-edu-form .part").value;
    let degreeType = document.getElementById("degreeType").value;
    let graduationdate = document.querySelector(".add-edu-form input[name='graduationDate']").value;
    let graduationStatus = document.getElementById("graduationStatus").value;
    let userNum = document.querySelector("input[name='userNum']").value;
    let resumeId=document.querySelector("input[name='resumeId']").value;
    let addeduForm = document.querySelector(".sec03 .add-edu-form");
    let eduForm=document.getElementById("eduForm");

    console.log(schoolName);
    console.log(major);
    console.log(part);
    console.log(degreeType);
    console.log(graduationdate);

    if (!schoolName || !major || !part || !degreeType) {
        alert('학교명, 이수형태, 학력구분, 졸업여부를 모두 입력해주세요.');
        return;
    }

    const data = {
        schoolName: schoolName,
        major: major,
        part: part,
        degreeType: degreeType,
        graduationDate: graduationdate,
        graduationStatus: graduationStatus,
        userNum: userNum,
        resumeId: resumeId
    };

    // AJAX 요청 보내기
    fetch("/user/eduInsert", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    })
        .then(res => {
            if (!res.ok) throw new Error("서버 응답 실패");
            return res.json();
        })
        .then(result => {
            console.log("삽입된 학력 정보:", result);

            const educationList = document.getElementById("educationList");

            const div = document.createElement("div");
            div.classList.add("education", "flex");

            div.dataset.graduationdate = result.graduationdate;

            div.innerHTML = `
                        <div class="school">
                            <input type="hidden" name="educationId" value="${result.educationId}" id="eduId">
                            <h4>${result.schoolName}</h4>
                            <p>${result.major}</p>
                        </div>
                        <div class="edu-info">
                            <div>
                                <span>${result.part}</span>
                                <span>${result.degreeType}</span>
                                <span>${result.graduationStatus || '미입력'}</span>
                            </div>
                            <p class="graduration">${result.graduationDate} 졸업</p>
                        </div>
                        <img src="../image/pencil.png" alt="" class="pencil">
                        <form action="/user/deleteEdu/${result.educationId}" method="POST">
                            <input type="hidden" name="resumeId" value="${new URLSearchParams(window.location.search).get('uniqueTime')}">
                            <button type="submit" class="delete-btn">
                                <i class="fa-solid fa-x"></i>
                            </button>
                        </form>
    `;

            educationList.appendChild(div);
        })
        .catch(err => {
            console.error("오류 발생:", err);
        });
    // 폼 숨기기

    addeduForm.classList.remove("add");
    eduForm.reset();
})

document.getElementById("educationList").addEventListener("click", function (event) {
    const form = document.getElementById('eduEdit'); // 경력 수정용 폼
    form.classList.add('add'); // 폼 보여주기

    if (event.target.classList.contains("pencil")) {
        const btn = event.target;
        const eduDiv = btn.closest('.education');

        // const educationId = eduDiv.querySelector('input[name="educationId"]').value;
        // console.log(educationId)
        const schoolName = eduDiv.querySelector('.school h4')?.innerText.trim() || '';
        const major = eduDiv.querySelector('.school p')?.innerText.trim() || '';
        const part = eduDiv.querySelector('.edu-info span:nth-child(1)')?.innerText.trim() || '';
        const degreeType = eduDiv.querySelector('.edu-info span:nth-child(2)')?.innerText.trim() || '';
        const graduationStatus = eduDiv.querySelector('.edu-info span:nth-child(3)')?.innerText.trim() || '';
        const graduationDate = eduDiv.dataset.graduationDate;
        const educationId = eduDiv.querySelector("#eduId").value;

        const eduEditForm = document.getElementById('eduEditForm');


        // 값 채워주기
        console.log("------>>", educationId);
        eduEditForm.querySelector("#schoolEduId").value = educationId;
        document.getElementById("fake").value=educationId;
        eduEditForm.querySelector('input[name="schoolName"]').value = schoolName;
        eduEditForm.querySelector('input[name="major"]').value = major;
        eduEditForm.querySelector('select[name="part"]').value = part;
        eduEditForm.querySelector('select[name="degreeType"]').value = degreeType;
        eduEditForm.querySelector('input[name="graduationDate"]').value = graduationDate;
        eduEditForm.querySelector('select[name="graduationStatus"]').value = graduationStatus;

        console.log("삽   갔는지 확인:",eduEditForm.querySelector("#schoolEduId").value)
    }
});

let saveEducationBtn=document.getElementById("saveEducationBtn");
saveEducationBtn.addEventListener('click',function () {
    const eduEditForm = document.getElementById("eduEditForm");

    let schoolNameEdit = document.getElementById("schoolNameEdit").value;
    let majorEdit = document.getElementById("majorEdit").value;
    let partEdit = document.getElementById("partEdit").value;
    let degreeEdit = document.getElementById("degreeEdit").value;
    let graduationStatusEdit = document.getElementById("graduationStatusEdit").value;
    let graduationDateEdit = document.getElementById("graduationDateEdit").value;
    let userNum = document.getElementById("schoolUserNum").value;
    let resumeId = document.getElementById("schoolResumeId").value;
    let eduEdit = document.getElementById("eduEdit");
    let educationId = document.querySelector("#schoolEduId").value;
    let fakeId=document.getElementById("fake").value;
    console.log("fake-value>>>>>>>"+fakeId)
    console.log("!!!!!!!!!!!!!!!!!->> ", educationId)

    if (!schoolNameEdit || !majorEdit || !partEdit || !degreeEdit) {
        alert('학교명, 이수형태, 학력구분, 졸업여부를 모두 입력해주세요.');
        return;
    }

    const data = {
        schoolName: schoolNameEdit,
        major: majorEdit,
        part: partEdit,
        degreeType: degreeEdit,
        graduationDate: graduationDateEdit,
        graduationStatus: graduationStatusEdit,
        userNum: userNum,
        resumeId: resumeId,
        educationId: fakeId
    };

    console.log(data);
    // AJAX 요청 보내기
    fetch("/user/eduUpdate", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    })
        .then(res => {
            if (!res.ok) throw new Error("서버 응답 실패");
            return res.json();
        })
        .then(result => {
            console.log("수정된 학력 정보:"+ result);

            // 기존 학력 div 갱신
            const eduDiv = document.querySelector(`input[name="educationId"][value="${result.educationId}"]`)?.closest(".education");

            console.log("eduDiv>>"+eduDiv);

            if (!eduDiv) {
                console.error("학력 요소를 찾을 수 없습니다.");
                return;
            }

            eduDiv.querySelector(".school h4").innerText = result.schoolName;
            eduDiv.querySelector(".school p").innerText = result.major;

            const spans = eduDiv.querySelectorAll(".edu-info span");
            spans[0].innerText = result.part;
            spans[1].innerText = result.degreeType;
            spans[2].innerText = result.graduationStatus;

            eduDiv.querySelector(".graduration").innerText = result.graduationDate;

            console.log("result.educationId:", result.educationId);
            const targetInput = document.querySelector(`input[name="educationId"][value="${result.educationId}"]`);
            console.log("찾은 input:", targetInput);

            // 폼 숨기기 및 초기화
            eduEdit.classList.remove("add");
           document.getElementById("eduEditForm").reset();

        })
        .catch(err => {
            console.error("오류 발생:", err);
        });
})

// 경력 추가
sec04BtnSave.addEventListener('click', function () {
    let companyName = document.querySelector(".company-name").value;
    let department = document.querySelector(".department").value;
    let joinDate = document.querySelector("input[name='joinDate']").value;
    let quitDate = document.querySelector("input[name='quitDate']").value;
    let position = document.querySelector("input[name='position']").value;
    let userNum = document.querySelector("input[name='userNum']").value;
    let jobDescription= document.getElementById("myText").value;
    let resumeId=document.querySelector("input[name='resumeId']").value;
    let addeduForm = document.querySelector(".sec04 .add-edu-form");
    let careerForm=document.getElementById("careerForm");

    console.log(companyName);
    console.log(department);
    console.log(joinDate);
    console.log(quitDate);
    console.log(position);


    if (!department || !joinDate || !quitDate || !position) {
        alert('부서, 입사일, 퇴사일, 직급 모두 입력해주세요.');
        return;
    }

    const data = {
        companyName: companyName,
        department: department,
        joinDate: joinDate,
        quitDate: quitDate,
        position:position,
        userNum:userNum,
        jobDescription:jobDescription,
        resumeId:resumeId
    };

    // AJAX 요청 보내기
    fetch("/user/careerInsert", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    })
        .then(res => {
            if (!res.ok) throw new Error("서버 응답 실패");
            return res.json();
        })
        .then(result => {
            console.log("삽입된 경력 정보:", result);

            const comList = document.querySelector(".com-list");

            const div = document.createElement("div");
            div.classList.add("education", "flex");

            div.dataset.joinDate = result.joinDate;
            div.dataset.quitDate = result.quitDate;

            div.innerHTML = `
                <div class="school">
                <input type="hidden" name="careerId" value="${result.careerId}" >
                    <h4>${result.companyName}</h4>
                    <p>${result.department}</p>
                </div>
                <div class="edu-info">
                    <span>${result.joinDate} ~ ${result.quitDate}</span>
                    <span>${result.position}</span>
                    <p class="job_description">${result.jobDescription}</p>
                </div>
                <img src="../image/pencil.png" alt="" class="pencil">
                <form action="/user/deleteCareer/${result.careerNum}" method="POST">
                    <input type="hidden" name="resumeId" value="${new URLSearchParams(window.location.search).get('uniqueTime')}">
                    <button type="submit" class="delete-btn">
                        <i class="fa-solid fa-x"></i>
                    </button>
                </form>
    `;

            comList.appendChild(div);
        })
        .catch(err => {
            console.error("오류 발생:", err);
        });
    // 폼 숨기기

    addeduForm.classList.remove('add');
    careerForm.reset();
})

document.getElementById("careerList").addEventListener("click", function (event) {
    const form = document.getElementById('careerEdit'); // 경력 수정용 폼
    form.classList.add('add'); // 폼 보여주기

    if (event.target.classList.contains("pencil")) {
        const btn = event.target;
        const careerDiv = btn.closest('.education');
        const careerId = careerDiv.querySelector('input[name="careerId"]').value;
console.log("careerId"+careerId);
        const companyName = careerDiv.querySelector('.school h4')?.innerText.trim() || '';
        const department = careerDiv.querySelector('.school p')?.innerText.trim() || '';
        const joinDate = careerDiv.dataset.joinDate;
        const quitDate = careerDiv.dataset.quitDate;
        const position = careerDiv.querySelector('.edu-info span:nth-child(2)')?.innerText.trim() || '';
        const jobDescription = careerDiv.querySelector('.job_description')?.innerText.trim() || '';



        const careerEditForm = document.getElementById('careerEditForm');
        // 값 채워주기
        careerEditForm.querySelector('input[name="careerId"]').value = careerId;
        careerEditForm.querySelector('input[name="companyName"]').value = companyName;
        careerEditForm.querySelector('input[name="department"]').value = department;
        careerEditForm.querySelector('input[name="joinDate"]').value = joinDate;
        careerEditForm.querySelector('input[name="quitDate"]').value = quitDate;
        careerEditForm.querySelector('input[name="position"]').value = position;
        careerEditForm.querySelector('textarea[name="jobDescription"]').value = jobDescription;

    }
});

let editCareerBtn = document.getElementById("editCareerBtn");

editCareerBtn.addEventListener('click', function () {
    const careerEditForm = document.getElementById('careerEditForm');

    let companyName = document.getElementById("comName").value;
    let department = document.getElementById("comDepartment").value;
    let joinDate = document.getElementById("comJoinDate").value;
    let quitDate = document.getElementById("comQuitDate").value;
    let position = document.getElementById("comPosition").value;
    let userNum = document.getElementById("comUserNum").value;
    let jobDescription = document.getElementById("myText2").value;
    let resumeId = document.getElementById("conResumeId").value;
    // let addeduForm = careerEditForm.closest(".add-edu-form") || document.querySelector(".add-edu-form");
    let careerEdit = document.getElementById("careerEdit");

    let careerId = careerEditForm.querySelector("input[name='careerId']").value;

    if (!department || !joinDate || !quitDate || !position) {
        alert('부서, 입사일, 퇴사일, 직급 모두 입력해주세요.');
        return;
    }

    const data = {
        companyName: companyName,
        department: department,
        joinDate: joinDate,
        quitDate: quitDate,
        position: position,
        userNum: userNum,
        jobDescription: jobDescription,
        resumeId: resumeId,
        careerId: careerId
    };
console.log(data);
    fetch("/user/careerUpdate", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    })
        .then(res => {
            if (!res.ok) throw new Error("서버 응답 실패");
            return res.json();
        })
        .then(result => {
            console.log("수정된 경력 정보:", result);

            // 수정 대상 경력 div 찾기
            const careerDiv = document.querySelector(`input[name="careerId"][value="${result.careerId}"]`)?.closest(".education");

            if (!careerDiv) {
                console.error("수정할 기존 경력 요소를 찾지 못했습니다.");
                return;
            }

            // 값 갱신
            careerDiv.querySelector(".school h4").innerText = result.companyName;
            careerDiv.querySelector(".school p").innerText = result.department;
            careerDiv.querySelector(".edu-info span:nth-child(1)").innerText = `${result.joinDate} ~ ${result.quitDate}`;
            careerDiv.querySelector(".edu-info span:nth-child(2)").innerText = result.position;
            careerDiv.querySelector(".job_description").innerText = result.jobDescription;

            // 폼 숨기기 및 초기화
            careerEdit.classList.remove('add');
            careerEditForm.reset();
        })
        .catch(err => {
            console.error("오류 발생:", err);
        });
});




function updateCount() {
    const textarea = document.getElementById('myText');
    const count = textarea.value.length;
    document.getElementById('charCount').textContent = count;

    const textarea2 = document.getElementById('myText2');
    const count6 = textarea2.value.length;
    document.getElementById('charCount6').textContent = count6;

    const growth = document.getElementById("growth");
    const count2 = growth.value.length;
    document.getElementById("charCount2").textContent = count2;

    const studentDay = document.getElementById("studentDay");
    const count3 = studentDay.value.length;
    document.getElementById("charCount3").textContent = count3;

    const prosAndCons = document.getElementById("prosAndCons");
    const count4 = prosAndCons.value.length;
    document.getElementById("charCount4").textContent = count4;

    const aspiration = document.getElementById("aspiration");
    const count5 = aspiration.value.length;
    document.getElementById("charCount5").textContent = count5;

}



const plusBtn05 = document.querySelector('.sec05 .plus figure');
const licenseForm = document.querySelector('.sec05 .add-license-form');
const licenseEditForm = document.querySelector('.sec05 .edit-license-form');
const licenseSaveBtn = document.querySelector('.sec05 .btn-license-save');
const licenseCancelBtn = document.querySelector('.sec05 .addCancel');
const editDel = document.querySelector('.sec05 .editDel');
const licenseList = document.querySelector('.sec05 .license-list');

// 자격증 추가
plusBtn05.addEventListener('click', () => {

    licenseForm.classList.add('add');
});

// 취소
licenseCancelBtn.addEventListener('click', () => {

    licenseForm.classList.remove('add');
});

// 취소
editDel.addEventListener('click', () => {

    licenseEditForm.classList.remove('add');
});

// '저장' 버튼 누르면 리스트에 카드형태로 추가
licenseSaveBtn.addEventListener('click', () => {
    let licenseName = document.querySelector('.liName').value.trim();
    let licenseOrg = document.querySelector('.license-org').value.trim();
    let acquisition = document.querySelector('input[name="acquisition"]').value.trim();
    let expiration = document.querySelector('input[name="expiration"]').value.trim();
    let userNum = document.querySelector("input[name='userNum']").value
    let resumeId=document.querySelector("input[name='resumeId']").value;
    let licenseAdd=document.getElementById("licenseAdd");
    let form=document.getElementById("licenseForm");

    console.log(licenseName);
    console.log(licenseOrg);
    console.log(acquisition);
    console.log(expiration);
    console.log(userNum)
    console.log(resumeId)
    console.log(userNum)

    if (!licenseName || !licenseOrg || !acquisition || !expiration) {
        alert('자격증명, 발급기관, 취득일을 모두 입력해주세요.');
        return;
    }

    const data = {
        licenseName: licenseName,
        licenseOrg: licenseOrg,
        acquisition: acquisition,
        expiration: expiration,
        userNum:userNum,
        resumeId:resumeId
    };

    // AJAX 요청 보내기
    fetch("/user/licenseInsert", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    })
        .then(res => {
            if (!res.ok) throw new Error("서버 응답 실패");
            return res.json();
        })
        .then(result => {
            console.log("삽입된 자격증 정보:", result);

            const licenseList = document.querySelector(".license-list");

            const div = document.createElement("div");
            div.classList.add("license-card");

            div.dataset.acquisition = result.acquisition;
            div.dataset.expiration = result.expiration;

            let resumeId = new URLSearchParams(window.location.search).get('uniqueTime');
            div.innerHTML = `
                            <input type="hidden" name="licenseId" value="${result.licenseId}" >
                          <h3 class="name">${result.licenseName}</h3>
                          <span class="org">${result.licenseOrg}</span>
                          <p class="date">${result.acquisition} ~ ${result.expiration}</p>
                          <img src="../image/pencil.png" alt="" class="licensePencil">
                          <form id="licenseDeleteBtn">
                                 <input type="hidden" name="resumeId" value="${resumeId}">
                                <button type="submit" class="delete-btn">
                                     <i class="fa-solid fa-x"></i>
                                </button>
                          </form>
                `;

            licenseList.appendChild(div);
        })
        .catch(err => {
            console.error("오류 발생:", err);
        });
    // 폼 숨기기

    licenseForm.classList.remove('add');
    form.reset();


});

document.getElementById("licenseId").addEventListener('click',function (){
    licenseEditForm.classList.add('add');

    if (event.target.classList.contains("licensePencil")) {
        const btn = event.target;
        const licenseDiv = btn.closest('.license-card');
        const licenseId= licenseDiv.querySelector('input[name="licenseId"]').value;
        const licenseName = licenseDiv.querySelector('.name').textContent;
        const licenseOrg = licenseDiv.querySelector('.org').textContent;
        const acquisition = licenseDiv.dataset.acquisition;
        const expiration = licenseDiv.dataset.expiration;

        console.log("licenseId"+licenseId);
        console.log("licenseName"+licenseName);
        console.log("licenseOrg"+licenseOrg);

        const licenseEditForm=document.getElementById("licenseEditForm");

        licenseEditForm.querySelector('input[name="licenseId"]').value=licenseId;
        licenseEditForm.querySelector('input[name="licenseName"]').value=licenseName;
        licenseEditForm.querySelector('input[name="licenseOrg"]').value=licenseOrg;
        licenseEditForm.querySelector('input[name="acquisition"]').value=acquisition;
        licenseEditForm.querySelector('input[name="expiration"]').value=expiration;



    }
})

let licenseEditBtn= document.getElementById("licenseEditBtn");
licenseEditBtn.addEventListener('click',function (){
    const licenseEditForm = document.getElementById("licenseEditForm");

    let licenseName = document.getElementById("editLicenseName").value;
    let licenseOrg = document.getElementById("editLicenseOrg").value;
    let acquisition = document.getElementById("editAcquisition").value;
    let expiration = document.getElementById("editExpiration").value;
    let userNum = document.getElementById("licenseUserNum").value;
    let resumeId = document.getElementById("licenseResumeId").value;
    let licenseId = licenseEditForm.querySelector("input[name='licenseId']").value;

    let licenseEdit = document.getElementById("licenseEdit");

    if (!licenseName || !licenseOrg || !acquisition || !expiration) {
        alert('자격증명, 발급기관, 취득일을 모두 입력해주세요.');
        return;
    }

    const data = {
        licenseName: licenseName,
        licenseOrg: licenseOrg,
        acquisition: acquisition,
        expiration: expiration,
        userNum:userNum,
        resumeId:resumeId,
        licenseId:licenseId
    };
    console.log(data);

    fetch("/user/licenseUpdate", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    })
        .then(res => {
            if (!res.ok) throw new Error("서버 응답 실패");
            return res.json();
        })
        .then(result => {
            console.log("수정된 자격증 정보:", result);

            // 수정 대상 경력 div 찾기
            const licenseDiv = document.querySelector(`input[name="licenseId"][value="${result.licenseId}"]`)?.closest(".license-card");

            if (!licenseDiv) {
                console.error("수정할 기존 자격증 요소를 찾지 못했습니다.");
                return;
            }

            // 값 갱신
            licenseDiv.querySelector(".name").innerText = result.licenseName;
            licenseDiv.querySelector(".org").innerText = result.licenseOrg;
            licenseDiv.querySelector(".date").innerText = `${result.acquisition} ~ ${result.expiration}`;


            // 폼 숨기기 및 초기화
            licenseEdit.classList.remove('add');
            licenseEditForm.reset();
        })
        .catch(err => {
            console.error("오류 발생:", err);
        });

})


// 자격증 삭제
document.querySelector(".license-list").addEventListener("submit", function (event) {
    if (event.target.id === "licenseDeleteBtn") {
        event.preventDefault(); // 중요! 기본 submit 막기

        const confirmed = confirm("정말 삭제하시겠습니까?");
        if (!confirmed) return;

        const licenseCard = event.target.closest(".license-card");
        const licenseId = licenseCard.querySelector('input[name="licenseId"]').value;
        // const userNum = YOUR_USER_NUM_VALUE; // 필요 시 정의된 값 넣기

        const data = {
            licenseId: licenseId
        };

        fetch("/user/licenseDelete", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)
        })
            .then(res => {
                if (!res.ok) throw new Error("서버 응답 실패");
                return res.json();
            })
            .then(result => {
                console.log("삭제 완료", result);
                licenseCard.remove(); // 삭제 성공 시 카드 제거
            })
            .catch(err => {
                console.error("삭제 중 에러:", err);
                alert("삭제 실패");
            });
    }
});

// 사진 추가
document.getElementById('my-picture-input').addEventListener('change', function (event) {
    const file = event.target.files[0];
    if (!file) return;

    // 미리보기 보여주기
    const preview = document.getElementById('preview-image');
    if (preview) {
        preview.src = URL.createObjectURL(file);
        preview.style.display = 'block';  // 미리보기 이미지를 보이게 함
    }

    // hidden input에서 userNum 값을 가져오기
    const userNum = document.getElementById("pictureUserNum").value;
    const resumeId = document.getElementById("pictureResumeId").value;

    console.log("userNum>>>>"+userNum);
    console.log("resumeId>>>>>>>"+resumeId);
    // AJAX로 파일 업로드
    const formData = new FormData();
    formData.append("photo", file);
    formData.append("userNum", userNum);
    formData.append("resumeId", resumeId);

    fetch("/user/uploadPhoto", {
        method: "POST",
        body: formData
    })
        .then(res => res.json())
        .then(data => {
            if (data.success) {
                alert("업로드 성공!");
                // 필요하면 data.photoPath 등을 resumeVO에 저장하도록 처리
            } else {
                alert("업로드 실패!");
            }
        })
        .catch(err => {
            console.error("에러:", err);
            alert("업로드 중 에러 발생");
        });
});





document.addEventListener("DOMContentLoaded", function () {
    flatpickr(".custom-date", {
        dateFormat: "Y-m-d",
        disableMobile: true
    });
});

let modifyPencil=document.querySelector(".modifyPencil");
modifyPencil.addEventListener('click',function (){
    location.herf="/user/modify  "
})

var licensePencilList = document.querySelectorAll(".licensePencil");

licensePencilList.forEach(function (licensePencil) {
    licensePencil.addEventListener('click', function () {
        var editLicenseForm = document.querySelector(".edit-license-form");
        editLicenseForm.classList.add("add");
    });
});


document.querySelectorAll('.tag').forEach(tag => {
    tag.addEventListener('click', () => {
        const input = document.getElementById('resume-title');
        input.value = tag.textContent;
        input.focus();
    });
});



    const saveBtn = document.querySelector(".save");
    const popup = document.getElementById("savePopup");

    saveBtn.addEventListener("click", function () {
        popup.classList.toggle("active");
    });

    document.getElementById("saveResumeBtn").addEventListener("click", function () {
        alert("이력서가 저장되었습니다.");
        hiddenForm = document.getElementById("hiddenForm");
        document.getElementById("title2").value = document.getElementById("resume-title").value;
        document.getElementById("growth2").value = document.getElementById("growth").value;
        document.getElementById("studentDay2").value = document.getElementById("student-day").value;
        document.getElementById("prosAndCons2").value = document.getElementById("pros-and-cons").value;
        document.getElementById("aspiration2").value = document.getElementById("aspiration").value;

        popup.style.display = "none";
        hiddenForm.submit();
    });

    // document.getElementById("downloadResumeBtn").addEventListener("click", function () {
    //     alert("이력서가 파일로 저장되었습니다.");
    //     popup.style.display = "none";
    // });
    //


    document.querySelector(".add-edu-form form").addEventListener("submit", function (event) {
    event.preventDefault(); // 새로고침 방지

    const form = event.target;
    const formData = new FormData(form);

    fetch("/user/updateEdu", {
        method: "POST",
        body: formData,
    })
    .then(response => {
        if (!response.ok) throw new Error("서버 응답 실패");
        return response.text();
    })
    .then(() => {
        const schoolName = form.schoolName.value;
        const major = form.major.value;
        const part = form.part.value;
        const degreeType = form.degreeType.value;
        const graduationStatus = form.graduationStatus.value;
        const graduationDate = form.graduationDate.value;


    const newEdu = document.createElement("div");
    newEdu.className = "education flex";
    newEdu.innerHTML = `
    <input type="hidden" name="educationId">
            <div class="school">
                <h4>${schoolName}</h4>
                <p>${major}</p>
            </div>
            <div class="edu-info">
                <div>
                    <span>${part}</span>
                    <span>${degreeType}</span>
                    <span>${graduationStatus || '미입력'}</span>
                </div>
                <p class="graduration">${graduationDate}</p>
            </div>
            <img src="../image/pencil.png" alt="" class="pencil">
            <form method="POST" action="/user/deleteEdu">
                <input type="hidden" name="resumeId" value="${form.resumeId.value}">
                <input type="hidden" name="schoolName" value="${schoolName}">
                <button type="submit" class="delete-btn">
                    <i class="fa-solid fa-x"></i>
                </button>
            </form>
        `;

    document.getElementById("educationList").appendChild(newEdu);


    form.reset();
})
    .catch(error => {
    console.error("에러 발생:", error);
});
});

// 수정 버튼 눌렀을 때
// document.querySelectorAll(".pencil").forEach(btn => {
//     btn.addEventListener("click", function () {
//         const eduDiv = btn.closest(".education");
//         const eduForm = document.getElementById("eduEditForm");
//
//         const schoolName = eduDiv.querySelector("h4").textContent.trim();
//         const major = eduDiv.querySelector("p").textContent.trim();
//         const spans = eduDiv.querySelectorAll(".edu-info span");
//         const part = spans[0].textContent.trim();
//         const degreeType = spans[1].textContent.trim();
//         const graduationStatus = spans[2].textContent.trim();
//         const graduationDate = eduDiv.querySelector(".graduration").textContent.trim().split(" ")[0];
//         const educationId = eduDiv.getAttribute("data-id");
//
//         // 값 넣기
//         eduForm.schoolName.value = schoolName;
//         eduForm.major.value = major;
//         eduForm.part.value = part;
//         eduForm.degreeType.value = degreeType;
//         eduForm.graduationStatus.value = graduationStatus;
//         eduForm.graduationDate.value = graduationDate;
//
//         // hidden input 추가 또는 수정
//         let hiddenInput = eduForm.querySelector('input[name="educationId"]');
//         if (!hiddenInput) {
//             hiddenInput = document.createElement("input");
//             hiddenInput.type = "hidden";
//             hiddenInput.name = "educationId";
//             eduForm.appendChild(hiddenInput);
//         }
//         hiddenInput.value = educationId;
//
//         // 폼 보이기
//         eduForm.closest(".edit-edu-form").style.display = "block";
//     });
// });
//
//
// document.getElementById("educationList").addEventListener("click", function (event) {
//     if (event.target.classList.contains("pencil")) {
//         const btn = event.target;
//         const section = btn.closest('section');
//         const form = section.querySelector('.edit-edu-form');
//         form.classList.add('add');
//
//         const eduDiv = btn.closest('.education');
//
//         if (section.classList.contains('sec03')) {
//             const schoolName = eduDiv.querySelector('.school h4')?.innerText || '';
//             const major = eduDiv.querySelector('.school p')?.innerText || '';
//             const part = eduDiv.querySelector('.edu-info span:nth-child(1)')?.innerText || '';
//             const gradType = eduDiv.querySelector('.edu-info span:nth-child(2)')?.innerText || '';
//             const grad = eduDiv.querySelector('.edu-info span:nth-child(3)')?.innerText || '';
//             const gradDate = eduDiv.querySelector('.graduration')?.innerText.replace(' 졸업', '') || '';
//
//             form.querySelector('.input-school').value = schoolName;
//             form.querySelector('.input-major').value = major;
//             form.querySelector('.completion').value = part;
//             form.querySelector('.level').value = gradType;
//             form.querySelector('.grad').value = grad;
//             form.querySelector('input[name="graduationDate"]').value = gradDate;
//         }
//
//     }
// });
//
//
document.getElementById("educationList").addEventListener("click", function (event) {
    if (event.target.classList.contains("pencil")) {
        const btn = event.target;
        const eduDiv = btn.closest('.education');
        const educationId = eduDiv.getAttribute('data-id');

        const schoolName = eduDiv.querySelector('.school h4')?.innerText.trim() || '';
        const major = eduDiv.querySelector('.school p')?.innerText.trim() || '';
        const part = eduDiv.querySelector('.edu-info span:nth-child(1)')?.innerText.trim() || '';
        const degreeType = eduDiv.querySelector('.edu-info span:nth-child(2)')?.innerText.trim() || '';
        const graduationStatus = eduDiv.querySelector('.edu-info span:nth-child(3)')?.innerText.trim() || '';
        const graduationDate = eduDiv.querySelector('.graduration')?.innerText.replace(' 졸업', '').trim() || '';

        const form = document.querySelector('.edit-edu-form');
        form.classList.add('add'); // 폼 보여주기

        const addEduForm= document.getElementById("addEduForm");
        addEduForm.classList.toggle('add');



        // 값 채워주기
        form.querySelector('input[name="educationId"]').value = educationId;
        form.querySelector('.input-school').value = schoolName;
        form.querySelector('.input-major').value = major;
        form.querySelector('.completion').value = part;
        form.querySelector('.level').value = degreeType;
        form.querySelector('.grad').value = graduationStatus;
        form.querySelector('input[name="graduationDate"]').value = graduationDate;


    }
});

document.getElementById("saveEducationBtn").addEventListener("click", function () {
    const form = document.querySelector(".edit-edu-form");


    const data = {
        educationId: form.querySelector('input[name="educationId"]').value,
        schoolName: form.querySelector('.input-school').value,
        major: form.querySelector('.input-major').value,
        part: form.querySelector('.completion').value,
        degreeType: form.querySelector('.level').value,
        graduationStatus: form.querySelector('.grad').value,
        graduationDate: form.querySelector('input[name="graduationDate"]').value
    };
    console.log(data);
    // AJAX 요청 보내기
    // fetch("/educationEdit.do", {
    //     method: "POST",
    //     headers: {
    //         "Content-Type": "application/json"
    //     },
    //     body: JSON.stringify(data)
    // })
    //     .then(res => {
    //         if (!res.ok) throw new Error("서버 응답 실패");
    //         return res.json();
    //     })
    //     .then(result => {
    //         if (result.success) {
    //             alert("수정 성공!");
    //             location.reload(); // 필요하면 새로고침
    //         } else {
    //             alert("수정 실패: " + result.message);
    //         }
    //     })
    //     .catch(err => {
    //         console.error("오류 발생:", err);
    //         alert("에러 발생!");
    //     });
});
