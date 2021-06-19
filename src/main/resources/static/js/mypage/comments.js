const commentBox = document.querySelector('.comments');

const cri = CommentCriteria;
showList(cri);

if(window.innerHeight >= document.body.offsetHeight){
    cri.page++;
    showList(cri);
}

/* Scroll Down -> Board List Request */
window.addEventListener('scroll', () => {
    let val = window.innerHeight + window.scrollY;

    if(val >= document.body.offsetHeight){
        cri.page++;
        showList(cri);
    }
});



function showList(cri){

    const result = commentModule.getMyComments(cri);

    result.forEach((comment, idx) => {
        console.log(comment);

        let html = '';
        html = `<div class="comment" data-cid="${comment.id}">
                    <div>${comment.title}</div>
                    <div><a href="/board/read?id=${comment.boardId}">${comment.content}</a></div>
                    <div>${comment.nickname}</div>
                    <div>${comment.createdAt}</div>     
                    <hr>
                </div>`

        commentBox.insertAdjacentHTML('beforeend', html);
    });
}