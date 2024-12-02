// Обновление счетчика количества записей
function updateRowCounter() {
    let table = document.getElementById('LicenseTable');
    let tBody = table.querySelector('tbody');
    const count = tBody.querySelectorAll('tr').length;
    document.getElementById('rowCounter').innerText = 'Количество записей в таблице: ' + count;
}

// Подсчет лицензий по компаниям
function calculateLicensesPerCompany() {
    let table = document.getElementById('LicenseTable');
    let tBody = table.querySelector('tbody');
    let rows = tBody.querySelectorAll('tr');
    let licenseCount = {};

    rows.forEach(row => {
        let companyName = row.cells[2].innerText; // Индекс 2 соответствует "Компания"
        if (companyName) {
            if (licenseCount[companyName]) {
                licenseCount[companyName]++;
            } else {
                licenseCount[companyName] = 1;
            }
        }
    });

    return licenseCount;
}


function displayHistogram() {
    const licenseCount = calculateLicensesPerCompany();
    const labels = Object.keys(licenseCount);  // Названия компаний
    const values = Object.values(licenseCount); // Количество лицензий

    // Очистить существующий график перед созданием нового
    const canvas = document.getElementById('histogram');
    const ctx = canvas.getContext('2d');
    if (window.currentChart) {
        window.currentChart.destroy(); // Уничтожить старую диаграмму, если она существует
    }

    window.currentChart = new Chart(ctx, {
        type: 'pie', // Круговая диаграмма
        data: {
            labels: labels,
            datasets: [{
                data: values,
                backgroundColor: [
                    'rgba(255, 99, 132, 0.2)',
                    'rgba(54, 162, 235, 0.2)',
                    'rgba(255, 206, 86, 0.2)',
                    'rgba(75, 192, 192, 0.2)',
                    'rgba(153, 102, 255, 0.2)',
                    'rgba(255, 159, 64, 0.2)'
                ],
                borderColor: [
                    'rgba(255, 99, 132, 1)',
                    'rgba(54, 162, 235, 1)',
                    'rgba(255, 206, 86, 1)',
                    'rgba(75, 192, 192, 1)',
                    'rgba(153, 102, 255, 1)',
                    'rgba(255, 159, 64, 1)'
                ],
                borderWidth: 1
            }]
        },
        options: {
            plugins: {
                legend: {
                    position: 'right' // Легенда справа
                }
            }
        }
    });
}

// Инициализация всех функций после загрузки страницы
document.addEventListener("DOMContentLoaded", function () {
    updateRowCounter();
    displayHistogram();
});