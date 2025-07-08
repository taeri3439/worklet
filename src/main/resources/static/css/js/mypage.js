
var swiper5 = new Swiper(".mySwiper5", {
    slidesPerView: 3,
    spaceBetween: 30,
    navigation: {
        nextEl: ".swiper-button-next",
        prevEl: ".swiper-button-prev",
    },
    scrollbar: {
        el: ".swiper-scrollbar",
        hide: true,
      },
});

var swiper6 = new Swiper(".mySwiper6", {
    spaceBetween: 30,
    navigation: {
        nextEl: ".swiper-button-next",
        prevEl: ".swiper-button-prev",
    },
    scrollbar: {
        el: ".swiper-scrollbar",
        hide: true,
      },
});

let resumeBtn=document.querySelector(".resumeBtn");
resumeBtn.addEventListener('click',function (){
    location.href="/user/resumeProc";
})

let plus=document.querySelector(".sec03 .plus");
plus.addEventListener('click',function (){
    location.href="/user/resumeProc";
})

let userModify=document.querySelector(".userModify");
userModify.addEventListener('click',function (){
    location.href = "/user/modify";
})


