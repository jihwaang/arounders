const header = document.querySelector('header');
const btnBox = header.querySelector('.btn-box');
const btnProfile = header.querySelector('.btn-profile');
const btnMenu = header.querySelector('.btn-menu');
const tooltipBox = header.querySelector('.tooltip-box');
const aside = document.querySelector('aside');

const platformInfo = (function () {
    if (navigator.userAgent.indexOf('Mobi') > -1) return 'M';
    return 'P';
})();

let asideStatus = false;

btnBox.addEventListener('click', function(e) {

    if(e.target.className !== 'btn-profile' && platformInfo === 'M')
        tooltipBox.classList.add('dp-none');
});

/* 프로필 아이콘 Click */
btnProfile.addEventListener('click', function(e) {
    tooltipBox.classList.toggle('dp-none');

    if(aside == null) return;

    const top = window.getComputedStyle(document.querySelector('aside')).top;

    console.log(top);

    if(platformInfo !== 'M'){
        if(top === '130px')
            aside.style.top = '430px';
        else   
            aside.style.top = '130px';
    }
});

/* 햄버거 버튼 Click */
btnMenu.addEventListener('click', function(e) {

    toggoleAside();
    blur();
});

window.addEventListener('resize', ()=>{

    if(aside == null) return;

    let headerWidth = header.clientWidth;

    aside.style.right = `calc(calc(100% - ${headerWidth}px)/2)`;
    tooltipBox.style.right = `calc(calc(100% - ${headerWidth}px)/2)`;
});

/* Aside 생길 때, 배경 어둡게 */
function blur(){
    const screen = document.createElement('DIV');

    screen.classList.add('screen');
    document.body.insertAdjacentElement('beforeend', screen);

    screen.addEventListener('click', (e) => {
        toggoleAside();
        screen.remove();
    });
}

function toggoleAside(){
    setTimeout(() => {

        if(!asideStatus)
            showAside();
        else{
            hideAside();
        }

        asideStatus = !asideStatus;
    }, 0);
}

function showAside(){
    aside.style.right = '0';
}
function hideAside(){
    aside.style.right = '-300px';
}
/* PC일 때만 실행되는 right 설정 함수 */
if(platformInfo !== 'M' && aside != null){
    (function setRight(){

        let headerWidth = header.clientWidth;

        aside.style.right = `calc(calc(100% - ${headerWidth}px)/2)`;
        tooltipBox.style.right = `calc(calc(100% - ${headerWidth}px)/2)`;
    })();
}

/* 지황님 코드 */
(function indexInit() {
    const header = document.querySelector('header');
    const searchInput = header.querySelector('#search-input');
    const searchBtn = header.querySelector('#search-btn');
    const cancelBtn = header.querySelector('#cancel-btn');
    const platformInfo = (function () {
      if (navigator.userAgent.indexOf('Mobi') > -1) return 'M';
      return 'P';
    })();
  
    if (platformInfo === 'M') {
      searchBtn.addEventListener('click', () =>
        toggelSearchBar(btnBox, searchInput, searchBtn, cancelBtn)
      );
  
      cancelBtn.addEventListener('click', () =>
        toggelSearchBar(btnBox, searchInput, searchBtn, cancelBtn)
      );
  
      searchInput.addEventListener('blur', () => {
        toggelSearchBar(btnBox, searchInput, searchBtn, cancelBtn);
        searchInput.value = '';
      });
    } else {
      btnBox.classList.toggle('w-100');
      search.classList.toggle('w-80');
      search.classList.remove('mg-left-auto');
      search.classList.add('center');
      searchInput.classList.toggle('dp-none');
      searchInput.classList.toggle('w-100');
    }
  
    searchInput.addEventListener('keyup', (e) => {
      if (e.key.toLowerCase() !== 'enter') return;
      console.log('enter hit');
    });
  })();
  
  function toggelSearchBar(btnBox, searchInput, searchBtn, cancelBtn) {
    searchBtn.classList.toggle('dp-none');
    cancelBtn.classList.toggle('dp-none');
    logo.classList.toggle('dp-none');
    profile.classList.toggle('dp-none');
    menu.classList.toggle('dp-none');
  
    btnBox.classList.toggle('w-100');
    search.classList.toggle('w-100');
    searchInput.classList.toggle('dp-none');
    searchInput.classList.toggle('w-100');
    searchInput.focus();
}
  