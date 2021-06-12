/*
ajax({
	method: 'GET',
	url: '/replies/10/1',
	contentType: 'application/json',
	data: JSON.Stringify({data: 'data', id: 10}),
	loadend: (result) => {
		console.log(data);
	},
	load: () => {
		console.log('load !!');
	},
	error: (xhr, status, statusText) => {
		console.log(status);
		console.log(statusText);
	},
	progress: () => {
		console.log('progress');
	}
});
*/
function ajax(obj){

    const xhr = new XMLHttpRequest();

    var method = obj.method || 'GET';
    var url = obj.url || '';
    var data = obj.data || null;

    /* 성공/에러 */
    xhr.addEventListener('load', function() {

        const data = xhr.responseText;

        if(obj.load)
            obj.load(data);
    });

    /* 성공 */
    xhr.addEventListener('loadend', function() {

        const data = xhr.responseText;

        //console.log(data);


        if(obj.loadend)
            obj.loadend(data);
    });

    /* 실패 */
    xhr.addEventListener('error', function() {

        console.log('Ajax 중 에러 발생 : ' + xhr.status + ' / ' + xhr.statusText);

        if(obj.error){
            obj.error(xhr, xhr.status, xhr.statusText);
        }
    });

    /* 중단 */
    xhr.addEventListener('abort', function() {

        if(obj.abort){
            obj.abort(xhr);
        }
    });

    /* 진행 */
    xhr.upload.addEventListener('progress', function(e) {

        if(obj.progress){
            obj.progress(e);
        }
    });

    /* 요청 시작 */
    xhr.addEventListener('loadstart', function() {

        if(obj.loadstart)
            obj.loadstart(xhr);
    });

    if(obj.async === false)
        xhr.open(method, url, obj.async);
    else
        xhr.open(method, url, true);


    if(obj.contentType)
        xhr.setRequestHeader('Content-Type', obj.contentType);

    xhr.send(data);
}