import {likeModule} from '/js/likes/likeModule.js';
import {interestModule} from "/js/interests/interestModule.js";
import {commentModule} from "/js/comments/commentModule.js";

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
const commentSubmit = commentBox.querySelector('.comment-submit');
const commentTextarea = commentBox.querySelector('.comment-textarea');
commentModule.boardId = boardId;

let page = 1;

function pageInit(){
    page = 1;
}

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
/* 댓글리스트 불러오기 */
async function getComments(page){

    const data = await commentModule.getComments(page);

    let html = '';

    data.forEach((comment, idx) => {

        let hasChild = '';

        if(comment.hasChild > 0)
            hasChild = `<div class="comment-children">
                            <button class="btn-child">더보기</button>
                            <div class="children">
        
                            </div>
                        </div>`;

        html += `
            <div class="comment" data-cid="${comment.id}" data-wid="${comment.memberId}">
                <div class="comment-top">
                    <span class="comment-writer">${comment.nickname}</span>
                    <span class="comment-date">${displayedAt(new Date(comment.createdAt))}</span>
                </div>

                <div class="comment-mid">
                    <p class="comment-content">${comment.content}</p>
                </div>

                <div class="comment-bot">
                    <button class="comment-reply">답글</button>
                </div>

                ${hasChild}
            </div>`;
    });

    comments.insertAdjacentHTML('beforeend', html);
}
getComments(page);

/* 댓글 클릭 -> 모달 생성 */
comments.addEventListener('click', function (e) {

    if(e.target.tagName == 'BUTTON') return;

    e.preventDefault();

    /* 가장 가까운 부모댓글(대댓글) 찾기 */
    const target = e.target.closest('.child') || e.target.closest('.comment');
    const cid = target.dataset.cid;
    const writerId = target.dataset.wid;
    console.log(writerId);

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

    if(modalValidate(memberId, writerId)){
        btnModify.style.display = 'block';
        btnDelete.style.display = 'block';
    }
    else{
        btnModify.style.display = 'none';
        btnDelete.style.display = 'none';
    }
    btnRecomment.style.display = 'none';
    btnSave.style.display = 'none';


    modal.style.display = 'block';
});

/* 댓글 작성 */
commentSubmit.addEventListener('click', async () => {

    const content = commentTextarea.value;

    commentTextarea.value = '';

    const result = await commentModule.register(content);

    /* 댓글 리스트 초기화 */
    page = 1;
    comments.innerHTML = '';
    getComments(page);
});

/* 더보기 버튼 클릭 */
comments.addEventListener('click', function (e) {

    if(!e.target.classList.contains('btn-child')) return;

    const target = e.target.closest('.comment');
    const cid = target.dataset.cid;

    /* cid를 upperId로 갖는 대댓글 가져와서 붙이기 */
});

/* (대댓글)답글 버튼 클릭 -> 모달 */
comments.addEventListener('click', (e) => {

    if(!e.target.classList.contains('comment-reply')) return;

    const target = e.target.closest('.comment');
    const cid = target.dataset.cid;

    /* 모달 띄워서 cid에 대댓글 추가 */
    btnRecomment.style.display = 'block';
    btnDelete.style.display = 'none';
    btnModify.style.display = 'none';
    btnSave.style.display = 'none';

    modalCid.value = cid;
    modalBodyContent.readOnly = false;
    modalBodyContent.value = '';
    modalDate.innerText = '';
    modalWriter.innerText = '';

    modal.style.display = 'block';
});

/* 대댓글 작성 버튼 클릭 */
btnRecomment.addEventListener('click', async (e) => {

    const upperId = modalCid.value;
    const content = modalBodyContent.value;

    modalBodyContent.readOnly = true;

    const result = await commentModule.registerRe(content, upperId);

    /* 대댓글 추가하기 (댓글 리스트 초기화 아님) */
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
btnSave.addEventListener('click', async (e) => {

    const content = modal.querySelector('.modal-body-content').value;
    const cid = modal.querySelector('input[name="cid"]').value;
    
    const result = await commentModule.modify(content, cid);

    /* 수정 후 댓글 리스트 초기화 */
    modal.style.display = 'none';
    page = 1;
    comments.innerHTML = '';
    getComments(page);
});

/* Modal 댓글 삭제 */
btnDelete.addEventListener('click', async (e) => {
    const cid = modal.querySelector('input[name="cid"]').value;

    const result = await commentModule.remove(cid);

    modalBodyContent.value = `${result}번 댓글이 삭제되었습니다.`;

    /* 삭제 후 댓글 리스트 초기화 */
    page = 1;
    comments.innerHTML = '';
    getComments(page);
});

/*Modal Close 버튼 Click*/
btnClose.addEventListener('click', () => {
    modal.style.display = 'none';
});
btnCancel.addEventListener('click', () => {
    modal.style.display = 'none';
});
function modalValidate(currentId, writerId){

    return currentId == writerId;
}

function displayedAt(createdAt) {
    const milliSeconds = new Date() - createdAt;
    const seconds = milliSeconds / 1000;

    if (seconds < 60) return `방금 전`;

    const minutes = seconds / 60;

    if (minutes < 60) return `${Math.floor(minutes)}분 전`;

    const hours = minutes / 60;

    if (hours < 24) return `${Math.floor(hours)}시간 전`;

    const days = hours / 24;

    if (days < 7) return `${Math.floor(days)}일 전`;

    const weeks = days / 7;

    if (weeks < 5) return `${Math.floor(weeks)}주 전`;

    const months = days / 30;

    if (months < 12) return `${Math.floor(months)}개월 전`;

    const years = days / 365;

    return `${Math.floor(years)}년 전`;
}