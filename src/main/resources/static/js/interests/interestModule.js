export const interestModule = {
    url: `/interests/api/v1`,
    boardId: 0,
    doInterest: function () {
        return new Promise((resolve, reject) => {
            ajax({
                url: `${this.url}/${this.boardId}`,
                method: 'POST',
                data: null,
                loadend: (boardId) => {
                    //console.log(`${boardId}번 게시글을 관심 등록합니다.`);
                    resolve(boardId);
                },
                error: (xhr, status, statusText) => {
                    reject(xhr, status, statusText);
                }
            });
        });
    },
    getCounts: function () {
        return new Promise((resolve, reject) => {
            ajax({
                url: `${this.url}/board/${this.boardId}`,
                method: 'GET',
                loadend: (total) => {
                    resolve(total);
                },
                error: (xhr, status, statusText) => {
                    reject(xhr, status, statusText);
                }
            });
        });
    }
}