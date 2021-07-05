export const commentModule = {
    boardId: 0,
    /* 댓글 작성 */
    register: function (content) {

        return new Promise((resolve, reject) => {
            ajax({
                url:`/comments/api/v1/boards/${this.boardId}`,
                method: 'POST',
                data: JSON.stringify({content: content}),
                contentType: 'application/json',
                loadend: (id) => {
                    console.log(`${id}번 댓글이 작성되었습니다`);
                    resolve(id);
                },
                error: (xhr, status, statusText) => {
                    reject(xhr, status, statusText);
                }
            });
        });
    },
    /* 대댓글 작성 */
    registerRe: function (content, upperId){

        return new Promise((resolve, reject) => {
            ajax({
                url:`/comments/api/v1/boards/${this.boardId}/${upperId}`,
                method: 'POST',
                data: JSON.stringify({content: content}),
                contentType: 'application/json',
                loadend: (id) => {
                    console.log(`${upperId}번 댓글에 ${id}번 대댓글이 작성되었습니다`);
                    resolve(id);
                },
                error: (xhr, status, statusText) => {
                    reject(xhr, status, statusText);
                }
            });
        });
    },
    /* 댓글 삭제 */
    remove: function (commentId){

        return new Promise((resolve, reject) => {
            ajax({
                url: `/comments/api/v1/${commentId}`,
                method: 'DELETE',
                loadend: (id) => {
                    console.log(`${id}번 댓글이 삭제되었습니다.`);
                    resolve(id);
                },
                error: (xhr, status, statusText) => {
                    reject(xhr, status, statusText);
                }
            });
        });
    },
    /* 댓글 수정 */
    modify: function (content, commentId){

        return new Promise((resolve, reject) => {
            ajax({
                url: `/comments/api/v1/${commentId}`,
                method: 'PUT',
                data: JSON.stringify({content: content}),
                contentType: 'application/json',
                loadend: (id) => {
                    console.log(`${id}번 댓글이 수정되었습니다.`);
                    resolve(id);
                },
                error: (xhr, status, statusText) => {
                    reject(xhr, status, statusText);
                }
            });
        });
    },
    /* 특정 댓글 조회 */
    getComment: function (commentId){

        return new Promise((resolve, reject) => {
            ajax({
                url: `/comments/api/v1/${commentId}`,
                method: 'GET',
                loadend: (result) => {
                    const comment = JSON.parse(result);

                    console.log(comment);
                    resolve(comment);
                },
                error: (xhr, status, statusText) => {
                    reject(xhr, status, statusText);
                }
            });
        });
    },
    /* BoardId번 게시글의 댓글 목록 조회 */
    getComments: function (){

        return new Promise((resolve, reject) => {
            ajax({
                url: `/comments/api/v1/boards/${this.boardId}`,
                method: 'GET',
                loadend: (comments) => {
                    const json = JSON.parse(comments);

                    json.forEach((comment, idx) => {
                        console.log(comment);
                    });

                    resolve(json);
                },
                error: (xhr, status, statusText) => {
                    reject(xhr, status, statusText);
                }
            });
        });
    },
    /* 내가 쓴 댓글 목록 조회 */
    getMyComments: function (cri){

        let query = '?';

        query += `page=${cri.page}`;

        return new Promise((resolve, reject) => {
            ajax({
                url: `/mypages/api/v1/comments${query}`,
                method: 'GET',
                async: false,
                loadend: (comments) => {
                    resolve(JSON.parse(comments));
                },
                error: (xhr, status, statusText) => {
                    reject(xhr, status, statusText);
                }
            });
        });
    }
}

export const CommentCriteria = {

    page: 1,
    init: function() {
        this.page = 1;
    },
    set: function (){
        /* NOT_USING */
    }
}