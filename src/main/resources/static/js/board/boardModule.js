const boardModule = {

    getList: function (cri){
        const url = '/boards/api/v1';
        let query = '?';

        query += `page=${cri.page}&`;
        query += `region=${cri.region}&`;
        query += `cityId=${cri.cityId}&`;
        query += `order=${cri.order}&`;
        query += `category=${cri.category}&`;
        if(cri.status !== 'all')
            query += `status=${cri.status}&`;
        if(cri.field !== 'all')
            query += `field=${cri.field}&keyword=${cri.keyword}`;

        return new Promise((resolve, reject) => {
            ajax({
                url: url + query,
                method: 'GET',
                async: false,
                loadend: (json) => {
                    //console.log(boards);
                    resolve(JSON.parse(json));
                }
            });
        });
    },
    getMyList: function (cri){

        const url = '/mypages/api/v1/boards';
        let query = '?';

        /* mypage -> 내가 쓴 게시글 조회시, category별 조회만 가능(필터기능 X) */
        query += `page=${cri.page}&`;
        query += `category=${cri.category}`;

        return new Promise((resolve, reject) => {
            ajax({
                url: url + query,
                method: 'GET',
                async: false,
                loadend: (json) => {
                    //console.log(boards);
                    resolve(JSON.parse(json));
                }
            });
        });
    }
}

const BoardCriteria = {

    page: 1,
    cityId: 1, //test용
    region: '용산구', //test용
    order: 'desc',
    status: 'all',
    category: 1,
    keyword: null,
    field: 'all',
    init: function() {
        /* region, cityId는 초기 설정 후 건들지 ㄴㄴ */
        this.page = 1;
        this.order = 'desc';
        this.status = 'all';
        this.category = 1;
        this.keyword = null;
        this.field = 'all';
    },
    set: function (order, status, category, field, keyword){
        this.order = order || 'desc';
        this.status = status || 'all';
        this.category = category || 1;
        this.keyword = keyword || null;
        this.field = field || 'all';
    }
}