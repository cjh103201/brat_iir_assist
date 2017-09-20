$('#textSearch').click(function() {
	$.ajax({
		url : "text.action",
		data : { text : $('#textKeyword').val()},
		method : "get",
		dataType : "json",
		success : function(data, status, xhr) {
			$('.content').remove();
			if(data.length != 0) {
				for(var i=0; i<data.length; i++) {
					$('<tr class="content"><td>'+data[i][0]+'</td><td>'+data[i][1]+'</td><td>'+data[i][2]+'</td><td><button onclick="move(this.id)" id="'+data[i][0]+'" class="move btn search-btn" type="button"><i class="fa fa-search"></i></button></td></tr>').appendTo('#contents');
				}
			} else {
				alert("검색 결과가 없습니다.");
			}
		},
		fail : function(data, status, xhr) {
			alert("다시 시도하세요.");
		}
	});
});

$('#entitySearch').click(function() {
	$.ajax({
		url : "entity.action",
		data : { type : $('#entityType option:selected').val(), text : $('#entityKeyword').val()},
		method : "get",
		dataType : "json",
		success : function(data, status, xhr) {
			$('.content').remove();
			if(data.length != 0) {
				for(var i=0; i<data.length; i++) {
					$('<tr class="content"><td>'+data[i][0]+'</td><td>'+data[i][1]+'</td><td>'+data[i][2]+'</td><td><button onclick="move(this.id)" id="'+data[i][0]+'" class="move btn search-btn" type="button"><i class="fa fa-search"></i></button></td></tr>').appendTo('#contents');
				}
			} else {
				alert("검색 결과가 없습니다.");
			}
		},
		fail : function(data, status, xhr) {
			alert("다시 시도하세요.");
		}
	});
});

$('#eventSearch').click(function() {
	$.ajax({
		url : "event.action",
		data : {  type : $('#evnetType option:selected').val(), text : $('#eventKeyword').val()},
		method : "get",
		dataType : "json",
		success : function(data, status, xhr) {
			$('.content').remove();
			if(data.length != 0) {
				for(var i=0; i<data.length; i++) {
					$('<tr class="content"><td>'+data[i][0]+'</td><td>'+data[i][1]+'</td><td>'+data[i][2]+'</td><td><button onclick="move(this.id)" id="'+data[i][0]+'" class="move btn search-btn" type="button"><i class="fa fa-search"></i></button></td></tr>').appendTo('#contents');
				}
			} else {
				alert("검색 결과가 없습니다.");
			}
		},
		fail : function(data, status, xhr) {
			alert("다시 시도하세요.");
		}
	});
});

function move(x) {
	//alert(week);
	//alert(next);
	window.open("http://106.255.230.162:61112/#/01 Final(5m4w-9m1w)/" +x);
};