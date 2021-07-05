const inputSection = document.querySelector('#input-login');
const loginForm = document.querySelector('#form-login');
const modal = document.querySelector('.modal-container');
const overlay = modal.querySelector('.overlay');
const signup = modal.querySelector('.signup-box');
inputSection.addEventListener('click', (e) => {
   let target = e.target;
    if (target.classList.contains('btn-sign-in')) {
        loginForm.submit();
    }
    if (target.classList.contains('btn-sign-up')) {
        overlay.classList.remove('dp-none');
        signup.classList.remove('dp-none');
    }
    return;
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

