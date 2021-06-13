const reportModule = {
    url: '/reports/api/v1',
    report: function (boardId){

        ajax({
            url: `${this.url}/boards/${boardId}`,
            method: 'POST',
            loadend: (id) => {
                console.log(`${boardId}번 게시글을 신고했습니다. 접수 번호 : ${id}`);
            }
        });
    },
    getReports: function (status){

        let result = null;
        /* 동기 */
        ajax({
            url: status !== undefined? `${this.url}?status=${status}`:`${this.url}`,
            method: 'GET',
            loadend: (json) => {

                result = JSON.parse(json);
            },
            async: false
        });

        return result;
    },
    confirm: function (reportId) {

        ajax({
            url: `${this.url}/${reportId}`,
            method: 'PUT',
            loadend: (id) => {
                console.log(`${id}번 신고글 처리가 완료되었습니다.`);
            }
        });
    }
}