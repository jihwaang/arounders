/*
1) setBoardId로 boardId 세팅
2) 게시글을 좋아요 하고있는지 체크 --> isLike()
*/
/*
Pre-requirement,,,example
init(boardId);

btnLike.addEventListener('click', (e) => {
    e.preventDefault();

    likeValue? likeModule.dislike() : likeModule.like();
    isLike.classList.toggle('like');
    isLike.classList.toggle('dislike');
    likeValue = !likeValue;
});

function init(boardId){
    likeModule.boardId = boardId;
    likeValue = likeModule.isLike();
    isLike.classList.add(likeValue? 'like' : 'dislike');
}
*/
const likeModule = {
    boardId: 0,
    url: '/likes/api/v1',
    setBoardId: (boardId) => {
        this.boardId = boardId;
    },
    getCounts: function (){

        return new Promise((resolve, reject) => {
            ajax({
                url: `${this.url}/boards/${this.boardId}`,
                method: 'GET',
                async: false,
                loadend: (count) => {
                    resolve(count);
                },
                error: (xhr, status, statusText) => {
                    reject(xhr, status, statusText);
                }
            });
        });
    },
    like: function () {

        return new Promise((resolve, reject) => {
            ajax({
                url: `${this.url}/boards/${this.boardId}`,
                method: 'POST',
                loadend: (boardId) => {
                    console.log(`${boardId}번 게시글을 좋아합니다.`);
                    resolve(boardId);
                },
                error: (xhr, status, statusText) => {
                    reject(xhr, status, statusText);
                }
            });
        });
    },
    dislike: function (){

        return new Promise((resolve, reject) => {
            ajax({
                url: `${this.url}/boards/${this.boardId}`,
                method: 'DELETE',
                loadend: (boardId) => {
                    console.log(`${boardId}번 게시글 좋아요를 취소합니다.`);
                    resolve(boardId);
                },
                error: (xhr, status, statusText) => {
                    reject(xhr, status, statusText);
                }
            });
        });
    },
    isLike: function () {

        return new Promise((resolve, reject) => {
            ajax({
                url: `${this.url}/${this.boardId}`,
                method: 'GET',
                async: false,
                loadend: (data) => {
                    resolve(data === 'true');
                },
                error: (xhr, status, statusText) => {
                    reject(xhr, status, statusText);
                }
            });
        });
    }
}