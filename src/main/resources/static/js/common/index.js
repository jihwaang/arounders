const inputSection = document.querySelector('.input-form');
const form = document.querySelector('#form');
inputSection.addEventListener('click', (e) => {
   let target = e.target;
    if (target.classList.contains('btn-sign-in')) {
        form.submit();
    }
    if (target.classList.contains('btn-sign-up')) {
        location.href = '/member/signup';
    }
    return;
});

form.addEventListener('keyup', (e) => {
    if (e.key !== 'Enter') return;
    if (e.target.tagName.toLowerCase() !== 'input') return;
    form.submit();
})