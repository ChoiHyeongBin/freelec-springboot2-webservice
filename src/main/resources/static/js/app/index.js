var main = {    // index.js만의 유효범위(scope)를 만들어 사용 (다른 파일에서 함수명이 중복될 수 있으므로)
    init : function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
        });
    },
    save : function() {
        var data = {
            title: $('#title').val(),
            author: $('#author').val(),
            content: $('#content').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/posts',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)  // 자바스크립트의 값을 JSON 문자열로 변환
        }).done(function() {    // HTTP 요청이 성공하면 요청한 데이터가 done() 메소드로 전달
            alert('글이 등록되었습니다.');
            window.location.href = '/'; // 글 등록이 성공하면 메인페이지로 이동
        }).fail(function(error) {
            alert(JSON.stringify(error));
        });
    }
};

main.init();