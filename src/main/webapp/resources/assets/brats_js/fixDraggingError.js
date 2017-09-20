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
			alert("fail");
		}
	});
});

var next;
var week;

$('#checkFixTypeOfErrors').click(function() {
	next = $("#step option:selected").val();
	if(next == null || next == "") {
		next = "/3rd";
	} else {
		next = "/" + next;
	}

	week = $("#weeks option:selected").val();
	if(week.includes("Final")) {
		next = "";
	}
	
	if($('#weeks').val() == "") {
		alert("작업 주차를 선택하세요.");
		return;
	}
	
	$.ajax({
		url : "checkFixTypeOfErrors.action",
		data : { folderName : $("#weeks option:selected").val(), nextPath : $("#step option:selected").val()},
		method : "post",
		dataType : "json",
		success : function(data, status, xhr) {
			alert("success");
			$('.content').remove();
			for(var i=0; i<data.length; i++) {
				$('<tr><td>'+data[i][0]+'</td><td>'+data[i][1]+'</td><td>'+data[i][2]+'</td><td>'+data[i][3]+'</td>'
						+'<td><button onclick="move(this.id)" id="'+data[i][0]+'" class="move btn search-btn" type="button">'
						+'<i class="fa fa-search"></i></button></td></tr>').appendTo('#contents'); 
			}
		},
		fail : function(data, status, xhr) {
			alert("fail");
		}
	});
});

function move(x) {
	window.open("http://106.255.230.162:61112/#/"+ week + next +"/" +x);
};