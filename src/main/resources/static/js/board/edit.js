const buttonBox = document.querySelector('.button-box');
const btnToRead = buttonBox.querySelector('.btn-to-read');
const btnRemove = buttonBox.querySelector('.btn-remove');
const actionForm = document.querySelector('#actionForm');

/* Modal */

btnToRead.addEventListener('click', (e) => {
    e.preventDefault();

    location = '/board/read?id=' + boardId;
});

btnRemove.addEventListener('click', (e) => {
   e.preventDefault();

    modalBodyContent.innerText = '정말 삭제하시겠습니까?';
    btnDoRemove.style.display = 'block';
    modal.style.display = 'block';
});

btnDoRemove.addEventListener('click', (e) => {

    e.preventDefault();

    actionForm.submit();
});