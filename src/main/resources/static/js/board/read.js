import {likeModule} from '/js/likes/likeModule.js';
import {interestModule} from "/js/interests/interestModule.js";


/* Modal */
const modal = document.querySelector('.modal');
const modalBodyContent = modal.querySelector('.modal-body-content');
const modalDate = modal.querySelector('.modal-date');
const modalWriter = modal.querySelector('.modal-writer');
const modalCid = modal.querySelector('input[name="cid"]');

const btnRecomment = modal.querySelector('.btn-recomment');
const btnClose = modal.querySelector('.btn-close');
const btnCancel = modal.querySelector('.btn-cancel');
const btnSave = modal.querySelector('.btn-save');
const btnModify = modal.querySelector('.btn-modify');
const btnDelete = modal.querySelector('.btn-delete');

/* Board */
const board = document.querySelector('.board');

const boardFiles = board.querySelector('.board-files');
const btnLike = board.querySelector('.like');
const btnInterest = board.querySelector('.interest');
const btnBack = document.querySelector('.button-back');

likeModule.boardId = boardId;
interestModule.boardId = boardId;

/* Comment */
const commentBox = document.querySelector('.comment-box');
const comments = commentBox.querySelector('.comments');

/* About Board */
/* 게시글당 좋아요 버튼 */
btnLike.addEventListener('click', async function (e) {

    /* current not like -> like*/
    if(btnLike.classList.contains('btn-like'))
        await likeModule.like();
    /* current like -> not like */
    else
        await likeModule.dislike();

    /* Update value of Likes */
    const likeVal = this.nextElementSibling;
    likeVal.innerText = await likeModule.getCounts();

    /* Toggle Icon */
    btnLike.classList.toggle('btn-like');
    btnLike.classList.toggle('btn-like-on');
});
/* 게시글당 관심 버튼 */
btnInterest.addEventListener('click', async function (e) {

    /* Toggle Interest */
    await interestModule.doInterest();

    /* Update value of Interests */
    const interestVal = this.nextElementSibling;
    interestVal.innerText = await interestModule.getCounts();

    /* Toggle Icon */
    btnInterest.classList.toggle('btn-interest');
    btnInterest.classList.toggle('btn-interest-on');
});

btnBack.addEventListener('click', () => {
    history.back();
});
/* About Board */

/* About Comment */
/* 댓글 클릭 -> 모달 생성 */
comments.addEventListener('click', function (e) {

    if(e.target.tagName == 'BUTTON') return;

    e.preventDefault();

    /* 가장 가까운 부모댓글(대댓글) 찾기 */
    const target = e.target.closest('.child') || e.target.closest('.comment');
    const cid = target.dataset.cid;

    let writer;
    let date;
    let content;

    if(target.classList.contains('child')){
        writer = target.querySelector('.child-writer').innerText;
        date = target.querySelector('.child-date').innerText;
        content = target.querySelector('.child-content').innerText;
    }
    else{
        writer = target.querySelector('.comment-writer').innerText;
        date = target.querySelector('.comment-date').innerText;
        content = target.querySelector('.comment-content').innerText;
    }

    modalCid.value = cid;
    modalBodyContent.readOnly = true;
    modalBodyContent.value = content;
    modalDate.innerText = date;
    modalWriter.innerText = writer;

    btnRecomment.style.display = 'none';
    btnSave.style.display = 'none';
    btnModify.style.display = 'block';
    btnDelete.style.display = 'block';

    modal.style.display = 'block';
});
/* About Comment */

/* Modal */
/* Modal Modify btn */
btnModify.addEventListener('click', (e) => {
    modalBodyContent.readOnly = false;

    btnSave.style.display = 'block';
    btnDelete.style.display = 'none';
    btnModify.style.display = 'none';
});
/* Modal 수정 -> 저장 */
btnSave.addEventListener('click', (e) => {



    modal.style.display = 'none';
});
/*Modal Close 버튼 Click*/
btnClose.addEventListener('click', () => {
    modal.style.display = 'none';
});
btnCancel.addEventListener('click', () => {
    modal.style.display = 'none';
});