const buttonBox = document.querySelector('.button-box');
const btnToRead = buttonBox.querySelector('.btn-to-read');
const btnRemove = buttonBox.querySelector('.btn-remove');
const actionForm = document.querySelector('#actionForm');

const url = new URL(location.href);
const params = url.searchParams;

const page = params.get('page') || 1;
const category = params.get('category') || 1;
const field = params.get('field') || 'all';
const keyword = params.get('keyword') || null;
const order = params.get('order') || 'desc';
const status = params.get('status') || 'all';
/* Modal */

btnToRead.addEventListener('click', (e) => {
    e.preventDefault();

    location = `/board/read?id=${boardId}&page=${page}&category=${category}&field=${field}&keyword=${keyword}&order=${order}&status=${status}`;
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