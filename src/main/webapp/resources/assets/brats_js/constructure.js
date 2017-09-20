$('#weeks').change(function() {
	$.ajax({
		url : "folderList.action",
		data : { folderName : this.value},
		method : "get",
		dataType : "json",
		success : function(data, status, xhr) {
			$(".file").remove();
			$('.st').remove();
			if(data.length != 0) {
				$('#step').html('');
				$.each(data, function(index, data) {
					$('#step').append('<option class="st" value="'+data+'">' + data + '</option>');
				});
				$('.selectpicker').selectpicker('refresh');
			}
		},
		fail : function(data, status, xhr) {
			alert("다시 시도하세요.");
		}
	});
});

var next;
var week;

$('#constructureCheck').click(function() {
	next = $("#step option:selected").val();
	if(next == "" || next.includes("=====")) {
		next = "/3rd";
	} else {
		next = "/" + next;
	}

	week = $("#weeks option:selected").val();
	if(week.includes("Final")) {
		next = "";
	}

	var type = $('#type option:selected').val();
	if(type=="") {
		alert("검사 유형을 선택하세요.");
		return;
	}
	
	if($('#weeks').val() == "") {
		alert("작업 주차를 선택하세요.");
		return;
	}
	
	
	
	if(type == "lack") {
		$.ajax({
			url : "lackConstructureCheck.action",
			data : { folderName : $("#weeks option:selected").val(), nextPath : $("#step option:selected").val()},
			method : "get",
			dataType : "json",
			success : function(data, status, xhr) {
				$('.content').remove();
				$('#connect').remove();
				for(var i=0; i<data.length; i++) {
					$('<tr><td>'+data[i][0]+'</td><td>'+data[i][1]+'</td><td>'+data[i][2]+'</td><td>'+data[i][3]+'</td><td><button onclick="move(this.id)" id="'+data[i][0]+'" class="move btn search-btn" type="button"><i class="fa fa-search"></i></button></td></tr>').appendTo('#contents').attr('class', 'content');
				}
			},
			fail : function(data, status, xhr) {
				alert("다시 시도하세요.");
    			}
		});
	} else {
		$.ajax({
			url : "mismatchConstructureCheck.action",
			data : { folderName : $("#weeks option:selected").val(), nextPath : $("#step option:selected").val()},
			method : "get",
			dataType : "json",
			success : function(data, status, xhr) {
				$('.content').remove();
				$('.a').remove();
				$('<th class="a">문서명</th><th class="a">Line</th><th class="a">속성</th><th class="a" id="connect">연결 속성</th><th class="a">단어</th><th class="a">수정</th>').appendTo('#head');
				for(var i=0; i<data.length; i++) {
					$('<tr><td>'+data[i][0]+'</td><td>'+data[i][1]+'</td><td>'+data[i][2]+'</td><td>'+data[i][3]+'</td><td>'+data[i][4]+'</td><td><button onclick="move(this.id)" id="'+data[i][0]+'" class="move btn search-btn" type="button"><i class="fa fa-search"></i></button></td></tr>').appendTo('#contents').attr('class', 'content');
				}
			},
			fail : function(data, status, xhr) {
				alert("다시 시도하세요.");
			}
		});
	}
});

function move(x) {
	//alert(week);
	//alert(next);
	window.open("http://106.255.230.162:61112/#/"+ week + next +"/" +x);
};