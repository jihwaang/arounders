const commentBox = document.querySelector('.comments');

const cri = CommentCriteria;
showList(cri);

if(window.innerHeight >= document.body.offsetHeight){
    cri.page++;
    showList(cri);
}

/* Scroll Down -> Board List Request */
window.addEventListener('scroll', () => {

    console.log(`window.innerHeight : ${window.innerHeight}`);
    console.log(`window.scrollY : ${window.scrollY}`);
    console.log(`document.body.offsetHeight : ${document.body.offsetHeight}`);
    console.log('----------------------------');
    let val = window.innerHeight + window.scrollY;

    if(val >= document.body.offsetHeight){
        cri.page++;
        showList(cri);
    }
});

async function showList(cri){

    const result = await commentModule.getMyComments(cri);

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