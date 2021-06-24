const boardBox = document.querySelector('.boards');
const categoryList = document.querySelector('.category-list');
const categoryKey = categoryList.querySelector('.category-key');

const cri = BoardCriteria;

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

categoryList.addEventListener('click', (e) => {

    if(!e.target.classList.contains('category-key')) return;

    boardBox.innerHTML = '';
    const category = e.target.dataset['id'];

    cri.init();
    cri.set(null, null, category, null, null);
    showList(cri);
});

async function showList(cri){

    const result = await boardModule.getMyList(cri);

    /*if(result.length == 0) {
        alert('마지막 페이지입니다.');
        return;
    }*/

    result.forEach((board, idx) => {
        console.log(board);

        let html = '';
        html = `<div class="board">
                    <span>${board.category}</span>
                    <span>${board.region}</span>
                    <span>${board.status == '0'? '미완료':'완료'}</span>
                    <div><a href="/board/read?id=${board.id}">${board.title}</a></div>
                    <div>${board.writer}</div>
                    <div>${board.createdAt}</div>
                    <div><img src="/upload/${board.thumbnailPath}/${board.thumbnailName}"></div>
                    <div>댓글수: ${board.commentCount}</div>
                    <div>좋아요수 :${board.likeCount}</div>
                    <div>관심수 : ${board.interestCount}</div>
                    <hr>
                </div>`

        boardBox.insertAdjacentHTML('beforeend', html);
    });
}