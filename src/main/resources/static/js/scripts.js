function updateRowCounter() {
    const table = document.getElementById('LicenseTable');
    if (!table) return;
    const tBody = table.querySelector('tbody');
    if (!tBody) return;
    const count = tBody.querySelectorAll('tr').length;
    const counter = document.getElementById('rowCounter');
    if (counter) counter.innerText = 'Количество записей в таблице: ' + count;
}

document.addEventListener("DOMContentLoaded", function () {
    updateRowCounter();
});
