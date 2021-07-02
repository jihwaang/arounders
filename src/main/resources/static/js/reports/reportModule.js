export const reportModule = {
    url: '/reports/api/v1',
    report: function (boardId){

        return new Promise((resolve, reject) => {
            ajax({
                url: `${this.url}/boards/${boardId}`,
                method: 'POST',
                loadend: (id) => {
                    //console.log(`${boardId}번 게시글을 신고했습니다. 접수 번호 : ${id}`);
                    resolve(id);
                },
                error: (xhr, status, statusText) => {
                    reject(xhr, status, statusText);
                }
            });
        });
    },
    getReports: function (status){

        return new Promise((resolve, reject) => {
            ajax({
                url: status !== undefined? `${this.url}?status=${status}`:`${this.url}`,
                method: 'GET',
                loadend: (json) => {
                    resolve(JSON.parse(json));
                },
                error: (xhr, status, statusText) => {
                    reject(xhr, status, statusText);
                }
            });
        });
    },
    confirm: function (reportId) {

        return new Promise((resolve, reject) => {
            ajax({
                url: `${this.url}/${reportId}`,
                method: 'PUT',
                loadend: (id) => {
                    console.log(`${id}번 신고글 처리가 완료되었습니다.`);
                    resolve(id);
                },
                error: (xhr, status, statusText) => {
                    reject(xhr, status, statusText);
                }
            });
        });
    }
}