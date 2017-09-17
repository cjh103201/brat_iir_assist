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
