const inputSection = document.querySelector('#input-login');
const loginForm = document.querySelector('#form-login');
const modal = document.querySelector('.modal-container');
const overlay = modal.querySelector('.overlay');
const signup = modal.querySelector('.signup-box');
let agreedWithLocation = false;
inputSection.addEventListener('click', (e) => {
   let target = e.target;
    if (target.classList.contains('btn-sign-in')) {
        loginForm.submit();
    }
    if (target.classList.contains('btn-sign-up')) {
        overlay.classList.remove('dp-none');
        signup.classList.remove('dp-none');
    }
});

overlay.addEventListener('click', (e) => {
    console.log('overlay click');
   let dpNones = modal.querySelectorAll('.modal-item');
    dpNones.forEach(ele => {
        ele.classList.add('dp-none');
    });
});

loginForm.addEventListener('keyup', (e) => {
    if (e.key !== 'Enter') return;
    if (e.target.tagName.toLowerCase() !== 'input') return;
    loginForm.submit();
})

signup.addEventListener('click', (e) => {
   if (!e.target.classList.contains('btn-location-agreement')) return;
   e.preventDefault();

    let isDenied = true;

   const success = () => {
       isDenied = false;
       alert('위치정보수집 동의가 완료되었습니다.');
       document.getElementById('location-agreement').innerText = '동의완료';
       agreedWithLocation = true;
   };
   const error = () => {
       alert('위치정보수집 동의를 완료해주세요.');
       if(isDenied) getAgreement();
   };

   const getAgreement = () => {
       if(navigator.geolocation) {
           alert('위치정보수집 동의창이 뜨면 \'허용\'버튼을 눌러주세요.');
           navigator.geolocation.getCurrentPosition(success, error);
       } else {
           alert('지원하지 않는 브라우저 입니다.');
       }
   };

    getAgreement();

});

window.addEventListener('load', () => {
    const queryString = location.search;
    if (queryString.indexOf('error') != -1) document.querySelector('.invalid').classList.remove('dp-none');
})
