function updateRowCounter() {
    let table = document.getElementById('LicenseTable');
    let tBody = table.querySelector('tbody');
    const count = tBody.querySelectorAll('tr').length;
    document.getElementById('rowCounter').innerText = 'Количество записей в таблице: ' + count;
}

document.addEventListener("DOMContentLoaded", function () {
    updateRowCounter();
});