const mainTag = document.querySelector('main');

window.addEventListener('resize', resizeMain);

function resizeMain(){
    let headerWidth = header.clientWidth;
    let asideWidth = aside.clientWidth;

    mainTag.style.width = (headerWidth - asideWidth) + 'px';
}
if(platformInfo !== 'M' && aside != null) {
    resizeMain();
}