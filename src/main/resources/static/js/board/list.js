const filterBox = document.querySelector('.filter-box');

const selectCategory = filterBox.querySelector('select[name="category"]');
const inputKeyword = filterBox.querySelector('input[name="keyword"]');
const selectField = filterBox.querySelector('select[name="field"]');
const btnSearch = filterBox.querySelector('.btn-search');
const btnReset = filterBox.querySelector('.btn-reset');

const boardBox = document.querySelector('.boards');

const cri = BoardCriteria;

// 세션에서 값 가져와 넣기
// cri.region = '';
// cri.cityId = '';
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

/*btnMore.addEventListener('', (e) => {
    cri.page++;
    showList(cri);
});*/

btnSearch.addEventListener('click', (e) => {

    boardBox.innerHTML = '';

    const order = getOrder();
    const status = getStatus();
    const category = selectCategory.value;
    const field = selectField.value;
    const keyword = inputKeyword.value;

    //console.log(`${order}, ${status}, ${category}, ${field}, ${keyword}`);

    cri.init();
    cri.set(order, status, category, field, keyword);

    showList(cri);
});

function getOrder(){
    const orders = filterBox.querySelectorAll('input[name="order"]');

    for(let order of orders)
        if(order.checked)
            return order.value;
}
function getStatus(){
    const statuses = filterBox.querySelectorAll('input[name="status"]');

    for(let status of statuses)
        if(status.checked)
            return status.value;
}
async function showList(cri){

    const result = await boardModule.getList(cri);

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
                    <div><a href="read?id=${board.id}">${board.title}</a></div>
                    <div>${board.writer}</div>
                    <div>${board.regDate}</div>
                    <div><img src="/upload/${board.thumbnailPath}/${board.thumbnailName}"></div>
                    <div>댓글수: ${board.commentCount}</div>
                    <div>좋아요수 :${board.likeCount}</div>
                    <div>관심수 : ${board.interestCount}</div>
                    <hr>
                </div>`

        boardBox.insertAdjacentHTML('beforeend', html);
    });
}