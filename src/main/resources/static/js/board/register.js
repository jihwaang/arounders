const regForm = document.querySelector('#regForm');
const inputFile = regForm.querySelector('input[type="file"]');
const thumbIdx = regForm.querySelector('input[name="thumbIdx"]');
const btnSubmit = regForm.querySelector('.btn-submit');
const frame = regForm.querySelector('.thumbnail-frame');

let selected = null;

inputFile.addEventListener('input', function (e) {

    frame.innerHTML = '';

    const files = inputFile.files;

    for(let i=0; i<files.length; i++) {
        if (!isImage(files[i])) {
            alert('Image 파일만 업로드 할 수 있습니다.');
            inputFile.value = '';
            return;
        }
    }

    for(let i=0; i<files.length; i++) {
        const reader = new FileReader();

        reader.addEventListener('load', () => {

            const img = `<li><img src="${reader.result}" class="thumbnail"></li>`;

            frame.insertAdjacentHTML('beforeend', img);
        });

        reader.readAsDataURL(files[i]);
    }
});

/* Select Thumbnail Index */
frame.addEventListener('click', function (e) {

    if(e.target.tagName !== 'IMG') return;

    const img = e.target;

    if(selected != null){
        selected.classList.toggle('selected');
    }

    selected = img;
    selected.classList.toggle('selected');

    thumbIdx.value = getThumbIdx(img.parentNode);
    console.log(thumbIdx.value);
});

function getThumbIdx(parent){

    const children = parent.children;

    for(let i=0; i < children.length; i++)
        if(children[i].classList.contains('selected'))
            return i;
}

function isImage(file) {
    return file.type.indexOf('image') >= 0;
}

