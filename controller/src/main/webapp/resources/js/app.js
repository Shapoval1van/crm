function testAlert(){
    alert("Test Alert")
}


function checkAddCompanyForm() {
    var companyName = document.getElementById("companyName").value;
    var errorMessage = document.getElementById("CompanyErrorMessage");
    var result = true;

    errorMessage.innerText = "";
    console.log("companyName = " + companyName);
    if(companyName == "") {
        errorMessage.innerText = errorMessage.innerText + "Company name should not be empty\n";
        result = false;
    }

    if(document.getElementById("contactName").value == ""
    && (document.getElementById("contactPosition").value != ""
        || document.getElementById("contactPhone").value != ""
        || document.getElementById("contactEmail").value != ""
        || document.getElementById("contactSkype").value != ""
        ) ) {
        errorMessage.innerText = errorMessage.innerText + "Contact name should not be empty\n";
        result = false;
    }

    if(document.getElementById("dealName").value == ""
        && document.getElementById("dealBudget").value != "") {
        errorMessage.innerText = errorMessage.innerText + "Deal name should not be empty\n";
        result = false;
    }

    if(document.getElementById("dealName").value != ""
        && document.getElementById("dealBudget").value == "") {
        errorMessage.innerText = errorMessage.innerText + "Deal budget should not be empty\n";
        result = false;
    }

    return result;
}

var form = document.getElementById('main').children[0].children[0].children[0];
form.onclick = function (event) {
        var target = event.target;
        if (target.tagName != 'INPUT' || !target.getAttribute('value')) return;
        var val = target.getAttribute('value');
        if (val == 'create'){
            document.getElementsByName('select_company')[0].setAttribute('disabled',true);
            document.getElementsByName('name-company')[0].removeAttribute('disabled');
            document.getElementById('phone-company').removeAttribute('disabled');
            document.getElementById('email-company').removeAttribute('disabled');
            document.getElementById('web-company').removeAttribute('disabled');
            document.getElementById('note-company').removeAttribute('disabled');
            document.getElementsByName('select_company')[0].firstElementChild.setAttribute('selected',true);
        }
        else if (val == 'choose') {
            document.getElementsByName('select_company')[0].removeAttribute('disabled');
            document.getElementsByName('name-company')[0].setAttribute('disabled',true);
            document.getElementById('phone-company').setAttribute('disabled',true);
            document.getElementById('email-company').setAttribute('disabled',true);
            document.getElementById('web-company').setAttribute('disabled',true);
            document.getElementById('note-company').setAttribute('disabled',true);
            document.getElementsByName('name-company')[0].value = '';
            document.getElementById('phone-company').value = '';
            document.getElementById('email-company').value = '';
            document.getElementById('web-company').value = '';
            document.getElementById('note-company').value = '';
            document.getElementsByName('select_company')[0].firstElementChild.removeAttribute('selected');
        }
    }
