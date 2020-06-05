function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType:"application/json",
        data: JSON.stringify({
            "parentId": questionId,
            "content": content,
            "type": 1
        }),
        success: function (response) {
            if(response.code == 200){
                $("#comment_section").hide();
            }else{
                if(response.code == 2003){
                    var isAccepted = confirm(response.message);
                    if(isAccepted){
                        window.open("https://gitee.com/oauth/authorize?client_id=fcb2ff4472df1dc6c96ebbe86f7deb00bc79ebe96dafdd5eb62fc744eda301df&redirect_uri=http://localhost:8887/callbackGitEE&response_type=code");
                        window.localStorage.setItem("closable",true);
                    }
                }

                alert(response.message);
            }

        },
        dataType: "json"
    })
}