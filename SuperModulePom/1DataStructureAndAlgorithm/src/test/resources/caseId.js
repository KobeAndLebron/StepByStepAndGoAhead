// 格式化输出json字符串
window.onload = generateCaseId("cases");

function generateCaseId(id) {
	var mytable = document.getElementById(id);
	// 从1开始,与CaseManager行为保持一致
    var num = 1; 
	for (var i = 0, rows = mytable.rows.length; i < rows; i++) {
		for (var j = 0, cells = mytable.rows[i].cells.length; j < cells; j++) {
			if(mytable.rows[i].cells[j].getAttribute("attribute") == "caseId"){
				mytable.rows[i].cells[j].innerHTML = num++; 
			}
		}
	}
	
}