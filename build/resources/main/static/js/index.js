document.querySelector("#btn-submit").addEventListener("click",(e) =>{
	e.preventDefault();	//새로고침방지

	let sidoCdNm = document.querySelector("#sidoCdNm").value;
	let sgguCdNm = document.querySelector("#sgguCdNm").value;
	
	console.log(sidoCdNm); 			
	console.log(sgguCdNm);	
	
	getHospital(sidoCdNm,sgguCdNm);
});

let getHospital = async (sidoCdNm,sgguCdNm) =>{
	let response = await fetch(`http://signdev.iptime.org:8000/api/hospital?sidoCdNm=${sidoCdNm}&sgguCdNm=${sgguCdNm}`);
	let responsePasing = await response.json();
	
	console.log(responsePasing);
	setHospital(responsePasing);
	//return false;
};

let setHospital = (responsePasing) => {
	let tbodyHospitalDom = document.querySelector("#tbody-hospital");
	tbodyHospitalDom.innerHTML = "";
	
	//리턴이 필요없으니 forEach로
	responsePasing.forEach((e)=>{
		let trEL = document.createElement("tr");

        let tdEL1 = document.createElement("td");
        let tdEL2 = document.createElement("td");
        let tdEL3 = document.createElement("td");
        let tdEL4 = document.createElement("td");

        tdEL1.innerHTML = e.yadmNm;
        tdEL2.innerHTML = e.pcrPsblYn;
        tdEL3.innerHTML = e.telno;
        tdEL4.innerHTML = e.addr;

        trEL.append(tdEL1);
        trEL.append(tdEL2);
        trEL.append(tdEL3);
        trEL.append(tdEL4);
        tbodyHospitalDom.append(trEL);
	});
};

let setSggucdnm = (responsePasing) => {
	let sgguCdNmDom = document.querySelector("#sgguCdNm");
	sgguCdNmDom.innerHTML = "";
	console.log(responsePasing);
	
	//리턴이 필요없으니 forEach로
	responsePasing.forEach((e)=>{
		let optionEL = document.createElement("option");
		optionEL.text = e;
		sgguCdNmDom.append(optionEL);
	});
};

let getSggucdnm = async(sidoCdNm) => {
	let response = await fetch(`http://signdev.iptime.org:8000/api/sgguCdNm?sidoCdNm=${sidoCdNm}`);
	let responsePasing = await response.json();
	setSggucdnm(responsePasing);
};

let sidoCdNmDom = document.querySelector("#sidoCdNm");
sidoCdNmDom.addEventListener("change",(e)=>{
	console.log(e.target.value);
	let sidoCdNm = e.target.value;
	//백틱 숫자 1옆에 있는 `사용하면 자바스크립트 변수 바인딩 가능
	getSggucdnm(sidoCdNm);
});