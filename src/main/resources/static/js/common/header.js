const header = document.querySelector('header');
const btnBox = header.querySelector('.btn-box');
const btnProfile = header.querySelector('.btn-profile');
const btnMenu = header.querySelector('.btn-menu');
const tooltipBox = header.querySelector('.tooltip-box');
const aside = document.querySelector('aside');
const btnLogout = document.querySelector('.btn-logout');
const logoutActionForm = document.querySelector('#logout-action-form');
const mainTag = document.querySelector('main');
const logo = header.querySelector('#logo');
const profile = header.querySelector('#profile');
const search = header.querySelector('#search');
let asideStatus = false;

/* 프로필 아이콘 클릭 (완) */
btnBox.addEventListener('click', function(e) {

    if(e.target.className !== 'btn-profile')
        tooltipBox.classList.add('dp-none');
});

/* 프로필 아이콘 Click (완) */
btnProfile.addEventListener('click', function(e) {

    if(tooltipBox.classList.contains('dp-none'))
        tooltipBox.classList.remove('dp-none');
    else
        tooltipBox.classList.add('dp-none');

    if(aside == null) return;

    const top = window.getComputedStyle(document.querySelector('aside')).top;

    //console.log(top);

    /* PC에서는 메인의 너비가 100%가 아님 ==> Mobile에서는 aside의 탑이 변경 X */
    if(mainTag.style.width != '100%') {
        if (top === '130px')
            aside.style.top = '430px';
        else
            aside.style.top = '130px';
    }
});
/* resize시 init (완) */
window.addEventListener('resize', () => {
    let headerWidth = header.clientWidth;

    tooltipBox.classList.add('dp-none');

    /* moblie */
    if(headerWidth < 800){
        aside.style.top = '0';
        aside.style.right = '-300px';

        /* 모바일에서 검색창 활성화 상태에서 가로크기 축소시 로고, 프로필 영역 숨김 처리 추가 */
        if (!header.querySelector('#cancel-btn').classList.contains('dp-none') && !logo.classList.contains('dp-none') && !profile.classList.contains('dp-none')) {
            logo.classList.add('dp-none');
            profile.classList.add('dp-none');
        }
        /* 검색폼 길이 조절: 모바일에선 100% */
        search.classList.replace('w-80', 'w-100');

    }
    /* pc */
    else{
        aside.style.top = '130px';
        aside.style.right = '0';
        /* 모바일에서 검색창 활성화 상태에서 가로크기 확장시 로고, 프로필 영역 표시 처리 추가 */
        if (logo.classList.contains('dp-none') && profile.classList.contains('dp-none')) {
            logo.classList.remove('dp-none');
            profile.classList.remove('dp-none');
        }
        /* 검색폼 길이 조절: PC에선 80% */
        search.classList.replace('w-100', 'w-80');
    }
});

window.addEventListener('resize', resizeMain);
window.addEventListener('resize', setRight);

/* 메인 크기가 어사이드가 차지하는 만큼 줄어야함 (완) */
function resizeMain(){

    let headerWidth = header.clientWidth;
    let asideWidth = aside.clientWidth;

    if(!aside || headerWidth < 800) {
        mainTag.style.width = '100%';
        return;
    }

    mainTag.style.width = (headerWidth - asideWidth) + 'px';
}
resizeMain();

/* aside와 tooltipBox의 절대위치가 윈도우 크기에 따라 변경되야함 (완) */
/* PC일 때만 실행되는 right 설정 함수 */
function setRight(){

    if(!aside) return;
    if(mainTag.style.width === '100%'){
        aside.style.right = '-300px';
        return;
    }

    let headerWidth = header.clientWidth;

    aside.style.right = `calc(calc(100% - ${headerWidth}px)/2)`;
    tooltipBox.style.right = `calc(calc(100% - ${headerWidth}px)/2)`;
}
setRight();

/* (Moblie) 햄버거 버튼 Click (완) */
btnMenu.addEventListener('click', function(e) {

    toggoleAside();
    blur();
});
/* (Mobile) Aside 생길 때, 배경 어둡게 (완) */
function blur(){
    const screen = document.createElement('DIV');

    screen.classList.add('screen');
    document.body.insertAdjacentElement('beforeend', screen);

    screen.addEventListener('click', (e) => {
        toggoleAside();
        screen.remove();
    });
}
/*
    (Mobile)
    Aside가 나왔다 사라졌다하는 함수
    asideStatus == false일 때 클릭 -> show
    asideStatus == true일 때 클릭 -> hide
    asideStatus는 toggle (완)
*/
function toggoleAside(){
    setTimeout(() => {

        if(!asideStatus) showAside();
        else hideAside();

        asideStatus = !asideStatus;
    }, 0);
}
//(Mobile) 완
function showAside(){
    aside.style.right = '0';
}
//(Mobile) 완
function hideAside(){
    let headerWidth = header.clientWidth;

    if(headerWidth >= 800) return;

    aside.style.right = '-300px';
}

/* 지황님 코드 */
const searchInput = header.querySelector('#search-input'); //검색 바
const searchBtn = header.querySelector('#search-btn'); //돋보기 아이콘
const cancelBtn = header.querySelector('#cancel-btn'); //x버튼

searchBtn.addEventListener('click', () =>
    toggelSearchBar(btnBox, searchInput, searchBtn, cancelBtn)
);

cancelBtn.addEventListener('click', () => {
        toggelSearchBar(btnBox, searchInput, searchBtn, cancelBtn)
});

// searchInput.addEventListener('blur', () => {
//     toggelSearchBar(btnBox, searchInput, searchBtn, cancelBtn);
//     searchInput.value = '';
// });

(function indexInit() {

    const headerWidth = header.clientWidth;
    console.log(headerWidth);
    if(headerWidth >= 800){
        search.classList.replace('w-100', 'w-80');
        //btnBox.classList.toggle('w-100');
        //search.classList.toggle('w-80');
        //search.classList.remove('mg-left-auto');
        //search.classList.add('center');
        //searchInput.classList.toggle('dp-none');
        //searchInput.classList.toggle('w-100');
    }
})();

function toggelSearchBar(btnBox, searchInput, searchBtn, cancelBtn) {
    searchBtn.classList.toggle('dp-none');
    cancelBtn.classList.toggle('dp-none');
    logo.classList.toggle('dp-none');
    profile.classList.toggle('dp-none');
    //menu.classList.toggle('dp-none');

    //btnBox.classList.toggle('w-100');
    //search.classList.toggle('w-100');
    searchInput.classList.toggle('vb-visible');
    //searchInput.classList.toggle('w-100');
    searchInput.focus();
}

/* Header Search Form */
searchInput.addEventListener('keydown', (e) => {

    if (e.key.toLowerCase() !== 'enter') return;

    const keyword = e.target.value;

    location = `/board/list?field=tc&keyword=${keyword}`;
});

/* logout */
btnLogout.addEventListener('click', (e) => {

    e.preventDefault();

    logoutActionForm.submit();
});